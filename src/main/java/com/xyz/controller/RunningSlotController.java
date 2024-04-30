package com.xyz.controller;


import com.xyz.model.ShowSlot;
import com.xyz.service.RunningSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/slot")
public class RunningSlotController {

    @Autowired
    RunningSlotService service;

    @GetMapping("/city")
    Page<ShowSlot> getRunningSlotsInCity(@RequestParam Long movieId,
                                         @RequestParam(required = false) String cityName) {
        Pageable pageable = Pageable.ofSize(20);
        if (cityName != null) {
            return service.findSlotsByMovieAndTheatreCity(movieId, cityName, pageable);
        } else {
            return service.findSlotsByMovieId(movieId, pageable);
        }
    }

}
