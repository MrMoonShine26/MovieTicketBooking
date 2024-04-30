package com.xyz.service;


import com.xyz.model.Movie;
import com.xyz.model.ShowSlot;
import com.xyz.model.ShowSlotRequest;
import com.xyz.model.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public interface TheatreService {


    Theater addTheatre(Theater theater);

    Page<Theater> getAllTheatre(Pageable pageable);

    void addRunningShow(Movie movie, LocalDateTime dateTime);

    Optional<Theater> findById(Long id);

    void addRunningShow(Theater theaterId, ShowSlot runningSlot);

    Page<ShowSlot> findMoviesByTheatreId(Long id, Pageable pageable);

    Optional<Movie> findMovieByTheatre(Long theaterId, String movieName);


    Optional<ShowSlot> addMovieSlotDetail(Long theaterId, ShowSlotRequest request);

    Page<Theater> findTheaterByCity(String city, Pageable p);

    Page<ShowSlot> findMoviesByCity(String city, Pageable p);

    ResponseEntity<Theater> deleteTheater(Long id);

    ResponseEntity<?> deleteShow(Long theaterId, String slotId);
}
