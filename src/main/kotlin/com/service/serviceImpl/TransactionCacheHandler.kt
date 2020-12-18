package com.service.serviceImpl

import java.util.concurrent.ConcurrentMap
import java.math.BigDecimal
import com.model.Transaction
import org.checkerframework.checker.nullness.qual.NonNull

object TransactionCacheHandler {
    @JvmStatic
    fun getAmountsList(cache: @NonNull ConcurrentMap<@NonNull Any, @NonNull Any>): List<BigDecimal> {
        return cache.values
            .asSequence()
            .map { Transaction::class.java.cast(it) }
            .map { transaction -> transaction.amount }
            .toList()
    }
}