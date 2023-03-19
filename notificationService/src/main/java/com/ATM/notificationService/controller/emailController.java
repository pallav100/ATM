package com.ATM.notificationService.controller;

import com.ATM.notificationService.models.EmailObject;
import com.ATM.notificationService.models.EmailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.minidev.json.JSONObject;

import java.io.IOException;

@RestController
public class emailController {

//    @GetMapping("/email")
//    public ResponseEntity getAccById(@RequestParam String id) {
//        Account a = accountRepo.findAccountByAccId(id);
//        if(a==null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found by this id");
//        }
//        JSONObject res = new JSONObject();
////        System.out.println(a.getCustomer().toString());
//        res.put("Accid", a.getAccId());
//        return ResponseEntity.ok(res);
//    }
    @PostMapping("/email")
    public ResponseEntity sendEmail(@RequestBody JSONObject body) {
        try {
            System.out.println(body);
//            Thread.sleep(10000);

            EmailSender s = EmailSender.getEmailSender();
            String from = body.get("from").toString();
            String to = body.get("to").toString();
            String sub = body.get("subject").toString();
            String content = body.get("content").toString();

            EmailObject e = new EmailObject(from,sub, to, content);
            System.out.println(e.toString());
            s.sendEmail(e);
            JSONObject res = new JSONObject();
            res.put("message", "Email sent");
            System.out.println("send.toString()");

            return ResponseEntity.ok(res);

        } catch (IOException io) {
            io.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email sending failed");

        }
    };


}