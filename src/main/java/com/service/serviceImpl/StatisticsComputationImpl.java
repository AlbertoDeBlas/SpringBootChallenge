package com.service.serviceImpl;

import com.model.Statistics;
import com.service.StatisticsComputation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsComputationImpl implements StatisticsComputation {

    public Statistics computeStatistics(List<BigDecimal> amounts){
        DoubleSummaryStatistics summaryStatistics =
                amounts.stream()
                       .collect(Collectors.summarizingDouble(BigDecimal::doubleValue));

        return mapStatistics(summaryStatistics);
    }

    private Statistics mapStatistics(DoubleSummaryStatistics summaryStatistics) {
        BigDecimal sum = toScaledRoundedBigDecimal(summaryStatistics.getSum());
        BigDecimal avg = toScaledRoundedBigDecimal(summaryStatistics.getAverage());
        BigDecimal max = getFiniteValue(summaryStatistics.getMax());
        BigDecimal min = getFiniteValue(summaryStatistics.getMin());
        long count = summaryStatistics.getCount();

        return new Statistics(sum, avg, max, min, count);
    }

    private BigDecimal getFiniteValue(double value) {
        return Double.isFinite(value) ? toScaledRoundedBigDecimal(value) : BigDecimal.ZERO.setScale(2);
    }

    private BigDecimal toScaledRoundedBigDecimal(Double value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

}
