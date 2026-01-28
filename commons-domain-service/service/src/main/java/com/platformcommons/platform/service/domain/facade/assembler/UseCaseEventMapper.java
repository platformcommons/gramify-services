package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.App;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Feature;
import com.platformcommons.platform.service.domain.domain.UseCase;
import com.platformcommons.platform.service.domain.dto.AppEventDTO;
import com.platformcommons.platform.service.domain.dto.UseCaseEventDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UseCaseEventMapper {

    public UseCaseEventDTO from(UseCase useCase){
        Set<Feature> featureSet = useCase.getFeatureList();
        Map<String,String> featureCodeValueMap= new LinkedHashMap<>();
        if(featureSet!=null){
            featureSet.stream().filter(Objects::nonNull).forEach(it->featureCodeValueMap.put(it.getCode(),it.getValue()));
        }
        return UseCaseEventDTO.builder()
                .id(useCase.getId())
                .authorId(useCase.getAuthorId().getId())
                .authorName(useCase.getAuthorId().getName())
                .domainCodes(useCase.getDomainList()!=null && !useCase.getDomainList().isEmpty()?
                        useCase.getDomainList().stream().map(Domain::getCode).collect(Collectors.toCollection(LinkedHashSet::new)):null)
                .subDomainCodes(useCase.getSubDomainList()!=null && !useCase.getSubDomainList().isEmpty()?
                        useCase.getSubDomainList().stream().map(Domain::getCode).collect(Collectors.toCollection(LinkedHashSet::new)):null)
                .name(useCase.getName())
                .logo(useCase.getLogo())
                .features(featureCodeValueMap.isEmpty()? null: featureCodeValueMap)
                .description(useCase.getShortDescription())
                .sequence(useCase.getSequence())
                .build();
    }
}
