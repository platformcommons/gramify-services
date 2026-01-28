package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.service.AppService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.repo.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository appRepository;


    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public App createApp(App app) {
        return appRepository.save(app);
    }

    @Override
    public App updateApp(App app) {
        appRepository.findById(app.getId()).orElseThrow(()-> new NotFoundException(String.format("App with  %d  not found",app.getId())));
        return appRepository.save(app);
    }


    @Override
    public App updateAppSlug(Long appId, String slug){
        App app = findById(appId);
        app.setSlug(slug);
        return appRepository.save(app);
    }


    public App findById(Long id){
        return appRepository.findById(id).orElseThrow(()->
                new NotFoundException(String.format("App with  %d  not found",id)));
    }
    @Override
    public Page<App> readAppByName(String name, Integer page, Integer size) {
        Page<App> pageApp= appRepository.findAppByName(name, PageRequest.of(page,size));
        return pageApp;
    }


    @Override
    public FacetPage<App> getApp(Set<String> fields, Integer page, Integer size){
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria("*:*"))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(fields)
                        .setFacetMinCount(1));
        facetQuery.setPageRequest(PageRequest.of(page,size));
        FacetPage<App> results =solrTemplate.queryForFacetPage("app",facetQuery, App.class);
        return results;
    }

    @Override
    public FacetPage<App> getAppWithFilter(String searchTerm,Set<String> fields, Integer page, Integer size,
                                           List<String> domainCodes,List<String> subDomainCodes,
                                           List<Long> userCaseIds,String sortBy,String direction){
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForAppSearch(searchTerm)))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(fields)
                        .setFacetMinCount(1));
        facetQuery.setPageRequest(PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));
        FilterQuery filterQuery = new SimpleFilterQuery();
        if(domainCodes!=null && !domainCodes.isEmpty()) {
            Criteria criteria = new Criteria("domainCodes").in(domainCodes);
            filterQuery.addCriteria(criteria);
        }
        if(subDomainCodes!=null && !subDomainCodes.isEmpty()){
            Criteria criteria = new Criteria("subDomainCodes").in(subDomainCodes);
            filterQuery.addCriteria(criteria);
        }
        if(userCaseIds!=null && !userCaseIds.isEmpty()){
            Criteria criteria = new Criteria("useCaseIds").in(userCaseIds);
            filterQuery.addCriteria(criteria);
        }
        facetQuery.addFilterQuery(filterQuery);

        return solrTemplate.queryForFacetPage("app",facetQuery, App.class);
    }

    @Override
    public Set<App> getByIds(Set<Long> ids) {
        return appRepository.findByIdIn(ids);
    }

    private String buildQueryForAppSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "appKeywords:" + searchTerm +
                    " OR useCaseKeywords:" + searchTerm +
                    " OR name:" + searchTerm+
                    " OR name_src:" + searchTerm+
                    " OR useCaseNames:" + searchTerm;
        }
        else {
            return "*:*";
        }
    }


}
