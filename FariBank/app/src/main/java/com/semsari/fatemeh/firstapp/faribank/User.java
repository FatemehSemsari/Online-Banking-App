package com.semsari.fatemeh.firstapp.faribank;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.semsari.fatemeh.firstapp.faribank.util.Calendar;

public class User extends Person {
    private int contactFeature = 1;
    private int authenticated = 0;

    private List<Loan> loans = new ArrayList<>();
    private String comment, password, accountNumber;
    private List<Transaction> transactions = new ArrayList<>();
    private Map<String[], User> contacts = new HashMap<>();
    private RecentTransfer recentTransfers = new RecentTransfer();
    private List<Request> requests = new ArrayList<>();
    private static Menu menu = new Menu();
    private List<Fund> funds = new ArrayList<>();
    private boolean haveAccess = true;
    private PrintLists printLists = new PrintLists();
    private List<UserRequests> requestMessages = new ArrayList<>();


    public User(String name, String familyName, String phoneNumber, String idNumber, String password) {
        super(name, familyName, phoneNumber, idNumber);
        this.password = password;
    }

    public List<UserRequests> getRequestMessages() {
        return requestMessages;
    }

    public void setRequestMessage(UserRequests userRequests) {
        requestMessages.add(userRequests);
    }

    public List<Loan> getLoans(){
        return loans;
    }

    public void addLoan(Loan loan){
        loans.add(loan);
    }

    public RecentTransfer getRecentTransfers() {
        return recentTransfers;
    }

    public void setAccessStatus(boolean status) {
        haveAccess = status;
    }

    public boolean getAccess() {
        return haveAccess;
    }

