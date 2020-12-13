package com.service;

import com.model.Statistics;
import java.math.BigDecimal;
import java.util.List;

public interface StatisticsComputation {
    Statistics computeStatistics(List<BigDecimal> amounts);
}
