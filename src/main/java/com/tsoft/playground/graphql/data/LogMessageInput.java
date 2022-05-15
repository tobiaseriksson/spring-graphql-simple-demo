package com.tsoft.playground.graphql.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The GraphQL Schema
 * <p>
 * <p>
 * input LogMessageInput {
 * txt: String
 * belongToCase: ID!
 * createdBy: ID!
 * }
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogMessageInput {
    String txt;

    String belongToCase;

    String createdBy;
}
