package cluster.dbscan;

import cluster.dbscan.bean.DBScanCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yidxue on 2018/11/1
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<>();

        dataSet.add(new float[]{1, 2});
        dataSet.add(new float[]{3, 3});
        dataSet.add(new float[]{5, 4});
        dataSet.add(new float[]{4, 3});

//        dataSet.add(new float[]{6, 6});

        dataSet.add(new float[]{6, 8});
        dataSet.add(new float[]{7, 9});
        dataSet.add(new float[]{8, 10});
        dataSet.add(new float[]{10, 12});
        dataSet.add(new float[]{11, 15});


        DBScanRun dbScanRun = new DBScanRun(4, 3, dataSet);
        List<DBScanCluster> clusterList = dbScanRun.run();
        for (DBScanCluster cluster : clusterList) {
            System.out.println(cluster);
        }
    }
}
