package com.n26.service;

import com.n26.model.Statistics;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StatisticsComputationEmptyListTest {

    private StatisticsComputation statisticsComputation;
    private ArrayList<BigDecimal> emptyList;


    @Before
    public void setData(){
        emptyList = new ArrayList<>();
        statisticsComputation = new StatisticsComputation();
    }

    @Test
    public void computeSumRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(emptyList);
        assertEquals(BigDecimal.valueOf(000,2),statistics.getSum());
    }

    @Test
    public void computeAverageRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(emptyList);
        assertEquals(BigDecimal.valueOf(000,2),statistics.getAvg());
    }

    @Test
    public void computeMaxRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(emptyList);
        assertEquals(BigDecimal.valueOf(000,2),statistics.getMax());
    }

    @Test
    public void computeMinRoundUp(){
        Statistics statistics = statisticsComputation.computeStatistics(emptyList);
        assertEquals(BigDecimal.valueOf(000,2),statistics.getMin());
    }
}
