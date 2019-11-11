package com.youyuan.aba;

import lombok.*;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangy
 * @version 1.0
 * @description 解决ABA问题的方法之原子引用
 * @date 2019/10/15 21:05
 */
public class AtomicRefreceDemo {

    public static void main(String[] args) {

        User u1 = new User("北京", 21);
        User u2 = new User("上海", 22);

        //原子引用类
        AtomicReference<User> atomicReference = new AtomicReference<User>();

        atomicReference.set(u1); //初始值

        System.out.println(atomicReference.compareAndSet(u1, u2) + "\t current value:" + atomicReference.get());
        System.out.println(atomicReference.compareAndSet(u1, u2) + "\t current value:" + atomicReference);
    }

}

@AllArgsConstructor
@Data
class User implements Serializable {

    private static final long serialVersionUID = 3942788720706461034L;

    private String userName;

    private Integer age;
}
