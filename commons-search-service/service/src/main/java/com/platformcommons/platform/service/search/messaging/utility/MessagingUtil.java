package com.platformcommons.platform.service.search.messaging.utility;

import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.market.dto.CustomProductSKUDTO;
import com.platformcommons.platform.service.product.dto.ClassificationEventDTO;
import com.platformcommons.platform.service.product.dto.ProductSKUFactorDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MessagingUtil {

    public static final String SOLUTION_PRODUCT_STATUS_APPROVED = "SOLUTION_PRODUCT_STATUS.APPROVED";

    public static final String SOLUTION_PRODUCT_STATUS_UNAPPROVED = "SOLUTION_PRODUCT_STATUS.UNAPPROVED";


    public String getEngLabel(Set<MLTextDTO> mlTextDTOs){
        MLTextDTO mlTextDTO= mlTextDTOs!=null ? mlTextDTOs.stream().filter(it -> "ENG".equals(it.getLanguageCode()))
                .findFirst().orElseThrow(null): null;
        return mlTextDTO!=null ? mlTextDTO.getText(): null;
    }

    public Set<String> getClassificationCodes(Set<ClassificationEventDTO> classificationEventDTOS){
        return classificationEventDTOS!=null ? classificationEventDTOS.stream().map(ClassificationEventDTO::getClassificationCode)
                .collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

    public Set<String> getClassificationNames(Set<ClassificationEventDTO> classificationEventDTOS){
        return classificationEventDTOS!=null ? classificationEventDTOS.stream().map(it->this.getEngLabel(it.getNames()))
                .collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

    public  String generateGenericProductVarietySearchId(Long genericProductVarietyId, Long channelId,
                                                         Long marketId){
        return genericProductVarietyId+"-"+channelId+"-"+marketId;
    }


    public Double minPrice(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ? customProductSKUDTOS.stream()
                .map(it -> it.getMrp() != null ? it.getMrp().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).min().orElse(0): 0;
    }

    public Double maxPrice(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ?  customProductSKUDTOS.stream()
                .map(it -> it.getMrp() != null ? it.getMrp().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).max().orElse(0): 0;
    }

    public Double minQuantity(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ?  customProductSKUDTOS.stream()
                .map(it -> it.getQuantity() != null ? it.getQuantity().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).min().orElse(0): 0;
    }

    public Double maxQuantity(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return  customProductSKUDTOS!=null ? customProductSKUDTOS.stream()
                .map(it -> it.getQuantity() != null ? it.getQuantity().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(v->v).max().orElse(0) :0;
    }


    public Double totalQuantity(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return  customProductSKUDTOS!=null ? customProductSKUDTOS.stream()
                .map(it -> it.getQuantity()!= null ? it.getQuantity().getValue() : null)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue).sum() : 0d;
    }

    public String quantityUOM(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return  customProductSKUDTOS!=null ? customProductSKUDTOS.stream()
                .map(it -> it.getQuantity() != null ? it.getQuantity().getUoM() : null)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null) :null;
    }


    public String currencyCode(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return  customProductSKUDTOS!=null ? customProductSKUDTOS.stream()
                .map(it -> it.getMrp() != null ? it.getMrp().getCurrency() : null)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null) :null;
    }

    public CustomProductSKUDTO getDefaultSKU(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ? customProductSKUDTOS.stream().filter(it->it.getIsDefaultSKU()!=null && it.getIsDefaultSKU())
                .findFirst().orElse(null) : null;
    }



    public CustomProductSKUDTO getAnyDefaultSKU(Set<CustomProductSKUDTO> customProductSKUDTOS){
        return customProductSKUDTOS!=null ? customProductSKUDTOS.stream().min(Comparator.comparing(CustomProductSKUDTO::getId)).orElse(null) : null;
    }


    public Double getDefaultSKUPrice(Set<CustomProductSKUDTO> customProductSKUDTOS){
        CustomProductSKUDTO customProductSKUDTO = getDefaultSKU(customProductSKUDTOS);
        return  customProductSKUDTO!=null ? (customProductSKUDTO.getMrp()!=null ? customProductSKUDTO.getMrp().getValue() :null) :
                 null;
    }

    public Double getDefaultSKUQuantity(Set<CustomProductSKUDTO> customProductSKUDTOS){
        CustomProductSKUDTO customProductSKUDTO = getDefaultSKU(customProductSKUDTOS);
        return  customProductSKUDTO!=null ? (customProductSKUDTO.getQuantity()!=null ? customProductSKUDTO.getQuantity().getValue() :null) :
                null;
    }


    public String getDefaultSKUName(Set<CustomProductSKUDTO> customProductSKUDTOS){
        CustomProductSKUDTO customProductSKUDTO = getDefaultSKU(customProductSKUDTOS);
        return  customProductSKUDTO!=null ? getEngLabel(customProductSKUDTO.getName()) :
                null;
    }

    public Long getDefaultSKUId(Set<CustomProductSKUDTO> customProductSKUDTOS){
        CustomProductSKUDTO customProductSKUDTO = getDefaultSKU(customProductSKUDTOS);
        return  customProductSKUDTO!=null ? customProductSKUDTO.getId() :
                null;
    }

    public Long getDefaultSKUTmaChannelProduct(Set<CustomProductSKUDTO> customProductSKUDTOS) {
        CustomProductSKUDTO customProductSKUDTO = getDefaultSKU(customProductSKUDTOS);
        return  customProductSKUDTO!=null ? customProductSKUDTO.getTmaChannelProductId() :
                null;
    }

    public String getDefaultSKUName(CustomProductSKUDTO customProductSKUDTO){
        return  customProductSKUDTO!=null ? getEngLabel(customProductSKUDTO.getName()) :
                null;
    }

    public Set<String> getSkuFactorCodes(Set<ProductSKUFactorDTO> productSKUFactorList) {
        return productSKUFactorList != null ? productSKUFactorList.stream().map(ProductSKUFactorDTO::getCode).filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

}
