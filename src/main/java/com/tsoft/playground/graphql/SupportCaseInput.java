package com.tsoft.playground.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SupportCaseInput {
    String priority;
    String title;
    String text;
    String email;
}
