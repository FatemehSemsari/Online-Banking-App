package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;

public class RecentTransfer {
    private List<String> cardNumbers = new ArrayList<>();

    public void addCardNumber(String cardNumber) {
        cardNumbers.add(cardNumber);
    }

    public List<String> getCardNumbers() {
        return cardNumbers;
    }
}
