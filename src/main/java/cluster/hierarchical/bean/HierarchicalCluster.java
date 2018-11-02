package cluster.hierarchical.bean;

import cluster.base.BaseCluster;
import java.util.List;

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
}
