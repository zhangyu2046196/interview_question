package com.youyuan.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangy
 * @version 1.0
 * @description HashMap不安全解决方案
 * <p>
 * 1、线程不安全异常描述
 * java.util.ConcurrentModifycationException   并发修改异常
 * 2、线程不安全异常产生原因
 * 并发争抢资源导致
 * <p>
 * 3、解决方案
 * 3.1、使用Collections工具类的synchronizedMap包装一下  Collections.sysnchronizedMap(new ArrayList<>())
 * 3.2、使用new ConcurrentHashMap<>()
 * CurrentHashMap集合类的思想是写时复制，读写分离思想
 * @date 2019/10/16 12:56
 */
public class HashMapDemo {

    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<String, String>(); //线程不安全集合类

//        Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());  //Collections工具类包装

        Map<String, String> map = new ConcurrentHashMap<String, String>();  //juc包中的并发编程map

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, i + "").start();
        }
    }

}
