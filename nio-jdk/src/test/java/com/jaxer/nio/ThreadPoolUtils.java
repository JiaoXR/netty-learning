package com.jaxer.nio;

import java.util.concurrent.*;

/**
 * 线程池工具类
 * Created on 2020/7/12 11:18
 *
 * @author jaxer
 */
public class ThreadPoolUtils {
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void execute(Runnable task) {
        executorService.execute(task);
    }
}
