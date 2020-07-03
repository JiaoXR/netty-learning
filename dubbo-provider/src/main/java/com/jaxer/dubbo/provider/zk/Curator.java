package com.jaxer.dubbo.provider.zk;

import com.jaxer.dubbo.provider.constant.ZKConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * ZooKeeper客户端Curator
 * Created on 2020/7/1 23:20
 *
 * @author jaxer
 */
public class Curator {
    public static final CuratorFramework INSTANCE;

    static {
        INSTANCE = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.ZK_ADDRESS)
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1, 5))
                .build();
        INSTANCE.start();

        System.out.println("curator初始化");
    }

    /**
     * 判断节点是否存在
     */
    public static boolean exist(String path) throws Exception {
        return Curator.INSTANCE.checkExists().forPath(path) != null;
    }

    /**
     * 创建ZK节点
     *
     * @param path 路径
     * @param mode 节点类型
     */
    public static String createNode(String path, CreateMode mode) throws Exception {
        return INSTANCE.create()
                .creatingParentsIfNeeded()
                .withMode(mode)
                .forPath(path, "0".getBytes());
    }
}
