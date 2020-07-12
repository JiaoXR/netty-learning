package com.jaxer.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Created on 2020/7/12 11:31
 *
 * @author jaxer
 */
public class TestDatagramChannel {
    @Test
    public void sender() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello".getBytes());
        buffer.flip();
        channel.send(buffer, new InetSocketAddress("127.0.0.1", 8888));

        channel.close();
    }

    @Test
    public void receiver() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);

        datagramChannel.bind(new InetSocketAddress(8888));

        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isReadable()) {
                    handleReadable(datagramChannel, buffer);
                }
            }
        }

        datagramChannel.close();
        selector.close();
    }

    private void handleReadable(DatagramChannel datagramChannel, ByteBuffer buffer) {
        ThreadPoolUtils.execute(() -> {
            try {
                datagramChannel.receive(buffer);
                buffer.flip();
                System.out.println(new String(buffer.array()));
                buffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
