package com.n26.controller;

import com.n26.model.Statistics;
import com.n26.service.StatisticsComputation;
import com.n26.service.TransactionCache;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;

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
        ArrayList<BigDecimal> amounts = transactionCache.getCacheValues();
        return statisticsComputation.computeStatistics(amounts);
    }
}
