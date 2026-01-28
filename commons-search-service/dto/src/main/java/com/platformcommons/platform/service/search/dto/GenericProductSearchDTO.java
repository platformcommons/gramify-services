package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericProductSearchDTO extends BaseDTO {

    public Long genericProductId;

    public String genericProductCode;

    public String genericProductName;


    @Builder
    public GenericProductSearchDTO(Long genericProductId, String genericProductCode, String genericProductName) {

        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
    }
}
