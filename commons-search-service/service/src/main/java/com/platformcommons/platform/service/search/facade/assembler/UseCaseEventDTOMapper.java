package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.domain.dto.UseCaseEventDTO;
import com.platformcommons.platform.service.search.domain.UseCase;
import org.springframework.stereotype.Component;

@Component
public class UseCaseEventDTOMapper {

    public UseCase fromDTO(UseCaseEventDTO useCaseEventDTO){
        return UseCase.builder()
                .id(useCaseEventDTO.getId())
                .authorId(useCaseEventDTO.getAuthorId())
                .authorName(useCaseEventDTO.getAuthorName())
                .name(useCaseEventDTO.getName())
                .logo(useCaseEventDTO.getLogo())
                .features(useCaseEventDTO.getFeatures())
                .subDomainCodes(useCaseEventDTO.getSubDomainCodes())
                .domainCodes(useCaseEventDTO.getDomainCodes())
                .description(useCaseEventDTO.getDescription())
                .sequence(useCaseEventDTO.getSequence())
                .build();
    }
}
