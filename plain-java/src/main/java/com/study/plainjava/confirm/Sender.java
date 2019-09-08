package com.study.plainjava.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.plainjava.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    public final static String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        // create a channel
        Channel channel = connection.createChannel();
        // enable confirm
        channel.confirmSelect();
        // declare a queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message ="test_confirm";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        if (channel.waitForConfirms()) {
            System.out.println("sent out");
        } else {
            System.out.println("sent message failed");
        }

        // close the resources
        channel.close();
        connection.close();
    }
}
