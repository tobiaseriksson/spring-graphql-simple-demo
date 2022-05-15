package com.tsoft.playground.graphql.dao;

import com.tsoft.playground.dataGenerator.FakeDatabase;
import com.tsoft.playground.graphql.data.DemoResponse;
import com.tsoft.playground.graphql.data.Failure;
import com.tsoft.playground.graphql.data.LogMessage;
import com.tsoft.playground.graphql.data.LogMessageInput;
import com.tsoft.playground.graphql.data.Success;
import com.tsoft.playground.graphql.data.SupportCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
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
        return getBySupportCase(supportCase.getId());
    }

    public List<LogMessage> getBySupportCase(String supportCaseId) {
        return database.logMessages.values()
                        .stream()
                        .filter(p -> p.getSupportCaseId().equals(supportCaseId))
                        .collect(Collectors.toList());
    }

    public List<LogMessage> all() {
        return database.logMessages.values().stream().collect(Collectors.toList());
    }

    public synchronized DemoResponse add(LogMessageInput logMessageInput) {
        String id = database.uniqueId();
        ZonedDateTime now = ZonedDateTime.now();
        if( logMessageInput.getTxt().length() < 10 ) {
            Failure result = new Failure("You need to provide a log message that is atleast 10 characters long", 199);
            return result;
        }
        LogMessage logMessage = new LogMessage(id, logMessageInput.getTxt(), logMessageInput.getBelongToCase(),
                        logMessageInput.getCreatedBy(), now.toString());
        database.logMessages.put(logMessage.getId(), logMessage);
        Success result = new Success("Log message successfully added!");
        return result;
    }

}
