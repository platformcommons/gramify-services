package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.CompanyCodesRequestDTO;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataV2DTO;

import java.util.Map;
import java.util.Set;

public interface CompanyMasterDataV2Facade {
    PageDTO<CompanyMasterDataV2DTO> getBySearchText(String searchText, Integer page, Integer size);

    PageDTO<CompanyMasterDataV2DTO> getBySearchTextV2(String searchText, Integer page, Integer size);

    CompanyMasterDataV2DTO save(CompanyMasterDataV2DTO body);

    void putUpdate(CompanyMasterDataV2DTO body);

    CompanyMasterDataV2DTO getByCode(String code);

    Map<String, CompanyMasterDataV2DTO> getByCompanyCodesInBulk(Set<String> companyCodes, String sortBy, String direction);

    Set<CompanyMasterDataV2DTO> getByCompanyName(String companyName);

    void deleteByCode(String code);

    PageDTO<CompanyMasterDataV2DTO> getAllByPagination(Integer page, Integer size, String sortBy, String direction);
}
