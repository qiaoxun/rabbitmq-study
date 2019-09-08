package com.study.springbootannotation.sender;

import com.study.springbootannotation.App;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Sender implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        int i = 0;
        while (i < 10) {
            rabbitTemplate.convertAndSend(App.EXCHANGE_NAME, App.ROUTING_KEY, ("Hello from Rabbit Spring Boot Annotation, " + i));
            Thread.sleep(1000);
            i++;
        }
    }
}
