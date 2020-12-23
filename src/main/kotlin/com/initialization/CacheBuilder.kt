package com.initialization

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
open class CacheBuilder {



    @Bean
    open fun caffeineConfig(): Caffeine<Any, Any> {
        return CacheConfigurationHandler.transactionCaffeineConfig

    }

    @Bean
    open fun cacheManager(caffeine: Caffeine<Any, Any>): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager("TransactionCache")
        caffeineCacheManager.setCaffeine(caffeine)
        return caffeineCacheManager
    }
}