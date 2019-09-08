package com.study.plainjava.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.plainjava.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    public final static String EXCHANGE_NAME = "test_exchange_routing";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // declare exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String msg = "hello routing - info";
        channel.basicPublish(EXCHANGE_NAME, "info", null, msg.getBytes());

        System.out.println("sent msg: " + msg);

        channel.close();
        connection.close();
    }
}
