package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.*;
import com.platformcommons.platform.service.domain.dto.AppEventDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AppEventMapper {

    public AppEventDTO from(App app){
        Set<Feature> featureSet = app.getFeatureList();
        Map<String,String> featureCodeValueMap= new LinkedHashMap<>();
        if(featureSet!=null){
            featureSet.stream().filter(Objects::nonNull).forEach(it->featureCodeValueMap.put(it.getCode(),it.getValue()));
        }

        Set<String> useCaseKeywords = new LinkedHashSet<>();
        Set<String> useCaseNames= new LinkedHashSet<>();
        if(app.getUseCaseList()!=null && !app.getUseCaseList().isEmpty()){
            app.getUseCaseList().stream().forEach(useCase -> {
                useCaseNames.add(useCase.getName());
                if(useCase.getSearchKeywordList()!=null && !useCase.getSearchKeywordList().isEmpty()){
                    useCaseKeywords.addAll(useCase.getSearchKeywordList().stream().map(SearchKeyword::getTextValue).collect(Collectors.toCollection(LinkedHashSet::new)));
                }
            });
        }

        return AppEventDTO.builder()
                .id(app.getId())
                .slug(app.getSlug())
                .authorId(app.getAuthor().getId())
                .authorName(app.getAuthor().getName())
                .domainCodes(app.getDomainList()!=null && !app.getDomainList().isEmpty()?
                        app.getDomainList().stream().map(Domain::getCode).collect(Collectors.toCollection(LinkedHashSet::new)):null)
                .subDomainCodes(app.getSubDomainList()!=null && !app.getSubDomainList().isEmpty()?
                        app.getSubDomainList().stream().map(Domain::getCode).collect(Collectors.toCollection(LinkedHashSet::new)):null)
                .useCaseIds(app.getUseCaseList()!=null?app.getUseCaseList().stream().map(UseCase::getId).collect(Collectors.toCollection(LinkedHashSet::new)) : null)
                .appKeywords(app.getSearchKeywordList()!=null && !app.getSearchKeywordList().isEmpty() ?
                        app.getSearchKeywordList().stream().map(SearchKeyword::getTextValue).collect(Collectors.toSet()):null)
                .useCaseKeywords(!useCaseKeywords.isEmpty()? useCaseKeywords: null)
                .useCaseNames(!useCaseNames.isEmpty()? useCaseNames:null)
                .name(app.getName())
                .logo(app.getLogo())
                .features(featureCodeValueMap.isEmpty()? null: featureCodeValueMap)
                .description(app.getShortDescription())
                .rating(app.getAverageRating())
                .website(app.getWebsite())
                .build();
    }
}
