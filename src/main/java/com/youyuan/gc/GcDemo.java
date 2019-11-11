package com.youyuan.gc;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description  测试JVM参数类型中的XX参数中的boolean类型
 *
 * 测试正在运行的java进程中的PrintGCDeatils参数值
 * 1、使用jps查看正在运行的java进程号
 * 2、使用jinfo -flag PrintGCDetails 进程号查看JVM参数值
 *
 * @date 2019/10/23 18:18
 */
public class GcDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello gc PrintGCDetails value");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

}
