## DBSCAN
需要两个参数r和support，分别对应密度聚类中的领域半径 r和领域内最小的元素个数 MinPts。DBSCAN根据这两个参数来判断一个样本点是不是核心对象。

以下程序表示，领域半径为4，一个核心对象的领域内最小包含3个样本点，进行聚类：
### 样本集1
```
    public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<>();

        dataSet.add(new float[]{1, 2});
        dataSet.add(new float[]{3, 3});
        dataSet.add(new float[]{5, 4});
        dataSet.add(new float[]{4, 3});

        dataSet.add(new float[]{6, 8});
        dataSet.add(new float[]{7, 9});
        dataSet.add(new float[]{8, 10});
        dataSet.add(new float[]{10, 12});
        dataSet.add(new float[]{11, 15});


        DBScanRun dbScanRun = new DBScanRun(4, 3, dataSet);
        List<DBScanCluster> clusterList = dbScanRun.run();
        for (DBScanCluster cluster : clusterList) {
            System.out.println(cluster);
        }
    }
```
输出
```
Cluster
Cluster_id=1
Point_id=0  isCorePoint= true [1.0 2.0]
Point_id=1  isCorePoint= true [3.0 3.0]
Point_id=3  isCorePoint= true [4.0 3.0]
Point_id=2  isCorePoint= true [5.0 4.0]

Cluster
Cluster_id=2
Point_id=6  isCorePoint= true [8.0 10.0]
Point_id=7  isCorePoint= true [10.0 12.0]
Point_id=5  isCorePoint= true [7.0 9.0]
Point_id=4  isCorePoint= true [6.0 8.0]
Point_id=8  isCorePoint= false [11.0 15.0]
```
### 样本集2
多加了一个样本点。dataSet.add(new float[]{6, 6})，查看结果：

```
    public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<>();

        dataSet.add(new float[]{1, 2});
        dataSet.add(new float[]{3, 3});
        dataSet.add(new float[]{5, 4});
        dataSet.add(new float[]{4, 3});

        dataSet.add(new float[]{6, 6});

        dataSet.add(new float[]{6, 8});
        dataSet.add(new float[]{7, 9});
        dataSet.add(new float[]{8, 10});
        dataSet.add(new float[]{10, 12});
        dataSet.add(new float[]{11, 15});


        DBScanRun dbScanRun = new DBScanRun(4, 3, dataSet);
        List<DBScanCluster> clusterList = dbScanRun.run();
        for (DBScanCluster cluster : clusterList) {
            System.out.println(cluster);
        }
    }
```
输出
```
Cluster
Cluster_id=1
Point_id=0  isCorePoint= true [1.0 2.0]
Point_id=1  isCorePoint= true [3.0 3.0]
Point_id=4  isCorePoint= true [6.0 6.0]
Point_id=7  isCorePoint= true [8.0 10.0]
Point_id=8  isCorePoint= true [10.0 12.0]
Point_id=6  isCorePoint= true [7.0 9.0]
Point_id=3  isCorePoint= true [4.0 3.0]
Point_id=5  isCorePoint= true [6.0 8.0]
Point_id=9  isCorePoint= false [11.0 15.0]
Point_id=2  isCorePoint= true [5.0 4.0]
```