package com.youyuan.aba;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author zhangy
 * @version 1.0
 * @description ABA问题解决方案 原子引用+版本号(时间戳)
 * @date 2019/10/15 21:27
 */
public class ABADemo {

    private static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(100);  //原子引用

    private static AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<Integer>(100, 1); //带版本号的原子引用

    public static void main(String[] args) {
        System.out.println("========以下是产生ABA的问题========");

        new Thread(() -> {
            atomicReference.compareAndSet(100, 101); //修改为101
            atomicReference.compareAndSet(101, 100); //又修改回100
        }, "t1").start();

        new Thread(() -> {
            //休眠1秒钟，等待t1线程执行完
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行结果为:" + atomicReference.compareAndSet(100, 2019) + "\t当前值是:" + atomicReference);
        }, "t2").start();

        //休眠3秒
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("========以下是解决ABA问题========");

        new Thread(() -> {
            //休眠1秒钟等待t4线程获取到版本号
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //t3线程模拟ABA问题
            //修改值，第一个参数为预期值，第二个参数要改的值，第三个参数获取的版本号(时间戳)，第四个参数当前版本号(时间戳)+1
            System.out.println(Thread.currentThread().getName() + "第1次获取版本号" + integerAtomicStampedReference.getStamp());
            integerAtomicStampedReference.compareAndSet(100, 101, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "第2次获取版本号" + integerAtomicStampedReference.getStamp());
            integerAtomicStampedReference.compareAndSet(101, 100, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "第3次获取版本号" + integerAtomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            int stamp = integerAtomicStampedReference.getStamp();  //由于t3线程先休眠1秒钟，所以拿到的版本号是t3执行ABA前的版本号
            //休眠3秒钟等待t3线程完成ABA的操作
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行结果为" + integerAtomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1) + "\t当前版本号" + integerAtomicStampedReference.getStamp() + "\t当前值:" + integerAtomicStampedReference.getReference());
        }, "t4").start();

    }

}
