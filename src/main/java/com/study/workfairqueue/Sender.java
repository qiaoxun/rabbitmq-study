package com.study.workfairqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    public static String workQueue = "work_fair_queue_test";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(workQueue, false, false, false, null);

        /**
         * 每个消费者发送确认信息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         * 限制发送给同一个消费者不得超过一条信息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for (int i = 0; i < 50; i++) {
            String msg = "hello rabbit" + i;
            channel.basicPublish("", workQueue, null, msg.getBytes());
            System.out.println("send " + i + "; message: " + msg);
            Thread.sleep(200);
        }

        channel.close();
        connection.close();
    }
}
