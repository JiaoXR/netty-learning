package com.jaxer.rpc.client.proxy;

import com.jaxer.common.constant.CommonConstant;
import com.jaxer.rpc.api.invoke.InvokeMessage;
import com.jaxer.rpc.client.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created on 2020/6/27 23:57
 *
 * @author jaxer
 */
public class RpcProxy {
    /**
     * 创建给定类的代理对象
     *
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 若调用Object的方法（hashCode等），直接本地调用
                        // 否则再执行远程调用
                        if (Object.class.equals(method.getDeclaringClass())) {
                            return method.invoke(this, args);
                        }
                        return rpcInvoke(populateRpcParams(clazz, method, args));
                    }
                });
    }

    /**
     * 调用远程服务
     *
     * @param invokeMessage 调用信息
     * @return 返回调用结果
     */
    private static Object rpcInvoke(InvokeMessage invokeMessage) throws Exception {
        final RpcClientHandler handler = new RpcClientHandler();

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            ch.pipeline().addLast(handler);
                        }
                    });

            ChannelFuture future = bootstrap.connect(CommonConstant.LOCAL_HOST_IP, CommonConstant.NETTY_PORT).sync();
            System.out.println("成功连接到服务端！IP：" + CommonConstant.LOCAL_HOST_IP + ", 端口号：" + CommonConstant.NETTY_PORT);

            future.channel().writeAndFlush(invokeMessage).sync();

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        return handler.getResult();
    }

    /**
     * 封装调用信息
     *
     * @param clazz  调用的RPC接口
     * @param method 接口方法
     * @param args   接口参数
     * @return 封装的调用信息，用于发送给服务端
     */
    private static InvokeMessage populateRpcParams(Class<?> clazz, Method method, Object[] args) {
        InvokeMessage invokeMessage = new InvokeMessage();
        invokeMessage.setServiceName(clazz.getName());
        invokeMessage.setMethodName(method.getName());
        invokeMessage.setParameterTypes(method.getParameterTypes());
        invokeMessage.setParameterValues(args);
        return invokeMessage;
    }
}
