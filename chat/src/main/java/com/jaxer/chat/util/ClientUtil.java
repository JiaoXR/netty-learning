package com.jaxer.chat.util;

import com.jaxer.chat.protocol.packet.LoginRequestPacket;
import com.jaxer.chat.protocol.packet.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jaxer
 * @date 2020/6/30 3:05 PM
 */
public class ClientUtil {
    /**
     * 接收控制台输入的消息
     */
    public static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                // 未登录
                if (!SessionUtil.hasLogin(channel)) {
                    System.out.print("请输入用户名登录: ");

                    String nextLine = scanner.nextLine();
                    LoginRequestPacket requestPacket = new LoginRequestPacket();
                    requestPacket.setUsername(nextLine.trim());
                    channel.writeAndFlush(requestPacket);

                    waitForLoginResponse();
                } else {
                    // 已登录
                    System.out.print("请输入要发送的userId和消息:\n");

                    String toUserId = scanner.next();
                    String msg = scanner.next();

                    MessageRequestPacket msgPacket = new MessageRequestPacket();
                    msgPacket.setToUserId(toUserId.trim());
                    msgPacket.setContent(msg.trim());
                    channel.writeAndFlush(msgPacket);
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
