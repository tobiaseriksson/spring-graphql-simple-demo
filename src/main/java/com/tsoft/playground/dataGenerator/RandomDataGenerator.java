package com.tsoft.playground.dataGenerator;

import com.tsoft.playground.graphql.LogMessage;
import com.tsoft.playground.graphql.SupportCase;
import com.tsoft.playground.graphql.SupportCasePriority;
import com.tsoft.playground.graphql.SupportCaseStatus;
import com.tsoft.playground.graphql.User;
import com.tsoft.playground.graphql.Address;
import com.tsoft.playground.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RandomDataGenerator {

    private volatile AtomicInteger counter = new AtomicInteger(0);

    private List<String> firstNames;
    private List<String> lastNames;
    private List<String> cities;
    private List<String> streetNames;
    private List<String> domains;
    Random random = new Random();

    public RandomDataGenerator() {
        String txtFirstNames = Utils.readFileFromResources("random-data-sources/firstnames_boys.txt");
        txtFirstNames = txtFirstNames + "\n" + Utils.readFileFromResources("random-data-sources/firstnames_girls.txt");
        firstNames = Arrays.stream(txtFirstNames.split("\n")).filter( row -> row.trim().length() > 0 ).collect(Collectors.toList());

        String txtLastNames = Utils.readFileFromResources("random-data-sources/lastnames.txt");
        lastNames =
                        Arrays.stream(txtLastNames.split("\n")).filter( row -> row.trim().length() > 0 ).collect(Collectors.toList());

        String citiesTxt = Utils.readFileFromResources("random-data-sources/städer.txt");
        cities = Arrays.stream(citiesTxt.split("\n")).filter( row -> row.trim().length() > 0 ).collect(Collectors.toList());

        String txtsStreetNames = Utils.readFileFromResources("random-data-sources/gatunamn_göteborg.txt");
        txtsStreetNames = txtsStreetNames + Utils.readFileFromResources("random-data-sources/gatunamn_stockholm.txt");
        streetNames = Arrays.stream(txtsStreetNames.split("\n")).filter( row -> row.trim().length() > 0 ).collect(Collectors.toList());

        domains = Arrays.asList("gmail.com","hotmail.com","bth.org","sweden.gov","icloud.com");
    }

    public String uniqueId() {
        return ""+counter.incrementAndGet();
    }

    private String pickRandom(List<String> lst) {
        return lst.get(random.nextInt(lst.size()));
    }

    /**
     * 5 digit zipcode
     * @return
     */
    public String zipCode() {
        int zipcode = 1000 + random.nextInt(8999);
        return ""+zipcode;
    }

    public String street() {
        return pickRandom(streetNames)+" "+random.nextInt(100);
    }

    public String city() {
        return pickRandom(cities);
    }

    public String firstName() {
        return pickRandom(firstNames);
    }

    public String lastName() {
        return pickRandom(lastNames);
    }

    public String domain() { return pickRandom(domains); }

    public String email(String fn, String ln,String domain) {
        return String.format("%s.%s@%s",fn,ln,domain);
    }

    public List<User> generateNUsers(int N) {
        return IntStream.range(1,N).mapToObj( r -> {
            String fn = firstName();
            String ln = lastName();
            return new User(uniqueId(),fn,ln,null,email(fn,ln,domain()));
        }).collect(Collectors.toList());
    }

    public Address generateAddress() {
        return new Address(uniqueId(),"Sweden", city(), zipCode(), street());
    }

    public SupportCase generateCaseForUser(User user) {
        List<String> priorityList = Arrays.stream(SupportCasePriority.values()).map(e -> e.key).collect(Collectors.toList());
        List<String> statusList = Arrays.stream(SupportCaseStatus.values()).map(e -> e.key).collect(Collectors.toList());
        Random r = new Random();
        String id = uniqueId(); // UUID.randomUUID().toString();
        String priority = priorityList.get(r.nextInt(priorityList.size()));
        String title = "Need to fix problem #"+uniqueId();
        String txt = "lorem ipsum";
        String email = email(user.getFirstname(),user.getLastname(),domain());
        ZonedDateTime now = ZonedDateTime.now();
        String status = statusList.get(r.nextInt(statusList.size()));
        return new SupportCase(id,priority,title,txt,email,now.toString(),user.getId(),status);
    }

    public  LogMessage generateLogMessage(SupportCase supportCase, User user) {
        int currentCount = counter.incrementAndGet();
        String id = uniqueId(); // UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();
        return new LogMessage(id,"lorem ipsum "+currentCount,supportCase.getId(),user.getId(),now.toString());
    }

    public static void main(String[] args) {
        RandomDataGenerator gen = new RandomDataGenerator();
        Random r = new Random();
        List<User> users = gen.generateNUsers(10);
        users.stream().forEach( user -> {
            Address address = gen.generateAddress();
            user.setHomeAddress(address.getId());
            System.out.println(user+" : "+address);
            IntStream.range(0,r.nextInt(5)).forEach( i -> {
                SupportCase supportCase = gen.generateCaseForUser(user);
                System.out.println(supportCase);
                IntStream.range(0,r.nextInt(5)).forEach( x -> {
                   LogMessage logMessage = gen.generateLogMessage(supportCase,users.get(r.nextInt(users.size())));
                   System.out.println(logMessage);
                });
            });
        });
    }


}
