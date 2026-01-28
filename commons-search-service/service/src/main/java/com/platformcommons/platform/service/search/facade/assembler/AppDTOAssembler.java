package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.dto.AppDTO;
import org.springframework.stereotype.Component;

@Component
public class AppDTOAssembler {

    public AppDTO toDTO(App app){
        return AppDTO.builder()
                .id(app.getId())
                .slug(app.getSlug())
                .authorId(app.getAuthorId())
                .authorName(app.getAuthorName())
                .name(app.getName())
                .logo(app.getLogo())
                .domainCodes(app.getDomainCodes())
                .subDomainCodes(app.getSubDomainCodes())
                .useCaseIds(app.getUseCaseIds())
                .features(app.getFeatures())
                .description(app.getDescription())
                .rating(app.getRating()).build();

    }
    public App fromDTO(AppDTO appDTO){
        return App.builder()
                .id(appDTO.getId())
                .slug(appDTO.getSlug())
                .authorId(appDTO.getAuthorId())
                .authorName(appDTO.getAuthorName())
                .name(appDTO.getName())
                .logo(appDTO.getLogo())
                .domainCodes(appDTO.getDomainCodes())
                .subDomainCodes(appDTO.getSubDomainCodes())
                .useCaseIds(appDTO.getUseCaseIds())
                .features(appDTO.getFeatures())
                .description(appDTO.getDescription())
                .rating(appDTO.getRating()).build();

    }
}
