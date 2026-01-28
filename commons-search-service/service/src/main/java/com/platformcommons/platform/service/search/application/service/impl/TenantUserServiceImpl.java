package com.platformcommons.platform.service.search.application.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.platformcommons.platform.service.search.application.service.TenantUserService;
import com.platformcommons.platform.service.search.application.utility.ExcelHelper;
import com.platformcommons.platform.service.search.domain.TenantUser;
import com.platformcommons.platform.service.search.domain.repo.TenantUserRepository;
import com.platformcommons.platform.service.search.dto.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TenantUserServiceImpl implements TenantUserService {

	@Autowired
	private TenantUserRepository tenantUserRepository;

	@Override
	public List<TenantUser> readTenantUserByTerm(String term) {
		if(term.matches("^[0-9]+$")){
			return tenantUserRepository.findByMobile(Long.parseLong(term));
		}
		return tenantUserRepository.findByCustomQuery(term);
	}

	@Override
	public List<TenantUser> readTenantUserByDetails(SearchDTO searchDto){
		return tenantUserRepository.findByCompleteNameAndEmailAndMobile(searchDto.getCompleteName(),searchDto.getEmail(),searchDto.getMobile());
	}

	@Override
	public TenantUser saveTenantUser(TenantUser data) {
		return tenantUserRepository.save(data);
	}
	
	@Override
	public String updateTenantUser(TenantUser tenantUser) {
		if(tenantUserRepository.existsById(tenantUser.getId())) {
			tenantUserRepository.save(tenantUser);
			return "record updated";
		}
		return tenantUser.getId()+" id not exist" ;
	}

	@Override
	public List<TenantUser> readTenantUsersExcel(MultipartFile file){
		Iterable<TenantUser> users=null;
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				users=tenantUserRepository.saveAll(ExcelHelper.excelToTenantUser(file.getInputStream()));
			}
			catch(Exception e){
				throw new RuntimeException("fail to store excel data: " + e.getMessage());
			}
		}
		return StreamSupport.stream(users.spliterator(), false)
						.collect(Collectors.toList());

	}

}
