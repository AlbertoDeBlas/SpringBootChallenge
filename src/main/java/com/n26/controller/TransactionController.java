package com.n26.controller;

import com.n26.model.Transaction;
import com.n26.service.TransactionCache;
import com.n26.service.serviceImpl.TransactionCacheImpl;
import com.n26.validation.TransactionValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.inject.Inject;

@RestController
public class TransactionController {

    TransactionValidationService transactionValidationService;
    TransactionCache transactionCache;

    @Inject
    public TransactionController(TransactionValidationService transactionValidationService,
                                 TransactionCache transactionCache) {
        this.transactionValidationService = transactionValidationService;
        this.transactionCache = transactionCache;
    }

    @PostMapping("/transactions")
    @ResponseStatus( HttpStatus.CREATED )
    public void newTransaction(@Valid @RequestBody Transaction newTransaction){
        transactionValidationService.validateTransactionAge(newTransaction);
        transactionCache.cachingTransaction(newTransaction);
    }

    @DeleteMapping("/transactions")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    void deleteTransactions(){
        transactionCache.evictAllCacheValues();
    }

}
