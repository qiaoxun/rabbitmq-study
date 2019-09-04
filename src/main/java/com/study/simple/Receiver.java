package com.study.simple;

import com.rabbitmq.client.*;
import com.study.utils.ConnectionUtils;

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
                System.out.println("consumerTag: " + consumerTag);
                System.out.println("body: " + new String(body));
            }
        };
        channel.basicConsume("test_queue", true, defaultConsumer);
    }

    public static void oldApi() throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume("test_queue", true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("receive msg: " + message);
        }
    }


}
