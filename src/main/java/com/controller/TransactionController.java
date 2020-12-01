package com.controller;

import com.model.Transaction;
import com.service.TransactionCache;
import com.validation.TransactionValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.inject.Inject;

@RestController
public class TransactionController {

    private TransactionValidationService transactionValidationService;
    private TransactionCache transactionCache;

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
