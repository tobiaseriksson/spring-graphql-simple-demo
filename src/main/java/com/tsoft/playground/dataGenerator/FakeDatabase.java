package com.tsoft.playground.dataGenerator;

import com.tsoft.playground.graphql.data.Address;
import com.tsoft.playground.graphql.data.LogMessage;
import com.tsoft.playground.graphql.data.SupportCase;
import com.tsoft.playground.graphql.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class FakeDatabase {

    public Map<String, SupportCase> supportCases;

    public Map<String, LogMessage> logMessages;

    public Map<String, User> users;

    public Map<String, Address> addresses;

    private final RandomDataGenerator dataGenerator;

    /**
     * Prepares lots of fake data
     */
    @Autowired
    public FakeDatabase() {
        dataGenerator = new RandomDataGenerator();
        Random r = new Random();
        addresses = new HashMap<>();
        supportCases = new HashMap<>();
        logMessages = new HashMap<>();
        int numberOfUsers = 2000;
        users = dataGenerator.generateNUsers(numberOfUsers).stream().collect(Collectors.toMap(User::getId, Function.identity()));

        users.values().stream().forEach(user -> {
            Address address = dataGenerator.generateAddress();
            user.setHomeAddress(address.getId());
            // System.out.println(user + " : " + address);
            addresses.put(address.getId(), address);
            IntStream.range(0, r.nextInt(5)).forEach(i -> {
                SupportCase supportCase = dataGenerator.generateCaseForUser(user);
                // System.out.println(supportCase);
                supportCases.put(supportCase.getId(), supportCase);
                IntStream.range(0, r.nextInt(5)).forEach(x -> {
                    LogMessage logMessage = dataGenerator.generateLogMessage(supportCase, users.values().stream().collect(Collectors.toList()).get(r.nextInt(users.size())));
                    // System.out.println(logMessage);
                    logMessages.put(logMessage.getId(), logMessage);
                });
            });
        });
        System.out.println("RandomDataGenerator has now generated :");
        System.out.println("Users :"+users.keySet().size());
        System.out.println("Addresses :"+addresses.keySet().size());
        System.out.println("Support-Cases :"+supportCases.keySet().size());
        System.out.println("Log-Messages :"+logMessages.keySet().size());
    }

    /**
     * Generates a unique ID within the fake data
     *
     * @return
     */
    public String uniqueId() {
        return dataGenerator.uniqueId();
    }

}
