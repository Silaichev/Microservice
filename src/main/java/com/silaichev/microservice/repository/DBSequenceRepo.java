package com.silaichev.microservice.repository;

import com.silaichev.microservice.entity.DBSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DBSequenceRepo extends MongoRepository<DBSequence, String> {
}
