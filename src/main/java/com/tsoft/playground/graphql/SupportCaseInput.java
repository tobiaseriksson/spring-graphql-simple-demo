package com.tsoft.playground.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * input SupportCaseInput {
 * priority: String!
 * title: String!
 * text: String!
 * email: Email
 * createdBy: ID!
 * }
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SupportCaseInput {
    String priority;

    String title;

    String text;

    String email;

    String createdBy;
}
