package com.n26.validation;

import com.n26.model.Transaction;

public interface TransactionValidationService {
    public void validateTransactionAge(Transaction transaction);
}
