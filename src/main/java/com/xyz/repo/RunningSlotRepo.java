package com.xyz.repo;

import com.xyz.model.ShowSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RunningSlotRepo extends CrudRepository<ShowSlot, String> {

    Page<ShowSlot> findByMovieId(Long movieId, Pageable pageable);

    Page<ShowSlot> findByTheaterId(Long theaterId, Pageable pageable);

    Page<ShowSlot> findByMovieIdAndTheaterCity(Long movieId, String city, Pageable pageable);

    Page<ShowSlot> findByTheaterCity(String city, Pageable pageable);

}
