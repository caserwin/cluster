## Hierarchical
参考西瓜书p214的层次聚类: AGNES 算法的实现。此处我做了点扩展，不仅可以根据指定类别个数进行聚类，还可以指定类别间的最小距离来输出聚类结果。

### 示例1：结果聚成5类
int类型的5，表示结果聚成5个类别，"avg" 表示用类平均距离作为两个类别的距离。此外，min表示用两个类间的最小样本距离的作为两个类别的距离。max表示用两个类间的最大样本距离的作为两个类别的距离。

```
public static void main(String[] args) {
    ArrayList<float[]> dataSet = new ArrayList<>();

    dataSet.add(new float[]{1, 2, 3});
    dataSet.add(new float[]{3, 3, 3});
    dataSet.add(new float[]{3, 4, 4});
    dataSet.add(new float[]{5, 6, 5});
    dataSet.add(new float[]{8, 9, 6});
    dataSet.add(new float[]{4, 5, 4});
    dataSet.add(new float[]{6, 4, 2});
    dataSet.add(new float[]{3, 9, 7});
    dataSet.add(new float[]{5, 9, 8});
    dataSet.add(new float[]{4, 2, 10});
    dataSet.add(new float[]{4, 3, 10});
    dataSet.add(new float[]{1, 9, 12});
    dataSet.add(new float[]{2, 9, 12});
    dataSet.add(new float[]{7, 8, 112});
    dataSet.add(new float[]{7, 8, 4});

    HierarchicalRun hierarchicalRun = new HierarchicalRun(5, "avg", dataSet);
    List<HierarchicalCluster> clusterList = hierarchicalRun.run();
    for (HierarchicalCluster cluster : clusterList) {
        System.out.println(cluster);
    }
}
```
输出：
```
Cluster
Cluster_id=0, center:{5.75,8.75,6.25}
Point_id=4  [8.0 9.0 6.0] clusterId: 0
Point_id=14  [7.0 8.0 4.0] clusterId: 0
Point_id=7  [3.0 9.0 7.0] clusterId: 0
Point_id=8  [5.0 9.0 8.0] clusterId: 0

Cluster
Cluster_id=1, center:{3.625,3.75,3.25}
Point_id=3  [5.0 6.0 5.0] clusterId: 0
Point_id=5  [4.0 5.0 4.0] clusterId: 0
Point_id=6  [6.0 4.0 2.0] clusterId: 0
Point_id=1  [3.0 3.0 3.0] clusterId: 0
Point_id=2  [3.0 4.0 4.0] clusterId: 0
Point_id=0  [1.0 2.0 3.0] clusterId: 0

Cluster
Cluster_id=2, center:{1.5,9.0,12.0}
Point_id=11  [1.0 9.0 12.0] clusterId: 0
Point_id=12  [2.0 9.0 12.0] clusterId: 0

Cluster
Cluster_id=3, center:{4.0,2.5,10.0}
Point_id=9  [4.0 2.0 10.0] clusterId: 0
Point_id=10  [4.0 3.0 10.0] clusterId: 0

Cluster
Cluster_id=4, center:{7.0,8.0,112.0}
Point_id=13  [7.0 8.0 112.0] clusterId: 0
```

### 示例2：类间距离大于6时，停止聚类
double类型的6.0，表示最终结果的类别之间最小距离都是大于6的。

```
public static void main(String[] args) {
    ArrayList<float[]> dataSet = new ArrayList<>();

    dataSet.add(new float[]{1, 2, 3});
    dataSet.add(new float[]{3, 3, 3});
    dataSet.add(new float[]{3, 4, 4});
    dataSet.add(new float[]{5, 6, 5});
    dataSet.add(new float[]{8, 9, 6});
    dataSet.add(new float[]{4, 5, 4});
    dataSet.add(new float[]{6, 4, 2});
    dataSet.add(new float[]{3, 9, 7});
    dataSet.add(new float[]{5, 9, 8});
    dataSet.add(new float[]{4, 2, 10});
    dataSet.add(new float[]{4, 3, 10});
    dataSet.add(new float[]{1, 9, 12});
    dataSet.add(new float[]{2, 9, 12});
    dataSet.add(new float[]{7, 8, 112});
    dataSet.add(new float[]{7, 8, 4});

    HierarchicalRun hierarchicalRun = new HierarchicalRun(6.0, "avg", dataSet);
    List<HierarchicalCluster> clusterList = hierarchicalRun.run();
    for (HierarchicalCluster cluster : clusterList) {
        System.out.println(cluster);
    }
}
```
输出
```
Cluster
Cluster_id=0, center:{5.75,8.75,6.25}
Point_id=4  [8.0 9.0 6.0] clusterId: 0
Point_id=14  [7.0 8.0 4.0] clusterId: 0
Point_id=7  [3.0 9.0 7.0] clusterId: 0
Point_id=8  [5.0 9.0 8.0] clusterId: 0

Cluster
Cluster_id=1, center:{3.625,3.75,3.25}
Point_id=3  [5.0 6.0 5.0] clusterId: 0
Point_id=5  [4.0 5.0 4.0] clusterId: 0
Point_id=6  [6.0 4.0 2.0] clusterId: 0
Point_id=1  [3.0 3.0 3.0] clusterId: 0
Point_id=2  [3.0 4.0 4.0] clusterId: 0
Point_id=0  [1.0 2.0 3.0] clusterId: 0

Cluster
Cluster_id=2, center:{1.5,9.0,12.0}
Point_id=11  [1.0 9.0 12.0] clusterId: 0
Point_id=12  [2.0 9.0 12.0] clusterId: 0

Cluster
Cluster_id=3, center:{4.0,2.5,10.0}
Point_id=9  [4.0 2.0 10.0] clusterId: 0
Point_id=10  [4.0 3.0 10.0] clusterId: 0

Cluster
Cluster_id=4, center:{7.0,8.0,112.0}
Point_id=13  [7.0 8.0 112.0] clusterId: 0
```