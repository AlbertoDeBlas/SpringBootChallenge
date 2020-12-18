package com.service.serviceImpl;

import com.model.Transaction;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class TransactionCacheHandler {

    public static List<BigDecimal> getAmountsList(ConcurrentMap<Object, Object> cache) {
        List<BigDecimal> amounts = cache.values()
                                        .stream()
                                        .map(Transaction.class::cast)
                                        .map(Transaction::getAmount)
                                        .collect(Collectors.toList());

        return amounts;
    }
}
