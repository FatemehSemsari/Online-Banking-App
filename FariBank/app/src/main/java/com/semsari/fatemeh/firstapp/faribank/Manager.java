package com.semsari.fatemeh.firstapp.faribank;

public class Manager extends Person {
    private String passWord;
    private PrintLists printLists = new PrintLists();
    private Search search = new Search();
    private boolean haveAccess = true;

    public Manager(String name, String familyName, String phoneNumber, String idNumber, String passWord) {
        super(name, familyName, phoneNumber, idNumber);
        this.passWord = passWord;
    }

    public void setAccessStatus(boolean status) {
        haveAccess = status;
    }

    public boolean getAccess() {
        return haveAccess;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassword() {
        return passWord;
    }

    public void userManagement(AllUsers allUsers, AllSupporters allSupporters, AllManagers allManagers) {
        Menu.printUserManagementOptions();
        int choice = CostumScanner.getInstance().nextInt();
        while (choice != 6) {
            switch (choice) {
                case 1 -> usersList(allUsers, allManagers, allSupporters);
                case 2 -> addManager(allManagers);
                case 3 -> addSupporter(allSupporters);
                case 4 -> editUsers(allManagers, allSupporters, allUsers);
                case 5 -> determiningSpecificTaskForSupporter(allSupporters);
                case 6 -> {

                }
                default -> System.out.println("\u001B[31mInvalid Input!\u001B[0m");
            }
            Menu.printUserManagementOptions();
            choice = CostumScanner.getInstance().nextInt();
        }
    }

    public void determiningSpecificTaskForSupporter(AllSupporters allSupporters) {
        printLists.printSupporters(allSupporters.getSupporters());
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        choice--;
        determine(allSupporters.getSupporters().get(choice));
    }

    public void determine(Supporter supporter) {
        Menu.supporterTasks();
        int choice = CostumScanner.getInstance().nextInt();
        while (choice != 7) {
            handleChoice(choice, supporter);
            Menu.supporterTasks();
            choice = CostumScanner.getInstance().nextInt();
        }
    }

    public void handleChoice(int choice, Supporter supporter) {
        switch (choice) {
            case 1:
                supporter.addTask(RequestSubjects.AUTHENTICATION);
                break;
            case 2:
                supporter.addTask(RequestSubjects.REPORT);
                break;
            case 3:
                supporter.addTask(RequestSubjects.CAPITAL_FUNDS);
                break;
            case 4:
                supporter.addTask(RequestSubjects.CONTACTS);
                break;
            case 5:
                supporter.addTask(RequestSubjects.TRANSFER);
                break;
            case 6:
                supporter.addTask(RequestSubjects.SETTINGS);
                break;
            default:
                break;
        }
    }

    public void editUsers(AllManagers allManagers, AllSupporters allSupporters, AllUsers allUsers) {
        printLists.printManagers(allManagers.getManagers());
        printLists.printSupporters(allSupporters.getSupporters());
        printLists.printUsers(allUsers.getUser());
        Menu.printListOfRoles();
        int choice = CostumScanner.getInstance().nextInt();
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
        int input = CostumScanner.getInstance().nextInt();
        input--;
        switch (choice) {
            case 1:
                search.printUser(input, allUsers.getUser());
                editChosenUser(allUsers.getUser().get(input));
                break;
            case 2:
                search.printSupporter(input, allSupporters.getSupporters());
                editChosenSupporter(allSupporters.getSupporters().get(input));
                break;
            case 3:
                search.printManager(input, allManagers.getManagers());
                editChosenManager(allManagers.getManagers().get(input));
                break;
            default:
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
        }
    }

    public void editChosenSupporter(Supporter supporter) {
        Menu.printEditOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                System.out.print("\u001B[36mName:\u001B[0m");
                String name = CostumScanner.getInstance().nextLine();
                supporter.setName(name);
                System.out.println("\u001B[32mSupporter's name edited successfully!\u001B[0m");
                break;
            case 2:
                System.out.print("\u001B[36mFamily name:\u001B[0m");
                String familyName = CostumScanner.getInstance().nextLine();
                supporter.setFamilyName(familyName);
                System.out.println("\u001B[32mSupporter's family name edited successfully!\u001B[0m");
                break;
            case 3:
                handleSupporterAccess(supporter);
                break;
            case 4:
                break;
            default:
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
        }
    }

