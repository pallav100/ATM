package com.ATM.AccountService.repository;

import com.ATM.AccountService.models.card.Card;
import com.ATM.AccountService.models.card.DebitCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CardRepository extends MongoRepository<Card, String> {

    @Query("{cardNo:'?0'}")
    Card findCardByCardNo(String c);
    @Query("{active:'?0'}")
    Card findCardByActive(boolean c);

}
