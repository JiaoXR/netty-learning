package com.jaxer.common.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author jaxer
 * @date 2020/6/19 2:22 PM
 */
public class NettyUtil {
    private static final int CLIENT_MAX_RETRY_TIMES = 3;

    public static void main(String[] args) {
        StackTraceElement[] elements = Thread.getAllStackTraces().get(Thread.currentThread());
        for (StackTraceElement element : elements) {
            System.out.println(element.getMethodName());
        }
    }

    /**
     * 绑定端口，失败重试，原端口号+1
     *
     * @param port 端口号
     */
    public static void bindPortWithRetry(ServerBootstrap serverBootstrap, int port) {
        LocalDateTime now = LocalDateTime.now();
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(now + " 服务器启动成功！绑定端口 [" + port + "]");
            } else {
                System.out.println(now + " 服务器启动失败，尝试绑定端口 [" + (port + 1) + "]");
                bindPortWithRetry(serverBootstrap, port + 1);
            }
        });
    }

    /**
     * 客户端建立连接
     *
     * @param bootstrap  Bootstrap
     * @param ip         IP地址
     * @param port       端口号
     * @param retryTimes 重试次数
     * @return
     */
    public static ChannelFuture connect2Server(Bootstrap bootstrap, String ip, int port, int retryTimes) {
        LocalDateTime now = LocalDateTime.now();
        return bootstrap.connect(ip, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(now + " 成功连接到服务器！IP：" + ip + ", 端口：" + port);
            } else if (retryTimes <= 0) {
                System.err.println(now + "重连次数用尽，连接失败");
                bootstrap.config().group().shutdownGracefully();
            } else {
                int delay = 1 << (CLIENT_MAX_RETRY_TIMES - retryTimes);
                System.err.println(now + "延迟 [" + delay + "] 秒后重试。。");
                bootstrap.config().group().schedule(() -> connect2Server(bootstrap, ip, port, retryTimes - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * 生成ByteBuf
     *
     * @param ctx     上下文
     * @param content 内容
     * @return ByteBuf
     */
    public static ByteBuf getByteBuf(ChannelHandlerContext ctx, String content) {
        // 1. 获取二进制抽象 ByteBuf
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

        // 2. 准备数据，指定字符串的字符集为 utf-8
        ByteBuf buffer = ctx.alloc().buffer();

        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
