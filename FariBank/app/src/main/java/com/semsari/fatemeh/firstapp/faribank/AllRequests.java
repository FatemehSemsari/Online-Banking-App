package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;

public class AllRequests {
    private List<Request> allRequests = new ArrayList<>();

    public void addRequest(Request request) {
        allRequests.add(request);
    }

    public List<Request> getRequests() {
        return allRequests;
    }
}
