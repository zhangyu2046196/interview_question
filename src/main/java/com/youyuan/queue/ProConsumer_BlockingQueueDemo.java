package com.youyuan.queue;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangy
 * @version 1.0
 * @description 线程间通信生产者/消费者之阻塞队列
 * <p>
 * 考察内容：volatile、CAS、atomicinteger、BlockingQueue、线程间的通信
 * <p>
 * 模拟：生产者生产数据，消费者消费数据，数据放入阻塞队列中
 * @date 2019/10/22 8:45
 */
public class ProConsumer_BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        ShareResource shareResource = new ShareResource(new ArrayBlockingQueue<String>(3));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产者线程启动成功");
            shareResource.producerData();
        }, "A").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费者线程启动成功");
            System.out.println();
            System.out.println();
            shareResource.consumerData();
        }, "B").start();

        TimeUnit.SECONDS.sleep(5);
        shareResource.relace();
        System.out.println(Thread.currentThread().getName() + "\t main线程5秒后叫停");
    }

}

/**
 * 资源类
 */
class ShareResource {
    private volatile boolean flag = true;  //标记位 true生产者生产消息,消费者消费消息  volatile修饰内存可见性
    private AtomicInteger atomicInteger = new AtomicInteger();  //原子操作类
    private BlockingQueue<String> blockingQueue;  //阻塞队列

    /**
     * 构造方法入参为接口 提升架构层次
     *
     * @param blockingQueue 阻塞队列接口
     */
    public ShareResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /**
     * 生产者生产消息
     */
    public void producerData() {
        boolean result;
        String data;
        try {
            //通过while循环避免虚假唤醒
            while (flag) {
                data = String.valueOf(atomicInteger.incrementAndGet());
                result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
                if (result) {
                    System.out.println(Thread.currentThread().getName() + "\t 生产者插入队列成功" + data);
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t 生产者插入队列失败" + data);
                }
                TimeUnit.SECONDS.sleep(1L);  //休眠两秒钟等待消费者消费
            }
            System.out.println(Thread.currentThread().getName() + "\t 老板叫停项目,flag=false");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费者消费消息
     */
    public void consumerData() {
        String data;  //消费消息信息
        try {
            //通过循环判断避免虚假唤醒
            while (flag) {
                data = blockingQueue.poll(2L, TimeUnit.SECONDS);
                if (StringUtils.isEmpty(data)) {
                    flag = false;
                    System.out.println(Thread.currentThread().getName() + "\t 队列为空");
                    return;
                }

                System.out.println(Thread.currentThread().getName() + "\t 消费队列消息" + data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改flag状态
     */
    public void relace() {
        this.flag = false;
    }
}
