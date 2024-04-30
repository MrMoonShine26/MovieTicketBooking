package com.xyz.repo;

import com.xyz.model.ShowSlot;
import com.xyz.model.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TheatreRepo<T extends Theater> extends JpaRepository<T, Long> {
    Page<T> findAll(Pageable pageable);

    Page<T> findById(Long id, Pageable pageable);

    Page<ShowSlot> findRunningShowsById(Long id, Pageable pageable);

    Page<Theater> findRunningShowsByCity(String city, Pageable p);

//    Optional<Movie> findByNameAndMovieStartTime(String name, String movieStartTime);

//    Optional<Movie> findByTheaterIdandMovieName(Long theaterId, String name);
}
