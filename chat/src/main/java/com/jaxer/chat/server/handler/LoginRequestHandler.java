package com.jaxer.chat.server.handler;

import com.jaxer.chat.protocol.packet.LoginRequestPacket;
import com.jaxer.chat.protocol.packet.LoginResponsePacket;
import com.jaxer.chat.session.Session;
import com.jaxer.chat.util.SessionUtil;
import com.jaxer.common.util.UUIDUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * 处理登录请求
 *
 * @author jaxer
 * @date 2020/6/29 2:29 PM
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    public static final ChannelHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) throws Exception {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        if (valid(requestPacket)) {
            responsePacket.setSuccess(true);
            String userId = UUIDUtil.getID();
            System.out.println("[" + requestPacket.getUsername() + "] 登录成功，userId : " + userId);

            responsePacket.setUserId(userId);
            responsePacket.setUsername(requestPacket.getUsername());
            SessionUtil.bindSession(new Session(userId, requestPacket.getUsername()), ctx.channel());
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("老子不开心");
            System.out.println(LocalDateTime.now() + " : 登录失败！");
        }

        ctx.channel().writeAndFlush(responsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 校验登录请求是否有效
     */
    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
