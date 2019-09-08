package com.study.springboot.receiver;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void receiveMsg(String message) {
        System.out.println("receiver msg: " + message);
        countDownLatch.countDown();
    }

    public CountDownLatch getCountDownLatch() {
        return  countDownLatch;
    }
}
