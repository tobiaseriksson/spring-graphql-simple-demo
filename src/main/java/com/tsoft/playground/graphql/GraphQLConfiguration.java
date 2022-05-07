package com.tsoft.playground.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Configuration
public class GraphQLConfiguration {

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() throws IOException {
        return GraphQL.newGraphQL(buildSchema(readSchema())).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        RuntimeWiring runtimeWiring = buildWiring();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        return graphQLSchema;
    }

    private RuntimeWiring buildWiring() {
        RuntimeWiring.Builder wiring = RuntimeWiring.newRuntimeWiring();
        return wiring
                        .type(newTypeWiring("Query").dataFetcher("allSupportCases", graphQLDataFetchers.allSupportCases()))
                        .type(newTypeWiring("Query").dataFetcher("someSupportCases",
                                        graphQLDataFetchers.someSupportCases()))
                        .type(newTypeWiring("SupportCase").dataFetcher("logMessages",
                                        graphQLDataFetchers.logMessages()))
                        .type(newTypeWiring("Mutation").dataFetcher("addLogMessage",graphQLDataFetchers.addLogMessage()))
                        .build();
    }

    private String readSchema() throws IOException {
        File dot = ResourceUtils.getFile("classpath:.");
        System.out.println("Resource PATH = " + dot.getAbsolutePath());
        File graphQLFile = ResourceUtils.getFile("classpath:support.graphqls");
        String content = new String(Files.readAllBytes(graphQLFile.toPath()));
        System.out.println("GraphQL : " + content);
        return content;
    }
}
