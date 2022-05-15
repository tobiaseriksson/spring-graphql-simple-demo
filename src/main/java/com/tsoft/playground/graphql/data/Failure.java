package com.tsoft.playground.graphql.data;

import com.tsoft.playground.graphql.data.DemoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Failure implements DemoResponse {
    String message;
    int errorCode;
}
