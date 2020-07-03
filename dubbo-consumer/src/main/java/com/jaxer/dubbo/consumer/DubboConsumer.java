package com.jaxer.dubbo.consumer;

import com.jaxer.dubbo.api.service.HelloService;
import com.jaxer.dubbo.consumer.proxy.RpcProxy;

/**
 * Created on 2020/7/3 23:54
 *
 * @author jaxer
 */
public class DubboConsumer {
    public static void main(String[] args) {
        HelloService helloService = RpcProxy.create(HelloService.class);
        System.out.println(helloService.sayHello());
    }
}
