package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Search {
    private PrintLists printLists = new PrintLists();

    public void searchUser(AllUsers allUsers, AllSupporters allSupporters, AllManagers allManagers) {
        List<String> info = new ArrayList<>();
        info.add("0");
        info.add("0");
        info.add("0");
        info.add("0");
        printInfo(info);
        getInfo(info);
        filtereUsers(info, allUsers, allManagers, allSupporters);
    }

    public void getInfo(List<String> info) {
        int input = CostumScanner.getInstance().nextInt();
        while (input != 5) {
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
            case 4:
                System.out.println("\u001B[36mRole:\u001B[0m");
                info.set(3, CostumScanner.getInstance().nextLine());
                break;
            default:
                break;
        }
    }

    public void printUser(int choice, List<User> filteredUsers) {
        System.out.println(filteredUsers.get(choice).toString() + "\n" + "\u001B[34mAccount number:\u001B[0m" +
                filteredUsers.get(choice).getAccountNumber() + "\n" + "\u001B[34mNumber of transactions:\u001B[0m" +
                filteredUsers.get(choice).getTransactions().size());
    }

    public void filtereUsers(List<String> info, AllUsers allUsers, AllManagers allManagers,
            AllSupporters allSupporters) {
        if ("0".equals(info.get(3))) {
            searchAllUsers(info, allUsers, allSupporters, allManagers);
        }
        int choice = 0;
        switch (info.get(3).toLowerCase()) {
            case "manager":
                List<Manager> filteredManagers = searchManagers(allManagers, info);
                printLists.printManagers(filteredManagers);
                choice = CostumScanner.getInstance().nextInt();
                printManager(choice, filteredManagers);
                break;
            case "supporter":
                List<Supporter> filteredSuppurter = searchSupporters(allSupporters, info);
                printLists.printSupporters(filteredSuppurter);
                choice = CostumScanner.getInstance().nextInt();
                printSupporter(choice, filteredSuppurter);
                break;
            case "user":
                List<User> filteredUsers = searchUsers(allUsers, info);
                printLists.printUsers(filteredUsers);
                choice = CostumScanner.getInstance().nextInt();
                printUser(choice, filteredUsers);
                break;
            default:
        }
    }

    public void searchAllUsers(List<String> info, AllUsers allUsers, AllSupporters allSupporters,
            AllManagers allManagers) {
        System.out.println("\u001B[36m\tManagers:\u001B[0m");
        List<Manager> filteredManagers = searchManagers(allManagers, info);
        printLists.printManagers(filteredManagers);
        System.out.println("\u001B[36m\tSupporters:\u001B[0m");
        List<Supporter> filteredSuppurter = searchSupporters(allSupporters, info);
        printLists.printSupporters(filteredSuppurter);
        System.out.println("\u001B[36m\tUsers:\u001B[0m");
        List<User> filteredUsers = searchUsers(allUsers, info);
        printLists.printUsers(filteredUsers);
        Menu.printListOfRoles();
        int choice = CostumScanner.getInstance().nextInt();
        hanleChosenRole(choice, filteredManagers, filteredSuppurter, filteredUsers);
    }

    public void hanleChosenRole(int choice, List<Manager> managers, List<Supporter> supporters, List<User> users) {
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
        int input = CostumScanner.getInstance().nextInt();
        input--;
        switch (choice) {
            case 1:
                printUser(input, users);
                break;
            case 2:
                printSupporter(input, supporters);
                break;
            case 3:
                printManager(input, managers);
                break;
            case 4:
                break;
            default:
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
        }
    }

    public void printSupporter(int choice, List<Supporter> filteredList) {
        System.out.println(filteredList.get(choice).toString());
    }

    public List<Supporter> searchSupporters(AllSupporters allSupporters, List<String> info) {
        String name = info.get(0), familyName = info.get(1), phoneNumber = info.get(2);
        return allSupporters.getSupporters().stream()
                .filter(s -> "0".equals(name) || s.getName().equals(name))
                .filter(s -> "0".equals(familyName) || s.getFamilyName().equals(familyName))
                .filter(s -> "0".equals(phoneNumber) || s.getPhoneNumber().equals(phoneNumber))
                .collect(Collectors.toList());
    }

    public List<Manager> searchManagers(AllManagers allManagers, List<String> info) {
        String name = info.get(0), familyName = info.get(1), phoneNumber = info.get(2);
        return allManagers.getManagers().stream()
                .filter(m -> "0".equals(name) || m.getName().equals(name))
                .filter(m -> "0".equals(familyName) || m.getFamilyName().equals(familyName))
                .filter(m -> "0".equals(phoneNumber) || m.getPhoneNumber().equals(phoneNumber))
                .collect(Collectors.toList());
    }

    public void printManager(int choice, List<Manager> filteredManagers) {
        System.out.println(filteredManagers.get(choice).toString());
    }

    public List<User> searchUsers(AllUsers allUsers, List<String> info) {
        String name = info.get(0), familyName = info.get(1), phoneNumber = info.get(2);
        return allUsers.getUser().stream()
                .filter(u -> "0".equals(name) || u.getName().equals(name))
                .filter(u -> "0".equals(familyName) || u.getFamilyName().equals(familyName))
                .filter(u -> "0".equals(phoneNumber) || u.getPhoneNumber().equals(phoneNumber))
                .collect(Collectors.toList());
    }

    public void printFilteredUsers(List<User> filteredUsers) {
        int counter = 1;
        for (User user : filteredUsers) {
            System.out.println(counter + "." + "\u001B[36mName:\u001B[0m" + user.getName()
                    + " " + "\u001B[36mFamily name:\u001B[0m" + user.getFamilyName() + " "
                    + "\u001B[36mPhone number:\u001B[0m" + user.getPhoneNumber());
            counter++;
        }
    }

    public void printInfo(List<String> info) {
        if ("0".equals(info.get(0))) {
            System.out.println("\u001B[36mName:\u001B[0m" + " (Enter 1 to set name)");
        } else {
            System.out.println("\u001B[36mName:\u001B[0m" + info.get(0));
        }
        if ("0".equals(info.get(1))) {
            System.out.println("\u001B[36mFamily name:\u001B[0m" + " (Enter 2 to set family name)");
        } else {
            System.out.println("\u001B[36mFamily name:\u001B[0m" + info.get(1));
        }
        if ("0".equals(info.get(2))) {
            System.out.println("\u001B[36mPhone number:\u001B[0m" + " (Enter 3 to set phone number)");
        } else {
            System.out.println("\u001B[36mPhone number:\u001B[0m" + info.get(2));
        }
        if ("0".equals(info.get(3))) {
            System.out.println("\u001B[36mRole:\u001B[0m" + " (Enter 4 to set role)");
        } else {
            System.out.println("\u001B[36mRole:\u001B[0m" + info.get(3));
        }
        System.out.println("\u001B[36mEnter 5 to search user with entered information\u001B[0m");
    }
}
