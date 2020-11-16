package com.n26.service;

import com.n26.model.Transaction;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class TransactionCache {

    private CacheManager cacheManager;

    @Inject
    public TransactionCache(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    @Cacheable("TransactionCache")
    public Transaction cachingTransaction(Transaction transaction){
        return transaction;
    }
}
