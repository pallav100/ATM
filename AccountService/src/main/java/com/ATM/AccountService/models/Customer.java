package com.ATM.AccountService.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document("customers")
public class Customer {
    @Id
    private ObjectId id;
    @Field
    private String custId;
    private String name;
    private final String emailId;

    public Customer(String name, String emailId) {

        this.custId = UUID.randomUUID().toString();
        this.name = name;
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", name='" + name + ", email" + emailId + '\'' +
                '}';
    }

    public String getEmailId() {
        return emailId;
    }

    public String getId() {
        return custId;
    }

    public String getName() {
            return name;
        }
}


