package com.n26.service;

import com.n26.model.Statistics;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StatisticsComputationTest {

    private StatisticsComputation statisticsComputation;
    private ArrayList<BigDecimal> amountsNotUpRound;

    @Before
    public void setData(){
        amountsNotUpRound = new ArrayList<>(Arrays.asList(BigDecimal.valueOf(15.4333),BigDecimal.valueOf(13.2232),BigDecimal.valueOf(14.4564)));
        statisticsComputation = new StatisticsComputation();
    }

    @Test
    public void computeSumTest(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsNotUpRound);
        assertEquals(BigDecimal.valueOf(43.11),statistics.getSum());
    }

    @Test
    public void computeAverageTest(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsNotUpRound);
        assertEquals(BigDecimal.valueOf(14.37),statistics.getAvg());
    }

    @Test
    public void computeMaxTest(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsNotUpRound);
        assertEquals(BigDecimal.valueOf(15.43),statistics.getMax());
    }

    @Test
    public void computeMinTest(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsNotUpRound);
        assertEquals(BigDecimal.valueOf(13.22),statistics.getMin());
    }

    @Test
    public void countTest(){
        Statistics statistics = statisticsComputation.computeStatistics(amountsNotUpRound);
        assertEquals(3,statistics.getCount());
    }
}
