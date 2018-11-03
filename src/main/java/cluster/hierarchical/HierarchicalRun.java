package cluster.hierarchical;

import cluster.base.BaseModel;
import cluster.hierarchical.bean.HierarchicalCluster;
import cluster.hierarchical.bean.HierarchicalPoint;
import cluster.util.DistanceCompute;

import java.util.*;

/**
 * Created by yidxue on 2018/11/2
 */
public class HierarchicalRun extends BaseModel {
    private int k;
    private double dist;
    private String distType;
    private double globalNearestDistForIter = Integer.MIN_VALUE;

    private int dim;
    /**
     * 用于存放，原始数据集所构建的点集
     */
    private List<HierarchicalPoint> pointList = null;

    /**
     * 用于存放，原始数据集所构建的点集
     */
    private HashMap<Integer, HierarchicalCluster> clusterMap = null;

    /**
     * 用于记录，每一轮最近两个类的距离
     */
    private HashMap<String, Double> clusterDistMap = null;


    private DistanceCompute distanceCompute = new DistanceCompute();

    private List<float[]> originalData;

    public HierarchicalRun(double dist, String distType, List<float[]> originalData) {
        this.dist = dist;
        this.distType = distType;
        this.originalData = originalData;
        check();
        init();
    }

    public HierarchicalRun(int k, String distType, List<float[]> originalData) {
        this.k = k;
        this.distType = distType;
        this.originalData = originalData;
        check();
        init();
    }

    /**
     * 检查规范
     */
    @Override
    public void check() {
//        if (dist <= 0 || k < 1) {
//            throw new IllegalArgumentException("dist must be >= 0 and k must be >=1");
//        }

        if (originalData == null) {
            throw new IllegalArgumentException("program can't get real data");
        }
    }

    /**
     * 初始化数据集，把数组转化为Point类型。
     */
    @Override
    public void init() {
        pointList = new ArrayList<>();
        clusterMap = new HashMap<>();
        dim = originalData.get(0).length;
        for (int i = 0, j = originalData.size(); i < j; i++) {
            HierarchicalPoint point = new HierarchicalPoint(i, originalData.get(i));
            pointList.add(point);
            // 初始时每一个点设为一个类
            List<HierarchicalPoint> members = new ArrayList<>();
            members.add(point);
            clusterMap.put(i, new HierarchicalCluster(i, point.getLocalArray(), members));
        }
    }


    public List<HierarchicalCluster> run() {
        HashMap<Integer, HierarchicalCluster> clusterMapRes = iter(clusterMap);
        return new ArrayList<>(clusterMapRes.values());
    }

    /**
     * 迭代
     */
    public HashMap<Integer, HierarchicalCluster> iter(HashMap<Integer, HierarchicalCluster> clusterMap) {
        System.out.println("globalNearestDistForIter\t" + globalNearestDistForIter);
        if (dist < globalNearestDistForIter || clusterMap.values().size() == 1) {
            return clusterMap;
        } else {
            // 计算最近的两类
            HashMap<Integer, Integer> nearestClusterMap = getNearestClusterMap(clusterMap);

            for (Map.Entry kv : nearestClusterMap.entrySet()) {
                System.out.println(kv.getKey() + "-->" + kv.getValue());
            }
            System.out.println("=====================");

            HashMap<Integer, HierarchicalCluster> clusterMapNew = new HashMap<>();
            HashSet<Integer> iterSet = new HashSet<>();

            int count = 0;
            // 聚合
            for (int cid : nearestClusterMap.keySet()) {
                if (iterSet.contains(cid)) {
                    continue;
                }
                int ccid = nearestClusterMap.get(cid);
                double clusterDist = clusterDistMap.get(cid < ccid ? cid + "_" + ccid : ccid + "_" + cid);

                if (cid == nearestClusterMap.get(ccid) && clusterDist < dist) {
                    HierarchicalCluster cluster = mergeCluster(count, clusterMap.get(cid), clusterMap.get(ccid));
                    iterSet.add(ccid);
                    clusterMapNew.put(count, cluster);
                    if (clusterDist > globalNearestDistForIter) {
                        globalNearestDistForIter = clusterDist;
                    }
                } else {
                    HierarchicalCluster cluster = clusterMap.get(cid);
                    cluster.setId(count);
                    clusterMapNew.put(count, cluster);
                }
                iterSet.add(cid);
                count++;

            }
            return iter(clusterMapNew);
        }
    }


    /**
     * 计算每个类最近的距离类
     */
    private HashMap<Integer, Integer> getNearestClusterMap(Map<Integer, HierarchicalCluster> clusterMap) {
        clusterDistMap = new HashMap<>();
        // 最近的两类
        HashMap<Integer, Integer> nearestClusterMap = new HashMap<>();

        for (HierarchicalCluster cluster1 : clusterMap.values()) {
            int cid1 = cluster1.getId();
            HierarchicalCluster nearestCluster = null;
            double nearestDist = Integer.MAX_VALUE;
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
                    nearestCluster = cluster2;
                }
            }
            nearestClusterMap.put(cid1, nearestCluster.getId());
        }
        return nearestClusterMap;
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
