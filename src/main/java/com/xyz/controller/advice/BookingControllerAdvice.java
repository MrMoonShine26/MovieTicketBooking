package com.xyz.controller.advice;

import com.xyz.exception.BookingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class BookingControllerAdvice extends ResponseEntityExceptionHandler {

    //TODO: Add Status codes and message for events.
    @ExceptionHandler({BookingException.class})
    public ResponseEntity<String> handleBookingException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<String>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN); //Status should be base on Exception code
    }
}
