package com.tsoft.playground.graphql;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class LogMessage {
    private static AtomicInteger counter = new AtomicInteger();

    String id;
    String txt;
    String supportCaseId;

    public String getSupportCaseId() {
        return supportCaseId;
    }

    public void setSupportCaseId(String supportCaseId) {
        this.supportCaseId = supportCaseId;
    }

    public static LogMessage generateRandom(String supportCaseId) {
        int currentCount = counter.incrementAndGet();
        String id = UUID.randomUUID().toString();
        return new LogMessage(id,"lorem ipsum "+currentCount,supportCaseId);
    }

    public LogMessage(String id, String txt, String supportCaseId) {
        this.id = id;
        this.txt = txt;
        this.supportCaseId = supportCaseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "LogMessage{" + "id='" + id + '\'' + ", txt='" + txt + '\'' + ", supportCaseId='" + supportCaseId + '\''
                        + '}';
    }
}
