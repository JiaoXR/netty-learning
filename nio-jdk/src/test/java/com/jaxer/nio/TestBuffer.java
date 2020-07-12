package com.jaxer.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created on 2020/7/8 23:32
 *
 * @author jaxer
 */
public class TestBuffer {
    private static final String TEST_STRING = "hello";

    @Test
    public void test04() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        printBufferInfo(byteBuffer, "allocate");

        byteBuffer.put(TEST_STRING.getBytes());
        printBufferInfo(byteBuffer, "put");

        byteBuffer.flip();
        printBufferInfo(byteBuffer, "flip1");

        byteBuffer.flip();
        printBufferInfo(byteBuffer, "flip2");

        byteBuffer.clear();
        printBufferInfo(byteBuffer, "clear");
    }

    @Test
    public void test03() {
        // 分配直接缓冲区内存空间
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());
    }

    @Test
    public void test02() {
        // mark & reset 方法演示
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(TEST_STRING.getBytes());
        printBufferInfo(buffer, "put");

        buffer.flip();
        printBufferInfo(buffer, "flip");

        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes, 0, 2);
        printBufferInfo(buffer, "get");
        System.out.println(new String(bytes, 0, 2));

        buffer.mark();

        buffer.get(bytes, 2, 2);
        printBufferInfo(buffer, "get");
        System.out.println(new String(bytes, 2, 2));

        // reset: 重置position为mark的位置
        buffer.reset();
        printBufferInfo(buffer, "reset");
    }

    @Test
    public void test01() {
        // 分配空间（JVM内存）
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        printBufferInfo(buffer, "allocate");

        // 写数据
        buffer.put(TEST_STRING.getBytes());
        printBufferInfo(buffer, "put");

        // 切换（读/写）模式
        buffer.flip();
        printBufferInfo(buffer, "flip");

        // 读数据
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        printBufferInfo(buffer, "get");
        System.out.println("读取的数据为：" + new String(bytes));

        // rewind 可重复读数据
        buffer.rewind();
        printBufferInfo(buffer, "rewind");

        // 读数据
        byte[] bytes1 = new byte[buffer.limit()];
        buffer.get(bytes1);
        printBufferInfo(buffer, "get1");
        System.out.println("读取的数据为：" + new String(bytes1));

        // 清空缓冲区
        buffer.clear();
        printBufferInfo(buffer, "clear");

        // PS: 清空缓冲区之后，里面的数据还在
        byte[] bytes2 = new byte[5];
        buffer.get(bytes2);
        printBufferInfo(buffer, "get2");
        System.out.println("读取的数据为：" + new String(bytes2));
    }

    private void printBufferInfo(ByteBuffer buffer, String methodName) {
        System.out.println("--------- " + methodName + " -----------");
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("limit: " + buffer.limit());
        System.out.println("position: " + buffer.position());
    }
}
