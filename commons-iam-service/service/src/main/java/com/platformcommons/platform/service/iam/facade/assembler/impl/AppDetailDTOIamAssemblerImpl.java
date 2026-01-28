package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.iam.domain.AppDetail;
import com.platformcommons.platform.service.iam.dto.AppDetailDTO;
import com.platformcommons.platform.service.iam.facade.assembler.obo.AppDetailDTOIamAssembler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppDetailDTOIamAssemblerImpl implements AppDetailDTOIamAssembler {

    @Override
    public AppDetail fromDTO(AppDetailDTO appDetailDTO) {
        if(appDetailDTO == null) {
            return null;
        }
        return AppDetail.builder()
                .id(appDetailDTO.getId())
                .appCode(appDetailDTO.getAppCode())
                .appKey(UUID.randomUUID().toString())
                .supportUserLogin(appDetailDTO.getSupportUserLogin())
                .supportUserPassword(appDetailDTO.getSupportUserPassword())
                .tenantLogin(appDetailDTO.getTenantLogin())
                .openApis(appDetailDTO.getOpenApis())
                .build();
    }

    @Override
    public AppDetailDTO toDTO(AppDetail appDetail) {
        if(appDetail == null) {
            return null;
        }
        return AppDetailDTO.builder()
                .id(appDetail.getId())
                .appCode(appDetail.getAppCode())
                .appKey(appDetail.getAppKey())
                .tenantLogin(appDetail.getTenantLogin())
                .openApis(appDetail.getOpenApis())
                .build();
    }
}
