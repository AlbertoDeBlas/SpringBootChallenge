package com.service.serviceImpl

import com.service.serviceImpl.TransactionCacheHandler.getAmountsList
import javax.inject.Inject
import org.springframework.cache.CacheManager
import com.service.TransactionCache
import org.springframework.cache.annotation.Cacheable
import com.model.Transaction
import mu.KotlinLogging
import java.math.BigDecimal
import org.springframework.cache.caffeine.CaffeineCache
import java.util.concurrent.ConcurrentMap
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class TransactionCacheImpl @Inject constructor(private val cacheManager: CacheManager) : TransactionCache {
    @Cacheable("TransactionCache")
    override fun cachingTransaction(transaction: Transaction): Transaction {
        return transaction
    }

    override fun getCacheValues(): List<BigDecimal> {
        val caffeineCache = cacheManager.getCache("TransactionCache") as CaffeineCache
        val cache: ConcurrentMap<Any, Any> = caffeineCache.nativeCache.asMap()

        logger.debug { "getCacheValues return: ${getAmountsList(cache)}"}
        return getAmountsList(cache)
    }

    @CacheEvict(value = ["TransactionCache"], allEntries = true)
    override fun evictAllCacheValues() {
        logger.debug { "evictAllCacheValues"}
    }
}