package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.search.application.service.EducationalInstituteV2Service;
import com.platformcommons.platform.service.search.application.utility.ElasticSearchUtility;
import com.platformcommons.platform.service.search.domain.EducationalInstituteV2;
import com.platformcommons.platform.service.search.dto.EducationalInstituteV2DTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.facade.EducationalInstituteV2Facade;
import com.platformcommons.platform.service.search.facade.assembler.EducationalInstituteV2DTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class EducationalInstituteV2FacadeImpl implements EducationalInstituteV2Facade {

    @Autowired
    private EducationalInstituteV2Service service;

    @Autowired
    private EducationalInstituteV2DTOAssembler assembler;

    @Override
    public EducationalInstituteV2DTO save(EducationalInstituteV2DTO body) {
        return assembler.toDTO(service.save(assembler.fromDTO(body)));
    }

    @Override
    public void deleteByCode(String code) {
        service.deleteByCode(code);
    }

    @Override
    public FacetPageDTO<EducationalInstituteV2DTO> getBySearchText(String searchText, Set<String> instituteTypeSet, Integer page, Integer size) {
        size = Math.min(size, 100);
        SearchHits<EducationalInstituteV2> result = service.getBySearchText(searchText, instituteTypeSet, page, size);

        LinkedHashSet<EducationalInstituteV2DTO> educationalInstituteV2DTOS = result
                .getSearchHits()
                        .stream()
                        .map(SearchHit::getContent)
                        .map(assembler::toDTO)
                        .collect(Collectors.toCollection(LinkedHashSet::new));

        return new FacetPageDTO<>(
                educationalInstituteV2DTOS,
                ElasticSearchUtility.computeHasMoreHits(result.getTotalHits(), result.getSearchHits().size(), page, size),
                ElasticSearchUtility.createTermsFacetResult(result.getAggregations()),
                result.getTotalHits());
    }


    @Override
    public Map<String, EducationalInstituteV2DTO> getByEducationInstituteCodesInBulk(Set<String> educationInstituteCodes) {
        Map<String, EducationalInstituteV2> result = service.getByEducationInstituteCodesInBulk(educationInstituteCodes);
        return result.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry->assembler.toDTO(entry.getValue()),(a, b)->a));
    }
}
