package cluster.dbscan;

import cluster.base.BaseModel;
import cluster.dbscan.bean.DBScanCluster;
import cluster.dbscan.bean.DBScanPoint;
import cluster.util.DistanceCompute;

import java.util.*;

/**
 * Created by yidxue on 2018/11/1
 */
public class DBScanRun extends BaseModel {

    private double r;
    private int support;
    private List<float[]> originalData;
    /**
     * 用于存放核心对象
     */
    private HashMap<String, HashSet<DBScanPoint>> corePointMemberMap;
    /**
     * 用于存放所有的点
     */
    private HashMap<String, DBScanPoint> pointMap = null;
    /**
     * 用于存放核心对象的id
     */
    private List<String> corePointIDList;

    /**
     * 所有样本对象之间的距离
     */
    private HashMap<String, Double> pointDistMap = new HashMap<>();

    public DBScanRun(double r, int support, List<float[]> originalData) {
        this.r = r;
        this.support = support;
        this.originalData = originalData;
        this.corePointMemberMap = new HashMap<>();
        this.corePointIDList = new ArrayList<>();
        //检查规范
        check();
        //初始化点集。
        init();
    }

    /**
     * 检查规范
     */
    @Override
    public void check() {
        if (r <= 0 || support < 1) {
            throw new IllegalArgumentException("r must be >= 0 and num must be >=1");
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
        pointMap = new HashMap<>();
        for (int i = 0, j = originalData.size(); i < j; i++) {
            pointMap.put(String.valueOf(i), new DBScanPoint(i, originalData.get(i)));
        }
    }

    /**
     * 运行 DBScan
     */
    public List<DBScanCluster> run() {
        // 计算得到核心对象以及样本点之间的距离
        calculateCorePoint();
        // 得到核心对象聚合后的集合
        HashSet<HashSet<String>> corePointClusterSet = getCorePointCluster();
        // 聚类
        return cluster(corePointClusterSet);
    }

    /**
     * 返回所有核心对象
     */
    public HashSet<DBScanPoint> getCorePoints() {
        HashSet<DBScanPoint> corePointSet = new HashSet<>();
        for (String corePointID : corePointIDList) {
            corePointSet.add(pointMap.get(corePointID));
        }
        return corePointSet;
    }

    /**
     * 聚类
     */
    private List<DBScanCluster> cluster(HashSet<HashSet<String>> corePointClusterSet) {
        List<DBScanCluster> list = new ArrayList<>();
        int i = 1;
        for (HashSet<String> corePointCluster : corePointClusterSet) {
            DBScanCluster cluster = new DBScanCluster(i);
            HashSet<DBScanPoint> members = new HashSet<>();
            for (String pointID : corePointCluster) {
                members.addAll(corePointMemberMap.get(pointID));
                cluster.addCorePoint(pointMap.get(pointID));
            }

            cluster.setMembers(new ArrayList<>(members));
            list.add(cluster);
            i++;
        }
        return list;
    }


    /**
     * 得到所有核心对象
     */
    private void calculateCorePoint() {
        DistanceCompute distanceCompute = new DistanceCompute();
        for (DBScanPoint point1 : pointMap.values()) {
            HashSet<DBScanPoint> members = new HashSet<>();
            members.add(point1);
            int id1 = point1.getId();
            for (DBScanPoint point2 : pointMap.values()) {
                int id2 = point2.getId();
                if (id1 == id2) {
                    continue;
                }
                String pointTag = id1 < id2 ? id1 + "_" + id2 : id2 + "_" + id1;
                double dist;
                if (!pointDistMap.containsKey(pointTag)) {
                    dist = distanceCompute.getPointEuclideanDis(point1, point2);
                    pointDistMap.put(pointTag, dist);
                } else {
                    dist = pointDistMap.get(pointTag);
                }

                if (dist < r) {
                    members.add(point2);
                }
            }
            if (members.size() >= this.support) {
                point1.setIsCore(true);
                corePointMemberMap.put(String.valueOf(point1.getId()), members);
                corePointIDList.add(String.valueOf(point1.getId()));
            }
        }
    }

    /**
     * 得到核心对象聚合后的集合
     */
    private HashSet<HashSet<String>> getCorePointCluster() {
        HashMap<String, String> pidTocid = new HashMap<>();
        HashMap<String, HashSet<String>> cidTopid = new HashMap<>();

        int count = 1;
        for (Map.Entry<String, Double> entry : pointDistMap.entrySet()) {
            String[] ids = entry.getKey().split("_");
            if (!corePointIDList.contains(ids[0]) || !corePointIDList.contains(ids[1])) {
                continue;
            }
            if (entry.getValue() < r) {
                if (pidTocid.containsKey(ids[0]) || pidTocid.containsKey(ids[1])) {
                    String cid1 = pidTocid.get(ids[0]);
                    String cid2 = pidTocid.get(ids[1]);

                    if (cid1 == null) {
                        pidTocid.put(ids[0], cid2);
                        cidTopid.get(cid2).add(ids[0]);
                    }

                    if (cid2 == null) {
                        pidTocid.put(ids[1], cid1);
                        cidTopid.get(cid1).add(ids[1]);
                    }

                    if (cid1 != null && cid2 != null && !cid1.equals(cid2)) {
                        String keepCid = Integer.parseInt(cid1) < Integer.parseInt(cid2) ? cid1 : cid2;
                        String removeCid = Integer.parseInt(cid1) > Integer.parseInt(cid2) ? cid1 : cid2;

                        HashSet<String> pidSet = cidTopid.get(removeCid);
                        for (String pid : pidSet) {
                            pidTocid.put(pid, keepCid);
                        }

                        cidTopid.get(keepCid).addAll(cidTopid.get(removeCid));
                        cidTopid.remove(removeCid);
                    }

                } else {
                    pidTocid.put(ids[0], String.valueOf(count));
                    pidTocid.put(ids[1], String.valueOf(count));

                    HashSet<String> set = new HashSet<>();
                    set.add(ids[0]);
                    set.add(ids[1]);

                    cidTopid.put(String.valueOf(count), set);
                    count++;
                }
            }
        }
        return new HashSet<>(cidTopid.values());
    }
}
