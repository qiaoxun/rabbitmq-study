package com.study.springbootannotation;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.study.springbootannotation")
@SpringBootApplication
public class App
{
    public static final String EXCHANGE_NAME = "spring_boot_annotation_exchange";
    public static final String QUEUE_NAME = "spring_boot_annotation_queue";
    public static final String ROUTING_KEY = "foo.bar.#";


    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(ROUTING_KEY);
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
