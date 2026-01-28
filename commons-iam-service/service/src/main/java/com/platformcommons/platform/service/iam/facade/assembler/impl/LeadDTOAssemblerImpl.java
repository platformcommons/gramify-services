package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.domain.Lead.LeadBuilder;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO.LeadDTOBuilder;

import com.platformcommons.platform.service.iam.facade.assembler.ExtraAttributeDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.LeadDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.TenantDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import java.util.LinkedHashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
public class LeadDTOAssemblerImpl implements LeadDTOAssembler {

    @Autowired
    private TenantDTOAssembler tenantDTOAssembler;

    @Autowired
    private RefDataDTOValidator refDataDTOValidator;

    @Autowired
    private ExtraAttributeDTOAssembler extraAttributeDTOAssembler;

    @Override
    public Lead fromDTO(LeadDTO leadDTO) {
        if ( leadDTO == null ) {
            return null;
        }

        LeadBuilder lead = Lead.builder();

        lead.id( leadDTO.getId() );
        lead.organizationName( leadDTO.getOrganizationName() );
        lead.email( leadDTO.getEmail() );
        lead.mobile( leadDTO.getMobile() );
        lead.type( leadDTO.getType() );
        lead.activationStatus( leadDTO.getActivationStatus() );
        lead.key( leadDTO.getKey() );
        lead.appContext( leadDTO.getAppContext() );
        lead.leadContactPersonName( leadDTO.getLeadContactPersonName() );
        lead.isMobileNumberChanged(leadDTO.getIsMobileNumberChanged());
        lead.useMobileAsLogin(leadDTO.getUseMobileAsUserLogin());
        lead.marketContext(leadDTO.getMarketContext());
        lead.firstName(leadDTO.getFirstName());
        lead.lastName(leadDTO.getLastName());
        lead.whatsappNumber(leadDTO.getWhatsappNumber());
        lead.whatsappNumberCountryCode(leadDTO.getWhatsappNumberCountryCode());
        lead.mobileCountryCode(leadDTO.getMobileCountryCode());
        lead.tenantId(leadDTO.getTenantId());
        lead.organisationType(refDataDTOValidator.fromDTO(leadDTO.getOrganisationType()));
        if (leadDTO.getOrganisationIndustries() != null) {
            lead.organisationIndustries(leadDTO.getOrganisationIndustries().stream()
                    .map(refDataDTOValidator::fromDTO)
                    .collect(Collectors.toSet()));
        }
        lead.organisationSize(leadDTO.getOrganisationSize());
        lead.organisationLogo(leadDTO.getOrganisationLogo());
        lead.website(leadDTO.getWebsite());
        lead.isEmailVerified(leadDTO.getIsEmailVerified());
        lead.isMobileVerified(leadDTO.getIsMobileVerified());
        lead.extraAttributes(leadDTO.getExtraAttributes()!=null ?
                leadDTO.getExtraAttributes().stream().map(it-> extraAttributeDTOAssembler.fromDTO(it))
                        .collect(Collectors.toCollection(LinkedHashSet::new)):null);


        return lead.build();
    }

    @Override
    public LeadDTO toDTO(Lead lead) {
        if ( lead == null ) {
            return null;
        }

        LeadDTOBuilder leadDTO = LeadDTO.builder();

        leadDTO.id( lead.getId() );
        leadDTO.organizationName( lead.getOrganizationName() );
        leadDTO.email( lead.getEmail() );
        leadDTO.mobile( lead.getMobile() );
        leadDTO.type( lead.getType() );
        leadDTO.activationStatus( lead.getActivationStatus() );
        leadDTO.key( lead.getKey() );
        leadDTO.appContext( lead.getAppContext() );
        leadDTO.registeredOn( lead.getRegisteredOn() );
        leadDTO.activatedOn( lead.getActivatedOn() );
        leadDTO.tenantCreatedOn( lead.getTenantCreatedOn() );
        leadDTO.leadContactPersonName( lead.getLeadContactPersonName() );
        leadDTO.useMobileAsUserLogin(lead.getUseMobileAsLogin());
        leadDTO.marketContext(lead.getMarketContext());
        leadDTO.firstName(lead.getFirstName());
        leadDTO.lastName(lead.getLastName());
        leadDTO.whatsappNumber(lead.getWhatsappNumber());
        leadDTO.whatsappNumberCountryCode(lead.getWhatsappNumberCountryCode());
        leadDTO.mobileCountryCode(lead.getMobileCountryCode());
        leadDTO.organisationType(refDataDTOValidator.toDTO(lead.getOrganisationType()));
        if (lead.getOrganisationIndustries() != null) {
            leadDTO.organisationIndustries(lead.getOrganisationIndustries().stream()
                    .map(refDataDTOValidator::toDTO)
                    .collect(Collectors.toSet()));
        }
        leadDTO.organisationSize(lead.getOrganisationSize());
        leadDTO.organisationLogo(lead.getOrganisationLogo());
        leadDTO.website(lead.getWebsite());
        leadDTO.isEmailVerified(lead.getIsEmailVerified());
        leadDTO.isMobileVerified(lead.getIsMobileVerified());
        leadDTO.tenantId(lead.getTenantId());
        leadDTO.extraAttributes(lead.getExtraAttributes()!=null ?
                lead.getExtraAttributes().stream().map(it-> extraAttributeDTOAssembler.toDTO(it))
                        .collect(Collectors.toCollection(LinkedHashSet::new)):null);

        return leadDTO.build();
    }

    @Override
    public LeadDTO toMaskedDTO(Lead lead) {
        if ( lead == null ) {
            return null;
        }

        LeadDTOBuilder leadDTO = LeadDTO.builder();

        leadDTO.id( lead.getId() );
        leadDTO.organizationName( lead.getOrganizationName() );
        leadDTO.email( lead.getEmail() );
        leadDTO.mobile( lead.getMobile()!=null ? maskMobileNumber(lead.getMobile()): null  );
        leadDTO.activationStatus( lead.getActivationStatus() );
        leadDTO.key( lead.getKey() );
        leadDTO.leadContactPersonName( lead.getLeadContactPersonName() );
        leadDTO.firstName(lead.getFirstName());
        leadDTO.lastName(lead.getLastName());
        leadDTO.tenantId(lead.getTenantId());
        leadDTO.type(lead.getType());
        return leadDTO.build();
    }

    public  String maskMobileNumber(String text) {
        // Remove all non-digit characters from the mobile number
        String digitsOnly = text.replaceAll("\\D+", "");

        // Mask all but the last three digits
        int maskedLength = digitsOnly.length() >= 3 ? (digitsOnly.length() - 3) : digitsOnly.length();
        String maskedDigits = digitsOnly.substring(0, maskedLength).replaceAll("\\d", "*");

        // Replace the masked digits in the original text
        return text.replaceAll("\\d", maskedDigits);
    }
}
