package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.CommonsLocationService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchHelper;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.CommonsLocation;
import com.platformcommons.platform.service.search.domain.CompanyMasterDataV2;
import com.platformcommons.platform.service.search.domain.repo.CommonsLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.RequestMethod;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CommonsLocationServiceImpl implements CommonsLocationService {

    @Autowired
    private CommonsLocationRepository repository;

    @Autowired
    private SolrTemplate solrTemplate;

    private static final String STATE_CODE = "stateCode";

    private static final String CITY_CODE = "cityCode";

    private static final String DISTRICT_CODE = "districtCode";

    private static final String BLOCK_CODE = "blockCode";

    private static final String COUNTRY_CODE = "countryCode";

    @Override
    public CommonsLocation save(CommonsLocation commonsLocation) {
        commonsLocation.init();
        return repository.save(commonsLocation);
    }

    @Override
    public Page<CommonsLocation> getLocationByAddressLabel(String searchTerm, Pageable pageable, Boolean cityData,
                                                           Boolean stateData, Boolean countryData) {
        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForLocationSearch(searchTerm)));
        query.setPageRequest(pageable);
        if(Objects.equals(cityData,Boolean.TRUE)) {
            Criteria criteria = Criteria.where(CITY_CODE).isNotNull()
                    .and(STATE_CODE).isNotNull()
                    .and(COUNTRY_CODE).isNotNull();
            query.addCriteria(criteria);
        }
        if(Objects.equals(stateData,Boolean.TRUE)) {
            Criteria criteria = Criteria.where(CITY_CODE).isNull()
                    .and(STATE_CODE).isNotNull()
                    .and(COUNTRY_CODE).isNotNull();
            query.addCriteria(criteria);
        }
        if(Objects.equals(countryData,Boolean.TRUE)) {
            Criteria criteria = Criteria.where(CITY_CODE).isNull()
                    .and(STATE_CODE).isNull()
                    .and(COUNTRY_CODE).isNotNull();
            query.addCriteria(criteria);
        }
        return solrTemplate.query(ServiceConstant.COMMONS_LOCATION_CORE, query, CommonsLocation.class);
    }

    @Override
    public Set<CommonsLocation> getByAddressLabel(String addressLabel) {
        Query query = new SimpleQuery();
        Criteria criteria = Criteria.where("addressLabel_str").is(addressLabel);
        query.addCriteria(criteria);
        return new HashSet<>(solrTemplate.query(ServiceConstant.COMMONS_LOCATION_CORE,query , CommonsLocation.class).getContent());
    }

    @Override
    public List<CommonsLocation> getByLocationCodesInBulk(Set<String> dataCodes, String sortBy, String direction, String dataCodesField) {
        Query query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        Pageable pageable = PageRequest.of(0,dataCodes.size(), JPAUtility.convertToSort(sortBy,direction));
        query.setPageRequest(pageable);
        Criteria dataCodeFieldCriteria = null;
        if(Objects.equals(dataCodesField,"cityCode")) {
            dataCodeFieldCriteria = Criteria.where(CITY_CODE).isNotNull()
                    .and(STATE_CODE).isNotNull()
                    .and(COUNTRY_CODE).isNotNull();
        } else if (Objects.equals(dataCodesField,"blockCode")) {
            dataCodeFieldCriteria = Criteria.where(BLOCK_CODE).isNotNull()
                    .and(DISTRICT_CODE).isNotNull()
                    .and(CITY_CODE).isNull()
                    .and(STATE_CODE).isNotNull()
                    .and(COUNTRY_CODE).isNotNull();
        } else if (Objects.equals(dataCodesField,"districtCode")) {
            dataCodeFieldCriteria = Criteria.where(DISTRICT_CODE).isNotNull()
                    .and(BLOCK_CODE).isNull()
                    .and(CITY_CODE).isNull()
                    .and(DISTRICT_CODE).isNotNull()
                    .and(STATE_CODE).isNotNull()
                    .and(COUNTRY_CODE).isNotNull();
        } else if(Objects.equals(dataCodesField,"stateCode")) {
            dataCodeFieldCriteria = Criteria.where(CITY_CODE).isNull()
                    .and(BLOCK_CODE).isNull()
                    .and(DISTRICT_CODE).isNull()
                    .and(STATE_CODE).isNotNull()
                    .and(COUNTRY_CODE).isNotNull();
        }
        else  if(Objects.equals(dataCodesField,"countryCode")) {
            dataCodeFieldCriteria = Criteria.where(CITY_CODE).isNull()
                    .and(BLOCK_CODE).isNull()
                    .and(DISTRICT_CODE).isNull()
                    .and(STATE_CODE).isNull()
                    .and(COUNTRY_CODE).isNotNull();
        }
        else {
            throw new InvalidInputException("DataFieldCode is not of standard value");
        }
        query.addCriteria(dataCodeFieldCriteria);
        Criteria dataCodeCriteria = Criteria.where(dataCodesField).in(dataCodes);
        query.addCriteria(dataCodeCriteria);
        Page<CommonsLocation> result  = solrTemplate.query(ServiceConstant.COMMONS_LOCATION_CORE, query, CommonsLocation.class,
                RequestMethod.POST);
        return result.getContent();
    }

    @Override
    public Page<CommonsLocation> getLocationBySearchTerm(String searchText,String countryCode, Boolean cityData, Pageable pageable) {
        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForLocationSearch(searchText)));
        if(countryCode!=null){
            query.addCriteria(Criteria.where("countryCode").is(countryCode));
        }
        if(Objects.equals(cityData,Boolean.FALSE)) {
            Criteria criteria = Criteria.where(CITY_CODE).isNull();
            query.addCriteria(criteria);
        }
        query.setPageRequest(pageable);
        return solrTemplate.query(ServiceConstant.COMMONS_LOCATION_CORE, query, CommonsLocation.class);

    }

    @Override
    public void reindex() {
        int page = 0;
        int pageSize = 1000;
        Page<CommonsLocation> resultPage;

        do {
            Pageable pageable = PageRequest.of(page, pageSize);
            resultPage = repository.findAll(pageable); // JPA or custom paging

            List<CommonsLocation> dataBatch = resultPage.getContent();

            solrTemplate.saveBeans(ServiceConstant.COMMONS_LOCATION_CORE, dataBatch);
            solrTemplate.commit(ServiceConstant.COMMONS_LOCATION_CORE);

            page++;
        } while (!resultPage.isLast());

    }


    private String buildQueryForLocationSearch(String searchTerm){   //bang
        String queryString = "*:*";
        if(searchTerm != null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            List<String> searchTermsArray = Arrays.asList(searchTerm.split("\\s+"));
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(SearchHelper.buildQueryForIndividualField("addressLabel",searchTermsArray));
            queryString = queryBuilder.toString();
        }
        return queryString;
    }

}
