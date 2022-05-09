package com.tsoft.playground.dataGenerator;

import com.tsoft.playground.graphql.LogMessage;
import com.tsoft.playground.graphql.SupportCase;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateLotsOfData {

    private static volatile AtomicInteger counter = new AtomicInteger(0);

    public GenerateLotsOfData() {

    }

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

    public static LogMessage generateRandom(String supportCaseId) {
        int currentCount = counter.incrementAndGet();
        String id = UUID.randomUUID().toString();
        return new LogMessage(id,"lorem ipsum "+currentCount,supportCaseId);
    }

    public List<SupportCase> generateSupportCases(int howMany){
        return IntStream.range(0,howMany).mapToObj(n -> generateRandom() ).collect(Collectors.toList());
    }

    public List<LogMessage> generateLogMessagesForSupportCases( List<SupportCase> cases) {
        List<LogMessage> msgs = cases.stream().map( c -> {
            int numberOfLogMessages = new Random().nextInt(20);
            return IntStream.range(0,numberOfLogMessages).mapToObj(n -> generateRandom( c.getId() ) ).collect(
                            Collectors.toList());
        }).flatMap( l -> l.stream() ).collect(Collectors.toList());
        return msgs;
    }

    public static void main(String[] args) {
        GenerateLotsOfData test = new GenerateLotsOfData();
        List<SupportCase> cases = test.generateSupportCases(10);
        List<LogMessage> logMessages = test.generateLogMessagesForSupportCases(cases);
        cases.forEach( c -> System.out.println(c));
        logMessages.forEach( lm -> System.out.println(lm));
    }
}
