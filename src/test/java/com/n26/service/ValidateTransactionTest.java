package com.n26.service;

import com.n26.exception.OldTransactionException;
import com.n26.model.Transaction;
import com.n26.validation.TransactionValidationService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class ValidateTransactionTest {

    private TransactionValidationService transactionValidationService;
    private Transaction transaction;

    @Before
    public void setData(){
        transactionValidationService = new TransactionValidationService();
    }

    @Test(expected = OldTransactionException.class)
    public void notValidTransaction(){
        Instant instant = Instant.now().plusMillis(-61000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        transactionValidationService.validateTransaction(transaction);
    }

    @Test
    public void validTransaction(){
        Instant instant = Instant.now().plusMillis(-1000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        transactionValidationService.validateTransaction(transaction);
    }

}
