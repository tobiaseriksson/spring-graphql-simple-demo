package com.tsoft.playground.graphql;

public enum SupportCaseStatus {

    NEW("New"),
    ONGOING("Ongoing"),
    CLOSED("Closed"),
    CANCELLED("Cancelled"),
    REOPENED("Reopened");

    public final String key;

    SupportCaseStatus(String key) {
        this.key = key;
    }
}
