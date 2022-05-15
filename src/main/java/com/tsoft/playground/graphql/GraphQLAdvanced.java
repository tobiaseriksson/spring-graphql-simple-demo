package com.tsoft.playground.graphql;

import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import org.springframework.stereotype.Component;

@Component
public class GraphQLAdvanced {
    TypeResolver demoResponseTypeResolver = env -> {
        DemoResponse response = env.getObject();
        if( response instanceof Success ) {
            return (GraphQLObjectType) env.getSchema().getType("Success");
        } else {
            return (GraphQLObjectType) env.getSchema().getType("Failure");
        }
    };
}
