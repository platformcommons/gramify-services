package com.platformcommons.platform.service.search.application.service;

import java.util.List;

import com.platformcommons.platform.service.search.domain.CompanyMasterData;

public interface CompanyMasterDataService {
	
	List<CompanyMasterData> readCompanyByName(String name);

	CompanyMasterData saveCompany(CompanyMasterData data);
	
	String updateCompany(CompanyMasterData company);
}
