package com.controller;

import com.model.Statistics;
import com.service.StatisticsComputation;
import com.service.TransactionCache;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class StatisticsController {

    private StatisticsComputation statisticsComputation;

    private TransactionCache transactionCache;

    @Inject
    public StatisticsController(StatisticsComputation statisticsComputation,
                                TransactionCache transactionCache) {
        this.statisticsComputation = statisticsComputation;
        this.transactionCache = transactionCache;
    }

    @GetMapping("/statistics")
    Statistics getStatistics(){
        List<BigDecimal> amounts = transactionCache.getCacheValues();
        return statisticsComputation.computeStatistics(amounts);
    }
}
