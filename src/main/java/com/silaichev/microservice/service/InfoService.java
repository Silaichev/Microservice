package com.silaichev.microservice.service;


import com.google.gson.Gson;
import com.silaichev.microservice.entity.Info;
import com.silaichev.microservice.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InfoService {

    @Autowired
    private DBSequenceService dbSequenceService;

    @Autowired
    private InfoRepository infoRepo;

    public Info convertToInfo(String message){
        return new Gson().fromJson(message, Info.class);
    }
    public void createInfo(Info info) {
        if (Objects.nonNull(info)&&!checkExist(info)) {
            info.setId(dbSequenceService.incrementSequence());
            infoRepo.save(info);
        }
    }

    public void delete(Info info) {
        if (checkExist(info)) {
            Info deletingInfo = findInfoByName(info.getName());
            infoRepo.deleteById(deletingInfo.getId());
        }
    }

    public void editInfo(Info[] info) {
        if (checkExist(info[0])) {
            Info oldInfo = findInfoByName(info[0].getName());
            oldInfo.setName(info[1].getName());
            oldInfo.setPass(info[1].getPass());
            infoRepo.save(oldInfo);
        }
    }

    public Info findInfoByName(String name) {
        List<Info> filteredInfo = infoRepo.findAll().stream().filter(i -> i.getName().equals(name))
                .collect(Collectors.toList());
        return filteredInfo.get(0);
    }

    public boolean checkExist(Info info) {
        List<Info> filteredInfo = infoRepo.findAll().stream().filter(i -> i.getName().equals(info.getName()))
                .filter(i -> i.getPass().equals(info.getPass())).collect(Collectors.toList());
        return !filteredInfo.isEmpty();
    }

    public List<Info> getAllInfo() {
        return infoRepo.findAll();
    }
}
