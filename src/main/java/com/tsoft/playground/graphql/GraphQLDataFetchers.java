package com.tsoft.playground.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    public DataFetcher supportCase() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return supportCaseDAO.getById(id); // .all().stream().filter( sc -> sc.id.equals(id) ).findFirst();
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

    public DataFetcher addLogMessage() {
        return dataFetchingEnvironment -> {
            HashMap<String,Object> map = dataFetchingEnvironment.getArgument("logMessage");
            LogMessageInput logMessageInput = new ObjectMapper().convertValue(map, LogMessageInput.class);
            if( logMessageInput == null ) {
                throw new Exception("You need to provide a log message!");
            }
            SupportCase supportCase = supportCaseDAO.getById(logMessageInput.belongToCase);
            if( supportCase == null ){
                throw new Exception("The Support Case with ID "+logMessageInput.belongToCase+" does not exist!");
            }
            return logMessageDAO.add(logMessageInput);
        };
    }
    // addSupportCase
    public DataFetcher addSupportCase() {
        return dataFetchingEnvironment -> {
            HashMap<String,Object> map = dataFetchingEnvironment.getArgument("case");
            SupportCaseInput supportCaseInput = new ObjectMapper().convertValue(map, SupportCaseInput.class);
            if( supportCaseInput == null ) {
                throw new Exception("You need to provide a support case!");
            }
            return supportCaseDAO.add(supportCaseInput);
        };
    }
}
