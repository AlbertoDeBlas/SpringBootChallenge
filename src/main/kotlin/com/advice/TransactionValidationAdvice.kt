package com.advice

import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.http.ResponseEntity

@ControllerAdvice
class TransactionValidationAdvice : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        for (error in ex.bindingResult.allErrors) {
            if (error.code == "XSecondsPastOrPresent") {
                return ResponseEntity("Validation error", HttpStatus.NO_CONTENT)
            }
        }
        return ResponseEntity("Validation error", HttpStatus.UNPROCESSABLE_ENTITY)
    }
}