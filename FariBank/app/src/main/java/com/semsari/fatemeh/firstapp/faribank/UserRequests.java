package com.semsari.fatemeh.firstapp.faribank;

public class UserRequests {

    private String text;
    private boolean isUserRequest;

    public UserRequests(String text, boolean isUserRequest) {
        this.text = text;
        this.isUserRequest = isUserRequest;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUserRequest() {
        return isUserRequest;
    }

    public void setUserRequest(boolean userRequest) {
        isUserRequest = userRequest;
    }
}
