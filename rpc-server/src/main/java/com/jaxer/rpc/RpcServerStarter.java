package com.jaxer.rpc;

import com.jaxer.rpc.server.RpcServer;

/**
 * RPC服务端启动类
 * Created on 2020/6/27 12:27
 *
 * @author jaxer
 */
public class RpcServerStarter {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer();
        server.publish("com.jaxer.rpc.service");
        server.start();
    }
}
