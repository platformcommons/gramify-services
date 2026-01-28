package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.security.filter.appkey.AppDetailsDTO;
import com.platformcommons.platform.service.iam.domain.AppDetail;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppDetailDTOAssembler {

    public AppDetail fromDTO(AppDetailsDTO appDetailsDTO){
        return AppDetail.builder()
                .id(appDetailsDTO.getId())
                .appCode(appDetailsDTO.getAppCode())
                .appKey(UUID.randomUUID().toString())  //TODO to be verified
                .openApis(appDetailsDTO.getOpenApis())
                .build();
    }

    public AppDetailsDTO toDTO(AppDetail appDetail){
        return AppDetailsDTO.builder()
                .id(appDetail.getId())
                .appCode(appDetail.getAppCode())
                .apiKey(appDetail.getAppKey())
                .openApis(appDetail.getOpenApis())
                .build();
    }
}
