package com.tsoft.playground.graphql.dao;

import com.tsoft.playground.dataGenerator.FakeDatabase;
import com.tsoft.playground.graphql.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDAO {

    @Autowired
    private FakeDatabase database;

    public UserDAO() {
    }

    public List<User> all() {
        return database.users.values().stream().collect(Collectors.toList());
    }

    public User getById(String id) {
        return database.users.get(id);
    }

}
