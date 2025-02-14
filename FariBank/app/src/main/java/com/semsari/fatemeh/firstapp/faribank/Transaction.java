package com.semsari.fatemeh.firstapp.faribank;

import java.time.Instant;

public class Transaction implements Comparable<Transaction> {
    private Instant transactionDate;
    private String transactionType;
    private String issueTracking;
    private double amount = 0;

    public Transaction(Instant tansactionDate, String transactionType, String issueTracking) {
        this.transactionDate = tansactionDate;
        this.transactionType = transactionType;
        this.issueTracking = issueTracking;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setIssueTracking(String issuTracking) {
        this.issueTracking = issuTracking;
    }

    public String getIssueTracking() {
        return issueTracking;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    @Override
    public int compareTo(Transaction other) {
        return other.getTransactionDate().compareTo(this.getTransactionDate());
    }

    @Override
    public String toString() {
        return null;
    }
}
