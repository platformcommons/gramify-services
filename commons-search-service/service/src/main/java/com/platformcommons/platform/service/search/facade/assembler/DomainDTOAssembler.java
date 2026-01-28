package com.platformcommons.platform.service.search.facade.assembler;


import com.platformcommons.platform.service.search.domain.Domain;
import com.platformcommons.platform.service.search.dto.DomainDTO;
import org.springframework.stereotype.Component;

@Component
public class DomainDTOAssembler {
    public DomainDTO toDTO(Domain domain){
        return DomainDTO.builder()
                .id(domain.getId())
                .name(domain.getName())
                .code(domain.getCode())
                .context(domain.getContext())
                .tenantId(domain.getTenantId())
                .subDomainCodes(domain.getSubDomainCodes())
                .isRoot(domain.getIsRoot()).build();
    }

    public Domain fromDTO(DomainDTO domainDTO){
        return Domain.builder()
                .id(domainDTO.getId())
                .name(domainDTO.getName())
                .code(domainDTO.getCode())
                .context(domainDTO.getContext())
                .tenantId(domainDTO.getTenantId())
                .subDomainCodes(domainDTO.getSubDomainCodes())
                .isRoot(domainDTO.getIsRoot()).build();
    }

}
