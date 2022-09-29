package com.silaichev.microservice.rabbit;

import com.google.gson.Gson;
import com.silaichev.microservice.entity.Info;
import com.silaichev.microservice.service.CredentialService;
import com.silaichev.microservice.service.InfoService;
import com.silaichev.microservice.service.RabbitService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalRabbitListener {

    @Autowired
    private RabbitConfiguration rabbitConfiguration;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private InfoService infoService;

    @RabbitListener(queues = "#{rabbitConfiguration.getMAC()}")
    public void processMicroservice(String message){
        if(!message.contains(rabbitConfiguration.getMAC())){
             Info info = new Gson().fromJson(message, Info.class);
             infoService.createInfo(info);
        }else if(message.equals(rabbitConfiguration.getMAC())){
            credentialService.create(message);
        }

    }

}
