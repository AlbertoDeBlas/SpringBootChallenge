package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Transaction;
import com.advice.TransactionValidationAdvice;
import com.service.TransactionCache;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerOKResponseTest {

    private MockMvc mockMvc;
    private JacksonTester<Transaction> jsonTransaction;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;

    @Mock
    private TransactionCache transactionCache;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setData(){
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new TransactionValidationAdvice())
                .build();
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now().plusMillis(-21000)));
    }

    @Test
    public void checkNotExceptionStatusCodeCreatedIsReturnedForPost() throws Exception{
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTransaction
                        .write(transaction)
                        .getJson()))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void checkNotExceptionStatusCodeNoContentIsReturnedForDelete() throws Exception{
        mockMvc.perform(delete("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTransaction
                        .write(transaction)
                        .getJson()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
