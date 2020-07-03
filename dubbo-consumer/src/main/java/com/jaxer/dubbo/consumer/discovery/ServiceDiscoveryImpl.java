package com.jaxer.dubbo.consumer.discovery;

import com.jaxer.dubbo.consumer.zk.ZKConstant;
import com.jaxer.dubbo.consumer.loadbalance.RandomLoadBalance;
import com.jaxer.dubbo.consumer.zk.Curator;

import java.util.List;

/**
 * Created on 2020/7/3 23:48
 *
 * @author jaxer
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {
    @Override
    public String discovery(String serviceName) {
        String fullPath = ZKConstant.ZK_ROOT_PATH + "/" + serviceName;
        List<String> serviceList = Curator.getChildren(fullPath);
        System.out.println("serviceList = " + serviceList);
        if (serviceList.size() == 0) {
            return null;
        }
        return new RandomLoadBalance().choose(serviceList);
    }
}
