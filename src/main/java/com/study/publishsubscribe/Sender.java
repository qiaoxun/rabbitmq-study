package com.study.publishsubscribe;

import com.rabbitmq.client.*;
import com.study.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    public final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // declare exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String msg = "hello publish/subscribe";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

        System.out.println("sent msg: " + msg);

        channel.close();
        connection.close();
    }
}
