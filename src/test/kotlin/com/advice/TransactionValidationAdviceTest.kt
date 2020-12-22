package com.advice;

import com.controller.TransactionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initialization.CacheBuilder;
import com.initialization.CacheConfigurationHandler;
import com.model.Transaction;

import com.service.TransactionCache;
import com.service.serviceImpl.TransactionCacheImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionValidationAdviceTest {

    private MockMvc mockMvc;
    private JacksonTester<Transaction> jsonTransaction;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionCache transactionCache;

    private Transaction transaction;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setData(){
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new TransactionValidationAdvice())
                .build();
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now().plusMillis(-61000)));
    }

    @Test
    public void checkStatusCodeNoContentIsReturnedForOldTransaction() throws Exception{
        mockMvc.perform(post("/transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonTransaction
                            .write(transaction)
                            .getJson()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void checkStatusCodeUnprocessableEntityIsReturnedForFutureTransaction() throws Exception{
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now().plusMillis(+1000)));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTransaction
                        .write(transaction)
                        .getJson()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
