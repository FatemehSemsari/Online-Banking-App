package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationQueue {

    private List<User> individuals = new ArrayList<>();

    public void addUser(User user) {
        individuals.add(user);
    }

    public List<User> getIndividuals() {
        return individuals;
    }
}
