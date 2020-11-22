package com.n26.service;

import com.n26.model.Statistics;

import java.math.BigDecimal;
import java.util.List;

public interface StatisticsComputation {
    public Statistics computeStatistics(List<BigDecimal> amounts);
}
