package com.n26.service;

import com.n26.model.Transaction;

import java.util.ArrayList;

public interface TransactionCache {
    public Transaction cachingTransaction(Transaction transaction);

    public ArrayList getCacheValues();

    public void evictAllCacheValues();
}
