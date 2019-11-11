package com.youyuan.gc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangy
 * @version 1.0
 * @description 程序获取服务器CPU个数，JVM初始化堆内存大小(-Xms),JVM最大堆内存大小(-Xmx)
 * @date 2019/10/23 21:50
 */
public class HeapSizeDemo {

    public static void main(String[] args) {
        System.out.println("CPU个数\t" + Runtime.getRuntime().availableProcessors());
        System.out.println("-Xms\t" + Runtime.getRuntime().totalMemory());
        System.out.println("-Xmx\t" + Runtime.getRuntime().maxMemory());
        HashMap<Integer,String> map=new HashMap<Integer,String>();
    }

}
