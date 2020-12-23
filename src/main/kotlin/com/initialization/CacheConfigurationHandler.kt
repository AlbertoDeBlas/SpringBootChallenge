package com.initialization

import com.github.benmanes.caffeine.cache.Caffeine
import com.model.Transaction
import com.github.benmanes.caffeine.cache.Expiry
import org.checkerframework.checker.index.qual.NonNegative
import java.util.concurrent.TimeUnit

object CacheConfigurationHandler {
    @JvmStatic
    val transactionCaffeineConfig: Caffeine<Any, Any>
        get() = Caffeine.newBuilder().expireAfter(object : Expiry<Any, Any> {
            override fun expireAfterCreate(o: Any, transaction: Any, l: Long): Long {
                return computeExpiration(transaction)
            }

            override fun expireAfterUpdate(o: Any, transaction: Any, l: Long, l1: @NonNegative Long): Long {
                return computeExpiration(transaction)
            }

            override fun expireAfterRead(o: Any, transaction: Any, l: Long, l1: @NonNegative Long): Long {
                return computeExpiration(transaction)
            }

            private fun computeExpiration(transaction: Any): Long {
                transaction as Transaction
                val differenceBetweenCurrentAndTransaction = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) -
                        TimeUnit.MILLISECONDS.toNanos(transaction.timestamp.time)
                return TimeUnit.SECONDS.toNanos(60) - differenceBetweenCurrentAndTransaction
            }
        })
}