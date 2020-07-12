package com.jaxer.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Created on 2020/7/12 11:45
 *
 * @author jaxer
 */
public class TestPipe {
    @Test
    public void test01() throws IOException {
        Pipe pipe = Pipe.open();

        Pipe.SinkChannel sinkChannel = pipe.sink();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("hello, pipe".getBytes());
        buffer.flip();
        sinkChannel.write(buffer);
        buffer.clear();

        Pipe.SourceChannel sourceChannel = pipe.source();
        sourceChannel.read(buffer);
        System.out.println(new String(buffer.array()));

        sourceChannel.close();
        sinkChannel.close();
    }
}
