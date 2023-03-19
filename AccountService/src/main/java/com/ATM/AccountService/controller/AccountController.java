package com.ATM.AccountService.controller;

import com.ATM.AccountService.models.Account;
import com.ATM.AccountService.models.Customer;
import com.ATM.AccountService.models.card.Card;
import com.ATM.AccountService.repository.AccountRepository;
import com.ATM.AccountService.repository.CardRepository;
import com.ATM.AccountService.repository.CustomerRepository;
import com.ATM.AccountService.utils.webclientRequestHandler;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@RestController
public class AccountController {
    webclientRequestHandler webflux = new webclientRequestHandler();
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    CardRepository cardRepo;

    @GetMapping("/account")
    public ResponseEntity getAccById(@RequestParam String id) {
        Account a = accountRepo.findAccountByAccId(id);
        if(a==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found by this id");
        }
        JSONObject res = new JSONObject();
//        System.out.println(a.getCustomer().toString());
        res.put("Accid", a.getAccId());
        return ResponseEntity.ok(res);
    }
    @GetMapping("/account/balance")
    public ResponseEntity getAccBalance(@RequestParam String id) {
        Account a = accountRepo.findAccountByAccId(id);
        if(a==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found by this id");
        }
        JSONObject res = new JSONObject();
        res.put("current_balance", a.getBalance());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/account")
    public ResponseEntity createAccount(@RequestBody JSONObject body) {
        try {
            System.out.println(body.get("customerId"));
            String custId = body.get("customerId").toString();
            Customer c = customerRepo.findCustomerById(custId);
            if (c == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No customer found by this id");
            } else {
                Account a = new Account(custId);
                accountRepo.save(a);
                JSONObject res = new JSONObject();
                res.put("Accid", a.getAccId());
                res.put("name", c.getName());
                // send verification email
                JSONObject emailbody = new JSONObject();
                emailbody.put("from", "edxpallav@gmail.com");
                emailbody.put("to", c.getEmailId());
                emailbody.put("subject", "Verify your email address for PMC Bank");
                emailbody.put("content", "Please go to https://www.PMC.com to verify your account creation with PMC.");
                webflux.makePostRequest("/notify/email", emailbody);
//                System.out.println(emailRes.block());
                return ResponseEntity.ok(res);
            }
        } catch (Exception e ){
            e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
            }
        };

        @PostMapping("/account/password")
        public ResponseEntity setAccountPswd(@RequestBody JSONObject body) {
                System.out.println(body.get("accId"));
                String accId = body.get("accId").toString();
                Account a = accountRepo.findAccountByAccId(accId);
            if (a == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found by this id");
            } else if(a.getActive()==false) {
                return ResponseEntity.ok("Account not activated yet, please verify your email!");
            } else {
                String pswd = body.get("password").toString();
                a.setPassword(pswd);
                accountRepo.save(a);
                return ResponseEntity.ok("Password updated successfully!");

            }
        }

        @PostMapping("/account/activate")
        public ResponseEntity verifyAccount(@RequestBody JSONObject body) {
            String accId = body.get("accId").toString();

            Account a = accountRepo.findAccountByAccId(accId);
            if (a == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found by this id");
            } else {
                String password = body.get("password").toString();
                a.setPassword(password);
                a.setActive(true);
                accountRepo.save(a);
                return ResponseEntity.ok("Account activated successfully!");

            }
        }

    @PostMapping("/account/balance")
    public ResponseEntity updateBalance(@RequestBody JSONObject body) {
        String cardNo = body.get("cardNo").toString();
//System.out.println(cardNo);
        try {
            Card c = cardRepo.findCardByCardNo(cardNo);
            if (c == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card number unrecognised");
            } else if(c!=null && !c.isActive()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card is inactive or blocked");
            } else {
                String accId = c.getAccount().getAccId();
                Account a = accountRepo.findAccountByAccId(accId);

                BigDecimal incomingAmt = new BigDecimal(body.get("amount").toString());
                BigDecimal newBalance = a.getBalance().add(incomingAmt);

                System.out.println(a.getBalance());
                System.out.println(newBalance);

                a.setBalance(newBalance);
                accountRepo.save(a);
                return ResponseEntity.ok("Account balance updated successfully!");
            }
        } catch( Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);


        }
    }
}