    public void addFund(Fund fund) {
        funds.add(fund);
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public Map<String[], User> getContacts() {
        return contacts;
    }

    public void setContactFeature(int contactFeature) {
        this.contactFeature = contactFeature;
    }

    public int getContactFeature() {
        return contactFeature;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAuthenticationStatus(int authenticated) {
        this.authenticated = authenticated;
    }

    public int getAuthenticationStatus() {
        return authenticated;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setsupporterComment(String comment) {
        this.comment = comment;
    }

    public void getSupporterComment() {
        System.out.println(comment);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "\u001B[34mname:\u001B[0m" + getName() + "\n" + "\u001B[34mfamily name:\u001B[0m" + getFamilyName()
                + "\n" +
                "\u001B[34mid:\u001B[0m" + getId() + "\n" + "\u001B[34mphone number:\u001B[0m" + getPhoneNumber()
                + "\n";
    }

    public void chargeTheAccount(double amount) {
        Calendar calendar = new Calendar();
        Transaction charge = new Charge(calendar.now(), "Charge", Integer.toString(generateIssueTracking()));
        charge.setAmount(amount);
        getTransactions().add(charge);
    }

    public static int generateIssueTracking() {
        Random random = new Random();
        return random.nextInt(1000000);
    }

    public void viewAccountCredit() {
        System.out.println("\u001B[34mYour Credit: \u001B[0m" + getCreditCard().getCredit());
    }

    public void transactions() {
        menu.printTransactionOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                showAllTransactions();
                break;
            case 2:
                searchTransaction();
                break;
            default:
                break;
        }
    }

    public void searchTransaction() {
        boolean find = false;
        System.out.println("\u001B[34mEnter date in this format -> yyyy-mm-ddThh:mm:ssZ\u001B[0m");
        String date = CostumScanner.getInstance().nextLine();
        Instant instant = Instant.parse(date);
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction transaction : getTransactions()) {
            if (transaction.getTransactionDate().equals(instant)) {
                filtered.add(transaction);
                find = true;
            }
        }
        for (Transaction transaction : filtered) {
            System.out.println(transaction.toString());
        }
        if (!find) {
            System.out.println("\u001B[31mThere is no transaction in this date!\u001B[0m");
        }
    }

    public void showAllTransactions() {
        Collections.sort(getTransactions());
        for (Transaction transaction : getTransactions()) {
            System.out.println(transaction.toString());
        }
    }

    public void handleContacts(AllUsers allUsers) {
        if (contactFeature == 0) {
            System.out.println("\u001B[31mYou need to enable the contacts feature from the settings!\u001B[0m");
            return;
        }
        menu.printContactsOptions();
        int choice = CostumScanner.getInstance().nextInt();
        while (choice != 4) {
            switch (choice) {
                case 1:
                    printLists.printContacts(contacts);
                    getContactInfo(allUsers);
                    break;
                case 2:
                    printLists.printContacts(contacts);
                    editContact(allUsers);
                    break;
                case 3:
                    printLists.printContacts(contacts);
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }
            menu.printContactsOptions();
            choice = CostumScanner.getInstance().nextInt();
        }
    }

    public String[] getContactInfo(AllUsers allUsers) {
        System.out.println("\u001B[34mName:\u001B[0m");
        String name = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[34mFamily name:\u001B[0m");
        String familyName = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[34mPhone number:\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        addContact(name, familyName, phoneNumber, allUsers);
        String[] fullName = new String[2];
        fullName[0] = name;
        fullName[1] = familyName;
        return fullName;
    }

    public int addContact(String name, String familyName, String phoneNumber, AllUsers allUsers) {
        int addStatus = 0;
        if (findUser(allUsers, phoneNumber) != null) {
            if(!getContactsList().contains(findUser(allUsers, phoneNumber))) {
                User contact = findUser(allUsers, phoneNumber);
                String[] fullName = new String[2];
                fullName[0] = name;
                fullName[1] = familyName;
                contacts.put(fullName, contact);
                System.out.println("\u001B[32mThe process done sucessfully\u001B[0m");
                addStatus = 1;
            }else {
                return 0;
            }
        } else {
            System.out.println("\u001B[31mNo such user in the bank!\u001B[0m");
            addStatus = -1;
        }
        return addStatus;
    }

    public void updateContact(String[] name, String phoneNumber){
        String[] keyToRemove = null;
        User userToUpdate = null;

        for (Map.Entry<String[], User> u : contacts.entrySet()) {
            if (u.getValue().getPhoneNumber().equals(phoneNumber)) {
                keyToRemove = u.getKey();
                userToUpdate = u.getValue();
                break;
            }
        }

        if (keyToRemove != null && userToUpdate != null) {
            contacts.remove(keyToRemove);
            contacts.put(name, userToUpdate);
        }
    }

    public boolean containContact(String phoneNumber) {
        for (User user : getContacts().values()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public static User findUser(AllUsers allUsers, String phoneNumber) {
        for (User user : allUsers.getUser()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user;
            }
        }
        return null;
    }

    public void editContact(AllUsers allUsers) {
        System.out.println("\u001B[34mChoose a contact:\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        choice--;
        edit(choice, allUsers);
    }

    public void edit(int index, AllUsers allUsers) {
        printContact(index);
        menu.printchangableOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                changeName(index);
                break;
            case 2:
                changeFamilyName(index);
                break;
            case 3:
                changePhoneNumber(index, allUsers);
                break;
            case 4:
                break;
            default:
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                break;
        }
    }

    public void changePhoneNumber(int index, AllUsers allUsers) {
        System.out.println("\u001B[34mEnter new phone number\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        int number = 0;
        String name = "";
        String familyName = "";
        for (String[] contact : getContacts().keySet()) {
            if (number == index) {
                name = contact[0];
                familyName = contact[1];
                break;
            }
            number++;
        }
        addContact(name, familyName, phoneNumber, allUsers);
    }

    public void changeFamilyName(int index) {
        System.out.println("\u001B[34mEnter new family name:\u001B[0m");
        String familyName = CostumScanner.getInstance().nextLine();
        int number = 0;
        for (String[] contact : getContacts().keySet()) {
            if (number == index) {
                contact[1] = familyName;
                break;
            }
            number++;
        }
        System.out.println("\u001B[32mChange applied\u001B[0m");
    }

    public void changeName(int index) {
        System.out.println("\u001B[34mEnter new name:\u001B[0m");
        String name = CostumScanner.getInstance().nextLine();
        int number = 0;
        for (String[] contact : getContacts().keySet()) {
            if (number == index) {
                contact[0] = name;
                break;
            }
            number++;
        }
        System.out.println("\u001B[32mChange applied\u001B[0m");
    }

    public void printContact(int index) {
        int number = 0;
        for (String[] contact : getContacts().keySet()) {
            if (number == index) {
                System.out.println(contact[0] + " " + contact[1]);
            }
            number++;
        }
        System.out.println(getContacts().values().toArray(new User[0])[index].getPhoneNumber());
    }

    public void printContacts() {
        int counter = 1;
        for (String[] contact : getContacts().keySet()) {
            System.out.println(counter + "." + contact[0] + " " + contact[1]);
            counter++;
        }
    }

    public void handleFractionalCurrency(double amount) {
        if (!hasRemainingFund()) {
            this.getCreditCard().setCredit(this.getCreditCard().getCredit() - amount);
            return;
        }
        int number = (int) amount;
        int digits = (int) (3 * String.valueOf(number).length() / 4);
        int fraction = calculateFraction(digits, number);
        if (fraction > amount) {
            this.getCreditCard().setCredit(this.getCreditCard().getCredit() - amount - 1200);
            return;
        }
        List<Fund> filtered = funds.stream().filter(fund -> fund.getFundType().equals(FundType.REMAINING_FUND))
                .collect(Collectors.toList());
        filtered.get(0).setFundCredit(filtered.get(0).getFundCredit() + fraction);
        FundTransaction fundTransaction = new FundTransaction();
        fundTransaction.setType("Deposit");
        fundTransaction.setAmount(fraction);
        filtered.get(0).addTransaction(fundTransaction);
        this.getCreditCard().setCredit(this.getCreditCard().getCredit() - amount - fraction - 1200);
    }

    public int calculateFraction(int digits, int amount) {
        String number = Integer.toString(amount);
        number = number.substring(number.length() - digits);
        return (int) Math.pow(10, digits) - Integer.parseInt(number);
    }

    public boolean hasRemainingFund() {
        List<Fund> filtered = funds.stream().filter(fund -> fund.getFundType().equals(FundType.REMAINING_FUND))
                .collect(Collectors.toList());
        return !filtered.isEmpty();
    }

    public void settings(User user) {
        menu.printSettings();
        int choice = CostumScanner.getInstance().nextInt();
        Settings settings = new Settings(user);
        switch (choice) {
            case 1:
                settings.changeCardPassword();
                break;
            case 2:
                settings.changeLoginPassword();
                break;
            case 3:
                settings.contactsFeature();
                break;
            default:
                break;
        }
    }

    public void handleMoneyTransfer(User user, AllUsers allUsers, List<Person> otherBanksUsers) {
        UserMoneyTransfer moneyTransfer = new UserMoneyTransfer(allUsers, getRecentTransfers(), user, otherBanksUsers);
        moneyTransfer.handleMoneyTransfer();
    }

    public List<User> getContactsList(){
        List<User> userContacts = new ArrayList<>();
        for (Map.Entry<String[], User> entry : contacts.entrySet()) {
            String[] key = entry.getKey();
            User user = entry.getValue();
            User contact = new Contact(key[0], key[1], user.getPhoneNumber(), user.getId(), user.getPassword());
            contact.setCreditCard(user.getCreditCard());
            userContacts.add(contact);
        }
        return userContacts;
    }

    public void removeContact(String[] name){
        for(Map.Entry<String[], User> u : contacts.entrySet()){
            if(u.getKey()[0].equals(name[0]) && u.getKey()[1].equals(name[1])){
                contacts.remove(u.getKey());
                break;
            }
        }
    }

}