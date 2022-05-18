package com.tsoft.playground.graphql;

import com.tsoft.playground.utils.Utils;
import graphql.GraphQL;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
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

    @Autowired
    GraphQLAdvanced graphQLAdvanced;

    @Bean
    public GraphQL graphQL() throws IOException {
        return GraphQL.newGraphQL(buildSchema(readSchema())).build();
                        //.instrumentation(new TracingInstrumentation()).build();
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
        return wiring.type(newTypeWiring("Query").dataFetcher("allUsers", graphQLDataFetchers.allUsers()))
                        .type(newTypeWiring("Query").dataFetcher("user", graphQLDataFetchers.user()))
                        .type(newTypeWiring("Query").dataFetcher("allSupportCases", graphQLDataFetchers.allSupportCases()))
                        .type(newTypeWiring("Query").dataFetcher("supportCase", graphQLDataFetchers.supportCase()))
                        .type(newTypeWiring("Query").dataFetcher("someSupportCases", graphQLDataFetchers.someSupportCases()))
                        .type(newTypeWiring("User").dataFetcher("homeAddress", graphQLDataFetchers.addressFromUser()))
                        .type(newTypeWiring("User").dataFetcher("supportCases", graphQLDataFetchers.supportCasesFromUser()))
                        .type(newTypeWiring("SupportCase").dataFetcher("logMessages", graphQLDataFetchers.logMessagesFromSupportCase()))
                        .type(newTypeWiring("SupportCase").dataFetcher("createdBy", graphQLDataFetchers.userFromCase()))
                        .type(newTypeWiring("LogMessage").dataFetcher("createdBy", graphQLDataFetchers.userFromLogMessage()))
                        .type(newTypeWiring("Mutation").dataFetcher("addLogMessage", graphQLDataFetchers.addLogMessage()))
                        .type(newTypeWiring("Mutation").dataFetcher("addSupportCase", graphQLDataFetchers.addSupportCase()))
                        .scalar(EmailScalar.EMAIL)
                        .type(newTypeWiring("DemoResponse").typeResolver(graphQLAdvanced.demoResponseTypeResolver))
                        .build();
    }

    private String readSchema() throws IOException {
        Utils utils = new Utils();
        String content = utils.readFileFromResources("support.graphqls");
        // System.out.println("GraphQL : " + content);
        return content;
    }
}
