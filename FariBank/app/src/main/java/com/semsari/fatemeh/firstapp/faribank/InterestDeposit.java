package com.semsari.fatemeh.firstapp.faribank;

public class InterestDeposit implements Runnable {
    private Fund bonusFund;
    private User user;

    public InterestDeposit(Fund bonusFund, User user) {
        this.bonusFund = bonusFund;
        this.user = user;
    }

    @Override
    public void run() {
        int months = findPeriodOfDepositing(bonusFund.getTime());
        for (int i = 0; i < months; i++) {
            try {
                Thread.sleep(232000);
                bonusFund.setFundCredit(bonusFund.getFundCredit() + bonusFund.getFundCredit() * 5 / 100);
                System.out.println("\u001B[34m" + user.getName() + user.getFamilyName()
                        + "'s profit has been deposited!\u001B[0m");
                FundTransaction fundTransaction = new FundTransaction();
                fundTransaction.setType("Deposite");
                fundTransaction.setAmount(bonusFund.getFundCredit() * 5 / 100);
                bonusFund.addTransaction(fundTransaction);
            } catch (InterruptedException e) {
                System.out.println("\u001B[31mDepositing interest for this bonus fund intrrupted!\u001B[0m");
                e.printStackTrace();
            }
        }
    }

    public int findPeriodOfDepositing(long seconds) {
        return (int) seconds / 3600 / 24 / 30;
    }
}
