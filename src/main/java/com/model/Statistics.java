package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistics {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal sum;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal avg;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal max;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal min;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private long count;
}
