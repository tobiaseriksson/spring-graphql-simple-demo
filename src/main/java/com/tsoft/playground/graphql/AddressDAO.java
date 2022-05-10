package com.tsoft.playground.graphql;

import com.tsoft.playground.dataGenerator.FakeDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressDAO {

    @Autowired
    private FakeDatabase database;

    public AddressDAO() {
    }

    public List<Address> all() {
        return database.addresses.values().stream().collect(Collectors.toList());
    }

    public Address getById(String id) {
        return database.addresses.get(id);
    }

}
