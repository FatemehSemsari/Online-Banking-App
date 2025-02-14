package com.semsari.fatemeh.firstapp.faribank;

import java.util.HashMap;
import java.util.Map;

public class AllPeople {
    private Map<String, Double> people = new HashMap<>();

    public void addPeople(String phoneNumber, Double simCredit) {
        people.put(phoneNumber, simCredit + people.getOrDefault(phoneNumber, 0.0));
    }

    public Map<String, Double> getPeople() {
        return people;
    }
}
