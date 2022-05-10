package com.tsoft.playground.graphql;

import com.tsoft.playground.dataGenerator.FakeDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class LogMessageDAO {

    @Autowired
    private FakeDatabase database;

    public LogMessageDAO() {
    }

    public LogMessage getById(String id) {
        return database.logMessages.get(id);
    }

    public List<LogMessage> getBySupportCase(SupportCase supportCase) {
        return getBySupportCase(supportCase.id);
    }

    public List<LogMessage> getBySupportCase(String supportCaseId) {
        return database.logMessages.values().stream().filter( p -> p.supportCaseId.equals(supportCaseId) ).collect(Collectors.toList());
    }

    public List<LogMessage> all() {
        return database.logMessages.values().stream().collect(Collectors.toList());
    }

    public synchronized LogMessage add(LogMessageInput logMessageInput){
        String id = UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();
        LogMessage logMessage =  new LogMessage(id, logMessageInput.txt, logMessageInput.belongToCase,
                        logMessageInput.createdBy,now.toString());
        database.logMessages.put(logMessage.getId(),logMessage);
        return logMessage;
    }

}
