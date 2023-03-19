package com.ATM.transactionService.controllers;

import com.ATM.transactionService.utils.WebfluxRequestBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import net.minidev.json.JSONObject;

@RestController
public class ATMtransactionController {
    @PostMapping("/api/transaction/atm")
    public ResponseEntity sendEmail(@RequestBody JSONObject body) {
        try {
            System.out.println(body);
            JSONObject transactBody = new JSONObject();
            transactBody.put("cardNo", body.get("cardNo"));
            transactBody.put("amount", body.get("amount"));

            JSONObject transactRes = new WebfluxRequestBuilder().makePostRequest("/customer/account/balance", transactBody);
            JSONObject res = new JSONObject();
            res.put("message", "Transaction successful");
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction failed. Try again later.");

        }
    };
}
