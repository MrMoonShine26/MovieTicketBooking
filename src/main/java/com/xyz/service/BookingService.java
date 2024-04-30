package com.xyz.service;

import com.xyz.exception.BookingException;
import com.xyz.model.BookingRequest;
import com.xyz.model.BookingResponse;

import java.util.Optional;

public interface BookingService {

    Optional<BookingResponse> bookTicket(BookingRequest bookingRequest) throws BookingException;
}
