package com.semsari.fatemeh.firstapp.faribank;


public class Main {
    private static Manager manager = new Manager("Manager", "Manager", "111", "1234", "MainManager");
    private static AllPeople allPoeple = new AllPeople();
    private static AllUsers allUsers = new AllUsers();
    private static AuthenticationQueue authentication = new AuthenticationQueue();

    public static void main(String[] argv) {
        Entry entry = new Entry(allPoeple);
        add(allUsers);
        int choice = 0;
        do {
            Menu.mainMenu();
            choice = CostumScanner.getInstance().nextInt();
            switch (choice) {
                case 1:
//                    entry.user(allUsers, authentication, allPoeple);
                    break;
                case 2:
                    entry.supporter(authentication, allUsers);
                    break;
                case 3:
                    entry.manager(allUsers);
                    break;
                default:
                    break;
            }
        } while (choice != 4);
    }

    public static void add(AllUsers allUsers) {
        User user1 = new User("zahra", "semsari", "14785", "15987", "zaHra!1383");
        CreditCard creditCard = new CreditCard("58946311222333", "1234");
        user1.setCreditCard(creditCard);
        User user2 = new User("sara", "alavi", "36985", "25558", "Sara@1356");
        CreditCard creditCard2 = new CreditCard("58946311333444", "5678");
        user2.setCreditCard(creditCard2);
        User user3 = new User("Ali", "Alavi", "15987", "6555888", "Ali$1349");
        CreditCard creditCard3 = new CreditCard("58946311444555", "9101");
        allUsers.addUser(user1);
        allUsers.addUser(user2);
        allUsers.addUser(user3);
        allUsers.addId(user1.getId());
        allUsers.addId(user2.getId());
        allUsers.addId(user3.getId());
        allUsers.addPhoneNumber(user1.getPhoneNumber());
        allUsers.addPhoneNumber(user2.getPhoneNumber());
        allUsers.addPhoneNumber(user3.getPhoneNumber());
    }

    public static AllUsers getAllUsers(){
        return allUsers;
    }

    public static AuthenticationQueue getQueue(){
        return authentication;
    }

    public static AllPeople getAllPoeple(){
        return allPoeple;
    }

}
