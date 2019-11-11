package com.youyuan.thread;

import ch.qos.logback.core.util.TimeUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description 实现Callable接口实现多线程的方式
 * <p>
 * 实现Callable接口和实现Runnable接口和继承Thread类实现多线程的区别
 * 1、实现Callable接口需要重写call方法
 * 2、call方法有返回值
 * 3、call方法有异常捕获
 * @date 2019/10/22 10:34
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread());  //实现Callable接口的多线程类需要用FutureTask包装

        new Thread(futureTask).start();  //启动

        int value1 = 100;
        Integer value2 = futureTask.get();  //获取call方法的返回值,因为执行call方法是异步执行，所以最好再最后在获取值，如果获取call方法的返回值时call方法还没有执行完，线程会阻塞，直到计算完成后
        System.out.println(Thread.currentThread().getName() + "\t 计算结果是:" + (value1 + value2));
    }
}

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t come in call");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}
