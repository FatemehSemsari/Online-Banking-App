package com.semsari.fatemeh.firstapp.faribank;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerLogIn {
    public static boolean phoneNumberExists(AllManagers allManagers, String phoneNumber) {
        List<Manager> filtered = allManagers.getManagers().stream()
                .filter(manager -> manager.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
        return !filtered.isEmpty();
    }

    public static boolean checkPassWord(AllManagers allManagers, String phoneNumber, String passWord) {
        List<Manager> filtered = allManagers.getManagers().stream()
                .filter(manager -> manager.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
        return filtered.get(0).getPassword().equals(passWord);
    }

    public static Manager findManager(AllManagers allManagers, String phoneNumber) {
        List<Manager> filtered = allManagers.getManagers().stream()
                .filter(manager -> manager.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
        return filtered.get(0);
    }
}
