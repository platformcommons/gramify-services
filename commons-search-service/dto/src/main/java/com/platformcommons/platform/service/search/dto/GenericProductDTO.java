package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseDTO;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericProductDTO extends BaseDTO implements Serializable {

    private String id;

    private Long genericProductId;

    private String genericProductCode;

    private String genericProductName;

    private Set<String> categoryCodes;

    private Set<String> categoryNames;

    private Set<String> subCategoryCodes;

    private Set<String> subCategoryNames;

    private Long tenantId;

    private Long marketId;

    private String marketCode;
    
    private String marketName;

    private Boolean isActive;

    @Builder
    public GenericProductDTO(String id,Long genericProductId, String genericProductCode, String genericProductName,
                             Set<String> categoryCodes, Set<String> categoryNames, Set<String> subCategoryCodes, Set<String> subCategoryNames,
                             Long tenantId, Long marketId, String marketCode, String marketName, Boolean isActive) {
        this.id = id;
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
        this.categoryCodes = categoryCodes;
        this.categoryNames = categoryNames;
        this.subCategoryCodes = subCategoryCodes;
        this.subCategoryNames = subCategoryNames;
        this.tenantId = tenantId;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.isActive = isActive;
    }
}
