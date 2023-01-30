package com.tsoft.playground.dataGenerator;

import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFakePlayground {

    public static void main(String[] args) {
        Faker faker = new Faker();

        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton

        String streetAddress = faker.address().streetAddress();
        List<String> attributes = Arrays.asList(name,firstName,lastName,streetAddress);

        System.out.println("Attributes "+String.join(", ",attributes));

        for(int i=1;i<10;i++) {
            String fact = faker.chuckNorris().fact();
            System.out.println(fact);
        }

        for(int i=1;i<10;i++) {
            String fact = faker.bigBangTheory().quote();
            System.out.println(fact);
        }
    }
}
