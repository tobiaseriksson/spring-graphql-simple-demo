package com.tsoft.playground.dataGenerator;

import com.tsoft.playground.graphql.SupportCasePriority;
import com.tsoft.playground.graphql.SupportCaseStatus;
import com.tsoft.playground.graphql.data.Address;
import com.tsoft.playground.graphql.data.LogMessage;
import com.tsoft.playground.graphql.data.SupportCase;
import com.tsoft.playground.graphql.data.User;
import com.tsoft.playground.utils.Utils;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RandomDataGenerator {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final List<String> firstNames;

    private final List<String> lastNames;

    private final List<String> cities;

    private final List<String> streetNames;

    private final List<String> domains;

    private final List<String> colors;

    Random random = new Random();

    Utils utils = new Utils();

    Faker faker = new Faker();

    public RandomDataGenerator() {

        String txtFirstNames = utils.readFileFromResources("random-data-sources/firstnames_boys.txt");
        txtFirstNames = txtFirstNames + "\n" + utils.readFileFromResources("random-data-sources/firstnames_girls.txt");
        firstNames = Arrays.stream(txtFirstNames.split("\n")).filter(row -> row.trim().length() > 0).collect(Collectors.toList());

        String txtLastNames = utils.readFileFromResources("random-data-sources/lastnames.txt");
        lastNames = Arrays.stream(txtLastNames.split("\n")).filter(row -> row.trim().length() > 0).collect(Collectors.toList());

        String citiesTxt = utils.readFileFromResources("random-data-sources/städer.txt");
        cities = Arrays.stream(citiesTxt.split("\n")).filter(row -> row.trim().length() > 0).collect(Collectors.toList());

        String txtsStreetNames = utils.readFileFromResources("random-data-sources/gatunamn_göteborg.txt");
        txtsStreetNames = txtsStreetNames + utils.readFileFromResources("random-data-sources/gatunamn_stockholm.txt");
        streetNames = Arrays.stream(txtsStreetNames.split("\n")).filter(row -> row.trim().length() > 0).collect(Collectors.toList());

        domains = Arrays.asList("gmail.com", "hotmail.com", "bth.org", "sweden.gov", "icloud.com");

        colors = Arrays.asList("red", "green", "blue", "yellow", "magenta", "black", "white", "pink" );
    }

    public static void main(String[] args) {
        RandomDataGenerator gen = new RandomDataGenerator();
        Random r = new Random();
        List<User> users = gen.generateNUsers(10);
        users.stream().forEach(user -> {
            Address address = gen.generateAddress();
            user.setHomeAddress(address.getId());
            // System.out.println(user + " : " + address);
            IntStream.range(0, r.nextInt(5)).forEach(i -> {
                SupportCase supportCase = gen.generateCaseForUser(user);
                // System.out.println(supportCase);
                IntStream.range(0, r.nextInt(5)).forEach(x -> {
                    LogMessage logMessage = gen.generateLogMessage(supportCase, users.get(r.nextInt(users.size())));
                    // System.out.println(logMessage);
                });
            });
        });
    }

    public String uniqueId() {
        // String id = UUID.randomUUID().toString(); Todo: UUIDs are better unique IDs, but numbers are easier to read.
        return "" + counter.incrementAndGet();
    }

    private String pickRandom(List<String> lst) {
        return lst.get(random.nextInt(lst.size()));
    }

    /**
     * 5 digit zipcode
     *
     * @return
     */
    public String zipCode() {
        int zipcode = 1000 + r(8999);
        return "" + zipcode;
    }

    public String street() {
        return pickRandom(streetNames) + " " + r(100);
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

    public String domain() {
        return pickRandom(domains);
    }

    public String color() {
        return pickRandom(colors);
    }

    public int shoeSize() { return 30+r(20); }

    public String email(String fn, String ln, String domain) {
        return String.format("%s.%s@%s", fn, ln, domain);
    }

    public int luckyNumber() { return 1+r(100); }

    public int r(int max) { return random.nextInt(max); }

    public String dateOfBirth() {
        return LocalDate.of( 1970+r(50), 1+r(12), 1+r(28)).toString();
    }

    public String lastLogin() {
        return LocalDate.now().minusDays( r(200 ) ).toString();
    }

    String sex = faker.demographic().sex();

    String race = faker.demographic().race();

    String maritalStatus = faker.demographic().maritalStatus();

    String education = faker.demographic().educationalAttainment();

    public List<User> generateNUsers(int N) {
        return IntStream.range(1, N).mapToObj(r -> {
            String fn = firstName();
            String ln = lastName();
            return new User(uniqueId(), fn, ln, null, email(fn, ln, domain()), shoeSize(), color(), luckyNumber(),
                            dateOfBirth(), lastLogin(), sex, race, maritalStatus, education );
        }).collect(Collectors.toList());
    }

    public Address generateAddress() {
        return new Address(uniqueId(), "Sweden", city(), zipCode(), street());
    }

    public SupportCase generateCaseForUser(User user) {
        List<String> priorityList = Arrays.stream(SupportCasePriority.values()).map(e -> e.key).collect(Collectors.toList());
        List<String> statusList = Arrays.stream(SupportCaseStatus.values()).map(e -> e.key).collect(Collectors.toList());
        Random r = new Random();
        String id = uniqueId(); // UUID.randomUUID().toString();
        String priority = priorityList.get(r.nextInt(priorityList.size()));
        String title = "Need to fix problem #" + uniqueId();
        String txt = "lorem ipsum";
        String email = email(user.getFirstname(), user.getLastname(), domain());
        ZonedDateTime now = ZonedDateTime.now();
        String status = statusList.get(r.nextInt(statusList.size()));
        return new SupportCase(id, priority, title, txt, email, now.toString(), user.getId(), status);
    }

    public LogMessage generateLogMessage(SupportCase supportCase, User user) {
        int currentCount = counter.incrementAndGet();
        String id = uniqueId(); // UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();
        String chuckNorrisFact = faker.chuckNorris().fact();
        return new LogMessage(id, chuckNorrisFact, supportCase.getId(), user.getId(), now.toString());
    }

}
