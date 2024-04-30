package com.xyz.repo;

import com.xyz.model.SearchShoSlotDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface MySearchRepository<T, ID, Y> extends JpaRepository<T, ID> {

//    SearchResult<Movie> searchProjectionByName(String text, int pageNumber, int limit);

//    SearchResult<SearchDTO> searchByName(String text, int pageNumber, int limit);

//    SearchResult<SearchSuggestionDTO> searchSuggestionByName(String text);

    List<Y> searchBy(String text, String l, int limit, String... fields);

    List<SearchShoSlotDTO> searchByN(String text, String l, int limit, String... fields);

    List<SearchShoSlotDTO> searchByNameDate(String text, LocalDate date, int limit, String... fields);
}
