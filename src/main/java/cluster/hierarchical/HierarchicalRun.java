package cluster.hierarchical;

import cluster.base.BaseModel;
import cluster.hierarchical.bean.HierarchicalCluster;
import cluster.hierarchical.bean.HierarchicalPoint;
import cluster.util.Constant;
import cluster.util.DistanceCompute;

import java.util.*;

/**
 * Created by yidxue on 2018/11/2
 */
public class HierarchicalRun extends BaseModel {
    /**
     * 距离类型。类平均距离？类间最大距离？类间最小距离？
     */
    private String distType;

    /**
     * 记录数据维度
     */
    private int dim;
    /**
     * 用于存放原始数据集
     */
    private List<float[]> originalData;

    /**
     * 用于存放类别
     */
    private HashMap<Integer, HierarchicalCluster> clusterMap = null;

    /**
     * 层次聚类类别
     */
    private String type;

    private int k;
    private double dist;
    private double globalNearestDistForIter = Integer.MIN_VALUE;


    public HierarchicalRun(double dist, String distType, List<float[]> originalData) {
        this.dist = dist;
        this.distType = distType;
        this.originalData = originalData;
        this.type = "bydist";
        check();
        init();
    }

    public HierarchicalRun(int k, String distType, List<float[]> originalData) {
        this.k = k;
        this.distType = distType;
        this.originalData = originalData;
        this.type = "byknum";
        check();
        init();
    }

    /**
     * 检查规范
     */
    @Override
    public void check() {
        if (dist <= 0 && Constant.HIERARCHICAL_DIST.equals(this.type)) {
            throw new IllegalArgumentException("dist must be >0");
        }

        if (k <= 0 && Constant.HIERARCHICAL_KNUM.equals(this.type)) {
            throw new IllegalArgumentException("k must be >0");
        }

        if (originalData == null) {
            throw new IllegalArgumentException("program can't get real data");
        }
    }

    /**
     * 初始化数据集，把数组转化为Point类型。
     */
    @Override
    public void init() {
        clusterMap = new HashMap<>();
        dim = originalData.get(0).length;
        for (int i = 0, j = originalData.size(); i < j; i++) {
            HierarchicalPoint point = new HierarchicalPoint(i, originalData.get(i));
            // 初始时每一个点设为一个类
            List<HierarchicalPoint> members = new ArrayList<>();
            members.add(point);
            clusterMap.put(i, new HierarchicalCluster(i, point.getLocalArray(), members));
        }
    }


    public List<HierarchicalCluster> run() {
        HashMap<Integer, HierarchicalCluster> clusterMapRes;
        if (Constant.HIERARCHICAL_DIST.equals(this.type)) {
            clusterMapRes = iterByDist(clusterMap);
        } else {
            clusterMapRes = iterByKNum(clusterMap);
        }
        return new ArrayList<>(clusterMapRes.values());
    }

    /**
     * 根据类别个数迭代
     */
    private HashMap<Integer, HierarchicalCluster> iterByKNum(HashMap<Integer, HierarchicalCluster> clusterMap) {
        if (clusterMap.values().size() <= k) {
            return clusterMap;
        } else {
            // 计算最近的两类
            String[] nearClusterPair = getNearestClusterMap(clusterMap);
            HashMap<Integer, HierarchicalCluster> clusterMapNew = new HashMap<>();
            int count = 0;
            // 聚合
            int cid1 = Integer.parseInt(nearClusterPair[0]);
            int cid2 = Integer.parseInt(nearClusterPair[1]);
            HierarchicalCluster mergerCluster = mergeCluster(count, clusterMap.get(cid1), clusterMap.get(cid2));
            clusterMapNew.put(count, mergerCluster);
            clusterMap.remove(cid1);
            clusterMap.remove(cid2);
            count++;

            for (HierarchicalCluster cluster : clusterMap.values()) {
                cluster.setId(count);
                clusterMapNew.put(count, cluster);
                count++;
            }
            return iterByKNum(clusterMapNew);
        }
    }


