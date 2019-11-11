package com.youyuan.lock;

import java.util.Locale;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试condition的精确唤醒
 * <p>
 * 模拟:A线程打印5遍  B线程打印10遍  C线程打印15遍  A、B、C三个线程都打印10遍
 * @date 2019/10/22 7:49
 */
public class ConditionDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.printA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.printC();
            }
        }, "C").start();
    }

}

/**
 * 资源类
 */
class ShareData {
    private int number = 1;  //标志位 1代表线程A  2代表线程B  3代表线程C
    private Lock lock = new ReentrantLock();  //定义锁，三个线程轮询操作需要用一把锁
    private Condition conditionA = lock.newCondition();  //线程A的condition
    private Condition conditionB = lock.newCondition();  //线程B的condition
    private Condition conditionC = lock.newCondition();  //线程C的condition

    /**
     * 线程A执行的打印方法
     */
    public void printA() {
        //加锁
        try {
            lock.lock();
            //通过循环判断标志位来避免虚假唤醒
            while (number != 1) {
                conditionA.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //修改标志位
            number = 2;
            //唤醒B线程
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 线程B执行的打印方法
     */
    public void printB() {
        //加锁
        try {
            lock.lock();
            //通过循环判断标志位来避免虚假唤醒
            while (number != 2) {
                conditionB.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //修改标志位
            number = 3;
            //唤醒B线程
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 线程C执行的打印方法
     */
    public void printC() {
        //加锁
        try {
            lock.lock();
            //通过循环判断标志位来避免虚假唤醒
            while (number != 3) {
                conditionC.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //修改标志位
            number = 1;
            //唤醒B线程
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
