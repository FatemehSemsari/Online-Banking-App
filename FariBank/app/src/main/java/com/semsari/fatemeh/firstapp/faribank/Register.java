package com.semsari.fatemeh.firstapp.faribank;

import java.util.Map;
import java.util.Random;

public class Register {
//    public void register(AllUsers allUsers, AuthenticationQueue authentication, AllPeople allPeople,
//            AllRequests allRequests) {
//        System.out.println("\u001B[33mEnter your name:\u001B[0m");
//        String name = CostumScanner.getInstance().nextLine();
//        System.out.println("\u001B[33mEnter your family name:\u001B[0m");
//        String familyName = CostumScanner.getInstance().nextLine();
//        System.out.println("\u001B[33mEnter your phone number:\u001B[0m");
//        String phoneNumber = CostumScanner.getInstance().nextLine();
//        if (phoneNumberIsAvailable(phoneNumber, allUsers)) {
//            System.out.println("\u001B[31mThis phone number already exists!\u001B[0m");
//            return;
//        }
//        System.out.println("\u001B[33mEnter your ID:\u001B[0m");
//        String idNumber = CostumScanner.getInstance().nextLine();
//        if (idIsAvailable(idNumber, allUsers)) {
//            System.out.println("\u001B[31mThis id already exists!\u001B[0m");
//            return;
//        }
//        String password = "";
//        do {
//            System.out.println("\u001B[33mEnter your password:\u001B[0m");
//            password = CostumScanner.getInstance().nextLine();
//        } while (!passwordIsSafe(password));
//        User user = new User(name, familyName, phoneNumber, idNumber, password);
//        sendRequestToSupporter(user, authentication, allPeople, allRequests);
//        System.out.println("\u001B[32mYour request for authentication has been successfully registered!\u001B[0m");
//    }

    public boolean phoneNumberIsAvailable(String phoneNumber, AllUsers allUsers) {
        return allUsers.getPhoneNumbers().contains(phoneNumber);
    }

    public boolean idIsAvailable(String idNumber, AllUsers allUsers) {
        return allUsers.getIds().contains(idNumber);
    }

    public boolean passwordIsSafe(String password) {
        if (password.matches(".*[a-z].*")) {
            if (password.matches(".*[A-Z].*")) {
                if (password.matches(".*[0-9].*")) {
                    if (password.matches(".*[!-,].*") || password.matches(".*[:-@].*")) {
                        return true;
                    } else {
                        System.out.println("\u001B[31mweek password!\u001B[0m");
                        return false;
                    }
                } else {
                    System.out.println("\u001B[31mweek password!\u001B[0m");
                    return false;
                }
            } else {
                System.out.println("\u001B[31mweek password!\u001B[0m");
                return false;
            }
        } else {
            System.out.println("\u001B[31mweek password!\u001B[0m");
            return false;
        }
    }

    public void sendRequestToSupporter(User user, AuthenticationQueue authentication, AllPeople allPeople,
            AllRequests allRequests) {
        checkUser(user, allPeople);
        authentication.addUser(user);
        Request request = new Request(RequestSubjects.AUTHENTICATION, "I want to register", "Registered", null, user);
        user.addRequest(request);
        allRequests.addRequest(request);
    }

    public void checkUser(User user, AllPeople allPeople) {
        for (Map.Entry<String, Double> person : allPeople.getPeople().entrySet()) {
            if (person.getKey().equals(user.getPhoneNumber())) {
                user.getSIMCard().setSIMCredit(person.getValue());
            }
        }
    }

    public void register(User user, AllUsers allUsers){
        allUsers.addPhoneNumber(user.getPhoneNumber());
        allUsers.addId(user.getId());
        user.setAuthenticationStatus(1);
        giveCardNumberAndCardPassword(user);
        giveAccountNumber(user);
        allUsers.addUser(user);
    }

    public void giveAccountNumber(User user) {
        Random random = new Random();
        int number = random.nextInt(1000000);
        String accountNumber = Integer.toString(number);
        user.setAccountNumber(accountNumber);
    }

    public void giveCardNumberAndCardPassword(User user) {
        Random random = new Random();
        int number = random.nextInt(90000000) + 10000000;
        int password = random.nextInt(9000) + 1000;
        String cardNumber = "58946311" + Integer.toString(number);
        String cardPassword = Integer.toString(password);
        CreditCard creditCard = new CreditCard(cardNumber, cardPassword);
        user.setCreditCard(creditCard);
    }
}
