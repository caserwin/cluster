package cluster.hierarchical.bean;

import cluster.base.BaseCluster;
import cluster.util.TypeTrans;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yidxue on 2018/11/2
 */
public class HierarchicalCluster extends BaseCluster<HierarchicalPoint> {

    public HierarchicalCluster(int id, float[] center, List<HierarchicalPoint> member) {
        this.id = id;
        this.center = center;
        this.members = member;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String centre = Arrays.stream(TypeTrans.getDoubleByFloat(center)).mapToObj(String::valueOf).collect(Collectors.joining(","));
        StringBuilder toString = new StringBuilder("Cluster \n" + "Cluster_id=" + this.id + ", center:{" + centre + "}");
        for (HierarchicalPoint point : members) {
            toString.append("\n").append(point.toString());
        }
        return toString + "\n";
    }
}
