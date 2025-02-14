package com.semsari.fatemeh.firstapp.faribank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.semsari.fatemeh.firstapp.faribank.util.Calendar;

public class UserMoneyTransfer {
    private AllUsers allUsers;
    private RecentTransfer recentTransfers;
    private User user;
    private List<Person> otherBanksUsers;

    public UserMoneyTransfer(AllUsers allUsers, RecentTransfer recentTransfers, User user,
            List<Person> otherBanksUsers) {
        this.allUsers = allUsers;
        this.recentTransfers = recentTransfers;
        this.user = user;
        this.otherBanksUsers = otherBanksUsers;
    }

    public String chooseRecentTransfer() {
        if (recentTransfers.getCardNumbers().isEmpty()) {
            System.out.println("\u001B[34mThere is no recent requests!\u001B[0m");
            return null;
        }
        int counter = 1;
        for (String cardNumber : recentTransfers.getCardNumbers()) {
            System.out.println(counter + "." + cardNumber);
            counter++;
        }
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        choice--;
        return recentTransfers.getCardNumbers().get(choice);
    }

    public void transferMoneyByCardNumber(AllUsers allUsers, String cardNumber, boolean isContact) {
        boolean isCorrect = checkUserInfo();
        if (!isCorrect) {
            return;
        }
        boolean find = false;
        for (User payee : allUsers.getUser()) {
            if (payee.getCreditCard().getCardNumber().equals(cardNumber)) {
                System.out.println("\u001B[34mEnter amount you want to transfer:\u001B[0m");
                double amount = CostumScanner.getInstance().nextDouble();
                if (user.getCreditCard().getCredit() < amount) {
                    System.out.println("\u001B[31mYour account balance is insufficient\u001B[0m");
                    return;
                }
                transfer(amount, payee, isContact);
                find = true;
                break;
            }
        }
        if (!find) {
            System.out.print("\u001B[31mNo such user in the bank!\u001B[0m");
            return;
        }
    }

    public boolean checkUserInfo() {
        System.out.println("\u001B[34mEnter your card number:\u001B[0m");
        String input = CostumScanner.getInstance().nextLine();
        if (!input.equals(user.getCreditCard().getCardNumber())) {
            System.out.println("\u001B[31mIncorrect card number!\u001B[0m");
            return false;
        }
        System.out.println("\u001B[34mEnter your card password:\u001B[0m");
        input = CostumScanner.getInstance().nextLine();
        if (!input.equals(user.getCreditCard().getCardPassword())) {
            System.out.println("\u001B[31mIncorrect password!\u001B[0m");
            return false;
        }
        return true;
    }

    public void transfer(double amount, Person payee, boolean isContact) {
        user.handleFractionalCurrency(amount);
        payee.getCreditCard().setCredit(payee.getCreditCard().getCredit() + amount);
        Transaction transfer = new Transfer(user.getCreditCard().getCardNumber(), payee.getCreditCard().getCardNumber(),
                new Calendar().now(), "transfer", Integer.toString(user.generateIssueTracking()));
        transfer.setAmount(amount);
        user.addTransaction(transfer);
        if (payee instanceof User) {
            ((User) payee).addTransaction(transfer);
            if (!user.getRecentTransfers().getCardNumbers().contains(payee.getCreditCard().getCardNumber())) {
                user.getRecentTransfers().addCardNumber(payee.getCreditCard().getCardNumber());
            }
        }
        System.out.println("\u001B[34m\t<<Receipt>>\u001B[0m");
        if (isContact) {
            String[] fullName = findContactName(user);
            System.out.println(transfer.toString() + "\u001B[34mPayee:\u001B[0m" + fullName[0] + " " + fullName[1]);
            return;
        }
        System.out.println(
                transfer.toString() + "\u001B[34mPayee:\u001B[0m" + payee.getName() + " " + payee.getFamilyName());
    }

    public String[] findContactName(User user) {
        for (Map.Entry<String[], User> individual : this.user.getContacts().entrySet()) {
            if (individual.getValue().getPhoneNumber().equals(user.getPhoneNumber())) {
                return individual.getKey();
            }
        }
        return null;
    }

