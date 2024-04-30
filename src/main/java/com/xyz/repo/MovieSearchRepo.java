package com.xyz.repo;

import com.xyz.model.Movie;
import com.xyz.model.SearchDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSearchRepo extends MySearchRepository<Movie, String, SearchDTO> {
}
