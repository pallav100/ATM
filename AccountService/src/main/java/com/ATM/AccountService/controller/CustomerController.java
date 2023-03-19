package com.ATM.AccountService.controller;

import com.ATM.AccountService.models.Customer;
import com.ATM.AccountService.repository.CustomerRepository;
import com.ATM.AccountService.utils.webclientRequestHandler;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class CustomerController {
        @Autowired
        CustomerRepository customerRepo;

        @PostMapping("/")
        public ResponseEntity createCustomer(@RequestBody JSONObject body ) {
            String name = body.get("name").toString();
            String email = body.get("emailId").toString();

            Customer c = customerRepo.findCustomerByNameAndEmail(name, email);
            JSONObject res = new JSONObject();

            if(c!=null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A user with this name and emaill already exists");
            }
            Customer nc = new Customer(name, email);
            customerRepo.save(nc);

            res.put("name", nc.getName());
            res.put("id", nc.getId());
            System.out.println("new customer added");


            return ResponseEntity.ok(res);
        }

//    @PostMapping("/customer/email")
//    public ResponseEntity saveEmail(@RequestBody JSONObject body ) {
//        String id = body.get("id").toString();
//        String email = body.get("email").toString();
//
//        Customer c = customerRepo.findCustomerById(id);
//
//        if(c==null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user by this id not found");
//        }
//        c.setEmailId(email);
//        customerRepo.save(c);
//        return ResponseEntity.ok("Email updated successfully");
//    }
        @GetMapping("/")
        public ResponseEntity getCustomerByName(@RequestParam(required = false) String name, @RequestParam(required = false) String id) {
            System.out.println(id);
            Customer c = null;
            if(name==null && id==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("necessary params missing");
            }
            if(name!=null) {
                 c = customerRepo.findCustomerByName(name);
            }
            else if(id!=null) {
                c = customerRepo.findCustomerById(id);
            }


            JSONObject res = new JSONObject();
            if(c!=null) {
                res.put("name", c.getName());
                res.put("id", c.getId());
            }
            return ResponseEntity.ok(res);
        }

}
