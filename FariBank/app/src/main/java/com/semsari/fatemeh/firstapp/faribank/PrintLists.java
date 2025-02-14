package com.semsari.fatemeh.firstapp.faribank;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PrintLists {
    public void printUserFunds(List<Fund> funds) {
        AtomicInteger counter = new AtomicInteger(1);
        funds.stream()
                .forEach(fund -> System.out.println(counter.getAndIncrement() + "." + fund.getFundType().toString()));
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public void printAllBankUsers(AllUsers allUsers, AllManagers allManagers, AllSupporters allSupporters) {
        Menu.printUsersPrintingOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                printUsers(allUsers.getUser());
                break;
            case 2:
                printSupporters(allSupporters.getSupporters());
                break;
            case 3:
                printManagers(allManagers.getManagers());
                break;
            default:
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
        }
    }

    public void printManagers(List<Manager> managers) {
        if (managers.isEmpty()) {
            System.out.println("\u001B[31mNo manager!\u001B[0m");
            return;
        }
        System.out.println("\u001B[36m\n<Managers>\u001B[0m");
        int startIndex = 0, itemsOfPage = 3;
        String userInput;
        while (true) {
            printManagerPage(managers, startIndex, itemsOfPage);
            printMovingOptions(startIndex, managers.size(), itemsOfPage);
            userInput = CostumScanner.getInstance().nextLine();
            if (">".equals(userInput)) {
                if (startIndex + itemsOfPage < managers.size()) {
                    startIndex += itemsOfPage;
                }
            } else if ("<".equals(userInput)) {
                if (startIndex - itemsOfPage >= 0) {
                    startIndex -= itemsOfPage;
                }
            } else if ("<-".equals(userInput)) {
                break;
            } else {
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
            }
        }
    }

    public void printManagerPage(List<Manager> managers, int startIndex, int itemsOfPage) {
        for (int i = startIndex; i < startIndex + itemsOfPage && i < managers.size(); i++) {
            System.out.println(managers.get(i).getName() + " " + managers.get(i).getFamilyName() + " "
                    + managers.get(i).getPhoneNumber());
        }
    }

    public void printSupporters(List<Supporter> supporters) {
        if (supporters.isEmpty()) {
            System.out.println("\u001B[31mNo supporter!\u001B[0m");
            return;
        }
        System.out.println("\u001B[36m\n<Supporters>\u001B[0m");
        int startIndex = 0, itemsOfPage = 3;
        String userInput;
        while (true) {
            printSupporterPage(supporters, startIndex, itemsOfPage);
            printMovingOptions(startIndex, supporters.size(), itemsOfPage);
            userInput = CostumScanner.getInstance().nextLine();
            if (">".equals(userInput)) {
                if (startIndex + itemsOfPage < supporters.size()) {
                    startIndex += itemsOfPage;
                }
            } else if ("<".equals(userInput)) {
                if (startIndex - itemsOfPage >= 0) {
                    startIndex -= itemsOfPage;
                }
            } else if ("<-".equals(userInput)) {
                break;
            } else {
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
            }
        }
    }

    public void printSupporterPage(List<Supporter> supportes, int startIndex, int itemsOfPage) {
        for (int i = startIndex; i < startIndex + itemsOfPage && i < supportes.size(); i++) {
            System.out.println(supportes.get(i).getName() + " " + supportes.get(i).getFamilyName() + " "
                    + supportes.get(i).getPhoneNumber());
        }
    }

    public void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("\u001B[31mNo user!\u001B[0m");
            return;
        }
        System.out.println("\u001B[36m\n<Users>\u001B[0m");
        int startIndex = 0, itemsOfPage = 3;
        String userInput;
        while (true) {
            printUserPage(users, startIndex, itemsOfPage);
            printMovingOptions(startIndex, users.size(), itemsOfPage);
            userInput = CostumScanner.getInstance().nextLine();
            if (">".equals(userInput)) {
                if (startIndex + itemsOfPage < users.size()) {
                    startIndex += itemsOfPage;
                }
            } else if ("<".equals(userInput)) {
                if (startIndex - itemsOfPage >= 0) {
                    startIndex -= itemsOfPage;
                }
            } else if ("<-".equals(userInput)) {
                break;
            } else {
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
            }
        }
    }

    public void printContacts(Map<String[], User> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("\u001B[31mNo contact!\u001B[0m");
            return;
        }
        System.out.println("\u001B[36m\n<Contacts>\u001B[0m");
        int startIndex = 0, itemsOfPage = 3;
        String userInput;
        while (true) {
            printcontactPage(contacts, startIndex, itemsOfPage);
            printMovingOptions(startIndex, contacts.size(), itemsOfPage);
            userInput = CostumScanner.getInstance().nextLine();
            if (">".equals(userInput)) {
                if (startIndex + itemsOfPage < contacts.size()) {
                    startIndex += itemsOfPage;
                }
            } else if ("<".equals(userInput)) {
                if (startIndex - itemsOfPage >= 0) {
                    startIndex -= itemsOfPage;
                }
            } else if ("<-".equals(userInput)) {
                break;
            } else {
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
            }
        }
    }

    public void printcontactPage(Map<String[], User> contacts, int startIndex, int itemsOfPage) {
        for (Map.Entry<String[], User> contact : contacts.entrySet()) {
            String[] key = contact.getKey();
            System.out.println(
                    key[0] + " " + key[1] + " " + contact.getValue().getPhoneNumber());
        }
    }

    public void printMovingOptions(int startIndex, int size, int itemsOfPage) {
        if (startIndex > 0 && startIndex + itemsOfPage < size) {
            System.out.println("\u001B[36m\t<previous    next>\u001B[0m");
            System.out.println("\u001B[36m\t<-Back\u001B[0m");
        } else if (startIndex == 0 && size > 3) {
            System.out.println("\u001B[36m\tnext>\u001B[0m");
            System.out.println("\u001B[36m\t<-Back\u001B[0m");
        } else if (startIndex == 0 && size <= 3) {
            System.out.println("\u001B[36m\t<-Back\u001B[0m");
        } else {
            System.out.println("\u001B[36m\t<previous\u001B[0m");
            System.out.println("\u001B[36m\t<-Back\u001B[0m");
        }
    }

    public void printUserPage(List<User> users, int startIndex, int itemsOfPage) {
        for (int i = startIndex; i < startIndex + itemsOfPage && i < users.size(); i++) {
            System.out.println(
                    users.get(i).getName() + " " + users.get(i).getFamilyName() + " " + users.get(i).getPhoneNumber());
        }
    }

    public void printRequests(List<Request> requests) {
        System.out.println("\u001B[36m\n<Requests>\u001B[0m");
        if (requests.isEmpty()) {
            System.out.println("\u001B[31mNo requests!\u001B[0m");
            return;
        }
        int startIndex = 0, itemsOfPage = 3;
        String userInput;
        while (true) {
            printRequestsPage(requests, startIndex, itemsOfPage);
            printMovingOptions(startIndex, requests.size(), itemsOfPage);
            userInput = CostumScanner.getInstance().nextLine();
            if (">".equals(userInput)) {
                if (startIndex + itemsOfPage < requests.size()) {
                    startIndex += itemsOfPage;
                }
            } else if ("<".equals(userInput)) {
                if (startIndex - itemsOfPage >= 0) {
                    startIndex -= itemsOfPage;
                }
            } else if ("<-".equals(userInput)) {
                break;
            } else {
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
            }
        }
    }

    public void printRequestsPage(List<Request> requests, int startIndex, int itemsOfPage) {
        for (int i = startIndex; i < startIndex + itemsOfPage && i < requests.size(); i++) {
            System.out.println(requests.get(i).getSubject().toString());
        }
    }
}
