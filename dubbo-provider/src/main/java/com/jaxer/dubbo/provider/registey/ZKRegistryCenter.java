package com.jaxer.dubbo.provider.registey;

import com.jaxer.dubbo.provider.constant.ZKConstant;
import com.jaxer.dubbo.provider.zk.Curator;
import org.apache.zookeeper.CreateMode;

/**
 * ZooKeeper注册中心
 * Created on 2020/6/28 23:50
 *
 * @author jaxer
 */
public class ZKRegistryCenter implements RegistryCenter {
    @Override
    public void register(String serviceName, String address) throws Exception {
        String rootPath = ZKConstant.ZK_ROOT_PATH + "/" + serviceName;

        // 创建根节点
        if (!Curator.exist(rootPath)) {
            String rootPathResult = Curator.createNode(rootPath, CreateMode.PERSISTENT);
            System.out.println("rootPathResult = " + rootPathResult);
        }

        // 创建临时节点
        String servicePath = rootPath + "/" + address;
        if (!Curator.exist(servicePath)) {
            String servicePathResult = Curator.createNode(servicePath, CreateMode.EPHEMERAL);
            System.out.println("servicePathResult = " + servicePathResult);
        }
    }
}
