package com.service;

import com.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionCache {
    public Transaction cachingTransaction(Transaction transaction);

    public List<BigDecimal> getCacheValues();

    public void evictAllCacheValues();
}
