package com.n26.repository;

import com.n26.model.Transaction;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;


@Repository
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

    public ArrayList getCacheValues(){
        CaffeineCache caffeineCache = (CaffeineCache)cacheManager.getCache("TransactionCache");
        ConcurrentMap<Object, Object> cache = caffeineCache.getNativeCache().asMap();
        return TransactionCacheHandler.getAmountsArrayList(cache);
    }



    @CacheEvict(value = "TransactionCache", allEntries = true)
    public void evictAllCacheValues() {}
}
