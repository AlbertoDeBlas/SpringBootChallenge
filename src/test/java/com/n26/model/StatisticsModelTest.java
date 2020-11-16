package com.n26.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.controller.StatisticsController;

import com.n26.service.StatisticsComputation;
import com.n26.service.serviceImpl.TransactionCacheImpl;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
public class StatisticsModelTest {

    private Statistics statistics;
    private MockMvc mockMvc;
    private JacksonTester<Statistics> jsonTransaction;

    @InjectMocks
    StatisticsController statisticsController;

    @MockBean
    private TransactionCacheImpl transactionCache;

    @MockBean
    private StatisticsComputation statisticsComputation;

    @Before
    public void setData(){
        JacksonTester.initFields(this, new ObjectMapper());
        statistics = new Statistics(BigDecimal.valueOf(15.43),
                BigDecimal.valueOf(15.43),
                BigDecimal.valueOf(15.43),
                BigDecimal.valueOf(15.43),
                Long.valueOf(1));
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
    }

    @Test
    public void sumFormatTest() throws Exception{
        checkFieldFormatString("$.sum","15.43");
    }

    @Test
    public void avgFormatTest() throws Exception{
        checkFieldFormatString("$.avg","15.43");
    }

    @Test
    public void maxFormatTest() throws Exception{
        checkFieldFormatString("$.max","15.43");
    }

    @Test
    public void minFormatTest() throws Exception{
        checkFieldFormatString("$.min","15.43");
    }

    @Test
    public void countFormatTest() throws Exception{
        checkFieldFormatNumber("$.count",1);
    }

    private void checkFieldFormatString(String field, String value) throws Exception{
        checkFieldFormat(field,Matchers.is(value));
    }

    private void checkFieldFormatNumber(String field, Number value) throws Exception{
        checkFieldFormat(field,Matchers.is(value));
    }

    private void checkFieldFormat(String field, Matcher matcher) throws Exception {
        Mockito.when(statisticsComputation.computeStatistics(any())).thenReturn(statistics);
        mockMvc.perform(get("/statistics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTransaction
                        .write(statistics)
                        .getJson()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath(field, matcher));
    }
}
