package com.advice;

import com.exception.OldTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OldTransactionAdvice {
    @ResponseBody
    @ExceptionHandler(OldTransactionException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String OldTransactionHandler(OldTransactionException ex){
        return ex.getMessage();
    }
}
