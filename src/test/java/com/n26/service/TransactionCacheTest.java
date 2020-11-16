package com.n26.service;

import com.github.benmanes.caffeine.cache.Ticker;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import com.n26.initialization.CacheConfigurationHandler;
import com.n26.model.Transaction;
import com.n26.service.serviceImpl.TransactionCacheImpl;
import com.n26.service.serviceImpl.TransactionCacheHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TransactionCacheTest {

    @InjectMocks
    private TransactionCache transactionCache;

    private Transaction transaction;
    private Caffeine caffeine;


    @Before
    public void setData(){
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now()));
        transactionCache = mock(TransactionCacheImpl.class);
        caffeine = CacheConfigurationHandler.getTransactionCaffeineConfig();
    }

    @Test
    public void transactionCaching(){
        Transaction firstTransaction = transactionCache.cachingTransaction(transaction);
        Transaction secondTransaction = transactionCache.cachingTransaction(transaction);

        assertThat(firstTransaction, equalTo(secondTransaction));
    }

    @Test
    public void cacheExpiration(){
        FakeTicker fakeTicker = new FakeTicker();
        LoadingCache<String, Transaction> cache = caffeine.ticker(fakeTicker).build(k -> transaction);
        cache.get("test");
        fakeTicker.advance(61, TimeUnit.SECONDS);

        assertNull(cache.getIfPresent("test"));
    }

    @Test
    public void cacheNotExpired(){
        FakeTicker fakeTicker = new FakeTicker();
        LoadingCache<String, Transaction> cache = caffeine.ticker(fakeTicker).build(k -> transaction);
        cache.get("test");
        fakeTicker.advance(59, TimeUnit.SECONDS);

        assertNotNull(cache.getIfPresent("test"));
    }

    @Test
    public void getAmountsArrayList(){
        ConcurrentHashMap<Object,Object> cachedTransactions = new ConcurrentHashMap<>();
        cachedTransactions.put("first",transaction);
        cachedTransactions.put("second",transaction);
        assertEquals(asList(transaction.getAmount(),transaction.getAmount()), TransactionCacheHandler.getAmountsArrayList(cachedTransactions));
    }
}

class FakeTicker implements Ticker {

    private final AtomicLong nanos = new AtomicLong();

    public FakeTicker advance(long time, TimeUnit timeUnit) {
        nanos.addAndGet(timeUnit.toNanos(time));
        return this;
    }

    @Override
    public long read() {
        return nanos.getAndAdd(0);
    }
}
