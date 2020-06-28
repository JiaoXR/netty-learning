package com.jaxer.rpc.client;

import com.jaxer.rpc.api.service.HelloService;
import com.jaxer.rpc.client.proxy.RpcProxy;

/**
 * 简单RPC实现-客户端
 * Created on 2020/6/27 18:54
 *
 * @author jaxer
 */
public class RpcClient {
    /*
        1. 知道服务端有哪些服务提供
        2. 本地创建服务的代理
        3. 将代理信息发送给服务端，接收返回结果
     */
    public static void main(String[] args) {
        HelloService helloService = RpcProxy.create(HelloService.class);
        System.out.println("调用结果：" + helloService.sayHello());
    }
}
