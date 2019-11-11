package com.youyuan.oom;

import java.util.Random;

/**
 * @author zhangy
 * @version 1.0
 * @description oom之java heap space堆内存溢出场景，创建大量对象
 * @date 2019/10/29 8:31
 */
public class JavaHeapSpaceDemo {

    public static void main(String[] args) {

//        byte[] bytes=new byte[80*1024*1024];

        String str = "beijing huanying nin";
        while (true) {
            str += str + new Random().nextInt(1111111) + new Random().nextInt(22222222);
        }
    }

}
