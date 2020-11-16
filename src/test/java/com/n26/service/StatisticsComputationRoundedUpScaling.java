package com.n26.service;

import com.n26.model.Statistics;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StatisticsComputationRoundedUpScaling {

    private StatisticsComputation statisticsComputation;
    private ArrayList<BigDecimal> amounts;


    @Before
    public void setData() {
        amounts = new ArrayList<>(Arrays.asList(BigDecimal.valueOf(5.0), BigDecimal.valueOf(4.0), BigDecimal.valueOf(2.0)));
        statisticsComputation = new StatisticsComputation();
    }

    @Test
    public void computeSumRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(amounts);
        assertEquals(BigDecimal.valueOf(1100,2),statistics.getSum());
    }

    @Test
    public void computeAverageRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(amounts);
        assertEquals(BigDecimal.valueOf(367,2),statistics.getAvg());
    }
}
