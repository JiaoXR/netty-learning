package com.jaxer.dubbo.consumer;

import com.jaxer.dubbo.consumer.discovery.ServiceDiscovery;
import com.jaxer.dubbo.consumer.discovery.ServiceDiscoveryImpl;
import org.junit.Test;

/**
 * Created on 2020/7/3 23:51
 *
 * @author jaxer
 */
public class ConsumerTest {
    @Test
    public void test01() throws Exception {
        ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();
        String discovery = serviceDiscovery.discovery("com.jaxer.dubbo.api.service.EmployeeService");
        System.out.println("discovery = " + discovery);
    }
}
