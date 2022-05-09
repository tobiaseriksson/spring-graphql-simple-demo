package com.tsoft.playground.graphql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SupportCaseDAO {

    private Map<Integer,SupportCase> supportCases;

    public SupportCaseDAO() {
        supportCases = new HashMap<>();
        supportCases.put(1, new SupportCase("1", "1", "Cannot start computer", "Description of problem #1",
                        "cool-runnings@jamaica.com"));
        supportCases.put(2, new SupportCase("2", "1", "Cannot login", "Description of problem #2",
                        "cool-runnings@jamaica.com"));
        supportCases.put(3, new SupportCase("3", "1", "Still cannot log in!!!", "Description of problem #3",
                        "cool-runnings@jamaica.com"));
        supportCases.put(4, new SupportCase("4", "1", "You are the worst support, I Still cannot log in!!!",
                        "Description of problem ","cool-runnings@jamaica.com"));
        supportCases.put(5, new SupportCase("5", "3", "my mailbox is full",
                        "Description of problem","cool-runnings@jamaica.com"));
        supportCases.put(6, new SupportCase("6", "3", "need VPN access",
                        "Description of problem","cool-runnings@jamaica.com"));
    }

    public List<SupportCase> all() {
        return supportCases.values().stream().collect(Collectors.toList());
    }

    public SupportCase getById(String id){
        return supportCases.get(Integer.parseInt(id));
    }

    public synchronized SupportCase add(SupportCaseInput nSC){
        int max = supportCases.keySet().stream().mapToInt(v -> v).max().orElseThrow();
        SupportCase caze =  new SupportCase(""+(max + 1), nSC.priority, nSC.title, nSC.text, nSC.email);
        supportCases.put( max + 1, caze);
        return caze;
    }
}
