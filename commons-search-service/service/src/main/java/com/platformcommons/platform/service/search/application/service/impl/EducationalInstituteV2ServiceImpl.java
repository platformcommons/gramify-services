package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.service.EducationalInstituteV2Service;
import com.platformcommons.platform.service.search.application.utility.ElasticSearchUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.EducationalInstituteV2;
import com.platformcommons.platform.service.search.domain.repo.EducationalInstituteV2ElasticRepository;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EducationalInstituteV2ServiceImpl implements EducationalInstituteV2Service {

    @Autowired
    private EducationalInstituteV2ElasticRepository repository;

    @Autowired(required = false)
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public EducationalInstituteV2 save(EducationalInstituteV2 body) {
        Set<EducationalInstituteV2> existingInstitute = getByInstituteNameAndType(body.getInstituteName(),body.getInstituteType());
        if(existingInstitute != null && !existingInstitute.isEmpty()) {
            throw new DuplicateResourceException(String.format("Institute with name - %s  and type - %s already exists",
                    body.getInstituteName(),body.getInstituteType()));
        }
        body.init();
        return repository.save(body);
    }

    @Override
    public void deleteByCode(String code) {
        EducationalInstituteV2 educationalInstituteV2 = repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Institute with code %s not found", code)));
        repository.delete(educationalInstituteV2);
    }

    @Override
    public SearchHits<EducationalInstituteV2> getBySearchText(String searchText, Set<String> instituteTypeSet, Integer page, Integer size) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (searchText != null && !searchText.trim().isEmpty()) {
            createQueryForInstituteSearchText(boolQuery, searchText);
        }

        if (instituteTypeSet != null && !instituteTypeSet.isEmpty()) {
            ElasticSearchUtility.createTermQueryAndFilterInBoolQuery(boolQuery, "instituteType", instituteTypeSet);
        }

        Pageable pageable = PageRequest.of(page, size);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageable)
                .withTrackTotalHits(true)
                .build();

        return elasticsearchRestTemplate.search(query, EducationalInstituteV2.class, IndexCoordinates.of("education_institute"));
    }


    public void createQueryForInstituteSearchText(BoolQueryBuilder parentBool, String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return;
        }

        searchText = searchText.toLowerCase();
        String searchTerm = SearchTermParser.parseSearchTerm(searchText);

        BoolQueryBuilder shouldQuery = QueryBuilders.boolQuery();
        shouldQuery.should(
                QueryBuilders.matchQuery("instituteName", searchTerm)
                        .fuzziness(searchTerm.length() <= 2 ? Fuzziness.ZERO : Fuzziness.ONE)
                        .prefixLength(2)
                        .operator(Operator.AND)
                        .boost(3.0f)
        );
        shouldQuery.should(
                QueryBuilders.termQuery("instituteName.keyword", searchTerm)
                        .boost(5.0f)
        );
        shouldQuery.minimumShouldMatch(1);
        parentBool.must(shouldQuery);
    }


    @Override
    public Map<String, EducationalInstituteV2> getByEducationInstituteCodesInBulk(Set<String> educationInstituteCodes) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (educationInstituteCodes != null && !educationInstituteCodes.isEmpty()) {
            ElasticSearchUtility.createTermQueryAndFilterInBoolQuery(boolQuery, "code", educationInstituteCodes);
        }

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withTrackTotalHits(true)
                .build();

        return elasticsearchRestTemplate.search(query, EducationalInstituteV2.class, IndexCoordinates.of("education_institute"))
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toMap(EducationalInstituteV2::getCode,
                        Function.identity(),
                        (a, b) -> a));
    }

    public Set<EducationalInstituteV2> getByInstituteNameAndType(String instituteName, String instituteType) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (instituteName != null && !instituteName.trim().isEmpty()) {
            ElasticSearchUtility.createTermQueryAndFilterInBoolQuery(boolQueryBuilder, "instituteName.keyword", instituteName);
        }
        if (instituteType != null && !instituteType.trim().isEmpty()) {
            ElasticSearchUtility.createTermQueryAndFilterInBoolQuery(boolQueryBuilder, "instituteType", instituteType);
        }

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withTrackTotalHits(true)
                .build();

        return elasticsearchRestTemplate
                .search(nativeSearchQuery, EducationalInstituteV2.class, IndexCoordinates.of("education_institute"))
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toSet());
    }
}
