package com.semsari.fatemeh.firstapp.faribank;

import java.time.Duration;
import java.time.Instant;

import com.semsari.fatemeh.firstapp.faribank.util.Calendar;

public class BonusFund extends Fund {
    private Instant foundationTime;
    private long period;

    public BonusFund(FundType fundType) {
        super(fundType);
    }

    @Override
    public void setTime(int days, int hours) {
        foundationTime = new Calendar().now();
        convertToSeconds(days, hours);
    }

    public void convertToSeconds(int days, int hours) {
        period = (long) days * 3600 * 24 + hours * 3600;
    }

    @Override
    public boolean checkFund(Instant now) {
        boolean isEpired = false;
        Duration duration = Duration.between(foundationTime, now);
        if (duration.getSeconds() > period) {
            isEpired = true;
            System.out.println("\u001B[31mThis fund has expired.\u001B[0m");
            System.out.println("\u001B[33mReturning your capital to the main account...\u001B[0m");
        } else {
            System.out.println(
                    "\u001B[31mYou can't transfer or withdrow an amount from this bonus fund before the specified time!\u001B[0m");
        }
        return isEpired;
    }

    @Override
    public long getTime() {
        return period;
    }

}
