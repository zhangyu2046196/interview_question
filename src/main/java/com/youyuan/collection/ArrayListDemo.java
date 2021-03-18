package com.youyuan.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhangy
 * @version 1.0
 * @description ArrayList集合类线程不安全的场景及解决方案
 * <p>
 * 1、线程不安全异常描述
 * java.util.ConcurrentModifycationException   并发修改异常
 * 2、线程不安全异常产生原因
 * 并发争抢资源导致
 * <p>
 * 3、解决方案
 * 3.1、使用new Vector   因为Vector的add方法加锁了
 * 3.2、使用Collections工具类的synchronizedList包装一下  Collections.sysnchronizedList(new ArrayList<>())
 * 3.3、使用new CopyOnWriteArrayList<>()
 *       CopyOnWriteArrayList集合类的思想是写时复制，读写分离思想
 *
 *
 *
 *
 * @date 2019/10/16 11:21
 */
public class ArrayListDemo {

    public static void main(String[] args) {
//        List<String> list = new ArrayList<String>();  //add方法没有加锁，所以线程不安全
//        List<String> list = new Vector<String>();  //add方法加锁了，所以线程安全
//        List<String> list = Collections.synchronizedList(new ArrayList<String>());  //使用Collections的synchronizedList包装一下
        List<String> list = new CopyOnWriteArrayList<String>();  //juc包中的类，并发编程安全的类，写时复制，读写分离思想

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, i + "").start();
        }



    }

}
