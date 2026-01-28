package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.application.TenantFinanceDetailService;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import com.platformcommons.platform.service.iam.domain.TenantFinanceDetail;
import com.platformcommons.platform.service.iam.dto.TenantFinanceDetailDTO;
import com.platformcommons.platform.service.iam.facade.TenantFinanceDetailFacade;
import com.platformcommons.platform.service.iam.facade.assembler.TenantFinanceDetailDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class TenantFinanceDetailFacadeImp implements TenantFinanceDetailFacade {

    @Autowired
    private TenantFinanceDetailService service;

    @Autowired
    private TenantFinanceDetailDTOAssembler assembler;

    @Override
    public TenantFinanceDetailDTO createForLoggedInTenant(TenantFinanceDetailDTO tenantFinanceDetailDTO){
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        PlatformUtil.validateTenantAdmin();
        TenantFinanceDetail savedEntity = service.createForLoggedInTenant(tenantId,assembler.fromDTO(tenantFinanceDetailDTO));
        return assembler.toDTO(savedEntity,Boolean.FALSE);
    }

    @Override
    public void patchForLoggedInTenant( TenantFinanceDetailDTO tenantFinanceDetailDTO){
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        PlatformUtil.validateTenantAdmin();
        TenantFinanceDetail tenantFinanceDetail = assembler.fromDTO(tenantFinanceDetailDTO);
        service.patchForLoggedInTenant(tenantFinanceDetail,tenantId);
    }

    @Override
    public void deleteById(Long id,String inactiveReason){
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        PlatformUtil.validateTenantAdmin();
        service.deleteById(id,inactiveReason,tenantId);
    }

    @Override
    public PageDTO<TenantFinanceDetailDTO> getPageByTenantId(Long tenantId, Integer page, Integer size, String sortBy, String direction) {
        if (sortBy == null) {
            sortBy = "id";
            direction = "desc";
        }
        Pageable pageRequest = PageRequest.of(page, size, JPAUtility.convertToSort(sortBy, direction));
        Page<TenantFinanceDetail> results = service.getPageByTenantId(tenantId, pageRequest);
        Boolean isAdmin = PlatformUtil.isTenantAdmin(tenantId);
        Boolean maskSensitiveData = !isAdmin;

        return new PageDTO<>(results.getContent()
                .stream()
                .map(it-> assembler.toDTO(it,maskSensitiveData))
                .collect(Collectors.toCollection(LinkedHashSet::new)),
                results.hasNext(), results.getTotalElements());
    }

}
