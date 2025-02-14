package com.semsari.fatemeh.firstapp.faribank;

public class Request {
    private RequestSubjects subject;
    private String request = "";
    private String status = "";
    private String answer = "";
    private User user = null;

    public Request(RequestSubjects subject, String request, String status, String answer, User user) {
        this.subject = subject;
        this.request = request;
        this.status = status;
        this.answer = answer;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setSubject(RequestSubjects subject) {
        this.subject = subject;
    }

    public RequestSubjects getSubject() {
        return subject;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        if (getAnswer() == null) {
            return "\u001B[34mSubject:\u001B[0m" + getSubject() + "\n" + "\u001B[34mRequest:\u001B[0m" + getRequest()
                    + "\n" +
                    "\u001B[34mStatus:\u001B[0m" + getStatus() + "\n" + "\u001B[34mUser phone number\u001B[0m"
                    + user.getPhoneNumber() + "\n"
                    + "\u001B[34mAnswer:\u001B[0m" + "\u001B[33mNo answers yet!\u001B[0m" + "\n";
        } else {
            return "\u001B[34mSubject:\u001B[0m" + getSubject() + "\n" + "\u001B[34mRequest:\u001B[0m" + getRequest()
                    + "\n" +
                    "\u001B[34mStatus:\u001B[0m" + getStatus() + "\n" + "\u001B[34mUser phone number\u001B[0m"
                    + user.getPhoneNumber() + "\n"
                    + "\u001B[34mAnswer:\u001B[0m" + getAnswer() + "\n";
        }
    }
}
