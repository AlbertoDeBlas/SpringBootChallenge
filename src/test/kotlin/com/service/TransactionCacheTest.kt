package com.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.initialization.CacheConfigurationHandler.transactionCaffeineConfig
import com.model.Transaction
import com.service.serviceImpl.TransactionCacheImpl
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.Instant
import java.util.*

class TransactionCacheTest {
    @Rule
    @JvmField
    var initRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var cacheManager: CacheManager
    private lateinit var transaction: Transaction
    private lateinit var anotherTransaction: Transaction
    private lateinit var caffeine: Caffeine<Any, Any>
    private lateinit var cache: Cache<Any, Any>
    private lateinit var caffeineCache: CaffeineCache
    private lateinit var transactionCache: TransactionCache
    @Before
    fun setData() {
        transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(Instant.now()))
        anotherTransaction = Transaction(BigDecimal.valueOf(1334, 2), Timestamp.from(Instant.now()))
        caffeine = transactionCaffeineConfig
        cache = caffeine.build()
        caffeineCache = CaffeineCache("transactionCache", cache)
        transactionCache = TransactionCacheImpl(cacheManager)
    }

    @Test
    fun `same transaction cached two times has to be the same`() {
        val firstTransaction = transactionCache.cachingTransaction(transaction)
        val secondTransaction = transactionCache.cachingTransaction(transaction)
        MatcherAssert.assertThat(firstTransaction, Matchers.equalTo(secondTransaction))
    }

    @Test
    fun `two different cached transactions have to retrieve different values`() {
        val firstTransaction = transactionCache.cachingTransaction(transaction)
        val secondTransaction = transactionCache.cachingTransaction(anotherTransaction)
        MatcherAssert.assertThat(firstTransaction, Matchers.not(secondTransaction))
    }

    @Test
    fun `for one value cache has to retrieve same value as cached object`(){
            caffeineCache.put("first", transaction)
            Mockito.`when`(cacheManager.getCache(Mockito.anyString()))
                .thenReturn(caffeineCache)
            val cachedValues = transactionCache.getCacheValues()
            val expectedValues = ArrayList<BigDecimal>()
            expectedValues.add(BigDecimal.valueOf(1234, 2))
            MatcherAssert.assertThat(expectedValues, Matchers.equalTo(ArrayList(cachedValues)))
        }

    @Test
    fun `for multiple values cache has to retrieve same values as cached objects`(){
            caffeineCache.put("first", transaction)
            caffeineCache.put("second", anotherTransaction)
            Mockito.`when`(cacheManager.getCache(Mockito.anyString()))
                .thenReturn(caffeineCache)
            val cachedValues = transactionCache.getCacheValues()
            val expectedValues = ArrayList<BigDecimal>()
            expectedValues.add(BigDecimal.valueOf(1334, 2))
            expectedValues.add(BigDecimal.valueOf(1234, 2))
            MatcherAssert.assertThat(expectedValues, Matchers.equalTo(ArrayList(cachedValues)))
        }

    @Test
    fun `if there are no values cache has to retrieve an empty list`() {
            Mockito.`when`(cacheManager.getCache(Mockito.anyString()))
                .thenReturn(caffeineCache)
            val cachedValues = transactionCache.getCacheValues()
            MatcherAssert.assertThat(cachedValues, Is.`is`(Matchers.empty()))
        }
}