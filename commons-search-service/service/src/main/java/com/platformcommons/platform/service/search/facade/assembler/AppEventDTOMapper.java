package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.domain.dto.AppEventDTO;
import com.platformcommons.platform.service.search.domain.App;
import org.springframework.stereotype.Component;

@Component
public class AppEventDTOMapper {


    public App fromDTO(AppEventDTO appEventDTO){
        return App.builder()
                .id(appEventDTO.getId())
                .slug(appEventDTO.getSlug())
                .authorId(appEventDTO.getAuthorId())
                .authorName(appEventDTO.getAuthorName())
                .name(appEventDTO.getName())
                .logo(appEventDTO.getLogo())
                .features(appEventDTO.getFeatures())
                .subDomainCodes(appEventDTO.getSubDomainCodes())
                .domainCodes(appEventDTO.getDomainCodes())
                .useCaseIds(appEventDTO.getUseCaseIds())
                .rating(appEventDTO.getRating())
                .description(appEventDTO.getDescription())
                .appKeywords(appEventDTO.getAppKeywords())
                .useCaseKeywords(appEventDTO.getUseCaseKeywords())
                .useCaseNames(appEventDTO.getUseCaseNames())
                .build();
    }
}
