package com.jaxer.rpc.service;

import com.jaxer.rpc.api.service.HelloService;

import java.time.LocalDateTime;

/**
 * Created on 2020/6/27 11:16
 *
 * @author jaxer
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return LocalDateTime.now() + " hello";
    }
}
