package com.service.serviceImpl

import java.util.concurrent.ConcurrentMap
import java.math.BigDecimal
import com.model.Transaction
import mu.KotlinLogging
import org.checkerframework.checker.nullness.qual.NonNull

private val logger = KotlinLogging.logger {}

object TransactionCacheHandler {
    @JvmStatic
    fun getAmountsList(cache: @NonNull ConcurrentMap<@NonNull Any, @NonNull Any>): List<BigDecimal> {
        logger.debug { "getAmountsList parameters: cache: $cache"}

        return cache.values
            .asSequence()
            .map { Transaction::class.java.cast(it) }
            .map { transaction -> transaction.amount }
            .toList()
    }
}