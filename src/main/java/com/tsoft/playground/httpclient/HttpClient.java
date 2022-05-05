package com.tsoft.playground.httpclient;

import java.io.IOException;

public class HttpClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        String response = HTTPUtils.get("http://localhost:8081/api/support-case");
        System.out.println(response);
        String graphqlQuery = "{\n" + "    \"query\": \"query allCases { allSupportCases { title } }\" \n" + "}";
        String graphqlResponse = HTTPUtils.post("http://localhost:8081/graphql", graphqlQuery);
        System.out.println(graphqlResponse);
    }

}
