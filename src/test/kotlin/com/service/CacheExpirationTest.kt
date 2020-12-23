package com.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.Ticker;
import com.initialization.CacheConfigurationHandler;
import com.model.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CacheExpirationTest {

    private Caffeine caffeine;
    private Transaction transaction;

    @Before
    public void setData(){
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now()));
        caffeine = CacheConfigurationHandler.getTransactionCaffeineConfig();
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
