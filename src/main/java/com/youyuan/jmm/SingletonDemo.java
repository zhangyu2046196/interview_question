package com.youyuan.jmm;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试volatile的应用场景，单例模式
 * @date 2019/10/15 12:30
 */
public class SingletonDemo {

    public volatile static SingletonDemo instance = null; //volatile修饰目的禁止指令重排

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "创建SingletonDemo对象......");
    }

    /**
     * 获取单例对象
     * DCL  double check lock 双重检测锁方式
     *
     * @return 返回单例对象
     */
    public static SingletonDemo getInstense() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程环境下测试单例模式
//        System.out.println(SingletonDemo.getInstense() == SingletonDemo.getInstense());
//        System.out.println(SingletonDemo.getInstense() == SingletonDemo.getInstense());
//        System.out.println(SingletonDemo.getInstense() == SingletonDemo.getInstense());
//
//        System.out.println("======================================");

        //高并发多线程环境下测试单例模式
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstense();
            }).start();
        }

    }


}
