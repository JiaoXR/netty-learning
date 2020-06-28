package com.jaxer.rpc.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 接收从RPC服务端返回的调用结果
 * Created on 2020/6/28 00:06
 *
 * @author jaxer
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<Object> {
    private Object result;

    public Object getResult() {
        return result;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead0 接收的数据：" + msg);
        this.result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
