package com.silaichev.microservice.rabbit;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

    public String MAC = System.getenv("MAC");
    public static final String CLOUD_QUEUE = "cloudQueue";

    @Bean
    public Queue cloudQueue() {
        return new Queue(CLOUD_QUEUE);
    }

    @Bean
    public Queue myQueue() {
        return new Queue(System.getenv("MAC"));
    }

    public String getMAC() {
        return MAC;
    }
}
