package com.model.annotation

import javax.validation.ConstraintValidator
import com.model.annotation.XSecondsPastOrPresent
import java.sql.Timestamp
import java.util.*
import javax.validation.ConstraintValidatorContext
import java.util.concurrent.TimeUnit

class XSecondsPastOrPresentImpl : ConstraintValidator<XSecondsPastOrPresent, Timestamp?> {
    private var seconds: Int = 60

    override fun initialize(constraintAnnotation: XSecondsPastOrPresent) {
        seconds = constraintAnnotation.seconds
    }

    override fun isValid(timestamp: Timestamp?, context: ConstraintValidatorContext): Boolean {
        val pastTime = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) -
                TimeUnit.MILLISECONDS.toNanos((timestamp ?: Timestamp(0)).time)
        val differenceInSeconds = TimeUnit.NANOSECONDS.toSeconds(pastTime)

        return differenceInSeconds < seconds
    }
}