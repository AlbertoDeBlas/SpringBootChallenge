package com.service

import com.model.Transaction
import java.math.BigDecimal

interface TransactionCache {
    fun getCacheValues(): List<BigDecimal>
    fun cachingTransaction(transaction: Transaction): Transaction
    fun evictAllCacheValues()
}