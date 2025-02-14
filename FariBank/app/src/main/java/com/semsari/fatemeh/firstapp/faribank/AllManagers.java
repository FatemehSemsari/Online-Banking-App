package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;

public class AllManagers {
    private List<Manager> allManagers = new ArrayList<>();

    public void addManager(Manager manager) {
        allManagers.add(manager);
    }

    public List<Manager> getManagers() {
        return allManagers;
    }
}
