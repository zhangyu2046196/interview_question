package com.youyuan.oom;

/**
 * @author zhangy
 * @version 1.0
 * @description  栈内存溢出场景
 * @date 2019/10/29 8:29
 */
public class StackOverflowErrorDemo {

    public static void main(String[] args) {
        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError();
    }

}
