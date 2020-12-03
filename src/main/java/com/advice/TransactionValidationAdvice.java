package com.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TransactionValidationAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            if(error.getCode().equals("XSecondsPastOrPresent")){
                return new ResponseEntity("Validation error", HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity("Validation error", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
