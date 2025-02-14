package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Supporter extends Person {
    private String password;
    private List<RequestSubjects> tasks = new ArrayList<>();
    private boolean haveAccess = true;
    private PrintLists printLists = new PrintLists();

    public Supporter(String name, String familyName, String phoneNumber, String idNumber, String password) {
        super(name, familyName, phoneNumber, idNumber);
        this.password = password;
    }

    public void setAccessStatus(boolean status) {
        haveAccess = status;
    }

    public boolean getAccess() {
        return haveAccess;
    }

    public void addTask(RequestSubjects requestSubjects) {
        tasks.add(requestSubjects);
    }

    public List<RequestSubjects> getTasks() {
        return tasks;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void authentication(AuthenticationQueue authentication, AllUsers allUsers, User user, Request request) {
        if (!authentication.getIndividuals().contains(user)) {
            System.out.println("\u001B[31mThis person is not in authentication queue!\u001B[0m");
            return;
        }
        printIndividualInfo(user);
        System.out.println("\u001B[35mDo you confirm the authentication of this person?(Y/N)\u001B[0m");
        String input = CostumScanner.getInstance().nextLine();
        switch (input) {
            case "Y":
                confirmation(user, allUsers, authentication);
                break;
            case "N":
                rejection(user);
                break;
            default:
                break;
        }
        System.out.println("\u001B[35mEnter status(In progress/Terminated):\u001B[0m");
        String status = CostumScanner.getInstance().nextLine();
        request.setStatus(status);
    }

    public void confirmation(User user, AllUsers allUsers, AuthenticationQueue authentication) {
        allUsers.addPhoneNumber(user.getPhoneNumber());
        allUsers.addId(user.getId());
        user.setAuthenticationStatus(1);
        giveCardNumberAndCardPassword(user);
        giveAccountNumber(user);
        allUsers.addUser(user);
        authentication.getIndividuals().remove(user);
        System.out.println("\u001B[32mAuthentication has been successfully completed!\u001B[0m");
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

    public void rejection(User user) {
        user.setAuthenticationStatus(-1);
        System.out.println("\u001B[35mWrite the comment for authentication rejection:\u001B[0m");
        String comment = CostumScanner.getInstance().nextLine();
        user.setsupporterComment(comment);
        System.out.println("\u001B[32mAuthentication rejected successfully!\u001B[0m");
    }

    public void printIndividualInfo(User user) {
        System.out.println("\u001B[35m\nIndividual's info\u001B[0m");
        System.out.println(user.toString());
    }

    public void printIndividuals(List<User> individuals) {
        int counter = 1;
        for (User individual : individuals) {
            System.out.println(counter + "." + individual.getName() + " " + individual.getFamilyName());
            counter++;
        }
        System.out.println("\u001B[35mChoose one individual:\u001B[0m");
    }

    public void handleRequests(AllRequests allRequests, AllUsers allUsers, AuthenticationQueue authentication) {
        if (allRequests.getRequests().isEmpty()) {
            System.out.println("\u001B[35mThere is no requests!\u001B[0m");
            return;
        }
        System.out.println("1.View all requests");
        System.out.println("2.Search request");
        int input = CostumScanner.getInstance().nextInt();
        switch (input) {
            case 1:
                System.out.println("\u001B[35mEnter your choice\u001B[0m");
                List<Request> filtered = printRequests(allRequests.getRequests());
                if (filtered == null) {
                    break;
                }
                System.out.println("\u001B[35mChoose a request:\u001B[0m");
                int choice = CostumScanner.getInstance().nextInt();
                choice--;
                printRequest(choice, filtered);
                answerRequest(filtered.get(choice), allUsers, authentication);
                break;
            case 2:
                searchRequest(allRequests.getRequests(), allUsers, authentication);
                break;
            default:
                break;
        }
    }

    public void answerRequest(Request request, AllUsers allUsers, AuthenticationQueue authentication) {
        if (RequestSubjects.AUTHENTICATION.equals(request.getSubject())) {
            authentication(authentication, allUsers, request.getUser(), request);
            return;
        }
        System.out.println("\u001B[35mEnter your answer:\u001B[0m");
        String answer = CostumScanner.getInstance().nextLine();
        request.setAnswer(answer);
        System.out.println("\u001B[35mEnter status(In progress/Terminated):\u001B[0m");
        String status = CostumScanner.getInstance().nextLine();
        request.setStatus(status);
    }

    public void searchRequest(List<Request> requests, AllUsers allUsers, AuthenticationQueue authentication) {
        System.out.println("\u001B[35mSearch by:\u001B[0m");
        System.out.println("1.User's phone nember");
        System.out.println("2.Request status");
        System.out.println("3.Subject");
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                System.out.println("\u001B[35mEnter user phone number:\u001B[0m");
                String phoneNumber = CostumScanner.getInstance().nextLine();
                searchByUser(phoneNumber, requests, allUsers, authentication);
                break;
            case 2:
                System.out.println("\u001B[35mEnter status(Registered/In progress/Terminated)\u001B[0m");
                String status = CostumScanner.getInstance().nextLine();
                searchByStatus(status, requests, allUsers, authentication);
                break;
            case 3:
                System.out.println("\u001B[35mEnter subject:\u001B[0m");
                String subject = CostumScanner.getInstance().nextLine();
                searchBySubject(subject, requests, allUsers, authentication);
                break;
            default:
                break;
        }
    }

    public void searchBySubject(String subject, List<Request> requests, AllUsers allUsers,
            AuthenticationQueue authentication) {
        RequestSubjects requestSubject = convert(subject);
        int number = 0;
        boolean find = false;
        for (Request request : requests) {
            if (request.getSubject().equals(requestSubject) && tasks.contains(requestSubject)) {
                printRequest(number, requests);
                find = true;
            }
            number++;
        }
        if (!find) {
            System.out.println("\u001B[31mNo request!\u001B[0m");
            return;
        }
        System.out.println("\u001B[35mchoose a request by entering user's phone number\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        searchByUser(phoneNumber, requests, allUsers, authentication);
    }

    public RequestSubjects convert(String subject) {
        boolean isCorrect = false;
        while (!isCorrect) {
            try {
                RequestSubjects requestSubjects = RequestSubjects.valueOf(subject.toUpperCase());
                isCorrect = true;
                return requestSubjects;
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31mThis request subject is not available!\u001B[0m");
                System.out.println("\u001B[35mEnter valid input:\u001B[0m");
                subject = CostumScanner.getInstance().nextLine();
                isCorrect = false;
            }
        }
        return null;
    }

    public void searchByStatus(String status, List<Request> requests, AllUsers allUsers,
            AuthenticationQueue authentication) {
        int number = 0;
        boolean find = false;
        for (Request request : requests) {
            if (request.getStatus().equals(status) && tasks.contains(request.getSubject())) {
                printRequest(number, requests);
                find = true;
            }
            number++;
        }
        if (!find) {
            System.out.println("\u001B[31mNo request!\u001B[0m");
            return;
        }
        System.out.println("\u001B[35mchoose a request by entering user's phone number\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        searchByUser(phoneNumber, requests, allUsers, authentication);
    }

    public void searchByUser(String phoneNumber, List<Request> requests, AllUsers allUsers,
            AuthenticationQueue authentication) {
        int number = 0;
        boolean find = false;
        for (Request request : requests) {
            if (request.getUser().getPhoneNumber().equals(phoneNumber) && tasks.contains(request.getSubject())) {
                printRequest(number, requests);
                answerRequest(request, allUsers, authentication);
                find = true;
                break;
            }
            number++;
        }
        if (!find) {
            System.out.println("\u001B[31mNo requests!\u001B[0m");
        }
    }

    public List<Request> printRequests(List<Request> requests) {
        List<Request> filteredRequests = new ArrayList<>();
        for (RequestSubjects task : tasks) {
            filteredRequests = requests.stream().filter(request -> request.getSubject().equals(task))
                    .collect(Collectors.toList());
        }
        printLists.printRequests(filteredRequests);
        if (filteredRequests.isEmpty()) {
            return null;
        }
        return filteredRequests;
    }

    public void printRequest(int choice, List<Request> requests) {
        System.out.println(requests.get(choice).toString());
    }

    public void handleBankUsers(AllUsers allUsers) {
        if (allUsers.getUser().isEmpty()) {
            System.out.println("\u001B[35mThere is no users in the bank!\u001B[0m");
            return;
        }
        System.out.println("1.View all users");
        System.out.println("2.Search user");
        int input = CostumScanner.getInstance().nextInt();
        switch (input) {
            case 1:
                printLists.printUsers(allUsers.getUser());
                System.out.println("\u001B[35mEnter your choice:\u001B[0m");
                int index = CostumScanner.getInstance().nextInt();
                index--;
                User user = allUsers.getUser().get(index);
                System.out.println(
                        user.toString() + "\u001B[34mAccount number:\u001B[0m" + user.getAccountNumber() + "\n" +
                                "\u001B[34mNumber of transactions:\u001B[0m" + user.getTransactions().size());
                break;
            case 2:
                searchUser(allUsers);
                break;
            default:
                break;
        }
    }

    public void searchUser(AllUsers allUsers) {
        List<String> info = new ArrayList<>();
        info.add("0");
        info.add("0");
        info.add("0");
        printInfo(info);
        getInfo(info);
        Search search = new Search();
        List<User> filteredUsers = search.searchUsers(allUsers, info);
        printLists.printUsers(filteredUsers);
        System.out.println("\u001B[35mEnter your choice:\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        choice--;
        search.printUser(choice, filteredUsers);
    }

    public void getInfo(List<String> info) {
        int input = CostumScanner.getInstance().nextInt();
        while (input != 4) {
            setInput(input, info);
            printInfo(info);
            input = CostumScanner.getInstance().nextInt();
        }
        if (info.get(0) == null && info.get(1) == null && info.get(2) == null && info.get(3) == null) {
            System.out.println("\u001B[31mYou haven't entered any information!\u001B[0m");
            return;
        }
    }

    public void setInput(int input, List<String> info) {
        switch (input) {
            case 1:
                System.out.println("\u001B[36mName:\u001B[0m");
                info.set(0, CostumScanner.getInstance().nextLine());
                break;
            case 2:
                System.out.println("\u001B[36mFamily name:\u001B[0m");
                info.set(1, CostumScanner.getInstance().nextLine());
                break;
            case 3:
                System.out.println("\u001B[36mPhone number:\u001B[0m");
                info.set(2, CostumScanner.getInstance().nextLine());
                break;
            default:
                break;
        }
    }

    public void printInfo(List<String> info) {
        String name = info.get(0), familyName = info.get(1), phoneNumber = info.get(2);
        if ("0".equals(name)) {
            System.out.println("\u001B[35mName:\u001B[0m" + " (Enter 1 to set name)");
        } else {
            System.out.println("\u001B[35mName:\u001B[0m" + name);
        }
        if ("0".equals(familyName)) {
            System.out.println("\u001B[35mFamily name:\u001B[0m" + " (Enter 2 to set family name)");
        } else {
            System.out.println("\u001B[35mFamily name:\u001B[0m" + familyName);
        }
        if ("0".equals(phoneNumber)) {
            System.out.println("\u001B[35mPhone number:\u001B[0m" + " (Enter 3 to set phone number)");
        } else {
            System.out.println("\u001B[35mPhone number:\u001B[0m" + phoneNumber);
        }
        System.out.println("\u001B[35mEnter 4 to search user with entered information\u001B[0m");
    }

    @Override
    public String toString() {
        return "\u001B[35mName:\u001B[0m" + getName()
                + " " + "\u001B[35mFamily name:\u001B[0m" + getFamilyName() + " " + "\u001B[35mPhone number:\u001B[0m"
                + getPhoneNumber();
    }
}
