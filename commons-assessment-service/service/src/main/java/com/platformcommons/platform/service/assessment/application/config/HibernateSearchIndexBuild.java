package com.platformcommons.platform.service.assessment.application.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Configuration
@DependsOn({"hibernateOrmSearchMappingConfig","luceneAnalysisConfig"})
@RequiredArgsConstructor
public class HibernateSearchIndexBuild implements ApplicationListener<ApplicationReadyEvent> {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {

      /*  SearchSession searchSession=Search.session(entityManager);

        MassIndexer indexer=searchSession.massIndexer();
        indexer=indexer.idFetchSize(150)
                         .batchSizeToLoadObjects(25)
                         .threadsToLoadObjects(12);
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }*/
    }

}