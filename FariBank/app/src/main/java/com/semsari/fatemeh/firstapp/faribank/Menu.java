package com.semsari.fatemeh.firstapp.faribank;

public class Menu {
    public static void mainMenu() {
        System.out.println("1.Log in as a user");
        System.out.println("2.Log in as a supporter");
        System.out.println("3.Log in as a manager");
        System.out.println("4.Exit");
        System.out.println("Enter your choice:");
    }

    public static void printMenuForSupporter() {
        System.out.println("\u001B[35m\n\t<Main Menu>\u001B[0m");
        System.out.println("1.Requests");
        System.out.println("2.Users");
        System.out.println("3.Exit");
        System.out.println("\u001B[35mEnter your choice:\u001B[0m");
    }

    public static void printMenuForUser() {
        System.out.println("\u001B[34m\n\t<Main Menu>\u001B[0m");
        System.out.println("1.Account management");
        System.out.println("2.Cantacts");
        System.out.println("3.Money transfer");
        System.out.println("4.Support");
        System.out.println("5.Settings");
        System.out.println("6.Capital Funds");
        System.out.println("7.Buy SIM Recharge");
        System.out.println("8.Exit");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printAccountManagementServices() {
        System.out.println("\n1.Charge the account");
        System.out.println("2.View account credit");
        System.out.println("3.Transactions");
        System.out.println("4.View SIM card credit");
        System.out.println("5.Exit");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printFeryToFeryTransferOptions() {
        System.out.println("1.Enter card number");
        System.out.println("2.Recents");
        System.out.println("3.Transfer money to cantact");
    }

    public static void printSettings() {
        System.out.println("\u001B[34m\n\t<<Settings>>\u001B[0m");
        System.out.println("1.change card password");
        System.out.println("2.change login password");
        System.out.println("3.contacts feature");
    }

    public static void printUserRequestOptions() {
        System.out.println("\u001B[34m\nEnter your choice:\u001B[0m");
        System.out.println("\u001B[34mRequest subject:\u001B[0m");
        System.out.println("1.Report");
        System.out.println("2.Contacts");
        System.out.println("3.Transfer");
        System.out.println("4.Settings");
        System.out.println("5.Capital Funds");
        System.out.println("6.SIM Recharge");
        System.out.println("\u001B[34m7.View all requests\u001B[0m");
    }

    public static void printchangableOptions() {
        System.out.println("\u001B[34m\nChange:\u001B[0m");
        System.out.println("1.name");
        System.out.println("2.family name");
        System.out.println("3.phoneNumber");
        System.out.println("4.Nothing");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printContactsOptions() {
        System.out.println("\n1.Add Contact");
        System.out.println("2.Edit contact");
        System.out.println("3.View all contacts");
        System.out.println("4.Exit");
        System.out.println("\u001B[34mChoose an action:\u001B[0m");
    }

    public static void printTransactionOptions() {
        System.out.println("\n1.View all transactions");
        System.out.println("2.Search transaction");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printUsersFundsOption() {
        System.out.println("\n1.Manage Fund");
        System.out.println("2.Add Found");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printManageFundOptions() {
        System.out.println("\n1.Transfer money to fund");
        System.out.println("2.Withdraw money from fund");
        System.out.println("3.View Fund Credit");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printFundTypes() {
        System.out.println("\n1.Bonus fund");
        System.out.println("2.Remainig fund");
        System.out.println("3.Savings fund");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printSIMRecharge() {
        System.out.println("\n1.Buy for a contact");
        System.out.println("2.Buy via entering phone number");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

    public static void printManagerOptions() {
        System.out.println("\u001B[36m\n\t<Main Menu>\u001B[0m");
        System.out.println("1.Bank Settings");
        System.out.println("2.Users Management");
        System.out.println("3.Automatic Transactions");
        System.out.println("4.Exit");
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
    }

    public static void printManagerSettings() {
        System.out.println("\n1.Set interest rate");
        System.out.println("2.Set commision rate");
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
    }

    public static void printUserManagementOptions() {
        System.out.println("\n1.User's List");
        System.out.println("2.Add Manager");
        System.out.println("3.Add Supporter");
        System.out.println("4.Edit Users Information");
        System.out.println("5.Determining specific task for supporter");
        System.out.println("6.Exit");
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
    }

    public static void printUsersPrintingOptions() {
        System.out.println("\n1.Users List");
        System.out.println("2.Supporters List");
        System.out.println("3.Managers List");
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
    }

    public static void printListOfRoles() {
        System.out.println("\n1.Users");
        System.out.println("2.Supporters");
        System.out.println("3.Managers");
        System.out.println("4.None of them");
        System.out.println("\u001B[36mChoose a role:\u001B[0m");
    }

    public static void printEditOptions() {
        System.out.println("\u001B[36m\nEdit:\u001B[0m");
        System.out.println("1.name");
        System.out.println("2.family name");
        System.out.println("3.User access");
        System.out.println("4.Nothing");
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
    }

    public static void printUserAccessOptions() {
        System.out.println("1.Give access");
        System.out.println("2.Block");
        System.out.println("\u001B[36mEnter your choice:\u001B[0m");
    }

    public static void supporterTasks() {
        System.out.println("\n1.Authentication");
        System.out.println("2.Support");
        System.out.println("3.Capital Funds");
        System.out.println("4.Contacts");
        System.out.println("5.Money Transfer");
        System.out.println("6.Settings");
        System.out.println("7.Done");
        System.out.println("\u001B[36mEnter your choice\u001B[0m");
    }

    public static void printMoneyTransferOptions() {
        System.out.println("\n1.Card by card");
        System.out.println("2.Paypol");
        System.out.println("3.Stable transfer");
        System.out.println("4.Fery to Fery transfer");
        System.out.println("\u001B[34mEnter your choice:\u001B[0m");
    }

}
