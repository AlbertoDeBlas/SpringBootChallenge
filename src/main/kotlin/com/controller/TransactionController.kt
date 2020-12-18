package com.controller;

import com.model.Transaction;
import com.service.TransactionCache;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.inject.Inject;

@RestController
public class TransactionController {

    private TransactionCache transactionCache;

    @Inject
    public TransactionController(TransactionCache transactionCache) {
        this.transactionCache = transactionCache;
    }

    @PostMapping("/transactions")
    @ResponseStatus( HttpStatus.CREATED )
    public void newTransaction(@Valid @RequestBody Transaction newTransaction){
        transactionCache.cachingTransaction(newTransaction);
    }

    @DeleteMapping("/transactions")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    void deleteTransactions(){
        transactionCache.evictAllCacheValues();
    }

}
