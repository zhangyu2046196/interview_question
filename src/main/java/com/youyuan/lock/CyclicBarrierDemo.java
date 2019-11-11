package com.youyuan.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhangy
 * @version 1.0
 * @description 与CountDownLatch相反，CountDownLatch是有个计数器等计数器为0代表线程执行完才执行后面的，而CyclicBarrier是计数器
 * <p>
 * 默认0每一个线程执行完就增加1，等加到指定的数后再执行后面的操作
 * <p>
 * <p>
 * 模拟：集齐七龙珠后召唤神龙
 * @date 2019/10/17 16:38
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        //定义CyclicBarrier有7个线程，执行完之后再执行后面召唤神龙的线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println(Thread.currentThread().getName() + "\t已经集齐七龙珠，开始召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            final int stamp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t已经集到第" + stamp + "个龙珠");
                try {
                    cyclicBarrier.await();  //计数器加1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }

}
