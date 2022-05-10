package com.tsoft.playground.graphql;

public enum SupportCasePriority {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    public final String key;

    SupportCasePriority(String prio) {
        key = prio;
    }
}
