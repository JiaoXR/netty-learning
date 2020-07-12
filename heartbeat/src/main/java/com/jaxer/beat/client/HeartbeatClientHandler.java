package com.jaxer.beat.client;

import com.jaxer.common.constant.CommonConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 客户端Channel处理器
 * Created on 2020/6/26 01:00
 *
 * @author jaxer
 */
public class HeartbeatClientHandler extends ChannelInboundHandlerAdapter {
    private final Random random = new Random(System.currentTimeMillis());
    private Bootstrap bootstrap;
    private ScheduledFuture<?> scheduledFuture;
    private GenericFutureListener listener;

    public HeartbeatClientHandler(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    // Channel 被激活时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendHeartbeat(ctx.channel());
    }

    /**
     * Channel关闭后执行的操作
     * 与服务端的连接断开后，自动重连
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().eventLoop().schedule(() -> {
            try {
                scheduledFuture.removeListener(listener);

                System.out.println(LocalDateTime.now() + "==> reconnecting...");
                bootstrap.connect(CommonConstant.LOCAL_HOST_IP, CommonConstant.NETTY_PORT).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 10, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 随机间隔一段时间想服务端发送心跳
     */
    private void sendHeartbeat(Channel channel) {
        int internal = random.nextInt(6) + 1;
        System.out.println(LocalDateTime.now() + " ==> " + internal + " 秒后向Server发送心跳...");

        scheduledFuture = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                System.out.println(LocalDateTime.now() + " 向Server端发送心跳");
                channel.writeAndFlush("ping ~ " + UUID.randomUUID());
            } else {
                System.out.println(LocalDateTime.now() + " 与服务端连接已断开");
                ChannelFuture channelFuture = channel.closeFuture();
                channelFuture.addListener(future -> scheduledFuture.removeListener(listener));
            }
        }, internal, TimeUnit.SECONDS);

        listener = (future) -> sendHeartbeat(channel);

        scheduledFuture.addListener(listener);
    }
}
