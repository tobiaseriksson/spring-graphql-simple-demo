package com.tsoft.playground.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class LogMessage {
    String id;
    String txt;
    String supportCaseId;
}
