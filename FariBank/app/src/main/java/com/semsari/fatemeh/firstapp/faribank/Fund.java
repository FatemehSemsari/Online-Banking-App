package com.semsari.fatemeh.firstapp.faribank;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Fund {
    private double fundCredit = 0;
    private FundType fundType;

    public void addTransaction(FundTransaction transaction) {
        transactions.add(transaction);
    }

    public List<FundTransaction> getTransactions() {
        return transactions;
    }

    private List<FundTransaction> transactions = new ArrayList<>();

    public Fund(FundType fundType) {
        this.fundType = fundType;
    }

    public void setFundCredit(double fundCredit) {
        this.fundCredit = fundCredit;
    }

    public double getFundCredit() {
        return fundCredit;
    }

    public void setFundType(FundType fundType) {
        this.fundType = fundType;
    }

    public FundType getFundType() {
        return fundType;
    }

    public void setTime(int days, int months) {

    }

    public long getTime() {
        return (Long) null;
    }

    public boolean checkFund(Instant now) {
        return false;
    }
}
