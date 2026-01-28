package com.platformcommons.platform.service.iam.facade.impl;

import com.netflix.discovery.converters.Auto;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.application.TenantPointOfContactService;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import com.platformcommons.platform.service.iam.domain.TenantPointOfContact;
import com.platformcommons.platform.service.iam.dto.TenantPointOfContactDTO;
import com.platformcommons.platform.service.iam.facade.TenantPointOfContactFacade;
import com.platformcommons.platform.service.iam.facade.assembler.TenantPointOfContactDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class TenantPointOfContactFacadeImp implements TenantPointOfContactFacade {

    @Autowired
    private TenantPointOfContactService service;

    @Autowired
    private TenantPointOfContactDTOAssembler assembler;

    @Override
    public TenantPointOfContactDTO getForLoggedInTenant(Long id){
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        PlatformUtil.validateTenantAdmin();
        TenantPointOfContact tenantPointOfContact = service.getByIdAndTenantId(tenantId, id);
        return assembler.toDTO(tenantPointOfContact);
    }

    @Override
    public TenantPointOfContactDTO createForLoggedInTenant(TenantPointOfContactDTO tenantPointOfContactDTO){
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        PlatformUtil.validateTenantAdmin();
        TenantPointOfContact savedEntity = service.post(tenantId, assembler.fromDTO(tenantPointOfContactDTO));

        return assembler.toDTO(savedEntity);
    }

    @Override
    public void patchForLoggedInTenant(TenantPointOfContactDTO tenantPointOfContactDTO){
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        PlatformUtil.validateTenantAdmin();
        TenantPointOfContact tenantPointOfContact = assembler.fromDTO(tenantPointOfContactDTO);
        service.patch(tenantPointOfContact, tenantId);
    }

    @Override
    public void deleteById(Long id,String inactiveReason){
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        PlatformUtil.validateTenantAdmin();
        service.deleteById(id, inactiveReason, tenantId);
    }

    @Override
    public PageDTO<TenantPointOfContactDTO> getPageByTenantId(Long tenantId, Integer page, Integer size, String sortBy, String direction) {
        if (sortBy == null) {
            sortBy = "id";
            direction = "asc";
        }
        Pageable pageRequest = PageRequest.of(page, size, JPAUtility.convertToSort(sortBy, direction));
        Page<TenantPointOfContact> results = service.getPageByTenantId(tenantId, pageRequest);
        return new PageDTO<>(results.getContent()
                .stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),
                results.hasNext(), results.getTotalElements());
    }
}
