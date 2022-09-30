package com.silaichev.microservice.service;

import com.silaichev.microservice.entity.Info;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class RequestsRecognizerService {

    @Autowired
    private InfoService infoService;

    private static final String PROCESS_PATTERN = "([{].+[}])";

    private static final String CREATING_PATTERN = "(^create)([{].+[}])";

    private static final String DELETING_PATTERN = "(^delete)([{].+[}])";

    private static final String BACKUP_PATTERN = "(^backup)([{].+[}])";

    private static final String EDIT_PATTERN = "(^edit)([{].+[}])";

    private static final String INFO_PATTERN = "([{][\"]id[\"]:\\w+[,][\"]name[\"]:[\"]\\w+[\"][,][\"]pass[\"]:[\"]\\D+[\"][}])";

    public void analyze(String message) {
        backupInfo(message);
        createInfo(message);
        editInfo(message);
        deleteInfo(message);
    }

    private void createInfo(String message) {
        if (checkRegex(message, CREATING_PATTERN)) {
            infoService.createInfo(infoService.convertToInfo(deleteCommand(message, CREATING_PATTERN)));
        }
    }

    private void deleteInfo(String message) {
        if (checkRegex(message, DELETING_PATTERN)) {
            Info info = infoService.convertToInfo(deleteCommand(message, DELETING_PATTERN));
            infoService.delete(info);
        }
    }

    private void backupInfo(String message) {
        if (checkRegex(message, BACKUP_PATTERN)) {
            convertToListInfo(deleteCommand(message, BACKUP_PATTERN))
                    .forEach(info -> infoService.createInfo(info));
        }
    }

    public void editInfo(String message) {
        if (checkRegex(message, EDIT_PATTERN)) {
            List<Info> infos = convertToListInfo(deleteCommand(message, EDIT_PATTERN));
            infoService.editInfo(infos.toArray(new Info[0]));
        }
    }

    private List<Info> convertToListInfo(String processedMessage) {
        List<Info> infos = new LinkedList<>();
        Matcher matcher = Pattern.compile(INFO_PATTERN).matcher(processedMessage);
        while (matcher.find()) {
            infos.add(infoService.convertToInfo(matcher.group()));
        }
        return infos;
    }

    private boolean checkRegex(String message, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(message);
        return matcher.find();
    }

    private String deleteCommand(String message, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(message);
        matcher.find();
        return matcher.group(2);
    }

    public boolean checkProcess(String message) {
        return checkRegex(message, PROCESS_PATTERN);
    }

}