    public void handleMoneyTransfer() {
        Menu.printMoneyTransferOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                cardByCard(300, 100000);
                break;
            case 2:
                paypol(100, 5000000);
                break;
            case 3:
                stableTransfer(2000, 5000000);
                break;
            case 4:
                transferFeryToFery();
                break;
            default:
                System.out.print("\u001B[31mNo such user in the bank!\u001B[0m");
                break;
        }
    }

    public void stableTransfer(double commision, double max) {
        System.out.println("\u001B[34mEnter your card number:\u001B[0m");
        String input = CostumScanner.getInstance().nextLine();
        if (!input.equals(user.getCreditCard().getCardNumber())) {
            System.out.println("\u001B[31mIncorrect card number!\u001B[0m");
            return;
        }
        System.out.println("\u001B[34mEnter your card password:\u001B[0m");
        input = CostumScanner.getInstance().nextLine();
        if (!input.equals(user.getCreditCard().getCardPassword())) {
            System.out.println("\u001B[31mIncorrect password!\u001B[0m");
            return;
        }
        System.out.println("\u001B[34mEnter destination card number:\u001B[0m");
        String cardNumber = CostumScanner.getInstance().nextLine();
        Person person = findPerson(cardNumber);
        if (person == null) {
            System.out.println("\u001B[31mPerson not found!\u001B[0m");
            return;
        }
        double amount = getAmount(commision, max);
        if(amount != 0){
            Thread thread = handleStableTransfer(amount, person);
            thread.start();
        }
    }

    public Thread handleStableTransfer(double amount, Person person) {
        return new Thread(() -> {
            try {
                Thread.sleep(180000);
                transfer(amount, person, false);
            } catch (InterruptedException e) {
                System.out.println("\u001B[31mThread intrrupted!\u001B[0m");
                e.printStackTrace();
            }
        });
    }

    public void paypol(double commision, double max) {
        cardByCard(commision, max);
    }

    public void cardByCard(double commision, double max) {
        System.out.println("\u001B[34mEnter your card number:\u001B[0m");
        String input = CostumScanner.getInstance().nextLine();
        if (!input.equals(user.getCreditCard().getCardNumber())) {
            System.out.println("\u001B[31mIncorrect card number!\u001B[0m");
            return;
        }
        System.out.println("\u001B[34mEnter your card password:\u001B[0m");
        input = CostumScanner.getInstance().nextLine();
        if (!input.equals(user.getCreditCard().getCardPassword())) {
            System.out.println("\u001B[31mIncorrect password!\u001B[0m");
            return;
        }
        System.out.println("\u001B[34mEnter destination card number:\u001B[0m");
        String cardNumber = CostumScanner.getInstance().nextLine();
        Person person = findPerson(cardNumber);
        if (person == null) {
            System.out.println("\u001B[31mPerson not found!\u001B[0m");
            return;
        }
        double amount = getAmount(commision, max);
        if (amount != 0) {
            transfer(amount, person, false);
        }
    }

    public double getAmount(double commision, double max) {
        System.out.println("\u001B[34mEnter amount you want to transfer:\u001B[0m");
        double amount = CostumScanner.getInstance().nextDouble();
        if (user.getCreditCard().getCredit() < amount) {
            System.out.println("\u001B[31mYour account balance is insufficient\u001B[0m");
            return 0;
        }
        if (amount > max) {
            System.out.println("\u001B[31mYou can't transfer more than limit!\u001B[0m");
            return 0;
        }
        return amount + commision;
    }

    public double checkAmount(double commision, double max, double amount) {
        if (user.getCreditCard().getCredit() < amount) {
            return -1;
        }
        if (amount > max) {
            return -1;
        }
        return amount + commision;
    }

    public Person findPerson(String cardNumber) {
        return otherBanksUsers.stream().filter(person -> cardNumber.equals(person.getCreditCard().getCardNumber()))
                .findFirst().orElse(null);
    }

    public void transferFeryToFery() {
        Menu.printFeryToFeryTransferOptions();
        int choice = CostumScanner.getInstance().nextInt();
        String cardNumber = "";
        switch (choice) {
            case 1:
                printCardNumbers(allUsers, user);
                System.out.println("\u001B[34mEnter card number:\u001B[0m");
                cardNumber = CostumScanner.getInstance().nextLine();
                transferMoneyByCardNumber(allUsers, cardNumber, false);
                break;
            case 2:
                cardNumber = chooseRecentTransfer();
                if (cardNumber == null) {
                    return;
                }
                transferMoneyByCardNumber(allUsers, cardNumber, false);
                break;
            case 3:
                transferToFeryContact();
                break;
            default:
                break;
        }
    }

    public void transferToFeryContact() {
        user.printContacts();
        System.out.println("\u001B[34mChoose a contact:\u001B[0m");
        int choose = CostumScanner.getInstance().nextInt();
        User contact = findContact(user.getContacts(), choose);
        if (contact.getContactFeature() == 0) {
            System.out.println("\u001B[31mThis contact has disabled the contact feature!\u001B[0m");
        } else if (areEachOtherCantact(contact, user)) {
            transferMoneyByCardNumber(allUsers, contact.getCreditCard().getCardNumber(), true);
        } else {
            System.out.println("\u001B[31mYou aren't contact of this contact!\u001B[0m");
        }
    }

    public void printCardNumbers(AllUsers allUsers, User user) {
        int counter = 1;
        for (User individual : allUsers.getUser()) {
            if (individual.getCreditCard().getCardNumber().equals(user.getCreditCard().getCardNumber())) {
                continue;
            }
            System.out.println(counter + "." + individual.getCreditCard().getCardNumber());
            counter++;
        }
    }

    public boolean areEachOtherCantact(User contact, User user) {
        List<User> list = new ArrayList<>();
        for (User individual : contact.getContacts().values()) {
            list.add(individual);
        }
        return list.contains(user);
    }

    public User findContact(Map<String[], User> contacts, int choose) {
        int number = 1;
        for (User contact : contacts.values()) {
            if (number == choose) {
                return contact;
            }
            number++;
        }
        return null;
    }
}
