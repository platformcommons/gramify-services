package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.CompanyMasterDataV2Service;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchHelper;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.CompanyMasterDataV2;
import com.platformcommons.platform.service.search.domain.repo.CompanyMasterDataV2Repository;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.RequestMethod;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CompanyMasterDataV2ServiceImpl implements CompanyMasterDataV2Service {

    @Autowired
    private CompanyMasterDataV2Repository repository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public CompanyMasterDataV2 save(CompanyMasterDataV2 companyMasterDataV2) {
        if(companyMasterDataV2.getName() == null) {
            throw new InvalidInputException("Company can not be saved without Name");
        }

        if(companyMasterDataV2.getWebsiteUrl() != null) {
            Set<CompanyMasterDataV2> existingCompanies = getByCompanyNameAndWebsiteUrl(companyMasterDataV2.getName(),
                    companyMasterDataV2.getWebsiteUrl());
            if(existingCompanies != null && !existingCompanies.isEmpty()) {
                throw new DuplicateResourceException(String.format("Company with name - %s and websiteUrl - %s already exists",
                        companyMasterDataV2.getName(),companyMasterDataV2.getWebsiteUrl()));
            }
        }
        else {
            Set<CompanyMasterDataV2> existingCompanies = getByCompanyName(companyMasterDataV2.getName());
            if(existingCompanies != null && !existingCompanies.isEmpty()) {
                throw new DuplicateResourceException(String.format("Company with name - %s already exists",
                        companyMasterDataV2.getName()));
            }
        }
        companyMasterDataV2.init();
        return repository.save(companyMasterDataV2);
    }

    public CompanyMasterDataV2 getByCode(String code){
        return repository.findByCode(code).orElseThrow(()->
                new NotFoundException(String.format("CompanyMasterData V2 with  %s  not found",code)));
    }

    @Override
    public Page<CompanyMasterDataV2> getAllByPagination(Pageable pageable) {
        Query query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        query.setPageRequest(pageable);
        return solrTemplate.query(ServiceConstant.COMPANY_MASTER_DATA_V2_CORE, query, CompanyMasterDataV2.class);
    }

    @Override
    public List<CompanyMasterDataV2> getByCompanyCodesInBulk(Set<String> companyCodes, String sortBy, String direction) {
        Query query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        Pageable pageable = PageRequest.of(0,companyCodes.size(), JPAUtility.convertToSort(sortBy,direction));
        query.setPageRequest(pageable);
        Criteria criteria = Criteria.where("code").in(companyCodes);
        query.addCriteria(criteria);
        Page<CompanyMasterDataV2> result  = solrTemplate.query(ServiceConstant.COMPANY_MASTER_DATA_V2_CORE, query, CompanyMasterDataV2.class,
                RequestMethod.POST);
        return result.getContent();
    }

    @Override
    public Set<CompanyMasterDataV2> getByCompanyName(String companyName) {
        Query query = new SimpleQuery();
        Criteria criteria = Criteria.where("name_str").is(companyName);
        query.addCriteria(criteria);
        return new HashSet<>(solrTemplate.query(ServiceConstant.COMPANY_MASTER_DATA_V2_CORE,query ,CompanyMasterDataV2.class).getContent());
    }

    @Override
    public void deleteByCode(String code) {
        repository.deleteById(code);
    }

    public Set<CompanyMasterDataV2> getByCompanyNameAndWebsiteUrl(String companyName, String websiteUrl) {
        Query query = new SimpleQuery();
        Criteria criteria = Criteria.where("name_str").is(companyName);
        query.addCriteria(criteria);
        criteria = Criteria.where("websiteUrl").is(websiteUrl);
        query.addCriteria(criteria);
        return new HashSet<>(solrTemplate.query(ServiceConstant.COMPANY_MASTER_DATA_V2_CORE,query ,CompanyMasterDataV2.class).getContent());
    }


    @Override
    public void putUpdate(CompanyMasterDataV2 companyMasterDataV2) {
        getByCode(companyMasterDataV2.getCode());
        repository.save(companyMasterDataV2);
    }

    @Override
    public Page<CompanyMasterDataV2> getBySearchText(String searchText, Pageable pageable) {
        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForCompanySearch(searchText)));
        query.setPageRequest(pageable);
        return solrTemplate.query(ServiceConstant.COMPANY_MASTER_DATA_V2_CORE,query, CompanyMasterDataV2.class);
    }


    private String buildQueryForCompanySearch(String searchTerm){
        String queryString = "*:*";
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            List<String> searchTermsArray = Arrays.asList(searchTerm.split("\\s+"));
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(SearchHelper.buildQueryForIndividualField("name",searchTermsArray))
                    .append(" OR ")
                    .append("name_str:\"" + searchTerm +"\"^5.0");
            queryString = queryBuilder.toString();
        }
        return queryString;
    }
}
