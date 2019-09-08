package com.study.plainjava.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.plainjava.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    public final static String EXCHANGE_NAME = "test_exchange_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // declare exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msg = "hello routing - Goods.spoon.using";

        try {
            channel.txSelect();
            channel.basicPublish(EXCHANGE_NAME, "Goods.spoon.using", null, msg.getBytes());
            int i = 1/0;
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            e.printStackTrace();
        }

        System.out.println("sent msg: " + msg);

        channel.close();
        connection.close();

    }
}
