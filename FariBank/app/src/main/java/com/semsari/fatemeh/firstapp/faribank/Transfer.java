package com.semsari.fatemeh.firstapp.faribank;

import java.time.Instant;

public class Transfer extends Transaction {
    private String originCard;
    private String destinationCard;

    public Transfer(String originCard, String destinationCard, Instant transactionDate, String transactionType,
            String issueTracking) {
        super(transactionDate, transactionType, issueTracking);
        this.destinationCard = destinationCard;
        this.originCard = originCard;
    }

    public String getOriginCard() {
        return originCard;
    }

    public void setOriginCard(String originCard) {
        this.originCard = originCard;
    }

    public void setDestinationCard(String destinationCard) {
        this.destinationCard = destinationCard;
    }

    public String getDestinationCard() {
        return destinationCard;
    }

    @Override
    public String toString() {
        return "\u001B[34mOrigin Card Number:\u001B[0m" + getOriginCard() + "\n"
                + "\u001B[34mDestination Card Number:\u001B[0m" +
                getDestinationCard() + "\n" + "\u001B[34mTransaction Date:\u001B[0m" + getTransactionDate().toString()
                + "\n" + "\u001B[34mTrasaction Type:\u001B[0m" + getTransactionType() + "\n"
                + "\u001B[34mIssue Tracking:\u001B[0m" + getIssueTracking() + "\n";
    }

}
