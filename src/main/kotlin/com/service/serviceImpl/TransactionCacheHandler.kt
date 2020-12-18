package com.service.serviceImpl

import java.util.concurrent.ConcurrentMap
import java.math.BigDecimal
import com.model.Transaction
import java.util.stream.Collectors

object TransactionCacheHandler {
    @JvmStatic
    fun getAmountsList(cache: ConcurrentMap<Any?, Any?>): List<BigDecimal> {
        return cache.values
            .asSequence()
            .map { Transaction::class.java.cast(it) }
            .map { transaction -> transaction.amount }
            .toList()
    }
}