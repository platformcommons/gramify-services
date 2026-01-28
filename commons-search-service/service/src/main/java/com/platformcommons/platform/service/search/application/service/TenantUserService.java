package com.platformcommons.platform.service.search.application.service;

import java.util.List;

import com.platformcommons.platform.service.search.domain.TenantUser;
import com.platformcommons.platform.service.search.dto.SearchDTO;
import org.springframework.web.multipart.MultipartFile;

public interface TenantUserService {
	
	List<TenantUser> readTenantUserByTerm(String term);

	List<TenantUser> readTenantUserByDetails(SearchDTO searchDto);

	TenantUser saveTenantUser(TenantUser data);
	
	String updateTenantUser(TenantUser user);

	List<TenantUser> readTenantUsersExcel(MultipartFile file);
}
