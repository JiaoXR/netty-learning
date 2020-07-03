package com.jaxer.dubbo.consumer.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

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
     * 获取某个路径的子节点
     */
    public static List<String> getChildren(String path) {
        try {
            return Curator.INSTANCE.getChildren().forPath(path);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
