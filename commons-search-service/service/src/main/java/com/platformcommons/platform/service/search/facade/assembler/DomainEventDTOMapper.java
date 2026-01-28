package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.domain.dto.DomainEventDTO;
import com.platformcommons.platform.service.search.domain.Domain;
import org.springframework.stereotype.Component;

@Component
public class DomainEventDTOMapper {


    public Domain fromDTO(DomainEventDTO domainEventDTO){

        return Domain.builder()
                .id(domainEventDTO.getId())
                .code(domainEventDTO.getCode())
                .name(domainEventDTO.getName())
                .context(domainEventDTO.getContext())
                .isRoot(domainEventDTO.getIsRoot())
                .subDomainCodes(domainEventDTO.getSubDomainCodes())
                .tenantId(domainEventDTO.getTenantId())
                .build();
    }

}
