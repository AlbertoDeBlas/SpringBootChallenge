package com.n26.service;

import com.n26.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface TransactionCache {
    public Transaction cachingTransaction(Transaction transaction);

    public ArrayList<BigDecimal> getCacheValues();

    public void evictAllCacheValues();
}
