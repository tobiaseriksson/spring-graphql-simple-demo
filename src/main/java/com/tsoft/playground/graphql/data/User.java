package com.tsoft.playground.graphql.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * type User {
 * id: ID!
 * firstname: String
 * lastname: String
 * homeAddress: Address
 * email: Email
 * shoeSize: Int
 * favouriteColor: String
 * luckyNumber: Int
 * dateOfBirth: String
 * lastLogIn: String
 * }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    String id;

    String firstname;

    String lastname;

    String homeAddress;

    String email;

    int shoeSize;

    String favouriteColor;

    int luckyNumber;

    String dateOfBirth;

    String lastLogIn;
}