    /**
     * 根据距离迭代
     */
    private HashMap<Integer, HierarchicalCluster> iterByDist(HashMap<Integer, HierarchicalCluster> clusterMap) {
        if (dist < globalNearestDistForIter || clusterMap.values().size() == 1) {
            return clusterMap;
        } else {
            // 计算最近的两类
            String[] nearClusterPair = getNearestClusterMap(clusterMap);
            HashMap<Integer, HierarchicalCluster> clusterMapNew = new HashMap<>();
            int count = 0;
            // 聚合
            int cid1 = Integer.parseInt(nearClusterPair[0]);
            int cid2 = Integer.parseInt(nearClusterPair[1]);
            double nearestDist = Double.parseDouble(nearClusterPair[2]);

            if (nearestDist < dist) {
                HierarchicalCluster mergerCluster = mergeCluster(count, clusterMap.get(cid1), clusterMap.get(cid2));
                clusterMapNew.put(count, mergerCluster);
                clusterMap.remove(cid1);
                clusterMap.remove(cid2);
                if (nearestDist > globalNearestDistForIter) {
                    globalNearestDistForIter = nearestDist;
                }
                count++;
                for (HierarchicalCluster cluster : clusterMap.values()) {
                    cluster.setId(count);
                    clusterMapNew.put(count, cluster);
                    count++;
                }
                return iterByDist(clusterMapNew);
            } else {
                return clusterMap;
            }
        }
    }


    /**
     * 计算每个类最近的距离类
     */
    private String[] getNearestClusterMap(Map<Integer, HierarchicalCluster> clusterMap) {
        DistanceCompute distanceCompute = new DistanceCompute();
        HashMap<String, Double> clusterDistMap = new HashMap<>();
        double nearestDist = Integer.MAX_VALUE;
        String[] nearClusterPair = new String[3];
        for (HierarchicalCluster cluster1 : clusterMap.values()) {
            int cid1 = cluster1.getId();
            for (HierarchicalCluster cluster2 : clusterMap.values()) {
                int cid2 = cluster2.getId();
                if (cid1 == cid2) {
                    continue;
                }
                String clusterTag = cid1 < cid2 ? cid1 + "_" + cid2 : cid2 + "_" + cid1;
                double dist;
                if (!clusterDistMap.containsKey(clusterTag)) {
                    dist = distanceCompute.getClusterEuclideanDis(cluster1, cluster2, this.distType);
                    clusterDistMap.put(clusterTag, dist);
                } else {
                    dist = clusterDistMap.get(clusterTag);
                }

                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearClusterPair[0] = cid1 < cid2 ? String.valueOf(cid1) : String.valueOf(cid2);
                    nearClusterPair[1] = cid1 > cid2 ? String.valueOf(cid1) : String.valueOf(cid2);
                    nearClusterPair[2] = String.valueOf(nearestDist);
                }
            }
        }

        return nearClusterPair;
    }


    /**
     * 合并两个类
     */
    private HierarchicalCluster mergeCluster(int id, HierarchicalCluster cluster1, HierarchicalCluster cluster2) {
        float[] centre1 = cluster1.getCenter();
        float[] centre2 = cluster2.getCenter();

        if (dim != centre1.length || dim != centre2.length) {
            throw new IllegalArgumentException("error data length !");
        }

        // 添加所有成员
        List<HierarchicalPoint> members = new ArrayList<>();
        members.addAll(cluster1.getMembers());
        members.addAll(cluster2.getMembers());

        float[] avgCentre = new float[dim];
        // 所有点，对应各个维度进行求和
        for (int i = 0; i < dim; i++) {
            avgCentre[i] = (centre1[i] + centre2[i]) / 2;
        }
        return new HierarchicalCluster(id, avgCentre, members);
    }
}
