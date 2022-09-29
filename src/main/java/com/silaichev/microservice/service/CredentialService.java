package com.silaichev.microservice.service;

import com.silaichev.microservice.entity.Credential;
import com.silaichev.microservice.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    private static final String ID = "CredentialsId";

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private DBSequenceService dbSequenceService;

    public void create(String cloudPseudonym) {
        credentialsRepository.save(new Credential(ID, cloudPseudonym));
    }

    public boolean credentialsExists() {
        return credentialsRepository.findById(ID).isPresent();
    }
}
