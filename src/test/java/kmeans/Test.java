package kmeans;

/**
 * Created by yidxue on 2018/11/3
 */
public class Test {
    public static void main(String[] args) {
        double[] d1 = {5.75, 8.75, 6.25};
        double[] d2 = {3.625, 3.75, 3.25};
        double[] d3 = {4.0, 2.5, 10.0};

        System.out.println(calculate(d1, d2));
        System.out.println(calculate(d1, d3));
        System.out.println(calculate(d2, d3));
    }

    public static double calculate(double[] d1, double[] d2) {
        double countDis = 0;

        for (int i = 0; i < d1.length; i++) {
            countDis += Math.pow(d1[i] - d2[i], 2);
        }
        return Math.sqrt(countDis);
    }
}
