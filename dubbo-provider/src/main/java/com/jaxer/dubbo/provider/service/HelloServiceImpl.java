package com.jaxer.dubbo.provider.service;

import com.jaxer.dubbo.api.service.HelloService;

import java.time.LocalDateTime;

/**
 * Created on 2020/6/27 11:16
 *
 * @author jaxer
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return LocalDateTime.now() + " world";
    }
}
