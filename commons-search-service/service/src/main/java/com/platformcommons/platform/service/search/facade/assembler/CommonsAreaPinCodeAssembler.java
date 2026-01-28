package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.CommonsAreaPinCode;
import com.platformcommons.platform.service.search.dto.CommonsAreaPinCodeDTO;
import org.springframework.stereotype.Component;

@Component
public class CommonsAreaPinCodeAssembler {
    public CommonsAreaPinCodeDTO toDTO(CommonsAreaPinCode entity){
        return CommonsAreaPinCodeDTO.builder()
                .id(entity.getId())
                .areaCode(entity.getAreaCode())
                .districtCode(entity.getDistrictCode())
                .stateCode(entity.getStateCode())
                .countryCode(entity.getCountryCode())
                .districtLabel(entity.getDistrictLabel())
                .stateLabel(entity.getStateLabel())
                .countryLabel(entity.getCountryLabel())
                .label(entity.getLabel())
                .alternativeLabel(entity.getAlternativeLabel())
                .build();
    }

    public CommonsAreaPinCode fromDTO(CommonsAreaPinCodeDTO dto){
        return CommonsAreaPinCode.builder()
                .id(dto.getId())
                .areaCode(dto.getAreaCode())
                .districtCode(dto.getDistrictCode())
                .stateCode(dto.getStateCode())
                .countryCode(dto.getCountryCode())
                .districtLabel(dto.getDistrictLabel())
                .stateLabel(dto.getStateLabel())
                .countryLabel(dto.getCountryLabel())
                .label(dto.getLabel())
                .alternativeLabel(dto.getAlternativeLabel())
                .build();
    }
}