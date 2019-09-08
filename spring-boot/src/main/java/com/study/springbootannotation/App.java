package com.study.springbootannotation;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Auto RabbitAutoConfiguration
 * 1. ConnectionFactory - CachingConnectionFactory
 * 2. RabbitProperties - starts with spring.rabbitmq
 * 3. RabbitTemplate
 * 4. AmqpAdmin
 *
 */
@ComponentScan("com.study.springbootannotation")
@SpringBootApplication
public class App
{
    public static final String EXCHANGE_NAME = "spring_boot_annotation_exchange2";
    public static final String QUEUE_NAME = "spring_boot_annotation_queue2";
    public static final String ROUTING_KEY = "foo.bar.#";



    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
