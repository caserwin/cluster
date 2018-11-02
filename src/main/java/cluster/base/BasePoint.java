package cluster.base;

/**
 * Created by yidxue on 2018/11/1
 */
public abstract class BasePoint {
    /**
     * 标识
     */
    protected int id;

    /**
     * 坐标
     */
    protected float[] localArray;

    /**
     * 标识属于哪个类中心
     */
    protected int clusterId;

    public float[] getLocalArray() {
        return localArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocalArray(float[] localArray) {
        this.localArray = localArray;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }
}
