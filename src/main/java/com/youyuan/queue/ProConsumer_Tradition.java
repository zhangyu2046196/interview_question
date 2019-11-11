package com.youyuan.queue;

import java.util.Locale;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangy
 * @version 1.0
 * @description 传统版的生产者和消费者
 * <p>
 * 步骤：
 * 线程操作资源类
 * 1、定义资源类
 * 2、加锁
 * 3、判断等待  (通过while循环方式判断，防止虚假唤醒)
 * 4、执行业务逻辑
 * 5、唤醒
 * 6、释放锁
 * <p>
 * 模拟：一个共享变量默认值0,两个线程A线程加1后B线程减1,循环操作5遍
 * @date 2019/10/18 8:19
 */
public class ProConsumer_Tradition {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();  //定义资源类

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                shareData.increment();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                shareData.decrement();
            }
        }, "B").start();

    }

}

/**
 * 资源类
 */
class ShareData {

    private volatile int number;  //定义共享变量

    private Lock lock = new ReentrantLock();  //定义锁，默认是非公平锁

    private Condition condition = lock.newCondition();  //定义线程通讯方式

    /**
     * 对共享变量执行加操作
     */
    public void increment() {
        lock.lock();  //加锁
        try {
            while (number != 0) {    //通过while循环判断防止虚假唤醒
                condition.await();  //线程阻塞等待
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);

            condition.signalAll();  //唤醒
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //释放锁
        }
    }

    /**
     * 执行减操作
     */
    public void decrement() {
        lock.lock();  //加锁
        try {
            while (number == 0) {   //通过while循环判断防止虚假唤醒
                condition.await();  //线程阻塞等待
            }

            number--;  //操作共享变量
            System.out.println(Thread.currentThread().getName() + "\t" + number);

            condition.signalAll();  //唤醒

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //释放锁
        }
    }
}
