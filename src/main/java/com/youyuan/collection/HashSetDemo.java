package com.youyuan.collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zhangy
 * @version 1.0
 * @description HashSet线程不安全的集合类解决方案
 * <p>
 * 1、线程不安全异常描述
 * java.util.ConcurrentModifycationException   并发修改异常
 * 2、线程不安全异常产生原因
 * 并发争抢资源导致
 * <p>
 * 3、解决方案
 * 3.1、使用Collections工具类的synchronizedSet包装一下  Collections.sysnchronizedSet(new HashSet<>())
 * 3.2、使用new CopyOnWriteArraySet<>()
 * CopyOnWriteArraySet集合类的思想是写时复制，读写分离思想
 * <p>
 * HashSet底层是通过HashMap实现的，只不过HashMap的key是HashSet要存储数据对象，value是一个固定的空的Object对象
 * @date 2019/10/16 12:33
 */
public class HashSetDemo {

    public static void main(String[] args) {
//        Set<String> set = new HashSet<String>();  //线程不安全的集合类

//        Set<String> set = Collections.synchronizedSet(new HashSet<String>());  //Collections工具类包装HashSet

        Set<String> set = new CopyOnWriteArraySet<String>();  //CopyOnWriteArraySet  思想写入并复制，读写分离

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, i + "").start();
        }
    }
}
