package com.advice

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class NotParsableFieldsAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidFormatException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun NotParsableFieldsHandler(ex: InvalidFormatException): String {
        return ex.message ?: "Not parsable Fields"
    }
}