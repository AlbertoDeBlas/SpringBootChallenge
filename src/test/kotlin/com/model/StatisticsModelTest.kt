package com.model

import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.json.JacksonTester
import com.controller.StatisticsController
import com.service.serviceImpl.TransactionCacheImpl
import com.service.StatisticsComputation
import org.mockito.junit.MockitoJUnit
import org.junit.Before
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.junit.MockitoRule
import java.math.BigDecimal
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import kotlin.Throws
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.lang.Exception

class StatisticsModelTest {
    private val statistics: Statistics = Statistics(
        BigDecimal.valueOf(15.43),
        BigDecimal.valueOf(15.43),
        BigDecimal.valueOf(15.43),
        BigDecimal.valueOf(15.43),
        1L
    )

    private lateinit var jsonTransaction: JacksonTester<Statistics?>

    @InjectMocks
    lateinit var statisticsController: StatisticsController

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var transactionCache: TransactionCacheImpl

    @Mock
    private lateinit var statisticsComputation: StatisticsComputation

    @Rule
    @JvmField
    var rule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setData() {
        JacksonTester.initFields(this, ObjectMapper())
        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build()
    }

    @Test
    @Throws(Exception::class)
    fun sumFormatTest() {
        checkFieldFormatString("$.sum", "15.43")
    }

    @Test
    @Throws(Exception::class)
    fun avgFormatTest() {
        checkFieldFormatString("$.avg", "15.43")
    }

    @Test
    @Throws(Exception::class)
    fun maxFormatTest() {
        checkFieldFormatString("$.max", "15.43")
    }

    @Test
    @Throws(Exception::class)
    fun minFormatTest() {
        checkFieldFormatString("$.min", "15.43")
    }

    @Test
    @Throws(Exception::class)
    fun countFormatTest() {
        checkFieldFormatNumber("$.count", 1)
    }

    @Throws(Exception::class)
    private fun checkFieldFormatString(field: String, value: String) {
        checkFieldFormat(field, Matchers.`is`(value))
    }

    @Throws(Exception::class)
    private fun checkFieldFormatNumber(field: String, value: Number) {
        checkFieldFormat(field, Matchers.`is`(value))
    }

    @Throws(Exception::class)
    private fun checkFieldFormat(field: String, matcher: Matcher<Any>) {
        Mockito.`when`(statisticsComputation.computeStatistics(MockitoHelper.anyObject()))
            .thenReturn(statistics)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/statistics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    jsonTransaction
                        .write(statistics)
                        .json
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath(field, matcher))
    }
    object MockitoHelper {
        fun <T> anyObject(): T {
            Mockito.any<T>()
            return uninitialized()
        }
        @Suppress("UNCHECKED_CAST")
        fun <T> uninitialized(): T =  null as T
    }
}