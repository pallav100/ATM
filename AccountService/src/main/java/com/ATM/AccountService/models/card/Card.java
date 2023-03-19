package com.ATM.AccountService.models.card;

import com.ATM.AccountService.models.Account;
import com.ATM.AccountService.models.Customer;

import java.time.LocalDate;
import java.util.Date;

public abstract class  Card {
    public Customer cardHolder;
    public Account account;

    public String cardNo;

    public Account getAccount() {
        return account;
    }

    public String getCardNo() {
        return cardNo;
    }

    public LocalDate getExpireOn() {
        return expireOn;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate expireOn = LocalDate.now();
    public boolean active = false;

    private void setActiveStatus(boolean v){
        this.active = v;
    }

}