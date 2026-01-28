package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.TenantUser;
import com.platformcommons.platform.service.search.dto.TenantUserDTO;
import org.springframework.stereotype.Component;

@Component
public class TenantUserDTOAssembler {

    public TenantUserDTO toDTO(TenantUser tenantUser){
        return TenantUserDTO.builder()
                .id(tenantUser.getId())
                .userLogin(tenantUser.getUser_login())
                .completeName(tenantUser.getCompleteName())
                .mobile(tenantUser.getMobile())
                .email(tenantUser.getEmail())
                .gender(tenantUser.getGender())
                .city(tenantUser.getCity())
                .state(tenantUser.getState())
                .pincode(tenantUser.getPincode())
                .tenantId(tenantUser.getTenant_id())
                .tenantLogin(tenantUser.getTenant_login())
                .rolesCodes(tenantUser.getRoles_codes()).build();
    }

    public TenantUser fromDTO(TenantUserDTO tenantUserDTO){
        return TenantUser.builder()
                .id(tenantUserDTO.getId())
                .user_login(tenantUserDTO.getUserLogin())
                .completeName(tenantUserDTO.getCompleteName())
                .mobile(tenantUserDTO.getMobile())
                .email(tenantUserDTO.getEmail())
                .gender(tenantUserDTO.getGender())
                .city(tenantUserDTO.getCity())
                .state(tenantUserDTO.getState())
                .pincode(tenantUserDTO.getPincode())
                .tenant_id(tenantUserDTO.getTenantId())
                .tenant_login(tenantUserDTO.getTenantLogin())
                .roles_codes(tenantUserDTO.getRolesCodes()).build();
    }
}
