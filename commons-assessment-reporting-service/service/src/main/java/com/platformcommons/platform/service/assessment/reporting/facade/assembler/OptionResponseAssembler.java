package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.OptionResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class OptionResponseAssembler {

    public OptionResponseDTO toDTO(Map<String,Object> result){
        return OptionResponseDTO.builder()
                .optionId(result.get("optionId")==null?null:((Number)result.get("optionId")).longValue())
                .responseCount(result.get("responseCount")==null?0L:((Number)result.get("responseCount")).longValue())
                .build();
    }


}