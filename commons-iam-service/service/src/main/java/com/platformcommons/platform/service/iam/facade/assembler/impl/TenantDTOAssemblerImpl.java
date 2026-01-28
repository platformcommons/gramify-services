package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.facade.assembler.LeadDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.TenantDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.TenantProfileDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class TenantDTOAssemblerImpl implements TenantDTOAssembler {

    @Autowired
    private LeadDTOAssembler leadDTOAssembler;

    @Autowired
    private TenantProfileDTOAssembler tenantProfileDTOAssembler;

    @Override
    public Tenant fromDTO(TenantDTO tenantDTO) {
        if ( tenantDTO == null ) {
            return null;
        }

        Tenant.TenantBuilder tenant = Tenant.builder();

        tenant.id( tenantDTO.getId() );
        tenant.tenantUUID( tenantDTO.getTenantUUID() );
        tenant.tenantLogin( tenantDTO.getTenantLogin() );
        tenant.tenantDomain( tenantDTO.getTenantDomain() );
        tenant.tenantName( tenantDTO.getTenantName() );
        tenant.email( tenantDTO.getEmail() );
        tenant.mobile( tenantDTO.getMobile() );
        tenant.iconpic( tenantDTO.getIconpic() );
        tenant.description( tenantDTO.getDescription() );
        tenant.website( tenantDTO.getWebsite() );
        tenant.tenantType( tenantDTO.getTenantType() );
        tenant.onBoardedDateTime( tenantDTO.getOnBoardedDateTime() );
        tenant.tenantStatus( tenantDTO.getTenantStatus() );
        tenant.useMobileAsLogin( tenantDTO.getUseMobileAsLogin() );
        tenant.isActive( tenantDTO.getIsActive() );
        tenant.slug( tenantDTO.getSlug() );
        tenant.tenantProfile(tenantProfileDTOAssembler.fromDTO(tenantDTO.getTenantProfile()));

        return tenant.build();
    }

    @Override
    public TenantDTO toDTO(Tenant tenant) {
        if ( tenant == null ) {
            return null;
        }

        TenantDTO.TenantDTOBuilder tenantDTO = TenantDTO.builder();

        tenantDTO.id( tenant.getId() );
        tenantDTO.tenantUUID( tenant.getTenantUUID() );
        tenantDTO.tenantLogin( tenant.getTenantLogin() );
        tenantDTO.tenantDomain( tenant.getTenantDomain() );
        tenantDTO.tenantName( tenant.getTenantName() );
        tenantDTO.email( tenant.getEmail() );
        tenantDTO.mobile( tenant.getMobile() );
        tenantDTO.iconpic( tenant.getIconpic() );
        tenantDTO.description( tenant.getDescription() );
        tenantDTO.website( tenant.getWebsite() );
        tenantDTO.tenantType( tenant.getTenantType() );
        tenantDTO.onBoardedDateTime( tenant.getOnBoardedDateTime() );
        tenantDTO.tenantStatus( tenant.getTenantStatus() );
        tenantDTO.isActive( tenant.getIsActive() );
        tenantDTO.slug( tenant.getSlug() );
        tenantDTO.tenantProfile(tenantProfileDTOAssembler.toDTO(tenant.getTenantProfile()));


        return tenantDTO.build();
    }

    @Override
    public List<TenantDTO> toDTOs(List<Tenant> tenantList) {
        List<TenantDTO> list = new ArrayList<>();
        if(tenantList != null) {
            for(Tenant tenant : tenantList) {
                list.add(toDTO(tenant));
            }
        }
        return list;
    }

}