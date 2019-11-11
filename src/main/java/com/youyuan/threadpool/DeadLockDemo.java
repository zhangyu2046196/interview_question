package com.youyuan.threadpool;

import ch.qos.logback.core.util.TimeUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description 死锁测试示例
 * <p>
 * 概念：
 * 两个或多个线程获取共享资源造成相互等待的现象
 * 原因：
 * 系统资源不足
 * 进程运行顺序不合理
 * 资源分配不合理
 * 问题定位方案：
 * windows通过jps命令查看java运行的进程号,linux可以通过ps -ef|grep java 来查看
 * windows通过jstack打印堆栈信息定位问题
 * 解决方案：
 * 禁止同步方法或同步代码块中在调用同步方法或同步代码块
 * @date 2019/10/23 11:31
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(() -> {
            DeadHoldLock deadHoldLock = new DeadHoldLock(lockA, lockB);
            deadHoldLock.deadLockTest();
        }, "AA").start();

        new Thread(() -> {
            DeadHoldLock deadHoldLock = new DeadHoldLock(lockB, lockA);
            deadHoldLock.deadLockTest();
        }, "BB").start();
    }

}

/**
 * 资源类
 */
class DeadHoldLock {
    private String lockA;  //A锁
    private String lockB;  //B锁

    public DeadHoldLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    public void deadLockTest() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 持有锁" + lockA + "\t 获取锁" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 持有锁" + lockB + "\t 获取锁" + lockA);
            }

        }
    }
}
