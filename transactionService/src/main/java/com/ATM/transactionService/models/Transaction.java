package com.ATM.transactionService.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private String atmId;
    private String transId;
    public BigDecimal amount;
    private String dateTime;

    private String cardNo;

    private String assignDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        return dtf.format(now);
    }

    public Transaction(String atmId, String cardNo, BigDecimal amount) {
        this.atmId = atmId;
        this.amount = amount;
        this.cardNo  = cardNo;
        this.dateTime = assignDateTime();
        this.transId = UUID.randomUUID().toString();
    }

    public String getTransId() {
        return transId;
    }


    public BigDecimal getAmount() {
        return amount;
    }
}
