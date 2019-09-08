package com.study.plainjava.workqueue;

import com.rabbitmq.client.*;
import com.study.plainjava.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        newApi();
    }

    public static void newApi() throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("message 0: " + new String(body));
            }
        };

        // 自动应答
        boolean autoAck = true;
        channel.basicConsume(Sender.workQueue, autoAck, defaultConsumer);
    }
}
