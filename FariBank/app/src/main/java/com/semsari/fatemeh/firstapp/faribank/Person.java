package com.semsari.fatemeh.firstapp.faribank;

public class Person {
    private String name;
    private String familyName;
    private String phoneNumber;
    private String idNumber;
    private CreditCard creditCard;
    private SIMCard SIMCard = new SIMCard();

    public Person(String name, String familyName, String phoneNumber, String idNumber) {
        this.name = name;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
    }

    public SIMCard getSIMCard() {
        return SIMCard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getId() {
        return idNumber;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

}
