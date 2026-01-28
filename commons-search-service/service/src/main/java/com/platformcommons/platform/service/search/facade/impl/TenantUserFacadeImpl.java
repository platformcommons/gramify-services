package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.TenantUserService;
import com.platformcommons.platform.service.search.domain.TenantUser;
import com.platformcommons.platform.service.search.dto.SearchDTO;
import com.platformcommons.platform.service.search.dto.TenantUserDTO;
import com.platformcommons.platform.service.search.facade.TenantUserFacade;
import com.platformcommons.platform.service.search.facade.assembler.TenantUserDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class TenantUserFacadeImpl implements TenantUserFacade {

    @Autowired
    TenantUserService tenantUserService;

    @Autowired
    TenantUserDTOAssembler tenantUserDTOAssembler;

    @Override
    public TenantUserDTO saveTenantUser(TenantUserDTO body) {
        return tenantUserDTOAssembler.toDTO(tenantUserService.saveTenantUser(tenantUserDTOAssembler.fromDTO(body)));
    }

    @Override
    public String updateTenantUser(TenantUserDTO body) {
        return tenantUserService.updateTenantUser(tenantUserDTOAssembler.fromDTO(body));
    }

    @Override
    public PageDTO<TenantUserDTO> readTenantUser(String keyword) {
        List<TenantUser> list=tenantUserService.readTenantUserByTerm(keyword);
        Set<TenantUserDTO> set=new HashSet<>();
        for(TenantUser user:list){
            set.add(tenantUserDTOAssembler.toDTO(user));
        }
        return new PageDTO<>(set,true,set.size());
    }

    @Override
    public PageDTO<TenantUserDTO> readTenantUserByDetails(SearchDTO body) {
        List<TenantUser> list=tenantUserService.readTenantUserByDetails(body);
        Set<TenantUserDTO> set=new HashSet<>();
        for(TenantUser user:list){
            set.add(tenantUserDTOAssembler.toDTO(user));
        }
        return new PageDTO<>(set,true,set.size());
    }

    @Override
    public void readExcel(MultipartFile file){
        tenantUserService.readTenantUsersExcel(file);
    }
}
