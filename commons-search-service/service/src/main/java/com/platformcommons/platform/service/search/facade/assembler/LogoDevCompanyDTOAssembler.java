package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.dto.CompanyMasterDataV2DTO;
import com.platformcommons.platform.service.search.dto.LogoDevCompanyDTO;

public interface LogoDevCompanyDTOAssembler {

    CompanyMasterDataV2DTO toCompanyMasterDataV2DTO(LogoDevCompanyDTO logoDevCompanyDTO);
}
