package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.iam.domain.TenantMetaAdditionalProperty;
import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.facade.assembler.TenantMetaAdditionalPropertyDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.TenantMetaConfigDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class TenantMetaConfigDTOAssemblerImpl implements TenantMetaConfigDTOAssembler {

    @Autowired
    private TenantMetaAdditionalPropertyDTOAssembler tenantMetaAdditionalPropertyDTOAssembler;

    @Override
    public TenantMetaConfigDTO toDTO(TenantMetaConfig entity) {
        if ( entity == null ) {
            return null;
        }

        TenantMetaConfigDTO.TenantMetaConfigDTOBuilder tenantMetaConfigDTO = TenantMetaConfigDTO.builder();

        tenantMetaConfigDTO.uuid( entity.getUuid() );
        tenantMetaConfigDTO.id( entity.getId() );
        tenantMetaConfigDTO.tenantId( entity.getTenantId() );
        tenantMetaConfigDTO.tenantLogin( entity.getTenantLogin() );
        tenantMetaConfigDTO.userLogin( entity.getUserLogin() );
        tenantMetaConfigDTO.password( entity.getPassword() );
        tenantMetaConfigDTO.actorContext( entity.getActorContext() );
        tenantMetaConfigDTO.tenantMetaAdditionalPropertySet( tenantMetaAdditionalPropertySetToTenantMetaAdditionalPropertyDTOSet( entity.getTenantMetaAdditionalPropertySet() ) );

        return tenantMetaConfigDTO.build();
    }


    @Override
    public TenantMetaConfig fromDTO(TenantMetaConfigDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TenantMetaConfig.TenantMetaConfigBuilder tenantMetaConfig = TenantMetaConfig.builder();

        tenantMetaConfig.uuid( dto.getUuid() );
        tenantMetaConfig.isActive( dto.getIsActive() );
        tenantMetaConfig.id( dto.getId() );
        tenantMetaConfig.tenantLogin( dto.getTenantLogin() );
        tenantMetaConfig.password( dto.getPassword() );
        tenantMetaConfig.actorContext( dto.getActorContext() );
        tenantMetaConfig.userLogin( dto.getUserLogin() );
        tenantMetaConfig.tenantMetaAdditionalPropertySet( tenantMetaAdditionalPropertyDTOSetToTenantMetaAdditionalPropertySet( dto.getTenantMetaAdditionalPropertySet() ) );

        return tenantMetaConfig.build();
    }

    protected Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertySetToTenantMetaAdditionalPropertyDTOSet(Set<TenantMetaAdditionalProperty> set) {
        if ( set == null ) {
            return null;
        }

        Set<TenantMetaAdditionalPropertyDTO> set1 = new HashSet<>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( TenantMetaAdditionalProperty tenantMetaAdditionalProperty : set ) {
            set1.add( tenantMetaAdditionalPropertyDTOAssembler.toDTO( tenantMetaAdditionalProperty ) );
        }

        return set1;
    }

    protected IAMUserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        IAMUserDTO.IAMUserDTOBuilder userDTO = IAMUserDTO.builder();
        userDTO.id( user.getId() );
        userDTO.firstName( user.getFirstName() );
        userDTO.lastName( user.getLastName() );

        return userDTO.build();
    }

    protected Set<IAMUserDTO> userSetToUserDTOSet(Set<User> set) {
        if ( set == null ) {
            return null;
        }

        Set<IAMUserDTO> set1 = new HashSet<>(Math.max((int) (set.size() / .75f) + 1, 16));
        for ( User user : set ) {
            set1.add( userToUserDTO( user ) );
        }

        return set1;
    }

    protected Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertyDTOSetToTenantMetaAdditionalPropertySet(Set<TenantMetaAdditionalPropertyDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<TenantMetaAdditionalProperty> set1 = new HashSet<>(Math.max((int) (set.size() / .75f) + 1, 16));
        for ( TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO : set ) {
            set1.add( tenantMetaAdditionalPropertyDTOAssembler.fromDTO( tenantMetaAdditionalPropertyDTO ) );
        }

        return set1;
    }
}
