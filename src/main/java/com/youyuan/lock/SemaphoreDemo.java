package com.youyuan.lock;

import java.util.concurrent.Semaphore;

/**
 * @author zhangy
 * @version 1.0
 * @description Semaphore信号灯测试，多个共享资源的互斥以及多线程并发控制
 * <p>
 * 模拟：比如有3个停车位，此时有6辆车争抢，争抢到车位的线程休眠几秒钟后可以释放车位，此时别的线程又可以争抢
 * @date 2019/10/17 17:53
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);  //模拟初始化有3个停车位

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();  //争抢到车位
                    System.out.println(Thread.currentThread().getName() + "\t争抢到车位");
                    //以下为争抢到车位的线程休眠3秒钟然后释放车位
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t休眠3秒钟后释放车位");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();  //释放车位资源
                }
            }, String.valueOf(i)).start();
        }
    }

}
