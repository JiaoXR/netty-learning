package com.jaxer.dubbo.provider.server;

import com.jaxer.common.constant.CommonConstant;
import com.jaxer.dubbo.provider.handler.DubboProviderHandler;
import com.jaxer.dubbo.provider.registey.RegistryCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * 简单RPC实现-服务端
 * Created on 2020/6/26 18:35
 *
 * @author jaxer
 */
public class DubboProvider {
    // 缓存解析的类名
    private List<String> classCache = new ArrayList<>();
    // 注册中心
    private Map<String, Object> registry = new HashMap<>();

    /**
     * 启动服务
     */
    public void start() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new DubboProviderHandler(registry));
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(CommonConstant.NETTY_PORT).sync();
            System.out.println("服务端启动成功！端口号：" + CommonConstant.NETTY_PORT);
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * 将给定包及其子包下的所有类注册到注册中心
     *
     * @param pkgName        要注册的包名
     * @param serviceAddress 服务地址
     * @param registryCenter 注册中心
     */
    public void publish(String pkgName, String serviceAddress, RegistryCenter registryCenter) throws Exception {
        /*
            1. 获取包下所有类的全类名
            2. 注册到注册中心
         */
        parseClassName(pkgName);
        System.out.println("解析的所有类名：" + classCache);

        doRegister(serviceAddress, registryCenter);
        System.out.println("注册中心信息：" + registry);
    }

    /**
     * 将类的接口名注册到注册中心
     * 格式：<接口名，实例>
     *
     * @param serviceAddress 服务地址
     * @param registryCenter 注册中心
     */
    private void doRegister(String serviceAddress, RegistryCenter registryCenter) throws Exception {
        for (String className : classCache) {
            Class<?> aClass = Class.forName(className);
            String interfaceName = aClass.getInterfaces()[0].getName();
            System.out.println("interfaceName = " + interfaceName);

            // 注册到本地列表
            registry.put(interfaceName, aClass.newInstance());

            // 注册到ZK
            registryCenter.register(interfaceName, serviceAddress);
        }
    }

    /**
     * 解析给定包及其子包下所有类的全类名
     *
     * @param pkgName 给定包名
     */
    private void parseClassName(String pkgName) {
        // 文件路径名
        String filePath = pkgName.replaceAll("\\.", "/");
        URL resource = this.getClass().getClassLoader().getResource(filePath);

        assert resource != null;
        File dir = new File(resource.getFile());
        if (Objects.isNull(dir.listFiles())) {
            return;
        }
        // 遍历目录下的所有文件：若是文件，获取并添加类名；若是目录，递归
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            String fullFileName = pkgName + "." + file.getName();
            if (file.isDirectory()) {
                parseClassName(fullFileName);
            } else {
                String fullName = fullFileName.replace(".class", "");
                classCache.add(fullName);
            }
        }
    }
}
