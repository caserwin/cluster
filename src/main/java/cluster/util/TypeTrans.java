package cluster.util;

import java.text.DecimalFormat;

/**
 * Created by yidxue on 2018/11/3
 */
public class TypeTrans {

    public static double[] getDoubleByFloat(float[] floatArray) {
        DecimalFormat df = new DecimalFormat("0.0000");

        double[] doubleArray = new double[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
            doubleArray[i] = Double.parseDouble(df.format((double) floatArray[i]));
        }
        return doubleArray;
    }
}
