package com.validation;

import com.model.Transaction;

public interface TransactionValidationService {
    public void validateTransactionAge(Transaction transaction);
}
