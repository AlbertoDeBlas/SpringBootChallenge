package com.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import com.model.annotation.XSecondsPastOrPresent
import javax.validation.constraints.PastOrPresent
import java.sql.Timestamp
import javax.validation.constraints.NotNull


data class Transaction(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "\"-?\\\\d+(\\\\.\\\\d+)?\"")
    @JsonProperty("amount")
    @field:NotNull
    val amount:  BigDecimal,

    @JsonProperty("timestamp")
    @field:XSecondsPastOrPresent(seconds = 60)
    @field:NotNull
    @field:PastOrPresent
    val timestamp: Timestamp
)