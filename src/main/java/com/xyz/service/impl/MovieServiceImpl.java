package com.xyz.service.impl;

import com.xyz.model.Movie;
import com.xyz.repo.MovieRepo;
import com.xyz.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepo repo;

    @Override
    public void addMovie(Movie movie) {

        repo.save(movie);
    }

    @Override
    public Optional<Movie> findById(Long movieId) {
        return repo.findById(movieId);
    }

}
