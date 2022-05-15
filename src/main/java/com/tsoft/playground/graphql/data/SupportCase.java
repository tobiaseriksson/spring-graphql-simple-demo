package com.tsoft.playground.graphql.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * type SupportCase {
 * id: ID!
 * priority: String!
 * title: String!
 * text: String!
 * logMessages: [LogMessage]
 * email: Email
 * createdDate: String
 * createdBy: User
 * status: String
 * }
 */
@Data
@ToString
@AllArgsConstructor
public class SupportCase {
    String id;

    String priority;

    String title;

    String text;

    String email;

    String createdDate;

    String createdBy;

    String status;
}
