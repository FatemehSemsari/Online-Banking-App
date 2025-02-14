package com.semsari.fatemeh.firstapp.faribank;

import java.util.*;

public class Entry {
    enum Role {
        USER, SUPPORTER
    }

    enum SupporterOptions {
        REQUESTS, USERS, EXIT, UNDEFINED
    }

    enum UserOptions {
        ACCOUNT_MANAGEMENT, CANTACTS, MONEY_TRANSFER, SUPPORT, SETTINGS, CAPITAL_FUNDS, BUY_SIM_RECHARGE, EXIT,
        UNDEFINED
    }

    enum ManagerOptions {
        BANK_SETTINGS, USERS_MANAGEMENT, AUTOMATIC_TRANSACTIONS, EXIT, UNDEFINED
    }

    private Menu menu = new Menu();
    private double interestRate = 13;
    private double commisionRate = 5;
    private Manager manager = new Manager("Manager", "Manager", "111", "1234", "MainManager");
    private AllPeople allPoeple = null;
    private AllManagers allManagers = null;
    private AllSupporters allSupporters = null;
    private AllRequests allRequests = new AllRequests();
    private List<Person> otherBanksUsers = new ArrayList<>();

    public Entry(AllPeople allPeople) {
        this.allPoeple = allPeople;
        addOtherBanksUsers();
    }

    private void addOtherBanksUsers() {
        Person person1 = new Person("person", "1", "321", "123");
        Person person2 = new Person("person", "2", "987", "789");
        Person person3 = new Person("person", "3", "456", "654");
        Person person4 = new Person("person", "4", "147", "741");
        person1.setCreditCard(new CreditCard("123456789", "1111"));
        person2.setCreditCard(new CreditCard("987654321", "1111"));
        person3.setCreditCard(new CreditCard("147852369", "1111"));
        person4.setCreditCard(new CreditCard("369852147", "1111"));
        otherBanksUsers.add(person1);
        otherBanksUsers.add(person2);
        otherBanksUsers.add(person3);
        otherBanksUsers.add(person4);
    }

    public Manager getManager(){
        return manager;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public void setCommisionRate(double commisionRate) {
        this.commisionRate = commisionRate;
    }

    public double getCommisionRate(){
        return commisionRate;
    }
    public List<Person> getOtherBanksUsers(){ return otherBanksUsers; }

    public void manager(AllUsers allUsers) {
        System.out.println("\u001B[36mEnter phone number:\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        if (!ManagerLogIn.phoneNumberExists(allManagers, phoneNumber)) {
            System.out.println("\u001B[31mPhone number does't match!\u001B[0m");
            return;
        }
        System.out.println("\u001B[36mEnter Password:\u001B[0m");
        String passWord = CostumScanner.getInstance().nextLine();
        if (!ManagerLogIn.checkPassWord(allManagers, phoneNumber, passWord)) {
            System.out.println("\u001B[31mPassword is incorrect!\u001B[0m");
            return;
        }
        System.out.println("\u001B[36m\n\tWelcome!\u001B[0m");
        Manager manager = ManagerLogIn.findManager(allManagers, phoneNumber);
        handleManager(manager, allUsers);
    }

    public void handleManager(Manager manager, AllUsers allUsers) {
        Menu.printManagerOptions();
        ManagerOptions managerOption = scanManagerOptions();
        while (managerOption != ManagerOptions.EXIT) {
            if (managerOption == ManagerOptions.UNDEFINED) {
                System.out.println("\u001B[31mUndefined Input!\u001B[0m");
            }
            handleManagerOptions(managerOption, manager, allUsers);
            Menu.printManagerOptions();
            managerOption = scanManagerOptions();
        }
    }

    public void handleManagerOptions(ManagerOptions managerOptions, Manager manager, AllUsers allUsers) {
        switch (managerOptions) {
            case BANK_SETTINGS:
                bankSettings(manager);
                break;
            case USERS_MANAGEMENT:
                manager.userManagement(allUsers, allSupporters, allManagers);
                break;
            case AUTOMATIC_TRANSACTIONS:
                break;
            case UNDEFINED:
                System.out.println("\u001B[31mUndefined Input!\u001B[0m");
                break;
            default:
                break;
        }
    }

    public void bankSettings(Manager manager) {
        Menu.printManagerSettings();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                System.out.println("\u001B[36mEnter interest rate:\u001B[0m");
                interestRate = CostumScanner.getInstance().nextDouble();
                break;
            case 2:
                System.out.println("\u001B[36mEnter commision rate:\u001B[0m");
                commisionRate = CostumScanner.getInstance().nextDouble();
                break;
            default:
                break;
        }
    }

