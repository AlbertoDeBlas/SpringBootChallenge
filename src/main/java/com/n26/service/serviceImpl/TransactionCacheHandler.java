package com.n26.service.serviceImpl;

import com.n26.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class TransactionCacheHandler {

    public static ArrayList<BigDecimal> getAmountsArrayList(ConcurrentMap<Object, Object> cache) {
        ArrayList<BigDecimal> amounts = new ArrayList<>();
        for(Map.Entry<Object,Object> entry: cache.entrySet()){
            Transaction t = (Transaction)entry.getValue();
            amounts.add(t.getAmount());
        }
        return amounts;
    }
}
