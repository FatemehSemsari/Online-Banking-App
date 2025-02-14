package com.semsari.fatemeh.firstapp.faribank;

public class CreditCard {
    private String cardNumber;
    private String cardPassword;
    private double credit = 0;

    public CreditCard(String cardNumber, String cardPassword) {
        this.cardNumber = cardNumber;
        this.cardPassword = cardPassword;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public String getCardPassword() {
        return cardPassword;
    }
}
