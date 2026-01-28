package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.domain.CompanyMasterData;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataDTO;

import java.util.List;

public interface CompanyMasterDataFacade {
	
	PageDTO<CompanyMasterDataDTO> readCompanyByName(String keyword);

	CompanyMasterDataDTO saveCompany(CompanyMasterDataDTO body);
	
	String updateCompany(CompanyMasterDataDTO body);
}
