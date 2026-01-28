package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.product.dto.ClassificationEventDTO;
import com.platformcommons.platform.service.product.dto.GenericProductEventDTO;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenericProductEventMapper {

    public GenericProduct buildGenericProduct(GenericProductEventDTO genericProductEventDTO) {

        assert genericProductEventDTO != null;
        return GenericProduct.builder()
                .id(String.valueOf(genericProductEventDTO.getGenericProductId()))
                .genericProductId(genericProductEventDTO.getGenericProductId())
                .genericProductCode(genericProductEventDTO.getGenericProductCode())
                .genericProductName(getEngLabel(genericProductEventDTO.getGenericProductName()))
                .categoryCodes(getClassificationCodes(genericProductEventDTO.getCategories()))
                .categoryNames(getClassificationNames(genericProductEventDTO.getCategories()))
                .subCategoryCodes(getClassificationCodes(genericProductEventDTO.getSubCategories()))
                .subCategoryNames(getClassificationNames(genericProductEventDTO.getSubCategories()))
                .tenantId(genericProductEventDTO.getTenantId())
                .marketId(genericProductEventDTO.getMarketId())
                .marketCode(genericProductEventDTO.getMarketCode())
                .marketName(genericProductEventDTO.getMarketName())
                .isActive(Boolean.TRUE)
                .build();
    }

    private String getEngLabel(Set<MLTextDTO> mlTextDTOs){
        MLTextDTO mlTextDTO= mlTextDTOs!=null ? mlTextDTOs.stream().filter(it-> it.getLanguageCode().equals("ENG"))
                .findFirst().orElseThrow(null): null;
        return mlTextDTO!=null ? mlTextDTO.getText(): null;
    }

    private Set<String> getClassificationCodes(Set<ClassificationEventDTO> classificationEventDTOS){
        return classificationEventDTOS!=null ? classificationEventDTOS.stream().map(ClassificationEventDTO::getClassificationCode)
                .collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

    private Set<String> getClassificationNames(Set<ClassificationEventDTO> classificationEventDTOS){
        return classificationEventDTOS!=null ? classificationEventDTOS.stream().map(it->this.getEngLabel(it.getNames()))
                .collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

}
