package com.xyz.service;

import com.xyz.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MovieService {

    void addMovie(Movie movie);

    Optional<Movie> findById(Long movieId);

//    Page<Movie> findByTheaterCity(String city, Pageable pageable);

}
