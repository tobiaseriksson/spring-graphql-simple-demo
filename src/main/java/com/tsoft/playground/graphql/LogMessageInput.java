package com.tsoft.playground.graphql;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * The GraphQL Schema
 *
 * input LogMessageInput {
 *   belongToCase: ID!
 *   txt: String
 * }
 *
 *
 */
public class LogMessageInput {

    String txt;
    String belongToCase;

    public LogMessageInput(String txt, String belongToCase) {
        this.txt = txt;
        this.belongToCase = belongToCase;
    }

    public LogMessageInput(HashMap<String, Object> map) {
        this.txt = (String) map.get("txt");
        this.belongToCase = (String) map.get("belongToCase");
    }

    public String getBelongToCase() {
        return belongToCase;
    }

    public void setBelongToCase(String belongToCase) {
        this.belongToCase = belongToCase;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "LogMessageInput{" + "txt='" + txt + '\'' + ", belongToCase='" + belongToCase + '\'' + '}';
    }
}
