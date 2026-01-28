package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.UseCase;
import com.platformcommons.platform.service.search.dto.UseCaseDTO;
import org.springframework.stereotype.Component;

@Component
public class UseCaseDTOAssembler {

    public UseCaseDTO toDTO(UseCase useCase){
        return UseCaseDTO.builder()
                .id(useCase.getId())
                .authorId(useCase.getAuthorId())
                .authorName(useCase.getAuthorName())
                .name(useCase.getName())
                .logo(useCase.getLogo())
                .domainCodes(useCase.getDomainCodes())
                .subDomainCodes(useCase.getSubDomainCodes())
                .sequence(useCase.getSequence())
                .features(useCase.getFeatures()).build();
    }

    public UseCase fromDTO(UseCaseDTO useCaseDTO){
        return UseCase.builder()
                .id(useCaseDTO.getId())
                .authorId(useCaseDTO.getAuthorId())
                .authorName(useCaseDTO.getAuthorName())
                .name(useCaseDTO.getName())
                .logo(useCaseDTO.getLogo())
                .domainCodes(useCaseDTO.getDomainCodes())
                .subDomainCodes(useCaseDTO.getSubDomainCodes())
                .sequence(useCaseDTO.getSequence())
                .features(useCaseDTO.getFeatures()).build();
    }
}
