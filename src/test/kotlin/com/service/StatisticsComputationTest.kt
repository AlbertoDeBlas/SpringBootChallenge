package com.service

import java.math.BigDecimal
import com.service.serviceImpl.StatisticsComputationImpl
import org.junit.Assert
import org.junit.Test

class StatisticsComputationTest {
    private val statisticsComputation: StatisticsComputation = StatisticsComputationImpl()
    private val amountsNotUpRound: List<BigDecimal> = listOf(
        BigDecimal.valueOf(15.4333),
        BigDecimal.valueOf(13.2232),
        BigDecimal.valueOf(14.4564))

    @Test
    fun computeSum() {
        val sum = statisticsComputation.computeStatistics(amountsNotUpRound).sum
        Assert.assertEquals(BigDecimal.valueOf(43.11), sum)
    }

    @Test
    fun computeAverage() {
        val avg = statisticsComputation.computeStatistics(amountsNotUpRound).avg
        Assert.assertEquals(BigDecimal.valueOf(14.37), avg)
    }

    @Test
    fun computeMax() {
        val max = statisticsComputation.computeStatistics(amountsNotUpRound).max
        Assert.assertEquals(BigDecimal.valueOf(15.43), max)
    }

    @Test
    fun computeMin() {
        val min = statisticsComputation.computeStatistics(amountsNotUpRound).min
        Assert.assertEquals(BigDecimal.valueOf(13.22), min)
    }

    @Test
    fun count() {
        val count = statisticsComputation.computeStatistics(amountsNotUpRound).count
        Assert.assertEquals(3, count)
    }
}