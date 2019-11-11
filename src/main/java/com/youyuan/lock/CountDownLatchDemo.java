package com.youyuan.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhangy
 * @version 1.0
 * @description CountDownLatch测试
 * <p>
 * 原理：
 * 里面有一个计数器，初始值是线程的数量，当一个线程执行完成后调用countDown()方法使计数器减1，直到计数器的值为0时在执行其它线程
 * @date 2019/10/17 7:56
 */
public class CountDownLatchDemo {

    private static final int COUNT = 6;  //初始线程数量

    private static CountDownLatch countDownLatch = new CountDownLatch(COUNT);  //定义CountDownLatch

    public static void main(String[] args) throws InterruptedException {

        unfiyContry();

    }

    /**
     * 模拟秦国统一六国，需要先将六国灭亡
     *
     * @throws InterruptedException
     */
    private static void unfiyContry() throws InterruptedException {
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国被灭亡");
                countDownLatch.countDown();  //计数器减1
            }, ContryEnum.foreach_CountryEnum(i).getContryMsg()).start();
        }

        countDownLatch.await();  //主线程等待

        System.out.println(Thread.currentThread().getName() + "\t秦国统一华夏");
    }

    /**
     * 模拟教室有班长和其它六个同学，等其它同学都离开后班长最后锁门
     *
     * @throws InterruptedException
     */
    private static void closeDoor() throws InterruptedException {
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 同学离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await(); //主线程等待

        System.out.println(Thread.currentThread().getName() + "\t 班长锁门离开教室");
    }

}

/**
 * 定义国家的枚举
 */
enum ContryEnum {

    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "韩"), SIX(6, "魏");

    @Getter
    private int contryCode;
    @Getter
    private String contryMsg;

    ContryEnum(int contryCode, String contryMsg) {
        this.contryCode = contryCode;
        this.contryMsg = contryMsg;
    }

    /**
     * 循环遍历获取指定的枚举
     *
     * @param contryCode
     * @return
     */
    public static ContryEnum foreach_CountryEnum(int contryCode) {
        ContryEnum[] values = ContryEnum.values();
        for (ContryEnum contry : values) {
            if (contry.getContryCode() == contryCode) {
                return contry;
            }
        }
        return null;
    }


}

