package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.PostService;
import com.platformcommons.platform.service.search.application.utility.SearchHelper;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.domain.Post;
import com.platformcommons.platform.service.search.domain.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private PostRepository repository;


    @Override
    public void save(Post post) {
        repository.save(post);
    }

    @Override
    public void delete(Long postId) {
        Optional<Post> optionalPost = repository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setIsActive(Boolean.FALSE);
            repository.save(post);
        }
    }

    @Override
    public Post getById(Long id){
        return repository.findById(id).orElseThrow(()->
                new NotFoundException(String.format("Post with  %d  not found",id)));
    }

    @Override
    public Page<Post> getPostTimeline(String searchTerm,String tenantLogin, String  appContext, String domainContext, String languageContext,
                                      String locationContext, String postType, Set<String> postSubTypes, String status, Pageable pageable) {
        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForPostSearch(searchTerm)));
        query.setPageRequest(pageable);
        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria tenantCriteria = Criteria.where("tenantLogin").is(tenantLogin);
        filterQuery.addCriteria(tenantCriteria);
        Criteria appContextCriteria = Criteria.where("appContext").is(appContext);
        filterQuery.addCriteria(appContextCriteria);
        Criteria isActiveCriteria = Criteria.where("isActive").is(Boolean.TRUE);
        filterQuery.addCriteria(isActiveCriteria);

        if(postType != null) {
            Criteria criteria = Criteria.where("postType").is(postType);
            filterQuery.addCriteria(criteria);
        }
        if(postSubTypes != null && !postSubTypes.isEmpty()) {
            Criteria criteria = Criteria.where("postSubType").in(postSubTypes);
            filterQuery.addCriteria(criteria);
        }
        if(status != null) {
            Criteria criteria = Criteria.where("status").is(status);
            filterQuery.addCriteria(criteria);
        }
        query.addFilterQuery(filterQuery);
        return solrTemplate.query(ServiceConstant.POST_CORE, query, Post.class);
    }


    public String buildQueryForPostSearch(String searchTerm){
        String queryString = "*:*";
        if(searchTerm != null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            List<String> searchTermsArray = Arrays.asList(searchTerm.split("\\s+"));
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(SearchHelper.buildQueryForIndividualField("title",searchTermsArray));
            queryString = queryBuilder.toString();
        }
        return queryString;
    }
}
