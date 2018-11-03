package cluster.hierarchical.bean;

import cluster.base.BaseCluster;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yidxue on 2018/11/2
 */
public class HierarchicalCluster extends BaseCluster<HierarchicalPoint> {
    private float[] centerLocal;

    public HierarchicalCluster(int id, float[] centerLocal, List<HierarchicalPoint> member) {
        this.id = id;
        this.centerLocal = centerLocal;
        this.members = member;
    }

    public float[] getCenterLocal() {
        return centerLocal;
    }

    public void setCenterLocal(float[] centerLocal) {
        this.centerLocal = centerLocal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String centre = Stream.of(centerLocal).map(String::valueOf).collect(Collectors.joining(","));
        StringBuilder toString = new StringBuilder("Cluster \n" + "Cluster_id=" + this.id + ", center:{" + centre + "}");
        for (HierarchicalPoint point : members) {
            toString.append("\n").append(point.toString());
        }
        return toString + "\n";
    }
}
