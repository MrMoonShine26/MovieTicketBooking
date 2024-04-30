package com.xyz.repo;

import com.xyz.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepo extends CrudRepository<Movie, Long> {
//    Page<Movie> findByTheaterCity(String city, Pageable pageable);
}
