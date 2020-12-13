package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.model.annotation.XSecondsPastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Data
//@AllArgsConstructor    // Empty constructor is needed for Jackson to recreate the object from JSON
//@NoArgsConstructor
public class Transaction {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "\"-?\\\\d+(\\\\.\\\\d+)?\"")
    private BigDecimal amount;

    @NotNull
    @PastOrPresent
    @XSecondsPastOrPresent(seconds=60)
    private Timestamp timestamp;

    // Empty constructor is needed for Jackson to recreate the object from JSON
    public Transaction() {
    }

    public Transaction(BigDecimal amount, Timestamp timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, timestamp);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
