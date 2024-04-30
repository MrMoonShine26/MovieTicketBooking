package com.xyz.controller;


import com.xyz.model.Movie;
import com.xyz.repo.MovieRepo;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
//@RequestMapping(value = "/v1/organizations")
public class MovieController {

    Logger logger = LogManager.getLogger(MovieController.class);
    @Autowired
    MovieRepo movieRepo;

    @GetMapping("/")
    String hello() {

        return "Hello";
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovies(@PathVariable Long id) {
        Optional<Movie> optionalMovie = movieRepo.findById(id);
        return ResponseEntity.of(optionalMovie);
    }


    @PostMapping("/movie")
    public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie,
                                      Errors errors) {
        logger.info("Adding move " + movie.toString());

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body("Bad Request");
        }

        Movie result = movieRepo.save(movie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        logger.info("URI returning " + location);
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<Movie> delete(@PathVariable Long id) {
        movieRepo.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/movie")
    public ResponseEntity<Movie> delete(@RequestBody Movie movie) {
        movieRepo.delete(movie);

        return ResponseEntity.noContent().build();
    }


}
