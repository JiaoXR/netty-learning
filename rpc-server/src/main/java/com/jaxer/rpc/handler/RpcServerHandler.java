package com.jaxer.rpc.handler;

import com.jaxer.rpc.api.invoke.InvokeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * 处理客户端请求
 * Created on 2020/6/27 12:44
 *
 * @author jaxer
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<InvokeMessage> {
    // 注册中心
    private Map<String, Object> registry;

    public RpcServerHandler(Map<String, Object> registry) {
        this.registry = registry;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvokeMessage msg) throws Exception {
        /*
            判断调用的服务是否在注册中心：
                1. 已注册：调用服务的实例方法
                2. 未注册：通知调用者服务不存在
         */
        Object result;
        String serviceName = msg.getServiceName();
        if (registry.containsKey(serviceName)) {
            Object service = registry.get(serviceName);
            result = service.getClass()
                    .getDeclaredMethod(msg.getMethodName(), msg.getParameterTypes())
                    .invoke(service, msg.getParameterValues());
        } else {
            result = "服务不存在";
        }

        // 返回调用结果
        System.out.println("返回结果：" + result);
        ctx.channel().writeAndFlush(result);
        ctx.close();
    }
}
