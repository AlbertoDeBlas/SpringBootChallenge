package com.n26.service.serviceImpl;

import com.n26.model.Statistics;
import com.n26.service.StatisticsComputation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

@Service
public class StatisticsComputationImpl implements StatisticsComputation {

    public Statistics computeStatistics(ArrayList<BigDecimal> amounts){
        BigDecimal sum = computeSum(amounts).setScale(2, RoundingMode.HALF_UP);
        BigDecimal avg = computeAverage(amounts).setScale(2, RoundingMode.HALF_UP);
        BigDecimal max = computeMax(amounts).setScale(2, RoundingMode.HALF_UP);
        BigDecimal min = computeMin(amounts).setScale(2, RoundingMode.HALF_UP);
        long count = count(amounts);
        return new Statistics(sum, avg, max, min, count);
    }

    private Statistics getZeroStatistics() {
        return new Statistics(BigDecimal.valueOf(0, 0),
                BigDecimal.valueOf(0, 0),
                BigDecimal.valueOf(0, 0),
                BigDecimal.valueOf(0, 0),
                0);
    }

    private BigDecimal computeSum(ArrayList<BigDecimal> amounts){
        return amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal computeAverage(ArrayList<BigDecimal> amounts){
        Double average = amounts.stream().mapToDouble(BigDecimal::doubleValue).average().orElse(0.0);

        return BigDecimal.valueOf(average);
    }

    private BigDecimal computeMax(ArrayList<BigDecimal> amounts){
        Optional<BigDecimal> max = amounts.stream().map(a->a).max(Comparator.naturalOrder());

        return max.orElse(BigDecimal.valueOf(0));
    }

    private BigDecimal computeMin(ArrayList<BigDecimal> amounts){
        Optional<BigDecimal> min = amounts.stream().map(a->a).min(Comparator.naturalOrder());

        return min.orElse(BigDecimal.valueOf(0));
    }

    private long count(ArrayList<BigDecimal> amounts){
        return amounts.size();
    }
}
