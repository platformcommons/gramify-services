package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.CompanyMasterDataService;
import com.platformcommons.platform.service.search.application.service.TenantUserService;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataDTO;
import com.platformcommons.platform.service.search.facade.CompanyMasterDataFacade;
import com.platformcommons.platform.service.search.facade.assembler.CompanyMasterDataDTOAssembler;
import com.platformcommons.platform.service.search.facade.assembler.TenantUserDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class CompanyMasterDataFacadeImpl implements CompanyMasterDataFacade {

    @Autowired
    CompanyMasterDataService companyMasterDataService;

    @Autowired
    CompanyMasterDataDTOAssembler companyMasterDataDTOAssembler;

    @Override
    public PageDTO<CompanyMasterDataDTO> readCompanyByName(String keyword) {
        Set<CompanyMasterDataDTO> set=companyMasterDataService.readCompanyByName(keyword).
                stream().map(ob->companyMasterDataDTOAssembler.toDTO(ob)).collect(Collectors.toSet());
        return new PageDTO<>(set,true,set.size());
    }

    @Override
    public CompanyMasterDataDTO saveCompany(CompanyMasterDataDTO body) {
        return companyMasterDataDTOAssembler.toDTO(companyMasterDataService.
                saveCompany(companyMasterDataDTOAssembler.fromDTO(body)));
    }

    @Override
    public String updateCompany(CompanyMasterDataDTO body) {
        return companyMasterDataService.updateCompany(companyMasterDataDTOAssembler.fromDTO(body));
    }
}
