package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;

public class AllUsers {
    private List<String> phoneNumbers = new ArrayList<>();
    private List<String> ids = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUser() {
        return users;
    }

    public void addPhoneNumber(String phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public void addId(String idNumber) {
        ids.add(idNumber);
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<String> getIds() {
        return ids;
    }

}
