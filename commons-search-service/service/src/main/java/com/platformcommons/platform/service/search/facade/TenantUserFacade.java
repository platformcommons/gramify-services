package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.SearchDTO;
import com.platformcommons.platform.service.search.dto.TenantUserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface TenantUserFacade {
    TenantUserDTO saveTenantUser(TenantUserDTO body);

    String updateTenantUser(TenantUserDTO body);

    PageDTO<TenantUserDTO> readTenantUser(String keyword);

    PageDTO<TenantUserDTO> readTenantUserByDetails(SearchDTO body);

    void readExcel(MultipartFile file);

}
