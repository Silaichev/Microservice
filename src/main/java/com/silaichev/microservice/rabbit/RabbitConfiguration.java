package com.silaichev.microservice.rabbit;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class RabbitConfiguration {

    public static final String CLOUD_QUEUE = "cloudQueue";
    public static final String MICROSERVICE_QUEUE = "microserviceQueue";
    public static final String SERVER_QUEUE = "serverQueue";
    public static final List<String> queuesNames = Arrays.asList(MICROSERVICE_QUEUE, SERVER_QUEUE);

    @Bean
    public List<Queue> creatingQueuesAndFanout() {
        List<Queue> queuesList = new ArrayList<>();
        queuesNames.forEach(queueName -> queuesList.add(new Queue(queueName)));
        return queuesList;
    }

}
