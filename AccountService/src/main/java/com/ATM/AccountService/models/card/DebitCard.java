package com.ATM.AccountService.models.card;

import com.ATM.AccountService.models.Account;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebitCard extends Card {
    private int pin;

    public DebitCard(Account account) {
        this.account =account;
        this.cardNo = String.valueOf(48800000000000L + (long) (Math.random() * 1000000000000L));
        this.expireOn = this.expireOn.plusYears(5);
    }

    public void setPin(int pin) {
        this.active = true;
        this.pin = pin;
    };
    public int getPin() {
        return this.pin;
    };


    public boolean checkValidPin(int p){
        Matcher m = Pattern.compile("\\d{4}").matcher(String.valueOf(p));
        if (m.matches()) {
            return true;
        } else {
            System.out.println("You have not entered a 4-digit pin");
            return false;
        }
    }
}
