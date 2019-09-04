package com.study.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("test_queue", false, false, false, null);
        String msg = "hello rabbit 3";
        channel.basicPublish("", "test_queue", null, msg.getBytes());

        channel.close();
        connection.close();
    }
}