    public void editChosenManager(Manager manager) {
        Menu.printEditOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                System.out.print("\u001B[36mName:\u001B[0m");
                String name = CostumScanner.getInstance().nextLine();
                manager.setName(name);
                System.out.println("\u001B[32mManager's name edited successfully!\u001B[0m");
                break;
            case 2:
                System.out.print("\u001B[36mFamily name:\u001B[0m");
                String familyName = CostumScanner.getInstance().nextLine();
                manager.setFamilyName(familyName);
                System.out.println("\u001B[32mManager's family name edited successfully!\u001B[0m");
                break;
            case 3:
                handleManagerAccess(manager);
                break;
            default:
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
        }
    }

    public void editChosenUser(User user) {
        Menu.printEditOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                System.out.print("\u001B[36mName:\u001B[0m");
                String name = CostumScanner.getInstance().nextLine();
                user.setName(name);
                System.out.println("\u001B[32mUser's name edited successfully!\u001B[0m");
                break;
            case 2:
                System.out.print("\u001B[36mFamily name:\u001B[0m");
                String familyName = CostumScanner.getInstance().nextLine();
                user.setFamilyName(familyName);
                System.out.println("\u001B[32mUser's family name edited successfully!\u001B[0m");
                break;
            case 3:
                handleUserAccess(user);
                break;
            case 4:
                break;
            default:
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
        }
    }

    public void handleUserAccess(User user) {
        Menu.printUserAccessOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                user.setAccessStatus(true);
                break;
            case 2:
                user.setAccessStatus(false);
                break;
            default:
                break;
        }
    }

    public void handleSupporterAccess(Supporter supporter) {
        Menu.printUserAccessOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                supporter.setAccessStatus(true);
                break;
            case 2:
                supporter.setAccessStatus(false);
                break;
            default:
                break;
        }
    }

    public void handleManagerAccess(Manager manager) {
        Menu.printUserAccessOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                manager.setAccessStatus(true);
                break;
            case 2:
                manager.setAccessStatus(false);
                break;
            default:
                break;
        }
    }

    public void usersList(AllUsers allUsers, AllManagers allManagers, AllSupporters allSupporters) {
        System.out.println("\n1.View list of all users");
        System.out.println("2.Search user");
        System.out.println("3.Exit");
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                printLists.printAllBankUsers(allUsers, allManagers, allSupporters);
                break;
            case 2:
                search.searchUser(allUsers, allSupporters, allManagers);
                break;
            case 3:
                break;
            default:
                System.out.println("\u001B[31mInvalid Input!\u001B[0m");
                break;
        }
    }

    @Override
    public String toString() {
        return "\u001B[36mName:\u001B[0m" + getName()
                + " " + "\u001B[36mFamily name:\u001B[0m" + getFamilyName() + " " + "\u001B[36mPhone number:\u001B[0m"
                + getPhoneNumber();
    }

    public void addManager(AllManagers allManagers) {
        System.out.println("\u001B[36mName:\u001B[0m");
        String name = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mFamily Name:\u001B[0m");
        String familyName = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mPhone Number:\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mId:\u001B[0m");
        String idNumber = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mPassword:\u001B[0m");
        String password = CostumScanner.getInstance().nextLine();
        Manager manager = new Manager(name, familyName, phoneNumber, idNumber, password);
        allManagers.addManager(manager);
        System.out.println("\u001B[32mManager added successfully!\u001B[0m");
    }

    public void addSupporter(AllSupporters allSupporters) {
        System.out.println("\u001B[36mName:\u001B[0m");
        String name = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mFamily Name:\u001B[0m");
        String familyName = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mPhone Number:\u001B[0m");
        String phoneNumber = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mId:\u001B[0m");
        String idNumber = CostumScanner.getInstance().nextLine();
        System.out.println("\u001B[36mPassword:\u001B[0m");
        String password = CostumScanner.getInstance().nextLine();
        Supporter supporter = new Supporter(name, familyName, phoneNumber, idNumber, password);
        allSupporters.addSupporter(supporter);
        System.out.println("\u001B[36mChoose supporter's tasks:\u001B[0m");
        determine(supporter);
        System.out.println("\u001B[32mSupporter added successfully!\u001B[0m");
    }

}
