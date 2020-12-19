package com.service

import java.math.BigDecimal
import com.service.serviceImpl.StatisticsComputationImpl
import org.junit.Assert
import org.junit.Test


class StatisticsComputationEmptyListTest {
    private var statisticsComputation: StatisticsComputation = StatisticsComputationImpl()
    private var emptyList: List<BigDecimal> = emptyList()

    @Test
    fun computeSumRoundUp() {
        val sum = statisticsComputation.computeStatistics(emptyList).sum
        Assert.assertEquals(BigDecimal.valueOf(0, 2), sum)
    }

    @Test
    fun computeAverageRoundUp() {
        val avg = statisticsComputation.computeStatistics(emptyList).avg
        Assert.assertEquals(BigDecimal.valueOf(0, 2), avg)
    }

    @Test
    fun computeMaxRoundUp() {
        val max = statisticsComputation.computeStatistics(emptyList).max
        Assert.assertEquals(BigDecimal.valueOf(0, 2), max)
    }

    @Test
    fun computeMinRoundUp() {
        val min = statisticsComputation.computeStatistics(emptyList).min
        Assert.assertEquals(BigDecimal.valueOf(0, 2), min)
    }
}