package com.ATM.AccountService.controller;

import com.ATM.AccountService.models.Account;
import com.ATM.AccountService.models.Customer;
import com.ATM.AccountService.models.card.Card;
import com.ATM.AccountService.models.card.DebitCard;
import com.ATM.AccountService.repository.AccountRepository;
import com.ATM.AccountService.repository.CardRepository;
import com.ATM.AccountService.repository.CustomerRepository;
import com.ATM.AccountService.utils.webclientRequestHandler;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
public class CardController {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CardRepository cardRepo;
    @Autowired
    CustomerRepository customerRepo;

    @PostMapping("/card")
    public ResponseEntity createCard(@RequestBody JSONObject body) {
        try {
            String accId = body.get("accId").toString();

            Account a = accountRepo.findAccountByAccId(accId);
            DebitCard newCard = new DebitCard(a);
            cardRepo.save(newCard);

            Customer c = customerRepo.findCustomerById(a.getCustomer());
            JSONObject emailbody = new JSONObject();
            emailbody.put("from", "edxpallav@gmail.com");
            emailbody.put("to", c.getEmailId());
            emailbody.put("subject", "Activate your new card with PMC Bank");
            emailbody.put("content", "Please go to https://www.PMC.com to activate your new card ending with ** 1234.");
            Mono<JSONObject> emailRes = new webclientRequestHandler().makePostRequest("/notify/email", emailbody);
            return ResponseEntity.ok("Card created success!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("/card/activate")
    public ResponseEntity activateCard(@RequestBody JSONObject body) {
        try {
            String cardNo = body.get("cardNo").toString();
            if(body.get("pin") == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Necessary params missing");

            }
            int pin = Integer.parseInt(body.get("pin").toString());

            DebitCard c = (DebitCard) cardRepo.findCardByCardNo(cardNo);

            if(c==null){

            } else {
                c.setPin(pin);
            }
            cardRepo.save((Card) c);
            return ResponseEntity.ok("Card activation success!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
