package com.tsoft.playground.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class SupportCase {
    String id;
    String priority;
    String title;
    String text;
}
