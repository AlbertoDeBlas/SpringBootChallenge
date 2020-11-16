package com.n26.validation;

import com.n26.exception.OldTransactionException;
import com.n26.model.Transaction;
import com.n26.validation.validationImpl.TransactionValidationServiceImpl;
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
        transactionValidationService = new TransactionValidationServiceImpl();
    }

    @Test(expected = OldTransactionException.class)
    public void notValidTransaction(){
        Instant instant = Instant.now().plusMillis(-61000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        transactionValidationService.validateTransactionAge(transaction);
    }

    @Test
    public void validTransaction(){
        Instant instant = Instant.now().plusMillis(-1000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        transactionValidationService.validateTransactionAge(transaction);
    }

    @Test(expected = OldTransactionException.class)
    public void notValidTransactionEdge(){
        Instant instant = Instant.now().plusMillis(-60000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        transactionValidationService.validateTransactionAge(transaction);
    }

}
