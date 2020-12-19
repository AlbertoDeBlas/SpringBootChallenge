package com.service

import java.math.BigDecimal
import com.service.serviceImpl.StatisticsComputationImpl
import org.junit.Assert
import org.junit.Test

class StatisticsComputationRoundedUpTest {
    private var statisticsComputation: StatisticsComputation = StatisticsComputationImpl()
    private val amountsUpRound: List<BigDecimal> = listOf(
        BigDecimal.valueOf(15.4353),
        BigDecimal.valueOf(15.4363),
        BigDecimal.valueOf(15.4373))

    @Test
    fun computeSumRoundUp() {
        val sum = statisticsComputation.computeStatistics(amountsUpRound).sum
        Assert.assertEquals(BigDecimal.valueOf(46.31), sum)
    }

    @Test
    fun computeAverageRoundUp() {
        val avg = statisticsComputation.computeStatistics(amountsUpRound).avg
        Assert.assertEquals(BigDecimal.valueOf(15.44), avg)
    }

    @Test
    fun computeMaxRoundUp() {
        val max = statisticsComputation.computeStatistics(amountsUpRound).max
        Assert.assertEquals(BigDecimal.valueOf(15.44), max)
    }

    @Test
    fun computeMinRoundUp() {
        val min = statisticsComputation.computeStatistics(amountsUpRound).min
        Assert.assertEquals(BigDecimal.valueOf(15.44), min)
    }
}