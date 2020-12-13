package com.model

import com.model.Transaction
import org.assertj.core.api.Assertions

import org.junit.Test
import org.junit.jupiter.api.TestInstance
import java.time.Instant
import java.math.BigDecimal
import java.sql.Timestamp
import javax.validation.Validation
import javax.validation.Validator

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionModelTest {

    private val  validator: Validator

   init {
       val factory = Validation.buildDefaultValidatorFactory()
       validator = factory.validator
   }

    @Test
    fun `Transaction with future timestamp should add a violation`() {
        val instant = Instant.now().plusMillis(+10000)
        val transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(instant))
        val violations = validator.validate(transaction)
        Assertions.assertThat(violations::isNotEmpty)
    }

    @Test
    fun `Transaction with a timestamp within last 60 seconds should not add violations`() {
        val instant = Instant.now().plusMillis(-10000)
        val transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(instant))
        val violations = validator.validate(transaction)
        Assertions.assertThat(violations::isEmpty)
    }

    @Test
    fun `Transaction with a timestamp older than 60 seconds should add a violation`() {
        val instant = Instant.now().plusMillis(-70000)
        val transaction = Transaction(BigDecimal.valueOf(1234, 2), Timestamp.from(instant))
        val violations = validator.validate(transaction)
        Assertions.assertThat(violations::isNotEmpty)
    }

    @Test
    fun `Transaction with null amount should add add a violation`() {
        val instant = Instant.now()
        val transaction = Transaction(null, Timestamp.from(instant))
        val violations = validator.validate(transaction)
        Assertions.assertThat(violations::isNotEmpty)
    }

    @Test
    fun `Transaction with null timestamp should add add a violation`() {
        val transaction = Transaction(BigDecimal.valueOf(1234, 2), null)
        val violations = validator.validate(transaction)
        Assertions.assertThat(violations::isNotEmpty)
    }
}