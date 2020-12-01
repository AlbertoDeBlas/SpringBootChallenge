package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor    // Empty constructor is needed for Jackson to recreate the object from JSON
@NoArgsConstructor
public class Transaction {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "\"-?\\\\d+(\\\\.\\\\d+)?\"")
    private BigDecimal amount;

    @NotNull
    @PastOrPresent
    private Timestamp timestamp;
}
