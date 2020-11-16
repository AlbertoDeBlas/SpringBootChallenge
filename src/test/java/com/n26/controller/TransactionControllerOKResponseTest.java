package com.n26.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.advice.OldTransactionAdvice;
import com.n26.model.Transaction;
import com.n26.service.TransactionCache;
import com.n26.validation.TransactionValidationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class TransactionControllerOKResponseTest {

    private MockMvc mockMvc;
    private JacksonTester<Transaction> jsonTransaction;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;

    @MockBean
    private TransactionValidationService transactionValidationService;

    @MockBean
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
