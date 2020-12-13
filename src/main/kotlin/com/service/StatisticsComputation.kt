package com.service

import com.model.Statistics
import java.math.BigDecimal

interface StatisticsComputation {
    fun computeStatistics(amounts: List<BigDecimal>): Statistics
}