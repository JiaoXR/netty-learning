package com.jaxer.beat.client;

import com.jaxer.CommonConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 心跳检测客户端
 * Created on 2020/6/26 00:57
 *
 * @author jaxer
 */
public class HeartbeatClient {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new HeartbeatClientHandler(bootstrap));
                    }
                })
                .connect(CommonConstant.LOCAL_HOST_IP, CommonConstant.NETTY_PORT);
    }
}
