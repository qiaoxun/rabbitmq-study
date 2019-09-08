package com.study.springbootannotation.receiver;

import com.study.springbootannotation.App;
import com.study.springbootannotation.domain.Person;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = App.QUEUE_NAME)
public class Receiver {

    @RabbitHandler
    public void handler(Person person) {
        System.out.println("person: " + person);
    }

}
