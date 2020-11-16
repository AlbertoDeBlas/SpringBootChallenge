package com.n26.validation.validationImpl;

import com.n26.exception.OldTransactionException;
import com.n26.model.Transaction;
import com.n26.validation.TransactionValidationService;
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
