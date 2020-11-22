package com.n26.service;

import com.n26.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface TransactionCache {
    public Transaction cachingTransaction(Transaction transaction);

    public List<BigDecimal> getCacheValues();

    public void evictAllCacheValues();
}
