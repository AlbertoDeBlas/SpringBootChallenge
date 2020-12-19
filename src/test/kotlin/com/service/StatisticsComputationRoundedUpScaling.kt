package com.service

import java.math.BigDecimal
import com.service.serviceImpl.StatisticsComputationImpl
import org.junit.Assert
import org.junit.Test

class StatisticsComputationRoundedUpScaling {
    private var statisticsComputation: StatisticsComputation = StatisticsComputationImpl()
    private var amounts: List<BigDecimal> = listOf(
        BigDecimal.valueOf(5.0),
        BigDecimal.valueOf(4.0),
        BigDecimal.valueOf(2.0))

    @Test
    fun computeSumRoundUp() {
        val sum = statisticsComputation.computeStatistics(amounts).sum
        Assert.assertEquals(BigDecimal.valueOf(1100, 2), sum)
    }

    @Test
    fun computeAverageRoundUp() {
        val avg = statisticsComputation.computeStatistics(amounts).avg
        Assert.assertEquals(BigDecimal.valueOf(367, 2), avg)
    }
}