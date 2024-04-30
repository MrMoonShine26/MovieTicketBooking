package com.xyz.controller;

import com.xyz.lucene.LuceneSearchIndexer;
import com.xyz.model.Movie;
import com.xyz.model.SearchShoSlotDTO;
import com.xyz.model.Theater;
import com.xyz.service.SearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {


    Logger logger = LogManager.getLogger(SearchController.class);

    @Autowired
    SearchService searchService;
    @Autowired
    LuceneSearchIndexer luceneSearchIndexer;

    @GetMapping(value = {"", "/"})
    ResponseEntity<?> search(@RequestParam String name,
                             @RequestParam(required = false) String lang,
                             @RequestParam(required = false) String date
    ) {

        List<SearchShoSlotDTO> data;
        if (lang != null) {
            logger.info("Searching for lang " + lang);
            data = searchService.searchMovieN(name, lang, 10);
            return ResponseEntity.ok(data);
        } else if (date != null) {
            logger.info("Date to match " + LocalDate.parse(date));
//            LocalDate dated = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            data = searchService.searchMovieNameDate(name, LocalDate.parse(date), 10);
            logger.info("Search result: size " + data.size());
            return ResponseEntity.ok(data);
        } else {
          return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Language or date is required");
        }

    }

    @GetMapping("/data/index")
    ResponseEntity<?> index() {
        luceneSearchIndexer.indexPersistedData(Movie.class.getName(), Theater.class.getName());
        return ResponseEntity.ok().build();
    }
}
