package com.n26.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.controller.TransactionController;
import com.n26.exception.OldTransactionException;
import com.n26.model.Transaction;
import com.n26.service.TransactionCache;
import com.n26.validation.TransactionValidationService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OldTransactionAdviceTest {

    private MockMvc mockMvc;
    private JacksonTester<Transaction> jsonTransaction;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;

    @Mock
    private TransactionValidationService transactionValidationService;

    @Mock
    private TransactionCache transactionCache;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setData(){
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new OldTransactionAdvice())
                .build();
        transaction = new Transaction(BigDecimal.valueOf(1234,2), Timestamp.from(Instant.now().plusMillis(-61000)));
    }

    @Test
    public void checkOldTransactionExceptionIsCaughtAndStatusCodeNoContentIsReturned() throws Exception{
        doThrow(new OldTransactionException())
                .when(transactionValidationService)
                .validateTransactionAge(any());

        mockMvc.perform(post("/transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonTransaction
                            .write(transaction)
                            .getJson()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


}
