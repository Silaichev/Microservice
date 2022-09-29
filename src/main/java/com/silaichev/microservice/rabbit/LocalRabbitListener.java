package com.silaichev.microservice.rabbit;

import com.google.gson.Gson;
import com.silaichev.microservice.entity.Info;
import com.silaichev.microservice.service.InfoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalRabbitListener {

    @Autowired
    private InfoService infoService;


    @RabbitListener(queues = RabbitConfiguration.MICROSERVICE_QUEUE)
    public void processMicroservice(String message){
        Info info = new Gson().fromJson(message, Info.class);
        infoService.createInfo(info);
        System.out.println(info.toString());
    }

}
