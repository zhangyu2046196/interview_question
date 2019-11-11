package com.youyuan.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangy
 * @version 1.0
 * @description cas原理测试
 * @date 2019/10/15 17:10
 */
public class CasDemo {

    public static void main(String[] args) {
        // atomic包的底层原理是cas
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current value:" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current value:" + atomicInteger.get());
    }

}
