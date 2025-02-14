package com.semsari.fatemeh.firstapp.faribank;

import com.semsari.fatemeh.firstapp.faribank.util.Calendar;

public class UsersFunds {
    private User user = null;
    private PrintLists printLists = new PrintLists();

    public UsersFunds(User user) {
        this.user = user;
    }

    public void handleFunds() {
        Menu.printUsersFundsOption();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                manageFund();
                break;
            case 2:
                addFund();
                break;
            default:
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                break;
        }
    }

    public void manageFund() {
        printLists.printUserFunds(user.getFunds());
        int index = CostumScanner.getInstance().nextInt();
        index--;
        Menu.printManageFundOptions();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
//                transferMoneyToFund(user.getFunds().get(index));
                break;
            case 2:
                withdrawMoneyFromTheFund(user.getFunds().get(index));
                break;
            case 3:
                System.out.println("\u001B[34mFund credit:\u001B[0m" + user.getFunds().get(index).getFundCredit());
                break;
            default:
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                break;
        }
    }

    public void withdrawMoneyFromTheFund(Fund fund) {
        if (fund.getFundType().equals(FundType.BONUS_FUND)) {
            if (fund.checkFund(new Calendar().now())) {
                returnCapitalAndDeleteFund(fund);
            }
            return;
        }
        System.out.println("\u001B[34mEnter amount you want to withdraw:\u001B[0m");
        double amount = CostumScanner.getInstance().nextDouble();
        if (amount > fund.getFundCredit()) {
            System.out.println("\u001B[31mFund's balance is insufficient!\u001B[0m");
            return;
        }
        user.getCreditCard().setCredit(user.getCreditCard().getCredit() + amount);
        fund.setFundCredit(fund.getFundCredit() - amount);
        System.out.println("\u001B[32mMoney transfered to fund successfully!\u001B[0m");
    }

    public boolean transferMoneyToFund(Fund fund) {
        if (fund.getFundType().equals(FundType.BONUS_FUND)) {
            if (fund.checkFund(new Calendar().now())) {
                returnCapitalAndDeleteFund(fund);
                return false;
            }
            return false;
        }
        return true;
    }

    public void returnCapitalAndDeleteFund(Fund fund) {
        user.getCreditCard().setCredit(user.getCreditCard().getCredit() + fund.getFundCredit());
        user.getFunds().remove(fund);
    }

    public void addFund() {
        Fund fund = null;
        Menu.printFundTypes();
        int choice = CostumScanner.getInstance().nextInt();
        switch (choice) {
            case 1:
                fund = new BonusFund(FundType.BONUS_FUND);
                checkAndAddFundBonusFund(fund);
                break;
            case 2:
                fund = new RemainingFunds(FundType.REMAINING_FUND);
                checkAndAddRemainingFund(fund);
                break;
            case 3:
                fund = new SavingsFund(FundType.SAVINGS_FUND);
                checkAndAddSavingsFund(fund);
                break;
            default:
                System.out.println("\u001B[31mInvalid input!\u001B[0m");
                break;
        }
    }

    public boolean checkAndAddRemainingFund(Fund fund) {
        if (user.getFunds().isEmpty()) {
            user.addFund(fund);
            System.out.println("\u001B[32mFund added successfully!\u001B[0m");
            return true;
        }
        for (Fund userFund : user.getFunds()) {
            if (userFund.getFundType().equals(fund.getFundType())) {
                System.out.println("\u001B[31mYou already have this type of fund!\u001B[0m");
                return false;
            }
        }
        user.addFund(fund);
        System.out.println("\u001B[32mFund added successfully!\u001B[0m");
        return true;
    }

    public boolean checkAndAddSavingsFund(Fund fund) {
        user.addFund(fund);
        System.out.println("\u001B[32mFund added successfully!\u001B[0m");
        return true;
    }

    public boolean checkAndAddFundBonusFund(Fund fund) {
        user.addFund(fund);
        new Thread(new InterestDeposit(fund, user)).start();
        return true;
    }

}
