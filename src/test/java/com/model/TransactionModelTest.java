package com.model;

import com.exception.OldTransactionException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionModelTest {

    private Transaction transaction;
    private Validator validator;

    @Before
    public void setData(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void transactionWithFutureTimestamp(){
        Instant instant = Instant.now().plusMillis(+10000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void transactionWithTimestampWithin60Seconds(){
        Instant instant = Instant.now().plusMillis(-10000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertTrue(violations.isEmpty());
    }

    @Test//(expected = MethodArgumentNotValidException.class)
    public void transactionWithTimestampOlderThan60Seconds(){
        Instant instant = Instant.now().plusMillis(-70000);
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(instant));
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void transactionWithNullAmount(){
        Instant instant = Instant.now();
        transaction = new Transaction(null, Timestamp.from(instant));
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void transactionWithNullTimestamp(){
        Instant instant = Instant.now();
        transaction = new Transaction(BigDecimal.valueOf(1234,2), null);
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertFalse(violations.isEmpty());
    }

}
