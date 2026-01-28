package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.CompanyMasterDataV2;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataV2DTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CompanyMasterDataV2DTOAssembler {

    CompanyMasterDataV2DTO toDTO(CompanyMasterDataV2 companyMasterDataV2);

    Set<CompanyMasterDataV2DTO> toDTOs(Set<CompanyMasterDataV2> entities);

    CompanyMasterDataV2 fromDTO(CompanyMasterDataV2DTO commonsLocationDTO);
}
