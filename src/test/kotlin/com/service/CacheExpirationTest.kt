package com.service

import com.github.benmanes.caffeine.cache.CacheLoader
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.github.benmanes.caffeine.cache.Ticker
import com.initialization.CacheConfigurationHandler.transactionCaffeineConfig
import com.model.Transaction
import org.junit.Before
import java.math.BigDecimal
import java.time.Instant
import com.initialization.CacheConfigurationHandler
import org.junit.Assert
import org.junit.Test
import java.sql.Timestamp
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

class CacheExpirationTest {
    private lateinit var caffeine: Caffeine<Any, Any>
    private lateinit var transaction: Transaction
    @Before
    fun setData() {
        transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(Instant.now()))
        caffeine = transactionCaffeineConfig
    }

    @Test
    fun `after 61 seconds cached value has expired and cache returns nothing`() {
        val fakeTicker = FakeTicker()
        val cache: LoadingCache<String, Transaction> = caffeine.ticker(fakeTicker).build { transaction }
        cache["test"]
        fakeTicker.advance(61, TimeUnit.SECONDS)
        Assert.assertNull(cache.getIfPresent("test"))
    }

    @Test
    fun `after 59 seconds cached value is still in cache and is returned`() {
        val fakeTicker = FakeTicker()
        val cache: LoadingCache<String, Transaction> = caffeine.ticker(fakeTicker).build { transaction }
        cache["test"]
        fakeTicker.advance(59, TimeUnit.SECONDS)
        Assert.assertNotNull(cache.getIfPresent("test"))
    }
}

internal class FakeTicker : Ticker {
    private val nanos = AtomicLong()
    fun advance(time: Long, timeUnit: TimeUnit): FakeTicker {
        nanos.addAndGet(timeUnit.toNanos(time))
        return this
    }

    override fun read(): Long {
        return nanos.getAndAdd(0)
    }
}