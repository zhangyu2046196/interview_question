package com.youyuan.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangy
 * @version 1.0
 * @description 自定义自旋锁
 * <p>
 * 自旋锁是在获取锁的时候不会立即阻塞，如果获取不到通过循环的方式不断去尝试，好处是避免上下文切换造成性能损耗，缺点是
 * 循环会造成CPU性能损耗
 * @date 2019/10/16 19:02
 */
public class SpinLockDemo {

    private AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();  //定义原子引用，原子引用传的值是thread默认是空


    /**
     * 获取锁，通过自旋锁的方式
     */
    public void myLock() {
        Thread thread = Thread.currentThread();  //获取当前线程，用当前线程对象作为CAS的期望值
        System.out.println(thread.getName() + "\t come in myLock");
        //判断期望值是不是null，如果是null把当前线程放入获取锁
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    /**
     * 释放锁
     */
    public void unLock() {
        Thread thread = Thread.currentThread();  //获取当前线程，用当前线程对象作为CAS的期望值
        System.out.println(thread.getName() + "\t invoked unLock");
        //释放锁，如果当前期望值是当前线程，设置为空，使别的线程能获取锁
        atomicReference.compareAndSet(thread, null);
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();  //获取锁
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();  //释放锁
        }, "A").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.myLock();  //获取锁
            spinLockDemo.unLock();  //释放锁
        }, "B").start();

    }

}
