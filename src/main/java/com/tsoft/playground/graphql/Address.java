package com.tsoft.playground.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * type Address {
 * id: ID!
 * country: String
 * city: String
 * postalCode: String
 * street: String
 * }
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    String id;

    String country;

    String city;

    String postalCode;

    String street;
}
