package com.youyuan.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description 单元素阻塞队列，不存储元素，生产完一个元素后必须被消费后才能生产下一个元素
 * <p>
 * 模拟：一个线程顺序往SynchronousQueue队列中插入元素，另一个线程顺序从SynchronousQueue中消费元素
 * @date 2019/10/18 7:22
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<String>();

        //线程A往SynchronousQueue插入元素
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put a");
                blockingQueue.put("a");

                System.out.println(Thread.currentThread().getName() + "\t put b");
                blockingQueue.put("b");

                System.out.println(Thread.currentThread().getName() + "\t put c");
                blockingQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();


        //线程B从SynchronousQueue队列中取出元素，为了演示效果每次取的时候休眠5秒钟
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t take "+blockingQueue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t take "+blockingQueue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t take "+blockingQueue.take());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

    }

}
