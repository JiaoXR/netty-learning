package com.jaxer.dubbo.consumer.discovery;

/**
 * 服务发现，从ZooKeeper发现服务的IP，并执行负载均衡策略选择其中一个
 * Created on 2020/7/3 23:47
 *
 * @author jaxer
 */
public interface ServiceDiscovery {
    /**
     * 服务发现
     *
     * @param serviceName 服务名称
     * @return 选择的服务地址
     */
    String discovery(String serviceName) throws Exception;
}
