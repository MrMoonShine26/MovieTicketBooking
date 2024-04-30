package com.xyz.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class BookingResponse extends BookingRequest {

    private double ticketPrice;
    private double discount;
    private double totalPrice;
}
