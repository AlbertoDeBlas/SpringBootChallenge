package com.validation.validationImpl;

import com.exception.OldTransactionException;
import com.model.Transaction;
import com.validation.TransactionValidationService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TransactionValidationServiceImpl implements TransactionValidationService {

    public void validateTransactionAge(Transaction transaction){
        long difference = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) -
                TimeUnit.MILLISECONDS.toNanos(transaction.getTimestamp().getTime());
        long differenceInSeconds = TimeUnit.NANOSECONDS.toSeconds(difference);
        if(differenceInSeconds >= 60){
            throw new OldTransactionException();
        }
    }


}
