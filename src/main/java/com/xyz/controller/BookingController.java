package com.xyz.controller;


import com.xyz.exception.BookingException;
import com.xyz.model.BookingRequest;
import com.xyz.model.BookingResponse;
import com.xyz.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService service;

    @PostMapping("/")
    ResponseEntity<BookingResponse> bookTicket(@RequestBody BookingRequest request) throws BookingException {
        Optional<BookingResponse> book = service.bookTicket(request);
        return ResponseEntity.of(book);

    }
}
