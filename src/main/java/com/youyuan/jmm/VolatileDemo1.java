package com.youyuan.jmm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试volatile不保证原子性
 * <p>
 * 背景：
 * 定义一个变量number初始值0,执行一个方法number++,当20个线程每个线程调用number++ 1000次时 正常结果应该是20000,因为volatile不保证原子性
 * 所以结果不一定是20000
 * @date 2019/9/30 11:51
 */
public class VolatileDemo1 {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch=new CountDownLatch(20);

        //定义共享资源变量
        MyData1 mdata = new MyData1(countDownLatch);

        for (int i = 1; i <=20 ; i++) {
            new Thread(mdata).start();
        }

        countDownLatch.await();



        //主线程main获取计算结果
        System.out.println(Thread.currentThread().getName() + " count value:" + mdata.count);
        System.out.println(Thread.currentThread().getName() + " number value:" + mdata.number);
        System.out.println(Thread.currentThread().getName() + " AtomicInteger value:" + mdata.atomicInteger);
    }

}

class MyData1 implements Runnable {

    int count = 0;  //没有任何修饰

    volatile int number = 0;  //volatile修饰,保证可见性不保证原子性

    AtomicInteger atomicInteger = new AtomicInteger();  //原子性整数操作类

    private CountDownLatch countDownLatch;

    public MyData1(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    /**
     * 线程不安全,不保证原子性
     */
    public void add() {
        count++;
    }

    /**
     * 通过synchronized保证线程安全和原子性
     */
    public synchronized void addPlus() {
        number++;
    }

    /**
     * 使用atomic包的类保证线程安全和原子性
     */
    public void addMyAtomicInteger() {
        atomicInteger.getAndIncrement();
    }

    @Override
    public void run() {
        try {
            for (int j = 1; j <= 1000; j++) {
                add();
                addPlus();
                addMyAtomicInteger();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
