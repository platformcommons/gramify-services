package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import com.platformcommons.platform.service.iam.domain.TenantProfile;
import com.platformcommons.platform.service.iam.dto.TenantProfileDTO;
import com.platformcommons.platform.service.iam.facade.assembler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TenantProfileDTOAssemblerImpl implements TenantProfileDTOAssembler {


    @Autowired
    private RefDataDTOValidator refDataDTOValidator;

    @Autowired
    private TenantAddressDTOAssembler tenantAddressDTOAssembler;

    @Autowired
    private TenantSocialMediaDTOAssembler tenantSocialMediaDTOAssembler;

    @Autowired
    private AttachmentDTOAssembler attachmentDTOAssembler;

    @Autowired
    private ExtraAttributeDTOAssembler extraAttributeDTOAssembler;

    @Override
    public TenantProfile fromDTO(TenantProfileDTO tenantProfileDTO) {
        if ( tenantProfileDTO == null ) {
            return null;
        }

        TenantProfile.TenantProfileBuilder tenantProfile = TenantProfile.builder();

        tenantProfile.id( tenantProfileDTO.getId() );
        tenantProfile.uuid( tenantProfileDTO.getUuid() );
        tenantProfile.isActive( tenantProfileDTO.getIsActive() );
        tenantProfile.inactiveReason( tenantProfileDTO.getInactiveReason());
        tenantProfile.headline( tenantProfileDTO.getHeadline() );
        tenantProfile.yearOfEstablishment( tenantProfileDTO.getYearOfEstablishment() );
        tenantProfile.coverImage( tenantProfileDTO.getCoverImage() );
        tenantProfile.panNumber(tenantProfileDTO.getPanNumber());
        tenantProfile.registrationNumber(tenantProfileDTO.getRegistrationNumber());
        tenantProfile.legalName(tenantProfileDTO.getLegalName());
        tenantProfile.type(refDataDTOValidator.fromDTO(tenantProfileDTO.getType()));
        tenantProfile.size(refDataDTOValidator.fromDTO(tenantProfileDTO.getSize()));
        if (tenantProfileDTO.getIndustries() != null) {
            tenantProfile.industries(tenantProfileDTO.getIndustries().stream()
                    .map(refDataDTOValidator::fromDTO)
                    .collect(Collectors.toSet()));
        }
        if (tenantProfileDTO.getTenantAddressDTOS() != null) {
            tenantProfile.tenantAddresses(tenantAddressDTOAssembler.fromDTOs(tenantProfileDTO.getTenantAddressDTOS()));
        }
        if (tenantProfileDTO.getTenantSocialMediaDTOS() != null) {
            tenantProfile.tenantSocialMedia(tenantSocialMediaDTOAssembler.fromDTOs(tenantProfileDTO.getTenantSocialMediaDTOS()));
        }
        if (tenantProfileDTO.getExtraAttributes() != null) {
            tenantProfile.extraAttributes(extraAttributeDTOAssembler.fromDTOs(tenantProfileDTO.getExtraAttributes()));
        }
        return tenantProfile.build();
    }


    @Override
    public TenantProfileDTO toDTO(TenantProfile tenantProfile) {
        if ( tenantProfile == null ) {
            return null;
        }

        TenantProfileDTO.TenantProfileDTOBuilder tenantProfileDTO = TenantProfileDTO.builder();

        tenantProfileDTO.id( tenantProfile.getId() );
        tenantProfileDTO.uuid( tenantProfile.getUuid() );
        tenantProfileDTO.isActive( tenantProfile.getIsActive() );
        tenantProfileDTO.inactiveReason( tenantProfile.getInactiveReason());
        tenantProfileDTO.headline( tenantProfile.getHeadline() );
        tenantProfileDTO.yearOfEstablishment( tenantProfile.getYearOfEstablishment() );
        tenantProfileDTO.coverImage( tenantProfile.getCoverImage() );
        tenantProfileDTO.panNumber(tenantProfile.getPanNumber());
        tenantProfileDTO.registrationNumber(tenantProfile.getRegistrationNumber());
        tenantProfileDTO.legalName(tenantProfile.getLegalName());
        tenantProfileDTO.type(refDataDTOValidator.toDTO(tenantProfile.getType()));
        tenantProfileDTO.size(refDataDTOValidator.toDTO(tenantProfile.getSize()));
        if (tenantProfile.getIndustries() != null) {
            tenantProfileDTO.industries(tenantProfile.getIndustries().stream()
                    .map(refDataDTOValidator::toDTO)
                    .collect(Collectors.toSet()));
        }
        if (tenantProfile.getTenantAddresses() != null) {
            tenantProfileDTO.tenantAddressDTOS(tenantAddressDTOAssembler.toDTOs(tenantProfile.getTenantAddresses()));
        }
        if (tenantProfile.getTenantSocialMedia() != null) {
            tenantProfileDTO.tenantSocialMediaDTOS(tenantSocialMediaDTOAssembler.toDTOs(tenantProfile.getTenantSocialMedia()));
        }
        if (tenantProfile.getExtraAttributes() != null) {
            tenantProfileDTO.extraAttributes(extraAttributeDTOAssembler.toDTOs(tenantProfile.getExtraAttributes()));
        }
        return tenantProfileDTO.build();
    }

}