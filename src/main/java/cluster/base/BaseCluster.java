package cluster.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yidxue on 2018/11/1
 */
public abstract class BaseCluster<T> {

    /**
     * 标识
     */
    protected int id;
    /**
     * 中心
     */
    protected float[] center;
    /**
     * 成员
     */
    protected List<T> members = new ArrayList<T>();

    public float[] getCenter() {
        return center;
    }

    public void setCenter(float[] center) {
        this.center = center;
    }

    public List<T> getMembers() {
        return members;
    }

    public void setMembers(List<T> members) {
        this.members = members;
    }

    public void addPoint(T newPoint) {
        if (!members.contains(newPoint)) {
            members.add(newPoint);
        } else {
            System.out.println("样本数据点 {" + newPoint.toString() + "} 已经存在！");
        }
    }
}
