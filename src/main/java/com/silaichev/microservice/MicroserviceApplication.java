package com.silaichev.microservice;

import com.silaichev.microservice.service.CredentialService;
import com.silaichev.microservice.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroserviceApplication {

	@Autowired
	private CredentialService credentialsService;

	@Autowired
	private RabbitService rabbitService;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}

	@Bean
	public boolean init(){
		if(!credentialsService.credentialsExists()){
			rabbitService.initRequest();
		}
		return true;
	}
}
