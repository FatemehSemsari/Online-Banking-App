package com.semsari.fatemeh.firstapp.faribank;

import java.util.List;

public class AddUserRequests {
    private PrintLists printLists = new PrintLists();

    public Request request(User user) {
        Menu menu = new Menu();
        menu.printUserRequestOptions();
        int choice = CostumScanner.getInstance().nextInt();
        String input = "";
        if (choice != 5) {
            System.out.println("\u001B[34mEnter request\u001B[0m");
            input = CostumScanner.getInstance().nextLine();
        }
        return makeRequest(choice, user, input);
    }

    public Request makeRequest(int choice, User user, String input){
        Request request = null;
        switch (choice) {
            case 1 -> request = new Request(RequestSubjects.REPORT, input, "Registered", null, user);
            case 2 -> request = new Request(RequestSubjects.CONTACTS, input, "Registered", null, user);
            case 3 -> request = new Request(RequestSubjects.TRANSFER, input, "Registered", null, user);
            case 4 -> request = new Request(RequestSubjects.SETTINGS, input, "Registered", null, user);
            case 5 -> request = new Request(RequestSubjects.CAPITAL_FUNDS, input, "Registered", null, user);
            case 6 -> request = new Request(RequestSubjects.SIMRECHARGE, input, "Registered", null, user);
            case 7 -> viewAllRequests(user.getRequests());
            default -> System.out.println("\u001B[31mInvalid input\u001B[0m");
        }
        if(request == null){
            return null;
        }
        user.addRequest(request);
        return request;
    }

    public void viewAllRequests(List<Request> requests) {
        if (requests.isEmpty()) {
            System.out.println("\u001B[34mThere is no requests!\u001B[0m");
            return;
        }
        printLists.printRequests(requests);
        System.out.println("\u001B[34mEnter request\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        choice--;
        System.out.println(requests.get(choice).toString());
    }
}
