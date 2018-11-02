package kmeans;

import cluster.kmeans.bean.KMeansCluster;
import cluster.kmeans.bean.KMeansPoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yidxue on 2018/11/1
 */
public class KMeansClusterTest {
    public static void main(String[] args) {
        float[] f = {1, 2, 3};
        List<KMeansPoint> members1 = new ArrayList<>();
        List<KMeansPoint> members2 = new ArrayList<>();

        members1.add(new KMeansPoint(new float[]{1,2,3}));
        members1.add(new KMeansPoint(new float[]{1,2,4}));
        members1.add(new KMeansPoint(new float[]{1,2,5}));

        members2.add(new KMeansPoint(new float[]{1,2,3}));
        members2.add(new KMeansPoint(new float[]{1,2,4}));
        members2.add(new KMeansPoint(new float[]{1,2,5}));

        KMeansCluster kMeansCluster1 = new KMeansCluster(1, new KMeansPoint(f), members1);
        KMeansCluster kMeansCluster2 = new KMeansCluster(1, new KMeansPoint(f), members2);

        System.out.println(kMeansCluster1.equals(kMeansCluster2));
    }
}
