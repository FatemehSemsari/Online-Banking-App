package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupporterLogIn {
    private List<Supporter> supporters = new ArrayList<>();

    public SupporterLogIn(AllSupporters allSupporters) {
        supporters = allSupporters.getSupporters();
    }

    public Supporter logIn() {
        System.out.println("\u001B[33mEnter phone number:\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        if (!phoneNumberExists(phoneNumber)) {
            System.out.println("\u001B[31mNo supporter with this phone number!\u001B[0m");
            return null;
        }
        System.out.println("\u001B[33mEnter password:\u001B[0m");
        String password = CostumScanner.getInstance().nextLine();
        if (!checkPassword(phoneNumber, password)) {
            System.out.println("\u001B[31mPassword is incorrect!\u001B[0m");
            return null;
        }
        return findSupporter(phoneNumber);
    }

    public Supporter findSupporter(String phoneNumber) {
        List<Supporter> filteredList = supporters.stream()
                .filter(supporter -> supporter.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
        return filteredList.get(0);
    }

    public boolean phoneNumberExists(String phoneNumber) {
        List<Supporter> filteredList = supporters.stream()
                .filter(supporter -> supporter.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
        return !filteredList.isEmpty();
    }

    public boolean checkPassword(String phoneNumber, String password) {
        List<Supporter> filteredList = supporters.stream()
                .filter(supporter -> supporter.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
        return filteredList.get(0).getPassword().equals(password);
    }
}
