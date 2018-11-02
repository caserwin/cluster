package cluster.dbscan.bean;

import cluster.base.BaseCluster;
import java.util.ArrayList;

/**
 * Created by yidxue on 2018/11/1
 */
public class DBScanCluster extends BaseCluster<DBScanPoint> {
    private ArrayList<DBScanPoint> corePoint = new ArrayList<>();

    public DBScanCluster(int id) {
        this.id = id;
    }

    public void addCorePoint(DBScanPoint point) {
        corePoint.add(point);
    }

    public ArrayList<DBScanPoint> getCorePoint() {
        return corePoint;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder("Cluster \n" + "Cluster_id=" + this.id);
        for (DBScanPoint point : members) {
            toString.append("\n").append(point.toString());
        }
        return toString + "\n";
    }
}
