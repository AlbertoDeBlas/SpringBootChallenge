package com.advice

import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.json.JacksonTester
import com.model.Transaction
import org.mockito.InjectMocks
import com.controller.TransactionController
import org.mockito.Mock
import com.service.TransactionCache
import org.mockito.junit.MockitoRule
import org.mockito.junit.MockitoJUnit
import org.junit.Before
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.advice.TransactionValidationAdvice
import org.junit.Rule
import org.junit.Test
import org.springframework.http.MediaType
import java.math.BigDecimal
import java.time.Instant
import kotlin.Throws
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.lang.Exception
import java.sql.Timestamp

class TransactionValidationAdviceTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var jsonTransaction: JacksonTester<Transaction?>

    @InjectMocks
    private lateinit var transactionController: TransactionController

    @Mock
    private lateinit var transactionCache: TransactionCache
    private lateinit var transaction: Transaction

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()
    @Before
    fun setData() {
        JacksonTester.initFields(this, ObjectMapper())
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
            .setControllerAdvice(TransactionValidationAdvice())
            .build()
        transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(Instant.now().plusMillis(-61000)))
    }

    @Test
    @Throws(Exception::class)
    fun checkStatusCodeNoContentIsReturnedForOldTransaction() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    jsonTransaction
                        .write(transaction)
                        .json
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    @Throws(Exception::class)
    fun checkStatusCodeUnprocessableEntityIsReturnedForFutureTransaction() {
        transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(Instant.now().plusMillis(+1000)))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    jsonTransaction
                        .write(transaction)
                        .json
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
    }
}