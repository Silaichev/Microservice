package com.silaichev.microservice.service;

import com.silaichev.microservice.rabbit.RabbitConfiguration;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

@Service
public class RabbitService {

    @Value("${init.message}")
    public String REFERENCE_INIT;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String getMac () {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void initRequest(){
        rabbitTemplate.send(RabbitConfiguration.CLOUD_QUEUE,
                new Message((REFERENCE_INIT+getMac()).getBytes(StandardCharsets.UTF_8)));
    }
}
