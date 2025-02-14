package com.semsari.fatemeh.firstapp.faribank;

import java.time.Instant;

public class Charge extends Transaction {
    public Charge(Instant tansactionDate, String transactionType, String issueTracking) {
        super(tansactionDate, transactionType, issueTracking);
    }

    @Override
    public String toString() {
        return "\u001B[34mTransaction Date:\u001B[0m" + getTransactionDate().toString()
                + "\n" + "\u001B[34mTrasaction Type:\u001B[0m" + getTransactionType() + "\n"
                + "\u001B[34mIssue Tracking:\u001B[0m" + getIssueTracking() + "\n";
    }
}
