package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.market.dto.CustomProductSKUDTO;
import com.platformcommons.platform.service.market.dto.PublishProductDTO;
import com.platformcommons.platform.service.product.dto.ClassificationEventDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.facade.client.GenericProductVarietyClientV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenericProductVarietyEventMapper {


    @Value("${commons.platform.service.search-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;
    @Autowired
    private GenericProductVarietyClientV2 genericProductVarietyClient;

    public GenericProductVariety fromEventDTO(PublishProductDTO publishProductDTO,
                                              GenericProductVarietyEventDTO genericProductVarietyEventDTO){


        assert genericProductVarietyEventDTO != null;
        return GenericProductVariety.builder()
                .id(generateGenericProductVarietySearchId(genericProductVarietyEventDTO.getGenericProductVarietyId(),
                        publishProductDTO.getChannelId(),publishProductDTO.getMarketId()))
                .genericProductVarietyId(genericProductVarietyEventDTO.getGenericProductVarietyId())
                .genericProductVarietyCode(genericProductVarietyEventDTO.getGenericProductVarietyCode())
                .genericProductVarietyName(getEngLabel(genericProductVarietyEventDTO.getGenericProductVarietyName()))
                .genericProductId(genericProductVarietyEventDTO.getGenericProductId())
                .genericProductCode(genericProductVarietyEventDTO.getGenericProductCode())
                .genericProductName(getEngLabel(genericProductVarietyEventDTO.getGenericProductName()))
                .categoryCodes(getClassificationCodes(genericProductVarietyEventDTO.getCategories()))
                .categoryNames(getClassificationNames(genericProductVarietyEventDTO.getCategories()))
                .subCategoryCodes(getClassificationCodes(genericProductVarietyEventDTO.getSubCategories()))
                .subCategoryNames(getClassificationNames(genericProductVarietyEventDTO.getSubCategories()))
                .tenantId(publishProductDTO.getTenantId())
                .productNames(Collections.singleton(getEngLabel(publishProductDTO.getCustomProductDTO().getName())))
                .maxPrice(maxPrice(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .minPrice(minPrice(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .currencyCode(currencyCode(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .minAvailableQuantity(minQuantity(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .maxAvailableQuantity(maxQuantity(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .totalAvailableQuantity(totalQuantity((publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs())))
                .availableQuantityUOM(quantityUOM(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .traderIds(Collections.singleton(publishProductDTO.getTraderId()))
                .traderCount(1L)
                .channelId(publishProductDTO.getChannelId())
                .channelCode(publishProductDTO.getChannelCode())
                .channelName(publishProductDTO.getChannelName())
                .marketId(publishProductDTO.getMarketId())
                .marketCode(publishProductDTO.getMarketCode())
                .marketName(publishProductDTO.getMarketName())
                .isActive(Boolean.TRUE)
                .solutionIds(publishProductDTO.getSolutionId()!=null ? Collections.singleton(publishProductDTO.getSolutionId()) : null)
                .build();
    }

    private Double totalQuantity(Set<CustomProductSKUDTO> customProductSKUDTOS) {
        return customProductSKUDTOS!=null ?  customProductSKUDTOS.stream().map(it -> it.getQuantity() != null ? it.getQuantity().getValue() : 0)
                .mapToDouble(v->v).sum(): 0;
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

    public  String generateGenericProductVarietySearchId(Long genericProductVarietyId, Long channelId,
                                                         Long marketId){
        return genericProductVarietyId+"-"+channelId+"-"+marketId;
    }


    private Double minPrice(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ? customProductSKUDTOS.stream().map(it -> it.getMrp() != null ? it.getMrp().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).min().orElse(0): 0;
    }

    private Double maxPrice(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ?  customProductSKUDTOS.stream().map(it -> it.getMrp() != null ? it.getMrp().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).max().orElse(0): 0;
    }

    private Double minQuantity(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ?  customProductSKUDTOS.stream().map(it -> it.getQuantity() != null ? it.getQuantity().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).min().orElse(0): 0;
    }

    private Double maxQuantity(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return  customProductSKUDTOS!=null ? customProductSKUDTOS.stream().map(it -> it.getQuantity() != null ? it.getQuantity().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).max().orElse(0) :0;
    }


    private String quantityUOM(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return  customProductSKUDTOS!=null ? customProductSKUDTOS.stream().map(it -> it.getQuantity() != null ? it.getQuantity().getUoM() : null)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null) :null;
    }


    private String currencyCode(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return  customProductSKUDTOS!=null ? customProductSKUDTOS.stream().map(it -> it.getMrp() != null ? it.getMrp().getCurrency() : null)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null) :null;
    }

    public GenericProductVariety buildGenericProductVarietyForDefaultChannel(GenericProductVarietyEventDTO genericProductVarietyEventDTO) {

        assert genericProductVarietyEventDTO != null;
        return GenericProductVariety.builder()
                .id(generateGenericProductVarietySearchId(genericProductVarietyEventDTO.getGenericProductVarietyId(),
                        genericProductVarietyEventDTO.getChannelId(),genericProductVarietyEventDTO.getMarketId()))
                .genericProductVarietyId(genericProductVarietyEventDTO.getGenericProductVarietyId())
                .genericProductVarietyCode(genericProductVarietyEventDTO.getGenericProductVarietyCode())
                .genericProductVarietyName(getEngLabel(genericProductVarietyEventDTO.getGenericProductVarietyName()))
                .genericProductId(genericProductVarietyEventDTO.getGenericProductId())
                .genericProductCode(genericProductVarietyEventDTO.getGenericProductCode())
                .genericProductName(getEngLabel(genericProductVarietyEventDTO.getGenericProductName()))
                .categoryCodes(getClassificationCodes(genericProductVarietyEventDTO.getCategories()))
                .categoryNames(getClassificationNames(genericProductVarietyEventDTO.getCategories()))
                .subCategoryCodes(getClassificationCodes(genericProductVarietyEventDTO.getSubCategories()))
                .subCategoryNames(getClassificationNames(genericProductVarietyEventDTO.getSubCategories()))
                .tenantId(genericProductVarietyEventDTO.getTenantId())
                .channelId(genericProductVarietyEventDTO.getChannelId())
                .channelCode(genericProductVarietyEventDTO.getChannelCode())
                .channelName(genericProductVarietyEventDTO.getChannelName())
                .marketId(genericProductVarietyEventDTO.getMarketId())
                .marketCode(genericProductVarietyEventDTO.getMarketCode())
                .marketName(genericProductVarietyEventDTO.getMarketName())
                .isActive(Boolean.TRUE)
                .build();
    }
}
