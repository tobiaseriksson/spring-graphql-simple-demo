package com.tsoft.playground.dataGenerator;

import com.tsoft.playground.graphql.LogMessage;
import com.tsoft.playground.graphql.SupportCase;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateLotsOfData {

    public GenerateLotsOfData() {

    }

    public List<SupportCase> generateSupportCases(int howMany){
        return IntStream.range(0,howMany).mapToObj(n -> SupportCase.generateRandom() ).collect(Collectors.toList());
    }

    public List<LogMessage> generateLogMessagesForSupportCases( List<SupportCase> cases) {
        List<LogMessage> msgs = cases.stream().map( c -> {
            int numberOfLogMessages = new Random().nextInt(20);
            return IntStream.range(0,numberOfLogMessages).mapToObj(n -> LogMessage.generateRandom( c.getId() ) ).collect(
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
