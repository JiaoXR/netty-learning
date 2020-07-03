package com.jaxer.dubbo.provider.registey;

/**
 * 注册中心
 * Created on 2020/6/28 23:49
 *
 * @author jaxer
 */
public interface RegistryCenter {
    /**
     * 服务注册
     *
     * @param serviceName 服务名
     * @param address     服务地址（IP+端口）
     */
    void register(String serviceName, String address) throws Exception;
}
