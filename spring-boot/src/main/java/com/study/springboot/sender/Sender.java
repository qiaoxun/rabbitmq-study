package com.study.springboot.sender;

import com.study.App;
import com.study.springboot.receiver.Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Sender implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Sender(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending...");
        rabbitTemplate.convertAndSend(App.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ");
        receiver.getCountDownLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
