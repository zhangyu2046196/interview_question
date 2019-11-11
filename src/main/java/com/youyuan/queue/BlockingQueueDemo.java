package com.youyuan.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description 阻塞队列测试  队列遵循FIFO规则
 * @date 2019/10/17 21:50
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);  //定义一个有界长度为3的阻塞队列

        //抛出异常组
        //当插入元素个数超出定义的队列长度3时或者当队列为空在删除是报错
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));

        System.out.println("element检测方法取出第一个元素" + blockingQueue.element());  //element方法是检测读取到队列中第一个元素

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());

        BlockingQueue<Integer> integerBlockingQueue = new ArrayBlockingQueue<Integer>(3); //定义一个有界长度为3的阻塞队列
        //特殊值
        //当插入成功返回true,插入元素个数超出队列长度3时返回false,当移除队列元素时返回队列元素，超出队列长度移除时返回null

        System.out.println(integerBlockingQueue.offer(1));
        System.out.println(integerBlockingQueue.offer(2));
        System.out.println(integerBlockingQueue.offer(3));

        System.out.println(integerBlockingQueue.poll());
        System.out.println(integerBlockingQueue.poll());
        System.out.println(integerBlockingQueue.poll());
        System.out.println(integerBlockingQueue.poll());

        BlockingQueue<String> stringBlockingQueue = new ArrayBlockingQueue<String>(3);  //定义一个有界队列长度为3的阻塞队列
        //阻塞
        //当插入队列的总数小于队列长度时直接插入，大于队列长度时阻塞，查询的时候如果队列为空时阻塞

        stringBlockingQueue.put("a");
        stringBlockingQueue.put("b");
        stringBlockingQueue.put("c");
//        stringBlockingQueue.put("d");

        System.out.println(stringBlockingQueue.take());
        System.out.println(stringBlockingQueue.take());
        System.out.println(stringBlockingQueue.take());
//        System.out.println(stringBlockingQueue.take());

        BlockingQueue<Integer> blockingQueue1 = new ArrayBlockingQueue<Integer>(3);  //定义一个有界队列长度是3的阻塞队列
        //超时
        //插入元素和查询元素时设置过期时间，当插入元素个数小于阻塞队列长度或查询元素不为空时直接操作成功，当插入元素个数大于阻塞队列长度或查询元素阻塞队列为空时等待超时时间，超时时间过后结束

        System.out.println(blockingQueue1.offer(1, 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue1.offer(2, 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue1.offer(3, 2L, TimeUnit.SECONDS));
//        System.out.println(blockingQueue1.offer(5, 2L, TimeUnit.SECONDS));

        System.out.println(blockingQueue1.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue1.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue1.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue1.poll(2L, TimeUnit.SECONDS));

    }

}
