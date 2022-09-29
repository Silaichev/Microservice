package com.silaichev.microservice.repository;


import com.silaichev.microservice.entity.Info;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InfoRepository extends MongoRepository<Info, Long> {
}
