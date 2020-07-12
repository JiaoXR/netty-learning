package com.jaxer.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 非阻塞式NIO网络通信
 * Created on 2020/7/12 10:17
 *
 * @author jaxer
 */
public class TestNonBlockingNIO {
    @Test
    public void client() throws IOException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        // 设置非阻塞
        channel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((LocalDateTime.now() + ", hello").getBytes());
        buffer.flip();
        channel.write(buffer);

        channel.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));

        // 注册Selector
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务端启动成功...");

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    handleAcceptable(selector, serverSocketChannel);
                } else if (key.isReadable()) {
                    handleReadable(key, buffer);
                }
            }
        }

        serverSocketChannel.close();
        selector.close();
    }

    private void handleReadable(SelectionKey key, ByteBuffer buffer) {
        ThreadPoolUtils.execute(() -> {
            SocketChannel channel = (SocketChannel) key.channel();
            try {
                while (channel.read(buffer) != -1) {
                    buffer.flip();
                    System.out.println(new String(buffer.array()));
                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleAcceptable(Selector selector, ServerSocketChannel serverSocketChannel) {
        ThreadPoolUtils.execute(() -> {
            try {
                SocketChannel channel = serverSocketChannel.accept();
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
