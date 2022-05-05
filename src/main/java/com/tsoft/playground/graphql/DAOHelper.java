package com.tsoft.playground.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DAOHelper {
    @Bean
    public LogMessageDAO logMessageDAO() {
        return new LogMessageDAO();
    }

    @Bean
    public SupportCaseDAO supportCaseDAO() {
        return new SupportCaseDAO();
    }

}
