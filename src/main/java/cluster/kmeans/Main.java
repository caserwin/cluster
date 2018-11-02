package cluster.kmeans;

import cluster.kmeans.bean.KMeansCluster;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by yidxue on 2018/4/7
 * @author yidxue
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<>();

        dataSet.add(new float[] { 1, 2, 3 });
        dataSet.add(new float[] { 3, 3, 3 });
        dataSet.add(new float[] { 3, 4, 4});
        dataSet.add(new float[] { 5, 6, 5});
        dataSet.add(new float[] { 8, 9, 6});
        dataSet.add(new float[] { 4, 5, 4});
        dataSet.add(new float[] { 6, 4, 2});
        dataSet.add(new float[] { 3, 9, 7});
        dataSet.add(new float[] { 5, 9, 8});
        dataSet.add(new float[] { 4, 2, 10});
        dataSet.add(new float[] { 1, 9, 12});
        dataSet.add(new float[] { 7, 8, 112});
        dataSet.add(new float[] { 7, 8, 4});

        KMeansRun kRun =new KMeansRun(3, dataSet);

        HashSet<KMeansCluster> clusterSet = kRun.run();
        System.out.println("单次迭代运行次数："+kRun.getIterTimes());
        for (KMeansCluster cluster : clusterSet) {
            System.out.println(cluster);
        }
    }
}
