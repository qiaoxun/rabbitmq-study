package com.study.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    public static String workQueue = "work_queue_test";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(workQueue, false, false, false, null);

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
