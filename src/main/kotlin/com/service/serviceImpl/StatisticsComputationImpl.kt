package com.service.serviceImpl

import com.model.Statistics
import com.service.StatisticsComputation
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.stream.Collectors
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
@Service
class StatisticsComputationImpl : StatisticsComputation {
    override fun computeStatistics(amounts: List<BigDecimal>): Statistics {
        val summaryStatistics = amounts.stream()
            .collect(Collectors.summarizingDouble( BigDecimal::toDouble ))
        return mapStatistics(summaryStatistics)
    }

    private fun mapStatistics(summaryStatistics: DoubleSummaryStatistics): Statistics {
        logger.debug { "mapStatistics parameters: summaryStatistics: $summaryStatistics" }

        val sum = toScaledRoundedBigDecimal(summaryStatistics.sum)
        val avg = toScaledRoundedBigDecimal(summaryStatistics.average)
        val max = getFiniteValue(summaryStatistics.max)
        val min = getFiniteValue(summaryStatistics.min)
        val count = summaryStatistics.count

        logger.debug { "mapStatistics return: sum: $sum, avg: $avg, max: $max, min: $min, count: $count" }

        return Statistics(sum, avg, max, min, count)
    }

    private fun getFiniteValue(value: Double): BigDecimal {
        return if (value.isFinite()) toScaledRoundedBigDecimal(value) else BigDecimal.ZERO.setScale(2)
    }

    private fun toScaledRoundedBigDecimal(value: Double): BigDecimal {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP)
    }
}