package com.n26.service.serviceImpl;

import com.n26.model.Transaction;
import com.n26.service.TransactionCache;
import com.n26.service.serviceImpl.TransactionCacheHandler;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;


@Service
public class TransactionCacheImpl implements TransactionCache {

    private CacheManager cacheManager;

    @Inject
    public TransactionCacheImpl(CacheManager cacheManager){
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
