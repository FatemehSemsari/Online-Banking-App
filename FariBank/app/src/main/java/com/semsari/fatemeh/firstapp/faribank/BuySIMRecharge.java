package com.semsari.fatemeh.firstapp.faribank;

import java.util.Map;

import com.semsari.fatemeh.firstapp.faribank.util.Calendar;

public class BuySIMRecharge {
    public void buyRecharge(AllPeople allPeople, User user, AllUsers allUsers) {
        Menu.printSIMRecharge();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                buyForContact(user);
                break;
            case 2:
                buyViaPhoneNumber(user, allPeople, allUsers);
                break;
            default:
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                break;
        }
    }

    public void buyViaPhoneNumber(User user, AllPeople allPeople, AllUsers allUsers) {
        System.out.println("\u001B[34mEnter phone number:\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[34mEnter amount you want to transfer:\u001B[0m");
        double amount = CostumScanner.getInstance().nextDouble();
        if (amount > user.getCreditCard().getCredit()) {
            System.out.println("\u001B[31mYour account balance is insufficient!\u001B[0m");
            return;
        }
        if (!isNumeric(phoneNumber)) {
            System.out.println("\u001B[31mInvalid phone number!\u001B[0m");
        }
        if (!allUsers.getPhoneNumbers().contains(phoneNumber)) {
            allPeople.addPeople(phoneNumber, amount);
            user.handleFractionalCurrency(amount);
            System.out.println("\u001B[32mCharge transfered successfully!\u001B[0m");
            return;
        }
        for (User individual : allUsers.getUser()) {
            if (individual.getPhoneNumber().equals(phoneNumber)) {
                individual.getSIMCard().setSIMCredit(individual.getSIMCard().getSIMCredit() + amount);
            }
        }
        user.getCreditCard().setCredit(user.getCreditCard().getCredit() - amount);
        completeTransfering(user);
    }

    public void completeTransfering(User user){
        Calendar calendar = new Calendar();
        Transaction charge = new Charge(calendar.now(), "Charge", Integer.toString(user.generateIssueTracking()));
        user.getTransactions().add(charge);
        System.out.println("\t" + "\u001B[34m<<Receipt>>\u001B[0m");
        System.out.println(charge.toString());
        System.out.println("\u001B[34mRemaining:\u001B[0m" + user.getCreditCard().getCredit());
    }

    public boolean isNumeric(String phoneNumber) {
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (phoneNumber.charAt(i) < '1' || phoneNumber.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public void buyForContact(User user) {
        user.printContacts();
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        choice--;
        System.out.println("\u001B[34mEnter amount you want to buy:\u001B[0m");
        double amount = CostumScanner.getInstance().nextDouble();
        if (amount > user.getCreditCard().getCredit()) {
            System.out.println("\u001B[31mYour account balance is insufficient!\u001B[0m");
            return;
        }
        user.handleFractionalCurrency(amount);
        User contact = findContact(user.getContacts(), choice);
        contact.getSIMCard().setSIMCredit(contact.getSIMCard().getSIMCredit() + amount);
        Calendar calendar = new Calendar();
        Transaction charge = new Charge(calendar.now(), "Charge", Integer.toString(user.generateIssueTracking()));
        user.getTransactions().add(charge);
        System.out.println("\t" + "\u001B[34m<<Receipt>>\u001B[0m");
        System.out.println(charge.toString());
        System.out.println("\u001B[34mRemaining:\u001B[0m" + user.getCreditCard().getCredit());
    }

    public User findContact(Map<String[], User> contacts, int choise) {
        int number = 0;
        for (User contact : contacts.values()) {
            if (number == choise) {
                return contact;
            }
            number++;
        }
        return null;
    }
}
