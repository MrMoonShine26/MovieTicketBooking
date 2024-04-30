package com.xyz.service;

import com.xyz.model.SearchDTO;
import com.xyz.model.SearchShoSlotDTO;
import com.xyz.repo.MovieSearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchService {

    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("name,language");
    @Autowired
    MovieSearchRepo searchRepo;

    public List<SearchDTO> searchMovie(String name, String lang, int limit) {

        return searchRepo.searchBy(name, lang, limit, SEARCHABLE_FIELDS.toArray(new String[0]));
    }

    public List<SearchShoSlotDTO> searchMovieN(String name, String lang, int limit) {

        return searchRepo.searchByN(name, lang, limit, SEARCHABLE_FIELDS.toArray(new String[0]));
    }

    public List<SearchShoSlotDTO> searchMovieNameDate(String name, LocalDate date, int limit) {

        return searchRepo.searchByNameDate(name, date, limit, SEARCHABLE_FIELDS.toArray(new String[0]));
    }

}
