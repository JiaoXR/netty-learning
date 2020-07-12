package com.jaxer.chat.client.handler;

import com.jaxer.chat.protocol.packet.LoginResponsePacket;
import com.jaxer.chat.session.Session;
import com.jaxer.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理登录响应
 *
 * @author jaxer
 * @date 2020/6/29 2:35 PM
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) throws Exception {
        String userId = responsePacket.getUserId();
        String username = responsePacket.getUsername();

        // 登录成功
        if (responsePacket.isSuccess()) {
            System.out.println("[" + username + "] 登录成功！userId：" + userId);
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
        } else {
            System.err.println("[" + username + "] 登录失败！userId：" + userId);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭");
    }
}
