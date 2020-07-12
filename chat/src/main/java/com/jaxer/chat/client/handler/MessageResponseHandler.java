package com.jaxer.chat.client.handler;

import com.jaxer.chat.protocol.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理消息响应
 *
 * @author jaxer
 * @date 2020/6/29 2:35 PM
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) throws Exception {
        // 展示消息内容
        System.err.println(responsePacket.getFromUserId() + ":" + responsePacket.getFromUsername() + " -> " + responsePacket.getContent());
    }
}
