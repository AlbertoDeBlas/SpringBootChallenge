package com.model.annotation

import mu.KotlinLogging
import javax.validation.ConstraintValidator
import java.sql.Timestamp
import javax.validation.ConstraintValidatorContext
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class XSecondsPastOrPresentImpl : ConstraintValidator<XSecondsPastOrPresent, Timestamp?> {
    private var seconds: Int = 60

    override fun initialize(constraintAnnotation: XSecondsPastOrPresent) {
        seconds = constraintAnnotation.seconds
    }

    override fun isValid(timestamp: Timestamp?, context: ConstraintValidatorContext): Boolean {
        val pastTime = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) -
                TimeUnit.MILLISECONDS.toNanos((timestamp ?: Timestamp(0)).time)
        val differenceInSeconds = TimeUnit.NANOSECONDS.toSeconds(pastTime)

        logger.debug { "isValid return: differenceInSeconds: $differenceInSeconds seconds: $seconds" }
        return differenceInSeconds < seconds
    }
}