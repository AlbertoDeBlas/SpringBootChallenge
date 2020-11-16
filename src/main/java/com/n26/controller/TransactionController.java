package com.n26.controller;

import com.n26.model.Transaction;
import com.n26.validation.TransactionValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.inject.Inject;

@RestController
public class TransactionController {

    TransactionValidationService transactionValidationService;

    @Inject
    public TransactionController(TransactionValidationService transactionValidationService) {
        this.transactionValidationService = transactionValidationService;
    }

    @PostMapping("/transactions")
    @ResponseStatus( HttpStatus.CREATED )
    public void newTransaction(@Valid @RequestBody Transaction newTransaction){
        transactionValidationService.validateTransaction(newTransaction);
    }

}
