package com.controller

import com.model.Statistics
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import com.service.StatisticsComputation
import com.service.TransactionCache
import org.springframework.web.bind.annotation.GetMapping


@RestController
class StatisticsController @Inject constructor(
    private val statisticsComputation: StatisticsComputation,
    private val transactionCache: TransactionCache
) {
    @get:GetMapping("/statistics")
    val statistics: Statistics
        get() {
            val amounts = transactionCache.cacheValues
            return statisticsComputation.computeStatistics(amounts)
        }
}