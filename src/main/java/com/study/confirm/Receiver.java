package com.study.confirm;

import com.rabbitmq.client.*;
import com.study.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                System.out.println("consumerTag: " + consumerTag);
                System.out.println("message: " + new String(body));
            }
        };
        channel.basicConsume(Sender.QUEUE_NAME, true, defaultConsumer);
    }
}
