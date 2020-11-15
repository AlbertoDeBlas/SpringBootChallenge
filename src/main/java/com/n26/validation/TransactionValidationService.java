package com.n26.validation;

import com.n26.exception.OldTransactionException;
import com.n26.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TransactionValidationService {

    public void validateTransaction(Transaction transaction){
        long difference = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) -
                TimeUnit.MILLISECONDS.toNanos(transaction.getTimestamp().getTime());
        long differenceInSeconds = TimeUnit.NANOSECONDS.toSeconds(difference);
        if(differenceInSeconds > 60){
            throw new OldTransactionException();
        }
    }


}
