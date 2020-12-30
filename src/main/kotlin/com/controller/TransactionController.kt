package com.controller

import javax.inject.Inject
import com.service.TransactionCache
import org.springframework.http.HttpStatus
import javax.validation.Valid
import com.model.Transaction
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class TransactionController @Inject constructor(private val transactionCache: TransactionCache) {
    @PostMapping("/transactions")
    @Validated
    @ResponseStatus(HttpStatus.CREATED)
    fun newTransaction(@RequestBody @Valid newTransaction:  Transaction) {
        transactionCache.cachingTransaction(newTransaction)
    }

    @DeleteMapping("/transactions")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTransactions() {
        transactionCache.evictAllCacheValues()
    }
}