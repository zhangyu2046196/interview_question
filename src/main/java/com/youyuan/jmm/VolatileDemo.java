package com.youyuan.jmm;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试volatile内存可见性
 * @date 2019/9/30 10:39
 */
public class VolatileDemo {

    public static void main(String[] args) {
        //初始化共享资源对象
        MyData myData = new MyData();

        //定义第一个变量来修改number值
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " come in");
            //休眠3秒钟 模拟高并发场景
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.updateTo98();
            System.out.println(Thread.currentThread().getName() + " update value:" + myData.number);
        }, "AAA").start();

        while (myData.number == 0) {
            // 主线程模拟阻塞，只有当number被AAA修改后并且通知main线程后才结束
        }

        //主线程打印
        System.out.println(Thread.currentThread().getName() + " get number:" + myData.number);
    }

}

class MyData {
//    int number = 0;
    volatile int number=0;  //演示用volatile修饰变量和不修饰变量主线程main是否能变量修改

    public void updateTo98() {
        this.number = 98;
    }
}
