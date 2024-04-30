package com.xyz.model;


import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@ToString
public class ShowSlotRequest {

    private String slotId;
    private Long movieId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

}
