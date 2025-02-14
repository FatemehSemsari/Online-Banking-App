package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;

public class AllSupporters {
    private List<Supporter> allSupporters = new ArrayList<>();

    public void addSupporter(Supporter supporter) {
        allSupporters.add(supporter);
    }

    public List<Supporter> getSupporters() {
        return allSupporters;
    }
}
