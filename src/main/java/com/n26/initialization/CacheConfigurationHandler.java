package com.n26.initialization;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.n26.model.Transaction;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

public class CacheConfigurationHandler {

    public static Caffeine<Object, Transaction> getTransactionCaffeineConfig() {
        return Caffeine.newBuilder().expireAfter(new Expiry<>() {

            @Override
            public long expireAfterCreate(@NonNull Object o, @NonNull Transaction transaction, long l) {
                return computeExpiration(transaction);
            }

            @Override
            public long expireAfterUpdate(@NonNull Object o, @NonNull Transaction transaction, long l, @NonNegative long l1) {
                return computeExpiration(transaction);
            }

            @Override
            public long expireAfterRead(@NonNull Object o, @NonNull Transaction transaction, long l, @NonNegative long l1) {
                return computeExpiration(transaction);
            }

            private long computeExpiration(@NonNull Transaction transaction) {
                long differenceBetweenCurrentAndTransaction =
                        TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) -
                                TimeUnit.MILLISECONDS.toNanos(transaction.getTimestamp().getTime());
                return TimeUnit.SECONDS.toNanos(60) - differenceBetweenCurrentAndTransaction;
            }
        });
    }
}
