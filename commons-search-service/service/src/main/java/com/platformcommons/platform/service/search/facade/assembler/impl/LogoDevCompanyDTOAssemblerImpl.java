package com.platformcommons.platform.service.search.facade.assembler.impl;

import com.platformcommons.platform.service.search.dto.CompanyMasterDataV2DTO;
import com.platformcommons.platform.service.search.dto.LogoDevCompanyDTO;
import com.platformcommons.platform.service.search.facade.assembler.LogoDevCompanyDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class LogoDevCompanyDTOAssemblerImpl implements LogoDevCompanyDTOAssembler {

    @Override
    public CompanyMasterDataV2DTO toCompanyMasterDataV2DTO(LogoDevCompanyDTO logoDevCompanyDTO) {
        if (logoDevCompanyDTO == null) return null;
        return CompanyMasterDataV2DTO.builder()
                .name(logoDevCompanyDTO.getName())
                .websiteUrl(logoDevCompanyDTO.getDomain())
                .logoUrl(logoDevCompanyDTO.getLogoUrl())
                .build();
    }
}