    public ManagerOptions scanManagerOptions() {
        ManagerOptions[] managerOptions = ManagerOptions.values();
        int userInput = CostumScanner.getInstance().nextInt();
        userInput--;
        if (userInput >= 0 && userInput < managerOptions.length) {
            return managerOptions[userInput];
        }
        return ManagerOptions.UNDEFINED;
    }

    public void supporter(AuthenticationQueue authentication, AllUsers allUsers) {
        SupporterLogIn supporterLogIn = new SupporterLogIn(allSupporters);
        Supporter supporter = supporterLogIn.logIn();
        if (supporter == null) {
            return;
        } else if (!supporter.getAccess()) {
            System.out.println("\u001B[31mYou have been blocked by manager!\u001B[0m");
            return;
        }
        System.out.println("\u001B[35m\n\tWelcome!\u001B[0m");
        menu.printMenuForSupporter();
        SupporterOptions option = scanOption();
        while (option != SupporterOptions.EXIT) {
            if (option == SupporterOptions.UNDEFINED) {
                System.out.println("\u001B[31mUndefined Input!\u001B[0m");
            }
            handleSupporterOptions(option, supporter, authentication, allUsers);
            menu.printMenuForSupporter();
            option = scanOption();
        }
    }

    public void handleSupporterOptions(SupporterOptions option, Supporter supporter, AuthenticationQueue authentication,
            AllUsers allUsers) {
        switch (option) {
            case REQUESTS:
                supporter.handleRequests(allRequests, allUsers, authentication);
                break;
            case USERS:
                supporter.handleBankUsers(allUsers);
                break;
            case UNDEFINED:
                break;
            default:
                break;
        }
    }

    public SupporterOptions scanOption() {
        SupporterOptions[] options = SupporterOptions.values();
        int userInput = CostumScanner.getInstance().nextInt();
        userInput--;
        if (userInput >= 0 && userInput < options.length) {
            return options[userInput];
        }
        return SupporterOptions.UNDEFINED;
    }

//    public void user(AllUsers allUsers, AuthenticationQueue authentication, AllPeople allPeople) {
//        System.out.println("Have you registered?(Y/N)");
//        String input = CostumScanner.getInstance().nextLine();
//        switch (input) {
//            case "Y":
//                logInToAccount(allUsers, authentication);
//                break;
//            case "N":
//                Register register = new Register();
//                register.register(allUsers, authentication, allPoeple, allRequests);
//                break;
//            default:
//                break;
//        }
//    }

    public void logInToAccount(AllUsers allUsers, AuthenticationQueue authentication) {
        int isInAllUsers = 1;
        Register register = new Register();
        System.out.println("\u001B[33mEnter your phone number:\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        if (!register.phoneNumberIsAvailable(phoneNumber, allUsers)) {
            isInAllUsers = 0;
            if (!isInAuthenticationQueue(phoneNumber, authentication)) {
                System.out.println("\u001B[31mPhone number doesn't exist!\u001B[0m");
                return;
            }
        }
        System.out.println("\u001B[33mEnter your password:\u001B[0m");
        String password = CostumScanner.getInstance().nextLine();
        if (!passwordIsCorrect(password, phoneNumber, allUsers)) {
            System.out.println("\u001B[31mPassword is incorrect!\u001B[0m");
            return;
        }
        if (isInAllUsers == 1) {
            handleUser(allUsers.getUser().get(allUsers.getPhoneNumbers().indexOf(phoneNumber)), allUsers);
        } else {
            handleNonAuthenticatedUser(authentication, phoneNumber);
        }
    }

