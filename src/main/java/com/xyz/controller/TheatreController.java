package com.xyz.controller;


import com.xyz.model.ShowSlot;
import com.xyz.model.ShowSlotRequest;
import com.xyz.model.Theater;
import com.xyz.service.TheatreService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/theater")
public class TheatreController {

    Logger logger = LogManager.getLogger(TheatreController.class);
    @Autowired
    TheatreService theaterService;

    @PostMapping(value = {"", "/"})
    ResponseEntity<?> addTheatre(@RequestBody Theater theater) {
        Theater result = theaterService.addTheatre(theater);
        logger.info(theater.getName() + "  " + theater.getPinCode());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(location);
    }

    @GetMapping("/{id}")
    ResponseEntity<Theater> getTheatre(@PathVariable Long id) {
        Optional<Theater> theatre = theaterService.findById(id);
        return ResponseEntity.of(theatre);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTheater(@PathVariable Long id) {
        return theaterService.deleteTheater(id);
    }

    @PostMapping("/{theaterId}/show")
    ResponseEntity<?> addMovieDetail(@Valid @RequestBody ShowSlotRequest request,
                                     @PathVariable Long theaterId,
                                     Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body("Bad Request");
        }
        logger.info("Slot " + request);
        Optional<ShowSlot> showSlot = theaterService.addMovieSlotDetail(theaterId, request);
        return ResponseEntity.of(showSlot);
    }


    @DeleteMapping("/{theaterId}/{slotId}")
    ResponseEntity<?> deleteShowSlot(@PathVariable Long theaterId,
                                     @PathVariable String slotId) {
        return theaterService.deleteShow(theaterId, slotId);
    }

    @GetMapping("/{id}/shows")
    Page<ShowSlot> getTheatreMovies(@PathVariable Long id) {
        Pageable p = Pageable.ofSize(10);
        Page<ShowSlot> theaters = theaterService.findMoviesByTheatreId(id, p);
        return theaters;
    }

    @GetMapping("")
    Page<Theater> getTheatreCity(@RequestParam String city) {
        Pageable p = Pageable.ofSize(10);
        Page<Theater> theaters = theaterService.findTheaterByCity(city, p);
        return theaters;
    }


    @GetMapping("/movies")
    Page<ShowSlot> getMoviesByCity(@RequestParam String city) {
        Pageable p = Pageable.ofSize(10);
        Page<ShowSlot> theaters = theaterService.findMoviesByCity(city, p);
        return theaters;
    }

    @GetMapping("/all")
    ResponseEntity<Page<Theater>> getTheatres(Pageable p) {
        Page<Theater> theatre = theaterService.getAllTheatre(p);
        return ResponseEntity.ok(theatre);
    }

}
