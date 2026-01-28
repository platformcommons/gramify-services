package com.platformcommons.platform.service.search.application.service.impl;

import java.util.List;

import com.platformcommons.platform.service.search.application.service.CompanyMasterDataService;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.CompanyMasterData;
import com.platformcommons.platform.service.search.domain.repo.CompanyMasterDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyMasterDataServiceImpl implements CompanyMasterDataService {

	@Autowired
	private CompanyMasterDataRepository companyMasterDataRepository;

	@Override
	public List<CompanyMasterData> readCompanyByName(String name) {
		return companyMasterDataRepository.findByCustomQuery(SearchTermParser.parseSearchTerm(name));
	}

	@Override
	public CompanyMasterData saveCompany(CompanyMasterData data) {
		return companyMasterDataRepository.save(data);
	}
	
	@Override
	public String updateCompany(CompanyMasterData company) {
		if(companyMasterDataRepository.existsById(company.getId())) {
			companyMasterDataRepository.save(company);
			return "record updated";
		}
		return company.getId()+" id not exist" ;
	}

}
