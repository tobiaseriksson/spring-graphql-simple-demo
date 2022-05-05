package com.tsoft.playground.graphql;

import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SupportCase {
    String id;
    String priority;
    String title;
    String text;
    List<LogMessage> logMessages;

    private static volatile AtomicInteger counter = new AtomicInteger(0);
    public static SupportCase generateRandom() {
        String[] priorityList = {"High","Medium","Low"};
        int r = new Random().nextInt(priorityList.length);
        String id = UUID.randomUUID().toString();
        String priority = priorityList[r];
        int c = counter.incrementAndGet();
        String title = "Need to fix problem #"+c;
        String txt = "lorem ipsum";
        return new SupportCase(id,priority,title,txt);
    }

    public SupportCase( String id, String priority, String title, String text ){
        this.id = id;
        this.priority = priority;
        this.title = title;
        this.text = text;
        logMessages = new LinkedList<LogMessage>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<LogMessage> getLogMessages() {
        return logMessages;
    }

    public void setLogMessages(List<LogMessage> logMessages) {
        this.logMessages = logMessages;
    }

    @Override
    public String toString() {
        return "SupportCase{" + "id='" + id + '\'' + ", priority='" + priority + '\'' + ", title='" + title + '\''
                        + ", text='" + text + '\'' + ", logMessages=" + logMessages + '}';
    }
}
