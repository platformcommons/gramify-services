package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.CompanyMasterData;
import com.platformcommons.platform.service.search.domain.TenantUser;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataDTO;
import com.platformcommons.platform.service.search.dto.TenantUserDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyMasterDataDTOAssembler {

    public CompanyMasterDataDTO toDTO(CompanyMasterData company){
        return CompanyMasterDataDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .alias(company.getAlias())
                .city(company.getCity())
                .state(company.getState())
                .domain(company.getDomain())
                .category(company.getCategory()).build();
    }

    public CompanyMasterData fromDTO(CompanyMasterDataDTO companyDTO){
        return CompanyMasterData.builder()
                .id(companyDTO.getId())
                .name(companyDTO.getName())
                .alias(companyDTO.getAlias())
                .city(companyDTO.getCity())
                .state(companyDTO.getState())
                .domain(companyDTO.getDomain())
                .category(companyDTO.getCategory()).build();
    }
}
