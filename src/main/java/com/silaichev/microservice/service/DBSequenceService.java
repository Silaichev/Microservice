package com.silaichev.microservice.service;

import com.silaichev.microservice.entity.DBSequence;
import com.silaichev.microservice.repository.DBSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DBSequenceService {

    @Autowired
    private DBSequenceRepository sequenceRepo;

    private static final String DB_SEQUENCE_ID = "info_sequence";

    public Long incrementSequence(){
        Long result = 0L;
        Optional<DBSequence> sequenceOptional = sequenceRepo.findById(DB_SEQUENCE_ID);
        if(sequenceOptional.isEmpty()){
            DBSequence newSequence = new DBSequence(DB_SEQUENCE_ID, 0L);
            sequenceRepo.save(newSequence);
        }else{
            DBSequence existsSequence = sequenceRepo.findById(DB_SEQUENCE_ID).get();
            existsSequence.incrementNumber();
            result = existsSequence.getSequenceNumber();
            sequenceRepo.save(existsSequence);
        }
        return result;
    }
}
