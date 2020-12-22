package com.controller

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

class TransactionControllerOKResponseTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var jsonTransaction: JacksonTester<Transaction?>

    @InjectMocks
    lateinit var transactionController: TransactionController
    private lateinit var transaction: Transaction

    @Mock
    lateinit var transactionCache: TransactionCache

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()
    @Before
    fun setData() {
        JacksonTester.initFields(this, ObjectMapper())
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
            .setControllerAdvice(TransactionValidationAdvice())
            .build()
        transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(Instant.now().plusMillis(-21000)))
    }

    @Test
    @Throws(Exception::class)
    fun checkNotExceptionStatusCodeCreatedIsReturnedForPost() {
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
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    @Throws(Exception::class)
    fun checkNotExceptionStatusCodeNoContentIsReturnedForDelete() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/transactions")
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
}