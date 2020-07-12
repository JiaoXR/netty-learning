package com.jaxer.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.SortedMap;

/**
 * 测试Channel
 * Created on 2020/7/9 22:31
 *
 * @author jaxer
 */
public class TestChannel {
    // 测试编码和解码
    @Test
    public void test05() throws IOException {
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("我是奥特曼");

        // 获取编码器和解码器
        Charset charset = Charset.forName("GBK");
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder = charset.newDecoder();

        charBuffer.flip();
        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get());
        }

        byteBuffer.flip();
        CharBuffer decode = decoder.decode(byteBuffer);
        System.out.println(decode.toString());
    }

    @Test
    public void test04() {
        // 支持的字符编码集
        SortedMap<String, Charset> charsetMap = Charset.availableCharsets();
        for (Map.Entry<String, Charset> entry : charsetMap.entrySet()) {
            System.out.println(entry);
        }
    }

    // 使用 FileChannel.transferTo 方法读写
    @Test
    public void test03() throws IOException {
        // 通道之间的传输（直接缓冲区）
        FileChannel inChannel = FileChannel.open(Paths.get("01.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("02.jpg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);

        inChannel.transferTo(0, inChannel.size(), outChannel);

        inChannel.close();
        outChannel.close();
    }

    // 使用 FileChannel.map 方法读写
    @Test
    public void test02() throws Exception {
        // 使用直接缓冲区复制文件（内存映射文件）
        FileChannel inChannel = FileChannel.open(Paths.get("01.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("02.jpg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 内存映射文件
        MappedByteBuffer inMapperBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMapperBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 数据读写
        byte[] dst = new byte[inMapperBuffer.limit()];
        inMapperBuffer.get(dst);
        outMapperBuffer.put(dst);

        inChannel.close();
        outChannel.close();
    }

    @Test
    public void test01() throws Exception {
        // 通过Channel读写文件
        FileInputStream inputStream = new FileInputStream("01.jpg");
        FileOutputStream outputStream = new FileOutputStream("02.jpg");

        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        outChannel.close();
        inChannel.close();
        inputStream.close();
        outputStream.close();
    }
}
