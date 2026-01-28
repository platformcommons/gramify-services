package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.CompanyMasterDataV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CompanyMasterDataV2Service {
    Page<CompanyMasterDataV2> getBySearchText(String searchText, Pageable pageable);

    CompanyMasterDataV2 save(CompanyMasterDataV2 companyMasterDataV2);

    void putUpdate(CompanyMasterDataV2 companyMasterDataV2);

    CompanyMasterDataV2 getByCode(String code);

    List<CompanyMasterDataV2> getByCompanyCodesInBulk(Set<String> companyCodes, String sortBy, String direction);

    Set<CompanyMasterDataV2> getByCompanyName(String companyName);

    void deleteByCode(String code);

    Page<CompanyMasterDataV2> getAllByPagination(Pageable pageable);
}
