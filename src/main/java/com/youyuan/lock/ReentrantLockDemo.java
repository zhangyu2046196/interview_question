package com.youyuan.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangy
 * @version 1.0
 * @description 可重入锁测试
 * <p>
 * 可重入锁也叫递归锁
 * <p>
 * 指的是同一线程外层函数获取锁后，内层递归函数也同样获取锁的代码
 * <p>
 * 同一个线程在外层函数获取锁的时候，在进入内层函数时会自动获取锁
 * <p>
 * <p>
 * ReentrantLock和synchronized都是默认的非公平的可重入锁
 * <p>
 * 作用：
 * 可重入锁的作用就是避免死锁
 * @date 2019/10/16 17:41
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSMS();
        }, "t1").start();

        new Thread(() -> {
            phone.sendSMS();
        }, "t2").start();

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("==============================");

        new Thread(new Message(), "t3").start();

        new Thread(new Message(), "t4").start();
    }

}

/**
 * synchronized可重入锁测试
 */
class Phone {

    /**
     * synchronized是非公平的可重入锁
     */
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail");
    }

}

/**
 * ReentrantLock可重入锁测试
 */
class Message implements Runnable {

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "\t invoked get");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "\t invoked set");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
