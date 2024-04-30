package com.xyz.service.impl;

import com.xyz.model.Movie;
import com.xyz.model.ShowSlot;
import com.xyz.model.ShowSlotRequest;
import com.xyz.model.Theater;
import com.xyz.repo.RunningSlotRepo;
import com.xyz.service.MovieService;
import com.xyz.service.RunningSlotService;
import com.xyz.service.TheatreService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class RunningSlotServiceImpl implements RunningSlotService {


    @Autowired
    RunningSlotRepo runningSlotRepo;

    @Autowired
    MovieService movieService;

    @Autowired
    TheatreService theatreService;

    @Override
    public Optional<ShowSlot> save(ShowSlot slot) {
        return Optional.of(runningSlotRepo.save(slot));
    }

    @Override
    public Optional<ShowSlot> findById(String slotId) {
        return runningSlotRepo.findById(slotId);
    }

    @Override
    public Page<ShowSlot> findByTheaterId(Long theaterId, Pageable pageable) {
        return runningSlotRepo.findByTheaterId(theaterId, pageable);
    }

    @Override
    public Page<ShowSlot> findSlotsByMovieId(Long movieId, Pageable pageable) {
        return runningSlotRepo.findByMovieId(movieId, pageable);
    }

    @Override
    public Page<ShowSlot> findSlotsByMovieAndTheatreCity(Long movieId, String cityName, Pageable p) {
        return runningSlotRepo.findByMovieIdAndTheaterCity(movieId, cityName, p);
    }

    @Override
    public Page<ShowSlot> findSlotsByTheatreCity(String cityName, Pageable p) {
        return runningSlotRepo.findByTheaterCity(cityName, p);
    }
}
