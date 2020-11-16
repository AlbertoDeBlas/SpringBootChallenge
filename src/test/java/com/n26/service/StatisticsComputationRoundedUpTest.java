package com.n26.service;

import com.n26.model.Statistics;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StatisticsComputationRoundedUpTest {

    private StatisticsComputation statisticsComputation;
    private ArrayList<BigDecimal> amountsUpRound;

    @Before
    public void setData(){
        amountsUpRound = new ArrayList<>(Arrays.asList(BigDecimal.valueOf(15.4353),BigDecimal.valueOf(15.4363),BigDecimal.valueOf(15.4373)));
        statisticsComputation = new StatisticsComputation();
    }

    @Test
    public void computeSumRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsUpRound);
        assertEquals(BigDecimal.valueOf(46.31),statistics.getSum());
    }

    @Test
    public void computeAverageRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsUpRound);
        assertEquals(BigDecimal.valueOf(15.44),statistics.getAvg());
    }

    @Test
    public void computeMaxRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsUpRound);
        assertEquals(BigDecimal.valueOf(15.44),statistics.getMax());
    }

    @Test
    public void computeMinRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsUpRound);
        assertEquals(BigDecimal.valueOf(15.44),statistics.getMin());
    }
}
