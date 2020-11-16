package com.n26.service;

import com.github.benmanes.caffeine.cache.Ticker;
//import com.google.common.testing.FakeTicker;



import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.n26.initialization.CacheBuilder;
import com.n26.initialization.CacheConfigurationHandler;
import com.n26.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class TransactionCacheTest {

    @InjectMocks
    private TransactionCache transactionCache;


    @Inject
    private CacheBuilder cacheBuilder;
    private Transaction transaction;
    private Caffeine caffeine;

    @Before
    public void setData(){
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now()));
        transactionCache = mock(TransactionCache.class);
        caffeine = CacheConfigurationHandler.getTransactionCaffeineConfig();
    }

    @Test
    public void testTransactionCaching(){
        Transaction firstTransaction = transactionCache.cachingTransaction(transaction);
        Transaction secondTransaction = transactionCache.cachingTransaction(transaction);

        assertThat(firstTransaction, equalTo(secondTransaction));
    }

    @Test
    public void testCacheExpiration(){
        FakeTicker fakeTicker = new FakeTicker();
        LoadingCache<String, Transaction> cache = caffeine.ticker(fakeTicker).build(k -> transaction);
        cache.get("test");
        fakeTicker.advance(61, TimeUnit.SECONDS);

        assertNull(cache.getIfPresent("test"));
    }

    @Test
    public void testCacheNotExpirated(){
        FakeTicker fakeTicker = new FakeTicker();
        LoadingCache<String, Transaction> cache = caffeine.ticker(fakeTicker).build(k -> transaction);
        cache.get("test");
        fakeTicker.advance(59, TimeUnit.SECONDS);

        assertNotNull(cache.getIfPresent("test"));

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
        long value = nanos.getAndAdd(0);
        return value;
    }
}
