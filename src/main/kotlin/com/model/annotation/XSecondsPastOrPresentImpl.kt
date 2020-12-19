package com.model.annotation;

import com.exception.OldTransactionException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class XSecondsPastOrPresentImpl  implements ConstraintValidator<XSecondsPastOrPresent, Timestamp> {

    private Integer seconds;

    @Override
    public void initialize(XSecondsPastOrPresent constraintAnnotation) {
        this.seconds = constraintAnnotation.seconds();
    }

    @Override
    public boolean isValid(Timestamp timestamp, ConstraintValidatorContext context) {
        Optional<Timestamp> optionalTimestamp = Optional.ofNullable(timestamp);

        long pastTime = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - TimeUnit.MILLISECONDS.toNanos(optionalTimestamp.orElse(new Timestamp(0)).getTime());
        long differenceInSeconds = TimeUnit.NANOSECONDS.toSeconds(pastTime);

        return (differenceInSeconds < seconds) ? true : false;
    }

}