package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.market.dto.PublishProductDTO;
import com.platformcommons.platform.service.market.dto.TMAChannelProductDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.messaging.utility.MessagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TMAChannelProductEventMapper {


    @Autowired
    private GenericProductVarietyEventMapper genericProductVarietyEventMapper;


    @Autowired
    private MessagingUtil messagingUtil;

    public TMAChannelProduct fromPublishProductDTO(PublishProductDTO publishProductDTO,
                                            GenericProductVarietyEventDTO genericProductVarietyEventDTO){


        return TMAChannelProduct.builder()
                .id(generateTMAChannelProductId(publishProductDTO.getCustomProductDTO().getId(),
                        publishProductDTO.getChannelId(),publishProductDTO.getMarketId()))
                .productId(publishProductDTO.getCustomProductDTO().getId())
                .productCode(publishProductDTO.getCustomProductDTO().getCode())
                .productName(messagingUtil.getEngLabel(publishProductDTO.getCustomProductDTO().getName()))
                .genericProductVarietyId(publishProductDTO.getCustomProductDTO().getVarietyId())
                .genericProductVarietyCode(genericProductVarietyEventDTO.getGenericProductVarietyCode())
                .genericProductId(genericProductVarietyEventDTO.getGenericProductId())
                .genericProductCode(genericProductVarietyEventDTO.getGenericProductCode())
                .categoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getCategories()))
                .categoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getCategories()))
                .subCategoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getSubCategories()))
                .subCategoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getSubCategories()))
                .traderId(publishProductDTO.getTraderId())
                .traderName(publishProductDTO.getTraderName())
                .traderIconURL(publishProductDTO.getTraderIconPic())
                .marketId(publishProductDTO.getMarketId())
                .marketCode(publishProductDTO.getMarketCode())
                .channelCode(publishProductDTO.getChannelCode())
                .channelId(publishProductDTO.getChannelId())
                .minPrice(messagingUtil.minPrice(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .maxPrice(messagingUtil.maxPrice(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .totalAvailableQuantity(messagingUtil.totalQuantity(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .currencyCode(messagingUtil.currencyCode(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .quantityUOM(messagingUtil.quantityUOM(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .defaultSKUId(messagingUtil.getDefaultSKUId(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .defaultSKUName(messagingUtil.getDefaultSKUName(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .defaultSKUPrice(messagingUtil.getDefaultSKUPrice(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .defaultSKUAvailableQuantity(messagingUtil.getDefaultSKUQuantity(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .defaultSKUTmaChannelProductId(messagingUtil.getDefaultSKUTmaChannelProduct(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs()))
                .isActive(Boolean.TRUE)
                .productType(publishProductDTO.getCustomProductDTO().getType()) //
                .productSubType(publishProductDTO.getCustomProductDTO().getSubType()) //]
                .unApprovedSolutionIds(!MessagingUtil.SOLUTION_PRODUCT_STATUS_APPROVED.equals(publishProductDTO.getSolutionProductStatus())? Collections.singleton(publishProductDTO.getSolutionId()): null)
                .approvedForSolutionIds(MessagingUtil.SOLUTION_PRODUCT_STATUS_APPROVED.equals(publishProductDTO.getSolutionProductStatus())? Collections.singleton(publishProductDTO.getSolutionId()): null)
                .build();

    }

    private String generateTMAChannelProductId(Long productId,Long channelId, Long marketId){
        return productId+"-"+channelId+"-"+marketId;
    }

    public TMAChannelProduct fromProductDTO(GenericProductVarietyEventDTO genericProductVarietyEventDTO, ProductDTO productDTO, Long channelId, Long marketId) {
        return TMAChannelProduct.builder()
                .id(generateTMAChannelProductId(productDTO.getId(),
                        channelId,marketId))
                .genericProductVarietyId(genericProductVarietyEventDTO.getGenericProductVarietyId())
                .genericProductVarietyCode(genericProductVarietyEventDTO.getGenericProductVarietyCode())
                .genericProductId(genericProductVarietyEventDTO.getGenericProductId())
                .genericProductCode(genericProductVarietyEventDTO.getGenericProductCode())
                .categoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getCategories()))
                .categoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getCategories()))
                .subCategoryCodes(messagingUtil.getClassificationCodes(genericProductVarietyEventDTO.getSubCategories()))
                .subCategoryNames(messagingUtil.getClassificationNames(genericProductVarietyEventDTO.getSubCategories()))
                .build();
    }
}
