package com.jaxer.chat.server;

import com.jaxer.chat.codec.PacketDecoder;
import com.jaxer.chat.codec.PacketEncoder;
import com.jaxer.chat.codec.Splitter;
import com.jaxer.chat.server.handler.LoginRequestHandler;
import com.jaxer.chat.server.handler.MessageRequestHandler;
import com.jaxer.common.constant.CommonConstant;
import com.jaxer.common.util.NettyUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 简单聊天功能服务端实现
 *
 * @author jaxer
 * @date 2020/6/29 2:04 PM
 */
public class ChatServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new PacketEncoder());
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(new MessageRequestHandler());
                    }
                });

        NettyUtil.bindPortWithRetry(serverBootstrap, CommonConstant.NETTY_PORT);
    }
}
