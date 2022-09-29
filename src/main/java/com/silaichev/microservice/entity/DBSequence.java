package com.silaichev.microservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class DBSequence {

    @Id
    private String id;
    private Long sequenceNumber;

    public void incrementNumber(){
        sequenceNumber++;
    }
}
