package com.jaxer.dubbo.provider;

import com.jaxer.dubbo.provider.registey.ZKRegistryCenter;
import com.jaxer.dubbo.provider.server.DubboProvider;

/**
 * RPC服务端启动类
 * Created on 2020/6/27 12:27
 *
 * @author jaxer
 */
public class DubboProviderStarter {
    public static void main(String[] args) throws Exception {
        DubboProvider server = new DubboProvider();
        server.publish("com.jaxer.dubbo.provider.service",
                "127.0.0.1:8888", new ZKRegistryCenter());
        server.start();
    }
}
