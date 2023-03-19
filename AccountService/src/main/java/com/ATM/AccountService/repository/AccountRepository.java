package com.ATM.AccountService.repository;

import com.ATM.AccountService.models.Account;
import com.ATM.AccountService.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

    public interface AccountRepository extends MongoRepository<Account, String> {

        @Query("{customer:'?0'}")
        Account findAccountByCustId(Customer c);

        @Query("{accId:'?0'}")
        Account findAccountByAccId(String accId);
    }

