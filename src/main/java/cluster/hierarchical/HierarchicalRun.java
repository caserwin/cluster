package cluster.hierarchical;

import cluster.hierarchical.bean.HierarchicalCluster;
import cluster.hierarchical.bean.HierarchicalPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yidxue on 2018/11/2
 */
public class HierarchicalRun {
    private double dist;
    private int k;
    /**
     * 用于存放，原始数据集所构建的点集
     */
    private static List<HierarchicalPoint> pointList = null;

    /**
     * 用于存放，原始数据集所构建的点集
     */
    private static List<HierarchicalCluster> clusterList = null;

    private List<float[]> originalData;

    public HierarchicalRun(double dist, List<float[]> originalData) {
        this.dist = dist;
        this.originalData = originalData;
        check();
        init();
    }

    public HierarchicalRun(int k, List<float[]> originalData) {
        this.k = k;
        this.originalData = originalData;
        check();
        init();
    }

    /**
     * 检查规范
     */
    private void check() {
        if (dist <= 0 || k < 1) {
            throw new IllegalArgumentException("dist must be >= 0 and k must be >=1");
        }

        if (originalData == null) {
            throw new IllegalArgumentException("program can't get real data");
        }
    }

    /**
     * 初始化数据集，把数组转化为Point类型。
     */
    private void init() {
        pointList = new ArrayList<>();
        clusterList = new ArrayList<>();

        for (int i = 0, j = originalData.size(); i < j; i++) {
            HierarchicalPoint point = new HierarchicalPoint(i, originalData.get(i));
            pointList.add(point);

            // 每一个点
            List<HierarchicalPoint> members = new ArrayList<>();
            members.add(point);
            clusterList.add(new HierarchicalCluster(i, point.getLocalArray(), members));
        }
    }

    public List<HierarchicalCluster> run() {


        return null;
    }
}
