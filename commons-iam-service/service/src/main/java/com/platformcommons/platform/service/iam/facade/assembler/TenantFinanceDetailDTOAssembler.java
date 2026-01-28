package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantFinanceDetail;
import com.platformcommons.platform.service.iam.dto.TenantFinanceDetailDTO;
import org.mapstruct.Mapper;

import java.util.List;

public interface TenantFinanceDetailDTOAssembler {

    TenantFinanceDetail fromDTO(TenantFinanceDetailDTO tenantFinanceDetailDTO);

    TenantFinanceDetailDTO toDTO(TenantFinanceDetail tenantFinanceDetail,Boolean maskSensitiveData);

    List<TenantFinanceDetail> fromDTOs(List<TenantFinanceDetailDTO> tenantFinanceDetailDTOS);

    List<TenantFinanceDetailDTO> toDTOs(List<TenantFinanceDetail> tenantFinanceDetails, Boolean maskSensitiveData);

}
