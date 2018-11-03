package cluster.util;

/**
 * Created by yidxue on 2018/11/3
 */
public class Tools {
    public static boolean isEqual(float[] f1, float[] f2) {
        if (f1.length != f2.length) {
            return false;
        }
        boolean is = true;
        for (int i = 0; i < f1.length; i++) {
            if (f1[i] != f2[i]) {
                is = false;
            }
        }
        return is;
    }
}
