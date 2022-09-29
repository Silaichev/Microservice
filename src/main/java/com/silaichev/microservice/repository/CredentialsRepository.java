package com.silaichev.microservice.repository;

import com.silaichev.microservice.entity.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CredentialsRepository extends MongoRepository<Credential, String> {
}
