package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.market.dto.CustomProductSKUDTO;
import com.platformcommons.platform.service.market.dto.PublishProductDTO;
import com.platformcommons.platform.service.market.dto.TMAChannelProductDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.product.dto.ProductSKUDTO;
import com.platformcommons.platform.service.product.dto.SpecificationValueDTO;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.messaging.utility.MessagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TMAChannelProductSKUEventMapper {

    @Autowired
    private MessagingUtil messagingUtil;

    public Set<TMAChannelProductSKU> map(PublishProductDTO publishProductDTO, GenericProductVarietyEventDTO genericProductVarietyEventDTO){
        Set<CustomProductSKUDTO> productSKUDTOS = publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs();
        return productSKUDTOS.stream().map(it-> this.map(it,
                publishProductDTO,genericProductVarietyEventDTO)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private TMAChannelProductSKU  map(CustomProductSKUDTO customProductSKUDTO, PublishProductDTO publishProductDTO,
                                      GenericProductVarietyEventDTO genericProductVarietyEventDTO){
        return TMAChannelProductSKU.builder()
                .id(generateTMAChannelProductSKUId(customProductSKUDTO.getId(),publishProductDTO.getCustomProductDTO().getId(),
                        publishProductDTO.getChannelId(),publishProductDTO.getMarketId()))
                .productSKUId(customProductSKUDTO.getId())
                .productSKUName(messagingUtil.getEngLabel(customProductSKUDTO.getName()))
                .productSKUCode(customProductSKUDTO.getCode())
                .productId(publishProductDTO.getCustomProductDTO().getId())
                .productCode(publishProductDTO.getCustomProductDTO().getCode())
                .productName(messagingUtil.getEngLabel(publishProductDTO.getCustomProductDTO().getName()))
                .genericProductVarietyId(genericProductVarietyEventDTO.getGenericProductVarietyId())
                .genericProductVarietyCode(genericProductVarietyEventDTO.getGenericProductVarietyCode())
                .genericProductVarietyName(messagingUtil.getEngLabel(genericProductVarietyEventDTO.getGenericProductVarietyName()))
                .genericProductId(genericProductVarietyEventDTO.getGenericProductId())
                .genericProductCode(genericProductVarietyEventDTO.getGenericProductCode())
                .genericProductName(messagingUtil.getEngLabel(genericProductVarietyEventDTO.getGenericProductName()))
                .categoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getCategories()))
                .categoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getCategories()))
                .subCategoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getSubCategories()))
                .subCategoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getSubCategories()))
                .availableQuantity(customProductSKUDTO.getQuantity() != null ? customProductSKUDTO.getQuantity().getValue() : null)
                .availableQuantityUOM(customProductSKUDTO.getQuantity() != null ? customProductSKUDTO.getQuantity().getUoM() : null)
                .skuMRP(customProductSKUDTO.getMrp() != null ? customProductSKUDTO.getMrp().getValue() : null)
                .skuMRPCurrency(customProductSKUDTO.getMrp() != null ? customProductSKUDTO.getMrp().getCurrency() : null)
                .skuDiscountedMRP(customProductSKUDTO.getDiscountedMrp() != null ? customProductSKUDTO.getDiscountedMrp().getValue() : null)
                .skuDiscountedMRPCurrency(customProductSKUDTO.getDiscountedMrp() != null ? customProductSKUDTO.getDiscountedMrp().getCurrency() : null)
                .traderId(publishProductDTO.getTraderId())
                .traderName(publishProductDTO.getTraderName())
                .traderIconURL(publishProductDTO.getTraderIconPic())
                .isActive(Boolean.TRUE)
                .marketId(publishProductDTO.getMarketId())
                .marketCode(publishProductDTO.getMarketCode())
                .marketName(publishProductDTO.getMarketName())
                .channelId(publishProductDTO.getChannelId())
                .channelCode(publishProductDTO.getChannelCode())
                .channelName(publishProductDTO.getChannelName())
                .defaultSKUTmaChannelProductId(messagingUtil.getDefaultSKUTmaChannelProduct(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .skuTmaChannelProductId(customProductSKUDTO.getTmaChannelProductId())
                .skuTmaChannelProductStatus(customProductSKUDTO.getTmaChannelProductStatus())
                .productType(publishProductDTO.getCustomProductDTO().getType()) //
                .productSubType(publishProductDTO.getCustomProductDTO().getSubType()) //
                .unApprovedSolutionIds(!MessagingUtil.SOLUTION_PRODUCT_STATUS_APPROVED.equals(publishProductDTO.getSolutionProductStatus())? Collections.singleton(publishProductDTO.getSolutionId()): null)
                .approvedSolutionIds(MessagingUtil.SOLUTION_PRODUCT_STATUS_APPROVED.equals(publishProductDTO.getSolutionProductStatus())? Collections.singleton(publishProductDTO.getSolutionId()): null)
                .tagCodes(MapperUtil.getProductSKUTagCodes(customProductSKUDTO.getProductSKUTagList()))
                .tagLabels(MapperUtil.getProductSKUTagLabels(customProductSKUDTO.getProductSKUTagList()))
                .tagCodesByType(MapperUtil.getTagCodesByTypeForProductSkuTag(customProductSKUDTO.getProductSKUTagList()))
                .tagLabelsByType(MapperUtil.getTagLabelsByTypeForProductSkuTag(customProductSKUDTO.getProductSKUTagList()))
                .tagLabelsByLang(MapperUtil.getTagLabelsByLangForProductSkuTag(customProductSKUDTO.getProductSKUTagList()))
                .skuFactorCodes( messagingUtil.getSkuFactorCodes(customProductSKUDTO.getProductSKUFactorList()))
                .servingAreaCodes(getServingLocation(customProductSKUDTO))
                .build();
    }

    public TMAChannelProductSKU  mapFromTmaChannelProductDTO(TMAChannelProductDTO tmaChannelProductDTO){
        return TMAChannelProductSKU.builder()
                .productSKUId(tmaChannelProductDTO.getProductSKUId())
                .productId(tmaChannelProductDTO.getProductId())
                .skuTmaChannelProductId(tmaChannelProductDTO.getId())
                .skuTmaChannelProductStatus(tmaChannelProductDTO.getPublishStatus())
                .traderId(tmaChannelProductDTO.getTraderId())
                .channelId(tmaChannelProductDTO.getChannelId())
                .marketId(tmaChannelProductDTO.getMarketId())
                .build();
    }

    public TMAChannelProductSKU mapTagsOnly(ProductSKUDTO dto, Long marketId, Long channelId) {
        return TMAChannelProductSKU.builder()
                .id(generateTMAChannelProductSKUId(dto.getId(), dto.getProductId(), channelId, marketId))
                .tagCodes(MapperUtil.getProductSKUTagCodes(dto.getProductSKUTagList()))
                .tagLabels(MapperUtil.getProductSKUTagLabels(dto.getProductSKUTagList()))
                .tagCodesByType(MapperUtil.getTagCodesByTypeForProductSkuTag(dto.getProductSKUTagList()))
                .tagLabelsByType(MapperUtil.getTagLabelsByTypeForProductSkuTag(dto.getProductSKUTagList()))
                .tagLabelsByLang(MapperUtil.getTagLabelsByLangForProductSkuTag(dto.getProductSKUTagList()))
                .build();
    }



    private String generateTMAChannelProductSKUId(Long skuId,Long productId, Long channelId, Long marketId){
        return  skuId+"-"+productId+"-"+channelId+"-"+marketId;
    }

    public Set<TMAChannelProductSKU> mapForGpAndGpv(ProductDTO productDTO, GenericProductVarietyEventDTO genericProductVarietyEventDTO, Long channelId, Long marketId) {
        Set<ProductSKUDTO> productSKUDTOS = productDTO.getProductSkuList();
        return productSKUDTOS.stream().map(it-> this.mapForGpAndGpv(genericProductVarietyEventDTO,it,productDTO.getId(),channelId,marketId)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private TMAChannelProductSKU  mapForGpAndGpv(GenericProductVarietyEventDTO genericProductVarietyEventDTO, ProductSKUDTO productSKUDTO,Long productId, Long channelId, Long marketId){
        return TMAChannelProductSKU.builder()
                .id(generateTMAChannelProductSKUId(productSKUDTO.getId(), productId,
                        channelId,marketId))
                .genericProductVarietyId(genericProductVarietyEventDTO.getGenericProductVarietyId())
                .genericProductVarietyCode(genericProductVarietyEventDTO.getGenericProductVarietyCode())
                .genericProductVarietyName(messagingUtil.getEngLabel(genericProductVarietyEventDTO.getGenericProductVarietyName()))
                .genericProductId(genericProductVarietyEventDTO.getGenericProductId())
                .genericProductCode(genericProductVarietyEventDTO.getGenericProductCode())
                .genericProductName(messagingUtil.getEngLabel(genericProductVarietyEventDTO.getGenericProductName()))
                .categoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getCategories()))
                .categoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getCategories()))
                .subCategoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getSubCategories()))
                .subCategoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getSubCategories()))
                .tagCodes(MapperUtil.getProductSKUTagCodes(productSKUDTO.getProductSKUTagList()))
                .tagLabels(MapperUtil.getProductSKUTagLabels(productSKUDTO.getProductSKUTagList()))
                .tagCodesByType(MapperUtil.getTagCodesByTypeForProductSkuTag(productSKUDTO.getProductSKUTagList()))
                .tagLabelsByType(MapperUtil.getTagLabelsByTypeForProductSkuTag(productSKUDTO.getProductSKUTagList()))
                .tagLabelsByLang(MapperUtil.getTagLabelsByLangForProductSkuTag(productSKUDTO.getProductSKUTagList()))
                .build();
    }

    private Set<String> getServingLocation(CustomProductSKUDTO customProductSKUDTO) {
        Set<String> locationCodes = customProductSKUDTO.getProductSkuSpecificationList().stream()
                .filter(Objects::nonNull)
                .flatMap(spec -> {
                    Set<SpecificationValueDTO> values = spec.getSpecificationValues();
                    return values != null ? values.stream() : Stream.empty();
                })
                .filter(val -> "LOCATION".equalsIgnoreCase(val.getType()))
                .map(SpecificationValueDTO::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return locationCodes;
    }
}
