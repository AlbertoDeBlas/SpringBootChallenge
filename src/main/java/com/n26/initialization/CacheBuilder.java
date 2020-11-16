package com.n26.initialization;

import com.github.benmanes.caffeine.cache.Caffeine;

import com.n26.model.Transaction;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheBuilder {

    @Bean
    public Caffeine<Object, Transaction> caffeineConfig() {
        return CacheConfigurationHandler.getTransactionCaffeineConfig();
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("TransactionCache");
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
