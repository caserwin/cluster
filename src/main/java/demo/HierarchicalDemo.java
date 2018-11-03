package demo;

import cluster.hierarchical.HierarchicalRun;
import cluster.hierarchical.bean.HierarchicalCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yidxue on 2018/11/2
 */
public class HierarchicalDemo {
    public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<>();

        dataSet.add(new float[]{1, 2, 3});
        dataSet.add(new float[]{3, 3, 3});
        dataSet.add(new float[]{3, 4, 4});
        dataSet.add(new float[]{5, 6, 5});
        dataSet.add(new float[]{8, 9, 6});
        dataSet.add(new float[]{4, 5, 4});
        dataSet.add(new float[]{6, 4, 2});
        dataSet.add(new float[]{3, 9, 7});
        dataSet.add(new float[]{5, 9, 8});
        dataSet.add(new float[]{4, 2, 10});
        dataSet.add(new float[]{4, 3, 10});
        dataSet.add(new float[]{1, 9, 12});
        dataSet.add(new float[]{2, 9, 12});
        dataSet.add(new float[]{7, 8, 112});
        dataSet.add(new float[]{7, 8, 4});

        HierarchicalRun hierarchicalRun = new HierarchicalRun(6, "avg", dataSet);
        List<HierarchicalCluster> clusterList = hierarchicalRun.run();
        for (HierarchicalCluster cluster : clusterList) {
            System.out.println(cluster);
        }
    }
}
