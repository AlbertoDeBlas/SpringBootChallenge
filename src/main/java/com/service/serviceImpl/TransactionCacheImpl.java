package com.service.serviceImpl;

import com.model.Transaction;
import com.service.TransactionCache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
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

    public List<BigDecimal> getCacheValues(){
        CaffeineCache caffeineCache = (CaffeineCache)cacheManager.getCache("TransactionCache");
        ConcurrentMap<Object, Object> cache = caffeineCache.getNativeCache().asMap();
        return TransactionCacheHandler.getAmountsList(cache);
    }



    @CacheEvict(value = "TransactionCache", allEntries = true)
    public void evictAllCacheValues() {}
}
