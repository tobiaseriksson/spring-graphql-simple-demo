package com.tsoft.playground.graphql;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LogMessageDAO {

    private Map<Integer,LogMessage> logMessages;

    AtomicInteger logIdGenerator = new AtomicInteger();

    public LogMessageDAO() {
        logMessages = new HashMap<>();
        logMessages.put( 1, new LogMessage("1", "It is all black", "1"));
        logMessages.put( 2, new LogMessage("2", "there is a spinning wheel ...", "2"));
        logMessages.put( 3, new LogMessage("3", "hmmm it has been 35 min and still spinning","2"));
        logMessages.put( 4, new LogMessage("4", "IT HAS BEEN 2 hours, and still not working","2"));
        logMessages.put( 5, new LogMessage("5", "need access to Kreml super secret vault","6"));
    }

    public LogMessage getById(String id) {
        return logMessages.get(id);
    }

    public List<LogMessage> getBySupportCase(SupportCase supportCase) {
        return getBySupportCase(supportCase.id);
    }

    public List<LogMessage> getBySupportCase(String supportCaseId) {
        return logMessages.values().stream().filter( p -> p.supportCaseId.equals(supportCaseId) ).collect(Collectors.toList());
    }

    public List<LogMessage> all() {
        return logMessages.values().stream().collect(Collectors.toList());
    }

    public LogMessage add(LogMessageInput logMessageInput){
        int max = logMessages.keySet().stream().mapToInt(v -> v).max().orElseThrow();
        LogMessage logMessage =  new LogMessage(""+(max + 1), logMessageInput.txt, logMessageInput.belongToCase);
        logMessages.put( max + 1, logMessage);
        return logMessage;
    }

}
