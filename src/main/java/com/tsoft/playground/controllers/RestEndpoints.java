package com.tsoft.playground.controllers;

import com.tsoft.playground.graphql.LogMessage;
import com.tsoft.playground.graphql.LogMessageDAO;
import com.tsoft.playground.graphql.SupportCase;
import com.tsoft.playground.graphql.SupportCaseDAO;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class RestEndpoints implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SupportCaseDAO supportCaseDAO;

    @Autowired
    private LogMessageDAO logMessageDAO;

    public RestEndpoints() {
    }

    @GetMapping("/support-case/{id}")
    public SupportCase supportCases(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return null;
        }
        return supportCaseDAO.getById(id.toString());
    }

    @GetMapping("/support-case")
    public List<SupportCase> supportCasesByTitle(
                    @RequestParam(required = false, value = "title-contains") String titleContains) {
        if (titleContains != null) {
            return supportCaseDAO.all()
                            .stream()
                            .filter(c -> c.getTitle().toLowerCase().contains(titleContains))
                            .collect(Collectors.toList());
        } else {
            return supportCaseDAO.all();
        }
    }

    @GetMapping("/support-case/{id}/log-messages")
    public List<LogMessage> logMessagesForCase(@PathVariable(value = "id") String id) {
        return logMessageDAO.getBySupportCase(id);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /**
         * List all the endpoints in the log
         */
        LOGGER.info(" API Endpoints : ");
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(
                        "requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        map.forEach((key, value) -> LOGGER.info("{} ", key));
    }
}
