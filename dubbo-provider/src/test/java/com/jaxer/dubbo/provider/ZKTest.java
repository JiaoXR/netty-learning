package com.jaxer.dubbo.provider;

import com.jaxer.dubbo.provider.registey.RegistryCenter;
import com.jaxer.dubbo.provider.registey.ZKRegistryCenter;
import com.jaxer.dubbo.provider.zk.Curator;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2020/6/29 00:10
 *
 * @author jaxer
 */
public class ZKTest {
    /**
     * 测试ZooKeeper注册节点
     */
    @Test
    public void testRegister() throws Exception {
        RegistryCenter registryCenter = new ZKRegistryCenter();
        registryCenter.register("com.jaxer.dubbo.api.service.HelloService",
                "127.0.0.1:8888");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * 删除ZooKeeper节点
     */
    @Test
    public void delZKNode() throws Exception {
//        Curator.INSTANCE.delete().forPath("/my-dubbo/com.jaxer.dubbo.api.service.HelloService/127.0.0.1:8888");
        Curator.INSTANCE.delete().forPath("/my-dubbo/com.jaxer.dubbo.api.service.HelloService");
        Curator.INSTANCE.delete().forPath("/my-dubbo/com.jaxer.dubbo.api.service.EmployeeService");
    }
}