    public boolean isInAuthenticationQueue(String phoneNumber, AuthenticationQueue authentication) {
        for (User user : authentication.getIndividuals()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBankUser(String phoneNumber, AllUsers allUsers){
        return allUsers.getPhoneNumbers().contains(phoneNumber);
    }

    public boolean passwordIsCorrect(String password, String phoneNumber, AllUsers allUsers) {
        String userPassword = (allUsers.getUser().get(allUsers.getPhoneNumbers().indexOf(phoneNumber)))
                .getPassword();
        return userPassword.equals(password);
    }

    public void handleUser(User user, AllUsers allUsers) {
        if (!user.getAccess()) {
            System.out.println("\u001B[31mYou have been blocked by manager!\u001B[0m");
            return;
        }
        System.out.println("\u001B[34m\tWelcome!\u001B[0m");
        System.out.println("\u001B[34mYour password: \u001B[0m" + user.getCreditCard().getCardPassword());
        System.out.println("\u001B[34mYour card number: \u001B[0m" + user.getCreditCard().getCardNumber());
        menu.printMenuForUser();
        UserOptions userOption = scanUserOptions();
        while (userOption != UserOptions.EXIT) {
            if (userOption == UserOptions.UNDEFINED) {
                System.out.println("\u001B[31mUndefind input!\u001B[0m");
            }
            handleUserOptions(user, userOption, allUsers);
            menu.printMenuForUser();
            userOption = scanUserOptions();
        }
    }

    public void handleUserOptions(User user, UserOptions userOption, AllUsers allUsers) {
        UsersFunds usersFunds = new UsersFunds(user);
        BuySIMRecharge buySIMRecharge = new BuySIMRecharge();
        switch (userOption) {
            case ACCOUNT_MANAGEMENT -> accountManagement(user);
            case CANTACTS -> user.handleContacts(allUsers);
            case MONEY_TRANSFER -> user.handleMoneyTransfer(user, allUsers, otherBanksUsers);
            case SUPPORT -> support(user);
            case SETTINGS -> user.settings(user);
            case CAPITAL_FUNDS -> usersFunds.handleFunds();
            case BUY_SIM_RECHARGE -> buySIMRecharge.buyRecharge(allPoeple, user, allUsers);
            default -> {

            }
        }
    }

    public void support(User user) {
        AddUserRequests addUserRequests = new AddUserRequests();
        Request request = addUserRequests.request(user);
        if (request == null) {
            return;
        }
        allRequests.addRequest(request);
        System.out.println("\u001B[32mYour Request has been successfully registered!\u001B[0m");
    }

    public void accountManagement(User user) {
        menu.printAccountManagementServices();
        int choice = CostumScanner.getInstance().nextInt();
        while (choice != 5) {
            switch (choice) {
                case 1:
//                    user.chargeTheAccount();
                    break;
                case 2:
                    user.viewAccountCredit();
                    break;
                case 3:
                    user.transactions();
                    break;
                case 4:
                    System.out.println("\u001B[34mSIM Credit: \u001B[0m" + user.getSIMCard().getSIMCredit());
                    break;
                case 5:
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
            menu.printAccountManagementServices();
            choice = CostumScanner.getInstance().nextInt();
        }
    }

    public UserOptions scanUserOptions() {
        UserOptions[] options = UserOptions.values();
        int userInput = CostumScanner.getInstance().nextInt();
        userInput--;
        if (userInput >= 0 && userInput < options.length) {
            return options[userInput];
        }
        return UserOptions.UNDEFINED;
    }

    public void handleNonAuthenticatedUser(AuthenticationQueue authentication, String phoneNumber) {
        for (User user : authentication.getIndividuals()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                checkAuthenticationStatus(user);
                break;
            }
        }
    }

    public void checkAuthenticationStatus(User user) {
        switch (user.getAuthenticationStatus()) {
            case -1:
                user.getSupporterComment();
                edit(user);
                break;
            case 0:
                System.out.println("\u001B[34mYour authentication is being checked!\u001B[0m");
                break;
            default:
                break;
        }
    }

    public void edit(User user) {
        System.out.println(user.toString());
        Menu.printchangableOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                changeName(user);
                break;
            case 2:
                changeFamilyName(user);
                break;
            case 3:
                changeId(user);
                break;
            case 4:
                changePhoneNumber(user);
                break;
            default:
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                break;
        }
    }

    public void changeName(User user) {
        System.out.println("\u001B[34mEnter your new name:\u001B[0m");
        String newName = CostumScanner.getInstance().nextLine();
        user.setName(newName);
        System.out.println("\u001B[32mYour name has been successfully changed!\u001B[0m");
    }

    public void changeFamilyName(User user) {
        System.out.println("\u001B[34mEnter your new family name:\u001B[0m");
        String newFamilyName = CostumScanner.getInstance().nextLine();
        user.setFamilyName(newFamilyName);
        System.out.println("\u001B[32Your family name has been successfully changed!\u001B[0m");
    }

    public void changeId(User user) {
        System.out.println("\u001B[34mEnter your new ID:\u001B[0m");
        String newId = CostumScanner.getInstance().nextLine();
        user.setId(newId);
        System.out.println("\u001B[32mYour ID has been successfully changed!\u001B[0m");
    }

    public void changePhoneNumber(User user) {
        System.out.println("\u001B[34mEnter your new phone number:\u001B[0m");
        String newPhoneNumber = CostumScanner.getInstance().nextLine();
        user.setPhoneNumber(newPhoneNumber);
        System.out.println("\u001B[32mYour phone number has been successfully changed!\u001B[0m");
    }
}