package com.service;

import com.model.Statistics;

import java.math.BigDecimal;
import java.util.List;

public interface StatisticsComputation {
    public Statistics computeStatistics(List<BigDecimal> amounts);
}
