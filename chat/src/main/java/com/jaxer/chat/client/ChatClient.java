package com.jaxer.chat.client;

import com.jaxer.chat.client.handler.LoginResponseHandler;
import com.jaxer.chat.client.handler.MessageResponseHandler;
import com.jaxer.chat.codec.PacketDecoder;
import com.jaxer.chat.codec.PacketEncoder;
import com.jaxer.chat.codec.Splitter;
import com.jaxer.chat.util.ClientUtil;
import com.jaxer.common.constant.CommonConstant;
import com.jaxer.common.util.NettyUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 简单聊天功能客户端实现
 *
 * @author jaxer
 * @date 2020/6/29 2:33 PM
 */
public class ChatClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(new PacketEncoder());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                    }
                });

        ChannelFuture channelFuture = NettyUtil.connect2Server(bootstrap,
                CommonConstant.LOCAL_HOST_IP, CommonConstant.NETTY_PORT, 3).sync();
        if (channelFuture.isSuccess()) {
            ClientUtil.startConsoleThread(channelFuture.channel());
        }
    }
}
