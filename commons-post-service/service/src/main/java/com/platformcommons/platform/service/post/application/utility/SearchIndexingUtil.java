package com.platformcommons.platform.service.post.application.utility;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
public class SearchIndexingUtil {

    private final EntityManager entityManager;

    @Autowired
    public SearchIndexingUtil(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void reindexAll() {
        SearchSession searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer().idFetchSize(150).batchSizeToLoadObjects(25)
                .threadsToLoadObjects(12);
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}