package com.jaxer.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 阻塞式NIO网络通信
 * Created on 2020/7/12 00:16
 *
 * @author jaxer
 */
public class TestBlockingNIO {
    @Test
    public void client() throws Exception {
        // 客户端：从本地读取一张图片文件，发送给服务端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        FileChannel fileChannel = FileChannel.open(Paths.get("01.jpg"), StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        socketChannel.close();
        fileChannel.close();
    }

    @Test
    public void server() throws IOException {
        // 服务端：从客户端接收图片文件，写入本地
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        SocketChannel channel = serverSocketChannel.accept();

        System.out.println("服务端启动成功...");

        FileChannel fileChannel = FileChannel.open(Paths.get("02.jpg"),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (channel.read(buffer) != -1) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }

        fileChannel.close();
        channel.close();
        serverSocketChannel.close();
    }
}
