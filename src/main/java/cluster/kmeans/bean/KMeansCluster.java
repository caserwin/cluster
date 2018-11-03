package cluster.kmeans.bean;

import cluster.base.BaseCluster;
import cluster.util.TypeTrans;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yidxue on 2018/4/7
 */
public class KMeansCluster extends BaseCluster<KMeansPoint> {

    public KMeansCluster(int id, float[] center, List<KMeansPoint> members) {
        this.id = id;
        this.center = center;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    @Override
    public float[] getCenter() {
        return center;
    }

    @Override
    public void setCenter(float[] center) {
        this.center = center;
    }

    @Override
    public List<KMeansPoint> getMembers() {
        return members;
    }

    @Override
    public boolean equals(Object obj) {
        // 判断类别是否相等
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // 判断元素个数是否相等
        List<KMeansPoint> points = ((KMeansCluster) obj).getMembers();
        if (points.size() != members.size()) {
            return false;
        }

        // 判断每个元素是否相等
        boolean is = true;
        for (KMeansPoint point1 : members) {
            boolean tmp = false;
            for (KMeansPoint point2 : points) {
                if (point1.equals(point2)) {
                    tmp = true;
                    break;
                }
            }
            if (!tmp) {
                is = false;
                break;
            }
        }
        return is;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        for (KMeansPoint point : members) {
            hashcode += point.hashCode();
        }
        return hashcode;
    }

    @Override
    public String toString() {
        String centre = Arrays.stream(TypeTrans.getDoubleByFloat(center)).mapToObj(String::valueOf).collect(Collectors.joining(","));
        StringBuilder toString = new StringBuilder("Cluster \n" + "Cluster_id=" + this.id + ", center:{" + centre + "}");
        for (KMeansPoint point : members) {
            toString.append("\n").append(point.toString());
        }
        return toString + "\n";
    }
}
