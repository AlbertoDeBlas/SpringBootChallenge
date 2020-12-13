package com.model

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Builder
import lombok.Data
import java.math.BigDecimal

@Data
data class Statistics(
    @get:JsonFormat(shape = JsonFormat.Shape.STRING) val sum: BigDecimal,
    @get:JsonFormat(shape = JsonFormat.Shape.STRING) val avg: BigDecimal,
    @get:JsonFormat(shape = JsonFormat.Shape.STRING) val max:BigDecimal,
    @get:JsonFormat(shape = JsonFormat.Shape.STRING) val min:BigDecimal,
    @get:JsonFormat(shape = JsonFormat.Shape.NUMBER_INT) val count:Long
)