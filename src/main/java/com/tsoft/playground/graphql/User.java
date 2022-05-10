package com.tsoft.playground.graphql;

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
}
