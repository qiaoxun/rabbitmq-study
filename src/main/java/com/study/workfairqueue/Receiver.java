package com.study.workfairqueue;

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
        /**
         * 每个消费者发送确认信息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         * 限制发送给同一个消费者不得超过一条信息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

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
                } finally {
                    // 手动回执完成消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
                System.out.println("message 0: " + new String(body));
            }
        };

        // 自动应答
        boolean autoAck = false;
        channel.basicConsume(Sender.workQueue, autoAck, defaultConsumer);
    }
}
