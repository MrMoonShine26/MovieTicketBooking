package com.xyz.lucene;

import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class LuceneSearchIndexer {

    private final EntityManager entityManager;

    private static final int THREAD_NUMBER = 4;

    private final Logger logger = LogManager.getLogger();

    public LuceneSearchIndexer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void indexPersistedData(String... indexClassNames) {
        try {
            SearchSession searchSession = Search.session(entityManager);
            List<Class<?>> classToIndex = new ArrayList<>();
            for (String className :
                    indexClassNames) {
                classToIndex.add(Class.forName(className));
            }

            MassIndexer indexer =
                    searchSession
                            .massIndexer(classToIndex)
                            .threadsToLoadObjects(THREAD_NUMBER);

            indexer.startAndWait();
        } catch (ClassNotFoundException e) {
//            throw new IndexException("Invalid class " + indexClassName, e);
            logger.error("ClassNotFoundException ", e);
        } catch (InterruptedException e) {
//            throw new IndexException("Index Interrupted", e);
            logger.error("InterruptedException ", e);
        }
    }
}