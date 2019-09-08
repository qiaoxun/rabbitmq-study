package com.study.plainjava.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.study.plainjava.utils.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class SenderAsync {
    public final static String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        // create a channel
        Channel channel = connection.createChannel();
        // enable confirm
        channel.confirmSelect();
        // declare a queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleAck --- multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleAck --- multiple == false");
                    confirmSet.remove(deliveryTag);
                }
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleNack --- multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleNack --- multiple == false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "test test";

        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }

    }
}
