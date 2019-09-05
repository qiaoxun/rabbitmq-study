package com.study.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    public final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // declare exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msg = "hello routing - Goods.spoon.using";
        channel.basicPublish(EXCHANGE_NAME, "Goods.spoon.using", null, msg.getBytes());

        System.out.println("sent msg: " + msg);

        channel.close();
        connection.close();
    }
}
