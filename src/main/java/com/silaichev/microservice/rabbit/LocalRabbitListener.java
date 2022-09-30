package com.silaichev.microservice.rabbit;

import com.silaichev.microservice.service.CredentialService;
import com.silaichev.microservice.service.RequestsRecognizerService;
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
    private RequestsRecognizerService recognizerService;

    @RabbitListener(queues = "#{rabbitConfiguration.getMAC()}")
    public void processMicroservice(String message) {
        if (recognizerService.checkProcess(message)) {
            recognizerService.analyze(message);
        } else if (message.equals(rabbitConfiguration.getMAC())) {
            credentialService.create(message);
        }

    }

}
