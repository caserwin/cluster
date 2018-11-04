## k-means
参考西瓜书p202的层次聚类的实现。

算法中就一个参数k，表示要聚成几类。k-means是个不稳定的算法，最终的聚类结果和初始随机选取的类中心位置有关。以下示例：
### 样本集
```
   public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<>();

        dataSet.add(new float[] { 1, 2, 3 });
        dataSet.add(new float[] { 3, 3, 3 });
        dataSet.add(new float[] { 3, 4, 4});
        dataSet.add(new float[] { 5, 6, 5});
        dataSet.add(new float[] { 8, 9, 6});
        dataSet.add(new float[] { 4, 5, 4});
        dataSet.add(new float[] { 6, 4, 2});
        dataSet.add(new float[] { 3, 9, 7});
        dataSet.add(new float[] { 5, 9, 8});
        dataSet.add(new float[] { 4, 2, 10});
        dataSet.add(new float[] { 1, 9, 12});
        dataSet.add(new float[] { 7, 8, 112});
        dataSet.add(new float[] { 7, 8, 4});

        KMeansRun kRun =new KMeansRun(3, dataSet);

        HashSet<KMeansCluster> clusterSet = kRun.run();
        System.out.println("单次迭代运行次数："+kRun.getIterTimes());
        for (KMeansCluster cluster : clusterSet) {
            System.out.println(cluster);
        }
    }
```
输出：
```
单次迭代运行次数：3
Cluster
Cluster_id=0, center:{4.8333,8.3333,7.0}
Point_id=3  [5.0 6.0 5.0] clusterId: 0 dist: 3.0776973
Point_id=4  [8.0 9.0 6.0] clusterId: 0 dist: 3.3870668
Point_id=7  [3.0 9.0 7.0] clusterId: 0 dist: 1.9507836
Point_id=8  [5.0 9.0 8.0] clusterId: 0 dist: 1.2133518
Point_id=10  [1.0 9.0 12.0] clusterId: 0 dist: 6.335526
Point_id=12  [7.0 8.0 4.0] clusterId: 0 dist: 3.7155826

Cluster
Cluster_id=1, center:{3.5,3.3333,4.3333}
Point_id=0  [1.0 2.0 3.0] clusterId: 1 dist: 3.1313825
Point_id=1  [3.0 3.0 3.0] clusterId: 1 dist: 1.4624941
Point_id=2  [3.0 4.0 4.0] clusterId: 1 dist: 0.8975276
Point_id=5  [4.0 5.0 4.0] clusterId: 1 dist: 1.7716911
Point_id=6  [6.0 4.0 2.0] clusterId: 1 dist: 3.484091
Point_id=9  [4.0 2.0 10.0] clusterId: 1 dist: 5.8428493

Cluster
Cluster_id=2, center:{7.0,8.0,112.0}
Point_id=11  [7.0 8.0 112.0] clusterId: 2 dist: 0.0
```
