package com.semsari.fatemeh.firstapp.faribank;

public class Settings {
    private User user = null;

    public Settings(User user) {
        this.user = user;
    }

    public void changeCardPassword() {
        System.out.println("\u001B[34mEnter new card password:\u001B[0m");
        String newPassword = CostumScanner.getInstance().nextLine();
        if (passwordIsCorrect(newPassword)) {
            user.getCreditCard().setCardPassword(newPassword);
        } else {
            System.out.println("\u001B[31mInvalid password!\u001B[0m");
        }
    }

    public boolean passwordIsCorrect(String password) {
        if (password.length() != 4) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (!(password.charAt(i) >= '1' && password.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }

    public void changeLoginPassword() {
        System.out.println("\u001B[34mEnter new login password:\u001B[0m");
        String newPassword = CostumScanner.getInstance().nextLine();
        if (passwordIsSafe(newPassword)) {
            user.setPassword(newPassword);
        } else {
            System.out.println("\u001B[31mWeek password!\u001B[0m");
        }
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

    public void contactsFeature() {
        System.out.println("1.Active contacts feature");
        System.out.println("2.Disabling contacts feature");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                user.setContactFeature(1);
                System.out.println("\u001B[32mContacts feature activated successfully!\u001B[0m");
                break;
            case 2:
                user.setContactFeature(0);
                System.out.println("\u001B[32mContacts feature disabled successfully!\u001B[0m");
                break;
            default:
                System.out.println("\u001B[31mwInvalid input!\u001B[0m");
                break;
        }
    }
}
