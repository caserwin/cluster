package cluster.base;

/**
 * Created by yidxue on 2018/11/2
 */
public abstract class BaseModel {
    /**
     * 参数检查
     */
    public abstract void check() throws Exception;

    /**
     * 数据初始化
     */
    public abstract void init() throws Exception;

}
