package cluster.util;

import cluster.base.BaseCluster;
import cluster.base.BasePoint;
import java.util.List;

/**
 * Created by yidxue on 2018/4/7
 */
public class DistanceCompute {

    private final static String CLUATSER_MAX = "max";
    private final static String CLUATSER_MIN = "min";
    private final static String CLUATSER_AVG = "avg";

    /**
     * 两个点的欧式距离
     */
    public double getEuclideanDis(BasePoint p1, BasePoint p2) {
        double countDis = 0;
        float[] p1LocalArray = p1.getLocalArray();
        float[] p2LocalArray = p2.getLocalArray();

        if (p1LocalArray.length != p2LocalArray.length) {
            throw new IllegalArgumentException("length of array must be equal!");
        }

        for (int i = 0; i < p1LocalArray.length; i++) {
            countDis += Math.pow(p1LocalArray[i] - p2LocalArray[i], 2);
        }

        return Math.sqrt(countDis);
    }

    /**
     * 两个类别的距离
     */
    public double getClusterEuclideanDis(BaseCluster cluster1, BaseCluster cluster2, String type) {
        if (!CLUATSER_AVG.equals(type.toLowerCase()) || !CLUATSER_MIN.equals(type.toLowerCase()) || !CLUATSER_MAX.equals(type.toLowerCase())) {
            throw new IllegalArgumentException("type must be max, min or avg !");
        }

        double dis;
        switch (type.toLowerCase()) {
            case "min":
                dis = getClusterMaxOrMinDis(cluster1, cluster2)[0];
                break;
            case "max":
                dis = getClusterMaxOrMinDis(cluster1, cluster2)[1];
                break;
            case "avg":
                dis = getEuclideanDis((BasePoint) cluster1.getCenter(), (BasePoint) cluster2.getCenter());
                break;
            default:
                dis = getEuclideanDis((BasePoint) cluster1.getCenter(), (BasePoint) cluster2.getCenter());
                break;
        }
        return dis;
    }


    private double[] getClusterMaxOrMinDis(BaseCluster cluster1, BaseCluster cluster2) {
        double[] res = {Double.MAX_VALUE, Double.MIN_VALUE};
        List points1 = cluster1.getMembers();
        List points2 = cluster2.getMembers();

        for (Object p1 : points1) {
            for (Object p2 : points2) {
                double dist = getEuclideanDis((BasePoint) p1, (BasePoint) p2);
                res[0] = dist < res[0] ? dist : res[0];
                res[1] = dist > res[1] ? dist : res[1];
            }
        }
        return res;
    }
}
