package com.xyz.repo;


import com.xyz.model.Movie;
import com.xyz.model.SearchDTO;
import com.xyz.model.SearchShoSlotDTO;
import com.xyz.model.ShowSlot;
import jakarta.persistence.EntityManager;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class MySearchRepositoryImpl<T, ID extends Serializable, Y> extends SimpleJpaRepository<T, ID>
        implements MySearchRepository<T, ID, Y> {

    private final EntityManager entityManager;

    private static final String FIELD_NAME = "name";
    private final String[] fieldsToSearchByName = {FIELD_NAME};

    public MySearchRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    public MySearchRepositoryImpl(
            JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Y> searchBy(String text, String lang, int limit, String... fields) {

//        SearchResult<Y> result = getSearchResult(text, lang, limit, fields);
        SearchResult<Y> result = getSearchResult(text, lang, limit, fields);
        return result.hits();

    }

    @Override
    public List<SearchShoSlotDTO> searchByN(String text, String lang, int limit, String... fields) {

//        SearchResult<Y> result = getSearchResult(text, lang, limit, fields);
        List<SearchShoSlotDTO> result = getSearchResultSlot(text, lang, limit, fields);
        return result;
    }

    private SearchResult<Y> getSearchResult(String name, String lang, int limit, String[] fields) {
        SearchSession searchSession = Search.session(entityManager);
        QueryParser parser = new QueryParser("name", new StandardAnalyzer());
//        Query query = parser.parse("name:" + name + " AND language:" + lang);
        Class<?> T = SearchDTO.class;

        SearchResult<?> result =
                searchSession
                        .search(Movie.class)
                        .select(T)
                        .where(f -> f.bool()
                                .must(f2 -> f2.match().field("name").matching(name).fuzzy(1))
                                .must(f2 -> f2.match().field("language").matching(lang).fuzzy(2))
                        )
                        .fetch(limit);

        return (SearchResult<Y>) result;
    }

    @Override
    public List<SearchShoSlotDTO> searchByNameDate(String text, LocalDate date, int limit, String... fields) {
        return getSearchRunningMovieDate(text, date, limit, fields);
    }

    private List<SearchShoSlotDTO> getSearchResultSlot(String name, String lang, int limit, String[] fields) {
        SearchSession searchSession = Search.session(entityManager);

        return searchSession
                .search(ShowSlot.class)
                .where(f -> f.bool()
                        .must(f2 -> f2.match().field("movie.name").matching(name).fuzzy(2))
                        .must(f2 -> f2.match().field("movie.language").matching(lang).fuzzy(2))
                )
                .fetchHits(limit)
                .stream()
                .map(showSlot -> new SearchShoSlotDTO(showSlot.getId(),
                        showSlot.getMovie().getName(),
                        showSlot.getMovie().getLanguage(),
                        showSlot.getTheater().getCity(),
                        showSlot.getTheater().getName(),
                        showSlot.getDate(),
                        showSlot.getShowStartTime())
                )
                .collect(Collectors.toList());
    }

    private List<SearchShoSlotDTO> getSearchRunningMovieDate(String name, LocalDate date, int limit, String[] fields) {
        SearchSession searchSession = Search.session(entityManager);

        return searchSession
                .search(ShowSlot.class)
                .where(f -> f.bool()
                        .must(f2 -> f2.match().field("movie.name").matching(name).fuzzy(2))
                        .must(f2 -> f2.match().field("date").matching(date))
                )
                .fetchHits(limit)
                .stream()
                .map(showSlot -> new SearchShoSlotDTO(showSlot.getId(),
                                showSlot.getMovie().getName(),
                                showSlot.getMovie().getLanguage(),
                                showSlot.getTheater().getCity(),
                                showSlot.getTheater().getName(),
                                showSlot.getDate(),
                                showSlot.getShowStartTime()
                        )
                )
                .collect(Collectors.toList());
    }

}