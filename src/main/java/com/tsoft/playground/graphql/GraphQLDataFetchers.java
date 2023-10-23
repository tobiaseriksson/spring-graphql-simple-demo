package com.tsoft.playground.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoft.playground.graphql.dao.AddressDAO;
import com.tsoft.playground.graphql.dao.LogMessageDAO;
import com.tsoft.playground.graphql.dao.SupportCaseDAO;
import com.tsoft.playground.graphql.dao.UserDAO;
import com.tsoft.playground.graphql.data.LogMessage;
import com.tsoft.playground.graphql.data.LogMessageInput;
import com.tsoft.playground.graphql.data.SupportCase;
import com.tsoft.playground.graphql.data.SupportCaseInput;
import com.tsoft.playground.graphql.data.User;
import graphql.language.Field;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private SupportCaseDAO supportCaseDAO;

    @Autowired
    private LogMessageDAO logMessageDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private UserDAO userDAO;

    private void logSelectedField(Field field) {
        System.out.println(field);
        if (field.getSelectionSet() == null || field.getSelectionSet().getSelections() == null) {
            return;
        }
        field.getSelectionSet().getSelections().stream().forEach(s -> {
            if (s instanceof Field) {
                Field subField = (Field) s;
                logSelectedField(subField);
            } else {
                System.err.println("Found something else " + s.toString());
            }
        });

    }

    public DataFetcher allSupportCases() {
        return dataFetchingEnvironment -> {
            System.out.println("*****************************");
            List<Field> fields = dataFetchingEnvironment.getMergedField().getFields();
            fields.stream().forEach(f -> {
                logSelectedField(f);
            });
            return supportCaseDAO.all().stream().collect(Collectors.toList());
        };
    }

    public DataFetcher logMessagesFromSupportCase() {
        return dataFetchingEnvironment -> {
            Integer limit = dataFetchingEnvironment.getArgument("limit");
            int max = 1000;
            if (limit != null) {
                max = limit;
            }
            SupportCase supportCase = dataFetchingEnvironment.getSource();
            String supportCaseId = supportCase.getId();
            return logMessageDAO.getBySupportCase(supportCaseId).stream().limit(max);
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
            if (limit != null) {
                max = limit;
            }
            String titleContains = dataFetchingEnvironment.getArgument("titleContains");
            if (titleContains != null) {
                return supportCaseDAO.all().stream().filter(sc -> sc.getTitle().toLowerCase().contains(titleContains)).limit(max).collect(Collectors.toList());
            } else {
                return supportCaseDAO.all().stream().limit(max);
            }
        };
    }

    public DataFetcher addLogMessage() {
        return dataFetchingEnvironment -> {
            HashMap<String, Object> map = dataFetchingEnvironment.getArgument("logMessage");
            LogMessageInput logMessageInput = new ObjectMapper().convertValue(map, LogMessageInput.class);
            if (logMessageInput == null) {
                throw new Exception("You need to provide a log message!");
            }
            SupportCase supportCase = supportCaseDAO.getById(logMessageInput.getBelongToCase());
            if (supportCase == null) {
                throw new Exception("The Support Case with ID " + logMessageInput.getBelongToCase() + " does not exist!");
            }
            return logMessageDAO.add(logMessageInput);
        };
    }

    // addSupportCase
    public DataFetcher addSupportCase() {
        return dataFetchingEnvironment -> {
            HashMap<String, Object> map = dataFetchingEnvironment.getArgument("case");
            SupportCaseInput supportCaseInput = new ObjectMapper().convertValue(map, SupportCaseInput.class);
            if (supportCaseInput == null) {
                throw new Exception("You need to provide a support case!");
            }
            return supportCaseDAO.add(supportCaseInput);
        };
    }

    public DataFetcher allUsers() {
        return dataFetchingEnvironment -> {
            Integer limit = dataFetchingEnvironment.getArgument("limit");
            int max = 1000;
            if (limit != null) {
                max = limit;
            }
            return userDAO.all().stream().limit(max);
        };
    }

    public DataFetcher user() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return userDAO.getById(id);
        };
    }

    public DataFetcher addressFromUser() {
        return dataFetchingEnvironment -> {
            User user = dataFetchingEnvironment.getSource();
            String addressId = user.getHomeAddress();
            return addressDAO.getById(addressId);
        };
    }

    public DataFetcher userFromCase() {
        return dataFetchingEnvironment -> {
            SupportCase caze = dataFetchingEnvironment.getSource();
            String userId = caze.getCreatedBy();
            return userDAO.getById(userId);
        };
    }

    public DataFetcher userFromLogMessage() {
        return dataFetchingEnvironment -> {
            LogMessage logMessage = dataFetchingEnvironment.getSource();
            String userId = logMessage.getUserId();
            return userDAO.getById(userId);
        };
    }

    public DataFetcher supportCasesFromUser() {
        return dataFetchingEnvironment -> {
            Integer limit = dataFetchingEnvironment.getArgument("limit");
            int max = 1000;
            if (limit != null) {
                max = limit;
            }
            String casePriorityFilter = dataFetchingEnvironment.getArgument("priority");
            User user = dataFetchingEnvironment.getSource();
            String userId = user.getId();
            return supportCaseDAO.all().stream().filter(caze -> caze.getCreatedBy().equals(userId)).filter(caze -> casePriorityFilter==null || caze.getPriority().equalsIgnoreCase(casePriorityFilter)).limit(max);
        };
    }
}
