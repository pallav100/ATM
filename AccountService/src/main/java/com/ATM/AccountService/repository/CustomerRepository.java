package com.ATM.AccountService.repository;

import com.ATM.AccountService.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{name:'?0'}")
    Customer findCustomerByName(String name);

    @Query("{custId:'?0'}")
    Customer findCustomerById(String custId);

    @Query("{name: '?0', emailId: '?1'}")
    Customer findCustomerByNameAndEmail(String name, String email);
}
