package com.tsoft.playground.controllers;

import com.tsoft.playground.graphql.dao.AddressDAO;
import com.tsoft.playground.graphql.dao.LogMessageDAO;
import com.tsoft.playground.graphql.dao.SupportCaseDAO;
import com.tsoft.playground.graphql.dao.UserDAO;
import com.tsoft.playground.graphql.data.Address;
import com.tsoft.playground.graphql.data.LogMessage;
import com.tsoft.playground.graphql.data.SupportCase;
import com.tsoft.playground.graphql.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class RestEndpoints implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private SupportCaseDAO supportCaseDAO;

    @Autowired
    private LogMessageDAO logMessageDAO;

    public RestEndpoints() {
    }

    @GetMapping("/users")
    public List<User> user(@RequestParam(required = false, value = "name-contains") String nameContains, @RequestParam(required = false, value = "limit") Integer limit) {
        int max = 1000;
        if (limit != null) {
            max = limit;
        }
        if (nameContains != null) {
            return userDAO.all().stream().filter(c -> c.getFirstname().toLowerCase().contains(nameContains)).limit(max).collect(Collectors.toList());
        } else {
            return userDAO.all().stream().limit(max).collect(Collectors.toList());
        }
    }

    @GetMapping("/users/{id}")
    public User user(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return null;
        }
        return userDAO.getById(id.toString());
    }

    @GetMapping("/users/{id}/address")
    public Address addressForUser(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return null;
        }
        User user = userDAO.getById(id.toString());
        if (user == null) {
            return null;
        }
        return addressDAO.getById(user.getHomeAddress());
    }

    @GetMapping("/addresses/{id}")
    public Address address(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return null;
        }
        return addressDAO.getById(id.toString());
    }

    @GetMapping("/addresses")
    public List<Address> addressForUser2(@RequestParam(required = false, value = "user-id") Integer userId,
                    @RequestParam(required = false, value = "limit") Integer limit ){
        if (userId != null) {
            User user = userDAO.getById(userId.toString());
            if (user == null) {
                return null;
            }
            Address oneAddress = addressDAO.getById(user.getHomeAddress());
            return new LinkedList<Address>(Arrays.asList(oneAddress));
        }
        var result = addressDAO.all();
        if( limit != null ) {
            return result.stream().limit(limit).collect(Collectors.toList());
        }
        return result;
    }

    @GetMapping("/support-cases/{id}")
    public SupportCase supportCases(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return null;
        }
        return supportCaseDAO.getById(id.toString());
    }

    @GetMapping("/support-cases")
    public List<SupportCase> supportCasesByTitle(@RequestParam(required = false, value = "title-contains") String titleContains, @RequestParam(required = false, value = "limit") Integer limit) {
        int max = 1000;
        if (limit != null) {
            max = limit;
        }
        if (titleContains != null) {
            return supportCaseDAO.all().stream().filter(c -> c.getTitle().toLowerCase().contains(titleContains)).limit(max).collect(Collectors.toList());
        } else {
            return supportCaseDAO.all().stream().limit(max).collect(Collectors.toList());
        }
    }

    @GetMapping("/support-cases/{id}/log-messages")
    public List<LogMessage> logMessagesForCase(@PathVariable(value = "id") String id) {
        return logMessageDAO.getBySupportCase(id);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /**
         * List all the endpoints
         */
        LOGGER.info(" API Endpoints : ");
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        map.forEach((key, value) -> LOGGER.info("{} ", key));
    }
}
