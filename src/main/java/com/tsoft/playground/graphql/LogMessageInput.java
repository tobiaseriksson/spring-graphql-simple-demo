package com.tsoft.playground.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * The GraphQL Schema
 *
 * input LogMessageInput {
 *   belongToCase: ID!
 *   txt: String
 * }
 *
 *
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogMessageInput {
    String txt;
    String belongToCase;

    public LogMessageInput(HashMap<String, Object> map) {
        this.txt = (String) map.get("txt");
        this.belongToCase = (String) map.get("belongToCase");
    }


}
