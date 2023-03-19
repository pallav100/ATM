package com.ATM.AccountService.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class Account {
    @Id
    private ObjectId id;
    private String accId;

    private String custId;
    private String password;
    private String createdAt;

    private Boolean active = false;

    public BigDecimal balance = new BigDecimal(0);

    @ConstructorProperties({"accId"})

    public Account( String custId) {
        this.accId = UUID.randomUUID().toString();
        this.custId = custId;
        this.createdAt =  assignDateTime();
    }
    private String assignDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        return dtf.format(now);
    }
    public void setCreatedAt(){
        this.createdAt = assignDateTime();
    }

    public String getCustomer() {
        return custId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) throws Error {
        if(this.active == false){
            throw new Error("Account is blocked, cannot perform this transaction");
        }
        this.balance = balance;
    }

    public String getAccId() {
        return accId;
    }
    private void setAccId(String s) {
        this.accId= s;
    }

    public void setPassword(String pswd){
        this.password = pswd;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accId=" + accId +
                ", customerId=" + custId +
                ", password='" + password + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
