package com.jaxer.chat.server.handler;

import com.jaxer.chat.protocol.packet.MessageRequestPacket;
import com.jaxer.chat.protocol.packet.MessageResponsePacket;
import com.jaxer.chat.session.Session;
import com.jaxer.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

/**
 * 处理发送的消息
 *
 * @author jaxer
 * @date 2020/6/29 3:05 PM
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requestPacket) throws Exception {
        /*
            1. 获取消息内容（toUserId，content）
            2. 获取发信人信息（userId）
            3. 获取接收人Channel，封装Response，发给收信人
         */
        // 1. 消息内容
        String toUserId = requestPacket.getToUserId();
        Channel toUserChannel = SessionUtil.getChannel(toUserId);
        if (Objects.isNull(toUserChannel) || !SessionUtil.hasLogin(toUserChannel)) {
            System.err.println("[" + toUserId + "] 不在线，消息发送失败!");
            return;
        }

        // 2. 发信人信息
        Session fromUserSession = SessionUtil.getSession(ctx.channel());
        String fromUserId = fromUserSession.getUserId();
        String fromUsername = fromUserSession.getUsername();

        // 3. 将消息发送给接收方
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromUserId(fromUserId);
        responsePacket.setFromUsername(fromUsername);
        responsePacket.setContent(requestPacket.getContent());
        toUserChannel.writeAndFlush(responsePacket);
    }
}
