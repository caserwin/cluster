package cluster.dbscan.bean;

import cluster.base.BasePoint;

/**
 * Created by yidxue on 2018/11/1
 */
public class DBScanPoint extends BasePoint {

    private boolean isCore = false;

    public DBScanPoint(int id, float[] localArray) {
        this.id = id;
        this.localArray = localArray;
    }

    public boolean getIsCore() {
        return this.isCore;
    }

    public void setIsCore(boolean isCore) {
        this.isCore = isCore;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        DBScanPoint point = (DBScanPoint) obj;
        if (point.localArray.length != localArray.length) {
            return false;
        }

        for (int i = 0; i < localArray.length; i++) {
            if (Float.compare(point.localArray[i], localArray[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        float x = localArray[0];
        float y = localArray[localArray.length - 1];
        long temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        int result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Point_id=" + id + "  isCorePoint= " + getIsCore() + " [");
        for (float aLocalArray : localArray) {
            result.append(aLocalArray).append(" ");
        }
        return result.toString().trim() + "]";
    }
}
