package com.n26.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


import com.n26.initialization.CacheConfigurationHandler;
import com.n26.model.Transaction;
import com.n26.service.serviceImpl.TransactionCacheImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class TransactionCacheTest {

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    @Mock
    private CacheManager cacheManager;

    private Transaction transaction;
    private Transaction anotherTransaction;
    private Caffeine caffeine;
    private Cache cache;
    private CaffeineCache caffeineCache;
    private TransactionCache transactionCache;

    @Before
    public void setData(){
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now()));
        anotherTransaction = new Transaction(BigDecimal.valueOf(1334,2), Timestamp.from(Instant.now()));
        caffeine = CacheConfigurationHandler.getTransactionCaffeineConfig();
        cache = caffeine.build();
        caffeineCache = new CaffeineCache("transactionCache", cache);
        transactionCache = new TransactionCacheImpl(cacheManager);
    }

    @Test
    public void transactionCaching(){
        Transaction firstTransaction = transactionCache.cachingTransaction(transaction);
        Transaction secondTransaction = transactionCache.cachingTransaction(transaction);

        assertThat(firstTransaction, equalTo(secondTransaction));
    }

    @Test
    public void transactionCachingDifferentTrans(){
        Transaction firstTransaction = transactionCache.cachingTransaction(transaction);
        Transaction secondTransaction = transactionCache.cachingTransaction(anotherTransaction);

        assertThat(firstTransaction, not(secondTransaction));
    }

    @Test
    public void getSingleCacheValues(){
        caffeineCache.put("first",transaction);
        Mockito.when(cacheManager.getCache(Mockito.anyString()))
                .thenReturn(caffeineCache);
        List<BigDecimal> cachedValues = transactionCache.getCacheValues();
        ArrayList<BigDecimal> expectedValues = new ArrayList<>();
        expectedValues.add(BigDecimal.valueOf(1234,2));

        assertThat(expectedValues, equalTo(new ArrayList<>(cachedValues)));
    }

    @Test
    public void getMultipleCacheValues(){
        caffeineCache.put("first",transaction);
        caffeineCache.put("second",anotherTransaction);
        Mockito.when(cacheManager.getCache(Mockito.anyString()))
                .thenReturn(caffeineCache);
        List<BigDecimal> cachedValues = transactionCache.getCacheValues();
        ArrayList<BigDecimal> expectedValues = new ArrayList<>();
        expectedValues.add(BigDecimal.valueOf(1334,2));
        expectedValues.add(BigDecimal.valueOf(1234,2));

        assertThat(expectedValues, equalTo(new ArrayList<>(cachedValues)));
    }

    @Test
    public void getEmptyCacheValues(){
        Mockito.when(cacheManager.getCache(Mockito.anyString()))
                .thenReturn(caffeineCache);
        List<BigDecimal> cachedValues = transactionCache.getCacheValues();

        assertThat(cachedValues,is(empty()));
    }
}


