package com.youyuan.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangy
 * @version 1.0
 * @description 读写锁测试
 * <p>
 * 写锁是独占锁，同一时间只能有一个线程获取锁
 * 读锁是共享锁，同一时间能有多个线程获取锁，提高并发量
 * <p>
 * ReentrantLock、synchronized是独占锁
 * @date 2019/10/16 21:14
 */
public class ReentrantReadWriteLockDemo {

    private static MyCache myCache = new MyCache();

    public static void main(String[] args) {
        //模拟5个线程写入
        for (int i = 1; i <= 5; i++) {
            final int key = i;
            new Thread(() -> {
                myCache.myPut(key + "", key);
            }, i + "").start();
        }

        //模拟5个线程读取
        for (int i = 1; i <= 5; i++) {
            final int key = i;
            new Thread(() -> {
                myCache.myGet(key + "");
            }, i + "").start();
        }
    }

}

/**
 * 通过缓存模拟读写锁
 */
class MyCache {

    private volatile Map<String, Object> map = new HashMap<String, Object>();  //定义map存储数据信息

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();  //定义读写锁

    /**
     * 添加缓存信息
     *
     * @param key
     * @param value
     */
    public void myPut(String key, Object value) {
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 开始写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    /**
     * 从缓存读取信息
     *
     * @param key
     * @return
     */
    public Object myGet(String key) {
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 开始读取");
            Object object = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成");
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
        return null;
    }

}

