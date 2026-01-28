package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.CompanyMasterDataV2Service;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.PlatformUtil;
import com.platformcommons.platform.service.search.domain.CompanyMasterDataV2;
import com.platformcommons.platform.service.search.domain.base.ElasticBaseEntity;
import com.platformcommons.platform.service.search.dto.CompanyCodesRequestDTO;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataV2DTO;
import com.platformcommons.platform.service.search.facade.CompanyMasterDataV2Facade;
import com.platformcommons.platform.service.search.facade.assembler.CompanyMasterDataV2DTOAssembler;
import com.platformcommons.platform.service.search.facade.assembler.LogoDevCompanyDTOAssembler;
import com.platformcommons.platform.service.search.facade.client.LogoDevClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class CompanyMasterDataV2FacadeImpl implements CompanyMasterDataV2Facade {

    @Autowired
    private CompanyMasterDataV2Service service;

    @Autowired
    private CompanyMasterDataV2DTOAssembler assembler;

    @Autowired
    private LogoDevClient logoDevClient;

    @Autowired
    private LogoDevCompanyDTOAssembler logoDevCompanyDTOAssembler;

    @Override
    public PageDTO<CompanyMasterDataV2DTO> getBySearchText(String searchText, Integer page, Integer size) {
        size = size > 100 ? 100 : size;
        Page<CompanyMasterDataV2> result = service.getBySearchText(searchText, PageRequest.of(page,size));
        return new PageDTO<>(result.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public PageDTO<CompanyMasterDataV2DTO> getBySearchTextV2(String searchText, Integer page, Integer size) {
        PageDTO<CompanyMasterDataV2DTO> result = getBySearchText(searchText, page, size);

        if (result == null || result.getElements() == null || result.getElements().isEmpty()) {
            try {
                List<CompanyMasterDataV2DTO> logoDevResults = logoDevClient.searchCompany(searchText).stream()
                                .map(logoDevCompanyDTOAssembler::toCompanyMasterDataV2DTO)
                                .collect(Collectors.toList());

                return new PageDTO<>(new LinkedHashSet<>(logoDevResults), false, logoDevResults.size());
            } catch (Exception ex) {
                log.error("Logo.dev search failed for searchText={}", searchText, ex);
                return new PageDTO<>(Collections.emptySet(), false, 0);
            }
        }
        return result;
    }

    @Override
    public Map<String, CompanyMasterDataV2DTO> getByCompanyCodesInBulk(Set<String> companyCodes, String sortBy, String direction) {
        List<CompanyMasterDataV2> result = service.getByCompanyCodesInBulk(companyCodes,sortBy,direction);
        return result.stream()
                .collect(Collectors.toMap(CompanyMasterDataV2::getCode, it->assembler.toDTO(it),(a, b)->a, LinkedHashMap::new));
    }

    @Override
    public Set<CompanyMasterDataV2DTO> getByCompanyName(String companyName) {
        return assembler.toDTOs(service.getByCompanyName(companyName));
    }

    @Override
    public void deleteByCode(String code) {
        PlatformUtil.validateIfTenantAdminOrPlatformAdmin();
        service.deleteByCode(code);
    }

    @Override
    public PageDTO<CompanyMasterDataV2DTO> getAllByPagination(Integer page, Integer size, String sortBy, String direction) {
        size = size > 100 ? 100 : size;
        Page<CompanyMasterDataV2> result = service.getAllByPagination(PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
        return new PageDTO<>(result.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public CompanyMasterDataV2DTO save(CompanyMasterDataV2DTO body) {
        return assembler.toDTO(service.save(assembler.fromDTO(body)));
    }

    @Override
    public void putUpdate(CompanyMasterDataV2DTO body) {
        PlatformUtil.validateIfTenantAdminOrPlatformAdmin();
        service.putUpdate(assembler.fromDTO(body));
    }

    @Override
    public CompanyMasterDataV2DTO getByCode(String code) {
        return assembler.toDTO(service.getByCode(code));
    }
}
