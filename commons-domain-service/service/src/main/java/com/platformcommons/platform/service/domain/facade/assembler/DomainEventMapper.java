package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.dto.DomainEventDTO;
import org.springframework.stereotype.Component;

@Component
public class DomainEventMapper {

    public DomainEventDTO from(Domain domain){
        return DomainEventDTO.builder()
                .id(domain.getId())
                .name(domain.getName())
                .context(domain.getContext())
                .code(domain.getCode())
                .isRoot(domain.getIsRoot())
                .tenantId(domain.getTenantId())
                .build();
    }
}
