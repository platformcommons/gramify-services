package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.iam.domain.TenantFinanceDetail;
import com.platformcommons.platform.service.iam.dto.TenantFinanceDetailDTO;
import com.platformcommons.platform.service.iam.facade.assembler.AttachmentDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.TenantFinanceDetailDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TenantFinanceDetailDTOAssemblerImpl implements TenantFinanceDetailDTOAssembler {

    @Autowired
    private AttachmentDTOAssembler attachmentDTOAssembler;

    @Override
    public TenantFinanceDetail fromDTO(TenantFinanceDetailDTO tenantFinanceDetailDTO) {
        if ( tenantFinanceDetailDTO == null ) {
            return null;
        }

        TenantFinanceDetail.TenantFinanceDetailBuilder tenantFinanceDetail = TenantFinanceDetail.builder();

        tenantFinanceDetail.uuid( tenantFinanceDetailDTO.getUuid() );
        tenantFinanceDetail.isActive( tenantFinanceDetailDTO.getIsActive() );
        tenantFinanceDetail.inactiveReason( tenantFinanceDetailDTO.getInactiveReason() );
        tenantFinanceDetail.id( tenantFinanceDetailDTO.getId() );
        tenantFinanceDetail.accountHolderName( tenantFinanceDetailDTO.getAccountHolderName() );
        tenantFinanceDetail.accountNumber( tenantFinanceDetailDTO.getAccountNumber() );
        tenantFinanceDetail.ifscCode( tenantFinanceDetailDTO.getIfscCode() );
        tenantFinanceDetail.bankName( tenantFinanceDetailDTO.getBankName() );
        tenantFinanceDetail.isVerified( tenantFinanceDetailDTO.getIsVerified() );
        tenantFinanceDetail.bankAccountType( tenantFinanceDetailDTO.getBankAccountType() );
        tenantFinanceDetail.attachments( attachmentDTOAssembler.fromDTOs( tenantFinanceDetailDTO.getAttachments() ) );

        return tenantFinanceDetail.build();
    }

    @Override
    public TenantFinanceDetailDTO toDTO(TenantFinanceDetail tenantFinanceDetail, Boolean maskSensitiveData) {
        if ( tenantFinanceDetail == null ) {
            return null;
        }

        TenantFinanceDetailDTO.TenantFinanceDetailDTOBuilder tenantFinanceDetailDTO = TenantFinanceDetailDTO.builder();

        tenantFinanceDetailDTO.uuid( tenantFinanceDetail.getUuid() );
        tenantFinanceDetailDTO.isActive( tenantFinanceDetail.getIsActive() );
        tenantFinanceDetailDTO.inactiveReason( tenantFinanceDetail.getInactiveReason() );
        tenantFinanceDetailDTO.id( tenantFinanceDetail.getId() );
        tenantFinanceDetailDTO.accountHolderName( tenantFinanceDetail.getAccountHolderName() );
        tenantFinanceDetailDTO.accountNumber( tenantFinanceDetail.getAccountNumber() );
        tenantFinanceDetailDTO.ifscCode( tenantFinanceDetail.getIfscCode() );
        tenantFinanceDetailDTO.bankName( tenantFinanceDetail.getBankName() );
        tenantFinanceDetailDTO.isVerified( tenantFinanceDetail.getIsVerified() );
        tenantFinanceDetailDTO.bankAccountType( tenantFinanceDetail.getBankAccountType() );
        tenantFinanceDetailDTO.attachments( attachmentDTOAssembler.toDTOs( tenantFinanceDetail.getAttachments() ) );

        if(Boolean.TRUE.equals(maskSensitiveData)) {
            tenantFinanceDetailDTO.accountNumber( maskString(tenantFinanceDetail.getAccountNumber()) );
            tenantFinanceDetailDTO.ifscCode( maskString(tenantFinanceDetail.getIfscCode()) );
            tenantFinanceDetailDTO.attachments(null);
        }

        return tenantFinanceDetailDTO.build();
    }

    @Override
    public List<TenantFinanceDetail> fromDTOs(List<TenantFinanceDetailDTO> tenantFinanceDetailDTOS) {
        if ( tenantFinanceDetailDTOS == null ) {
            return null;
        }

        List<TenantFinanceDetail> list = new ArrayList<TenantFinanceDetail>( tenantFinanceDetailDTOS.size() );
        for ( TenantFinanceDetailDTO tenantFinanceDetailDTO : tenantFinanceDetailDTOS ) {
            list.add( fromDTO( tenantFinanceDetailDTO ) );
        }

        return list;
    }

    @Override
    public List<TenantFinanceDetailDTO> toDTOs(List<TenantFinanceDetail> tenantFinanceDetails, Boolean maskSensitiveData) {
        if ( tenantFinanceDetails == null ) {
            return null;
        }

        List<TenantFinanceDetailDTO> list = new ArrayList<TenantFinanceDetailDTO>( tenantFinanceDetails.size() );
        for ( TenantFinanceDetail tenantFinanceDetail : tenantFinanceDetails ) {
            list.add( toDTO( tenantFinanceDetail, maskSensitiveData ) );
        }

        return list;
    }


    public static String maskString(String accountNumber) {
        String finalValue = null;

        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            accountNumber = accountNumber.trim();
            int length = accountNumber.length();

            if (length <= 3) {
                return repeat("*", length);
            }
            else {
                String maskedPart = repeat("*", length - 3);
                String visiblePart = accountNumber.substring(length - 3);

                return maskedPart + visiblePart;
            }

        }

        return finalValue;
    }

    private static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

}
