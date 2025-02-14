package com.semsari.fatemeh.firstapp.faribank;

import com.semsari.fatemeh.firstapp.faribank.util.Calendar;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    private double amount = 0;
    private int months = 0;
    private String status = "";
    private String type = "";

    private Instant startTime;

    private double installment = 0;

    User user = null;

    public Loan(double amount, int months, String status, String type, Instant startTime, User user) {
        this.amount = amount;
        this.months = months;
        this.status = status;
        this.type = type;
        this.startTime = startTime;
        this.user = user;
    }

    private List<Boolean> payedInstallments = new ArrayList<>();

    public List<Boolean> getPayedInstallments() {
        return payedInstallments;
    }

    public void addPayedInstallments(Boolean status) {
        payedInstallments.add(status);
    }

    public boolean checkInstallments(){
        Instant now = Calendar.now();
        Duration duration = Duration.between(startTime, now);
        int passedMonths = (int) duration.getSeconds()/540000;
        if(passedMonths > payedInstallments.size()){
            return false;
        }
        return true;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void payInstallment(){
        installment = amount/months;
        user.getCreditCard().setCredit(user.getCreditCard().getCredit() - installment);
        Transaction transfer = new Transfer(user.getCreditCard().getCardNumber(), "1111", Calendar.now(), "transfer", Integer.toString(user.generateIssueTracking()));
        user.addTransaction(transfer);
        addPayedInstallments(true);
    }
}
