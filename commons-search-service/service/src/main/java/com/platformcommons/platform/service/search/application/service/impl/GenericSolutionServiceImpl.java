package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.service.GenericSolutionService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.GenericSolution;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import com.platformcommons.platform.service.search.domain.repo.GenericSolutionRepository;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class GenericSolutionServiceImpl implements GenericSolutionService {

    @Autowired
    private GenericSolutionRepository genericSolutionRepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public GenericSolution createGenericSolution(GenericSolution genericSolution) {
        return genericSolutionRepository.save(genericSolution);
    }

    @Override
    public GenericSolution updateGenericSolution(GenericSolution genericSolution) {
        GenericSolution fetchedGenericSolution = genericSolutionRepository.findById(genericSolution.getId()).orElseThrow(()-> new NotFoundException(String.format("GenericSolution with  %d  not found",genericSolution.getId())));
        fetchedGenericSolution.update(genericSolution);
        return genericSolutionRepository.save(fetchedGenericSolution);
    }

    @Override
    public Page<GenericSolution> readGenericSolutionByTitles(String marketId, String searchTerm, Set<String> categoryCodes,
                                                             Set<String> subCategoryCodes, String solutionType, Integer page, Integer size, String sortBy, String direction) {
        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForGenericSolutionSearch(searchTerm)));

        Criteria marketCriteria = Criteria.where("marketId").is(marketId);
        query.addCriteria(marketCriteria);

        if(categoryCodes!=null && !categoryCodes.isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(categoryCodes);
            query.addCriteria(criteria);
        }
        if(subCategoryCodes!=null && !subCategoryCodes.isEmpty()){
            Criteria criteria = new Criteria("subCategoryCodes").in(subCategoryCodes);
            query.addCriteria(criteria);
        }
        if(solutionType!=null ){
            Criteria criteria = new Criteria("solutionType").is(solutionType);
            query.addCriteria(criteria);
        }
        query.setPageRequest(PageRequest.of(page, size, JPAUtility.convertToSort(sortBy,direction)));
        return solrTemplate.query("generic_solution",query, GenericSolution.class);
    }

    @Override
    public void syncGenericSolutionVariantCount(Long marketId, Long channelId, Long baseSolutionId) {
        GenericSolution fetchedGenericSolution = genericSolutionRepository.findByIdAndMarketId(baseSolutionId,marketId)
                .orElseThrow(()-> new NotFoundException(String.format("Request GenericSolution with id %d and marketId %d not found",baseSolutionId,marketId)));

        Query query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        Criteria marketChannelCriteria = Criteria.where("marketId").is(marketId).and("channelId").is(channelId);
        query.addCriteria(marketChannelCriteria);
        Criteria baseSolutionCriteria = Criteria.where("baseSolutionId").is(baseSolutionId);
        query.addCriteria(baseSolutionCriteria);

        long tmaChannelSolutionCount = solrTemplate.count("tma_channel_solution",query, TMAChannelSolution.class);

        fetchedGenericSolution.setNoOfChildSolutions(tmaChannelSolutionCount);
        genericSolutionRepository.save(fetchedGenericSolution);

    }

    private String buildQueryForGenericSolutionSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "titles:" + searchTerm ;
        }
        else {
            return "*:*";
        }
    }
}
