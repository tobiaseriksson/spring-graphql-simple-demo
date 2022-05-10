package com.tsoft.playground.graphql;

import com.tsoft.playground.dataGenerator.FakeDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SupportCaseDAO {

    @Autowired
    private FakeDatabase database;

    public SupportCaseDAO() {
    }

    public List<SupportCase> all() {
        return database.supportCases.values().stream().collect(Collectors.toList());
    }

    public SupportCase getById(String id){
        return database.supportCases.get(id);
    }

    public synchronized SupportCase add(SupportCaseInput nSC){
        String id = UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();
        SupportCase caze =  new SupportCase(id, nSC.priority, nSC.title, nSC.text, nSC.email, now.toString(),
                        nSC.createdBy, SupportCaseStatus.NEW.key );
        database.supportCases.put( caze.getId(), caze);
        return caze;
    }
}
