package com.xyz.service.impl;

import com.xyz.model.Movie;
import com.xyz.model.ShowSlot;
import com.xyz.model.ShowSlotRequest;
import com.xyz.model.Theater;
import com.xyz.repo.RunningSlotRepo;
import com.xyz.repo.TheatreRepo;
import com.xyz.service.MovieService;
import com.xyz.service.TheatreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class TheatreServiceImpl implements TheatreService {

    Logger logger = LogManager.getLogger(TheatreService.class.getName());
    @Autowired
    TheatreRepo<Theater> theaterRepo;

    @Autowired
    RunningSlotRepo runningSlotRepo;
    @Autowired
    MovieService movieService;

    @Override
    public Theater addTheatre(Theater theater) {
        Theater retVal = theaterRepo.save(theater);

        return retVal;
    }

    @Override
    public ResponseEntity<Theater> deleteTheater(Long id) {
        Optional<Theater> theaterOp = theaterRepo.findById(id);
        if (theaterOp.isPresent()) {
            theaterRepo.deleteById(id);
            ResponseEntity.ok();
        } else {
            logger.warn("Theater not found " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public Optional<Theater> findById(Long id) {
        Optional<Theater> theatre = theaterRepo.findById(id);
        return theatre;
    }

    @Override
    public Page<Theater> getAllTheatre(Pageable pageable) {
        return theaterRepo.findAll(pageable);
    }


    @Override
    public void addRunningShow(Movie movie, LocalDateTime dateTime) {

    }

    @Override
    public Optional<ShowSlot> addMovieSlotDetail(Long theaterId, ShowSlotRequest request) {

        Optional<Movie> movieOp = movieService.findById(request.getMovieId());
        Optional<Theater> theatreOp = theaterRepo.findById(theaterId);

        //new Slot
        if (request.getSlotId() == null
                && movieOp.isPresent()
                && theatreOp.isPresent()) {

            ShowSlot slot = new ShowSlot();
            slot.setMovie(movieOp.get());
            slot.setTheater(theatreOp.get());
//            slot.getTheaters().add(theatreOp.get());
            slot.setDate(request.getDate());
            slot.setShowStartTime(request.getStartTime());
            slot.setShowEndTime(request.getEndTime());

            Theater theater = theatreOp.get();
            theater.getRunningShows().add(slot);

            runningSlotRepo.save(slot);
            addRunningShow(theater, slot);

            return Optional.of(slot);
        }

        return Optional.empty();
    }

    @Override
    public ResponseEntity<?> deleteShow(Long theaterId, String slotId) {
        Optional<ShowSlot> showSlotOp = runningSlotRepo.findById(slotId);
        if (showSlotOp.isPresent()) {
            if (Objects.equals(theaterId, showSlotOp.get().getTheater().getId())) {
                runningSlotRepo.delete(showSlotOp.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                //TODO:: Handle with proper message and code.
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @Override
    public void addRunningShow(Theater theater, ShowSlot runningSlot) {

        theater.getRunningShows().add(runningSlot);
        theaterRepo.save(theater);
    }

    @Override
    public Page<ShowSlot> findMoviesByTheatreId(Long id, Pageable pageable) {

        return runningSlotRepo.findByTheaterId(id, pageable);
    }

    @Override
    public Optional<Movie> findMovieByTheatre(Long theaterId, String movieName) {

        return Optional.empty();
    }

    @Override
    public Page<Theater> findTheaterByCity(String city, Pageable p) {
        Page<Theater> page = theaterRepo.findRunningShowsByCity(city, p);

        page.get().forEach(theater -> Hibernate.initialize(theater.getRunningShows()));
        return page;
    }

    @Override
    public Page<ShowSlot> findMoviesByCity(String city, Pageable p) {

        return runningSlotRepo.findByTheaterCity(city, p);
    }
}
