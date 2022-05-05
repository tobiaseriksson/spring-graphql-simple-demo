package com.tsoft.playground.graphql;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private SupportCaseDAO supportCaseDAO;

    @Autowired
    private LogMessageDAO logMessageDAO;

    public DataFetcher allSupportCases() {
        return dataFetchingEnvironment -> {
            return supportCaseDAO.all()
                    .stream()
                    .collect(Collectors.toList());
        };
    }

    public DataFetcher logMessages() {
        return dataFetchingEnvironment -> {
            SupportCase supportCase = dataFetchingEnvironment.getSource();
            String supportCaseId = supportCase.getId();
            return logMessageDAO.getBySupportCase(supportCaseId);
        };
    }

    public DataFetcher someSupportCases() {
        return dataFetchingEnvironment -> {
            Integer limit = dataFetchingEnvironment.getArgument("limit");
            int max = 1000;
            if( limit != null ) {
                max = limit;
            }
            String titleContains = dataFetchingEnvironment.getArgument("titleContains");
            if( titleContains != null ) {
                return supportCaseDAO.all().stream().filter( sc -> sc.title.toLowerCase().contains(titleContains) ).limit(max).collect(
                                Collectors.toList());
            } else {
                return supportCaseDAO.all().stream().limit(max);
            }
        };
    }

}
