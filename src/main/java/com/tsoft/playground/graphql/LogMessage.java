package com.tsoft.playground.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * type LogMessage {
 * id: ID!
 * createdDate: String
 * createdBy: User
 * txt: String
 * }
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogMessage {
    String id;

    String txt;

    String supportCaseId;

    String userId;

    String createdDate;
}
