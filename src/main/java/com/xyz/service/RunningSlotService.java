package com.xyz.service;


import com.xyz.model.ShowSlot;
import com.xyz.model.ShowSlotRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RunningSlotService {

    Optional<ShowSlot> save(ShowSlot slot);

    Optional<ShowSlot> findById(String slotId);

    Page<ShowSlot> findByTheaterId(Long movieId, Pageable pageable);

    Page<ShowSlot> findSlotsByMovieId(Long movieId, Pageable pageable);

    Page<ShowSlot> findSlotsByMovieAndTheatreCity(Long movieId, String cityName, Pageable p);

    Page<ShowSlot> findSlotsByTheatreCity(String cityName, Pageable p);

}
