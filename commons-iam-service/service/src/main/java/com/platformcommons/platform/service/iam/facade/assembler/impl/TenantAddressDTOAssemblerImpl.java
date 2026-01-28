package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import com.platformcommons.platform.service.iam.domain.TenantAddress;
import com.platformcommons.platform.service.iam.dto.TenantAddressDTO;
import com.platformcommons.platform.service.iam.facade.assembler.TenantAddressDTOAssembler;
import com.platformcommons.platform.service.sdk.person.interfaces.assesmbler.AddressDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TenantAddressDTOAssemblerImpl implements TenantAddressDTOAssembler {

    @Autowired
    private RefDataDTOValidator refDataDTOValidator;

    @Autowired
    private AddressDTOAssembler addressDTOAssembler;


    @Override
    public TenantAddress fromDTO(TenantAddressDTO tenantAddressDTO) {
        if (tenantAddressDTO == null){
            return null;
        }

        TenantAddress.TenantAddressBuilder tenantAddress = TenantAddress.builder();
        tenantAddress.id(tenantAddressDTO.getId());
        tenantAddress.uuid(tenantAddressDTO.getUuid());
        tenantAddress.isActive(tenantAddressDTO.getIsActive());
        tenantAddress.inactiveReason( tenantAddressDTO.getInactiveReason());
        tenantAddress.addressType(refDataDTOValidator.fromDTO(tenantAddressDTO.getAddressType()));
        tenantAddress.address(addressDTOAssembler.fromDTO(tenantAddressDTO.getAddressDTO()));
        return tenantAddress.build();
    }

    @Override
    public TenantAddressDTO toDTO(TenantAddress tenantAddress) {
        if (tenantAddress == null){
            return null;
        }

        TenantAddressDTO.TenantAddressDTOBuilder tenantAddressDTO = TenantAddressDTO.builder();
        tenantAddressDTO.id(tenantAddress.getId());
        tenantAddressDTO.uuid(tenantAddress.getUuid());
        tenantAddressDTO.isActive(tenantAddress.getIsActive());
        tenantAddressDTO.inactiveReason( tenantAddress.getInactiveReason());
        tenantAddressDTO.addressType(refDataDTOValidator.toDTO(tenantAddress.getAddressType()));
        tenantAddressDTO.addressDTO(addressDTOAssembler.toDTO(tenantAddress.getAddress()));
        return tenantAddressDTO.build();
    }

    @Override
    public List<TenantAddress> fromDTOs(List<TenantAddressDTO> tenantAddressDTOs) {
        if ( tenantAddressDTOs == null ) {
            return null;
        }

        List<TenantAddress> list = new ArrayList<>( tenantAddressDTOs.size() );
        for ( TenantAddressDTO tenantAddressDTO : tenantAddressDTOs ) {
            list.add( fromDTO( tenantAddressDTO ) );
        }

        return list;
    }

    @Override
    public List<TenantAddressDTO> toDTOs(List<TenantAddress> tenantAddresses) {
        if ( tenantAddresses == null ) {
            return null;
        }

        List<TenantAddressDTO> list = new ArrayList<>( tenantAddresses.size() );
        for ( TenantAddress tenantAddress : tenantAddresses ) {
            list.add( toDTO( tenantAddress ) );
        }

        return list;
    }
}