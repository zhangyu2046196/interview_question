package com.youyuan.threadpool;

import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * @author zhangy
 * @version 1.0
 * @description 线程池测试
 * <p>
 * 重点：线程池的三种类型 固定大小线程池、单一线程池、缓存功能线程池
 * 底层都是使用的ThreadPoolExecutor
 * @date 2019/10/22 11:33
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        //自定义线程池
        ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 2L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        try {
            for (int i = 1; i <= 10; i++) {
                final int stamp = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 执行第" + stamp + "个用户请求");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();  //关闭线程放入队列
        }
    }

    private static void threadPoolInit() {
        //        ExecutorService executorService = Executors.newFixedThreadPool(5);  //一池5处理线程
//        ExecutorService executorService = Executors.newSingleThreadExecutor();  //一池1处理线程
        ExecutorService executorService = Executors.newCachedThreadPool();  //一池N处理线程

        //启动线程执行程序
        try {
            //模拟10个用户请求
            for (int i = 1; i <= 10; i++) {
                final int stamp = i;
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 执行第" + stamp + "个用户请求。");
                });
                TimeUnit.MILLISECONDS.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();  //关闭线程(线程放入队列)
        }
    }

}
