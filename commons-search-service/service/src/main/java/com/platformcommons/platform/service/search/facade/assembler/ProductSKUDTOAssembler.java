package com.platformcommons.platform.service.search.facade.assembler;


import com.platformcommons.platform.service.search.domain.ProductSKU;
import com.platformcommons.platform.service.search.dto.ProductSKUDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductSKUDTOAssembler {

    public ProductSKUDTO toDTO(ProductSKU productSKU){
        return ProductSKUDTO.builder()
                .id(productSKU.getId())
                .productId(productSKU.getProductId())
                .productCode(productSKU.getProductCode())
                .productNames(productSKU.getProductNames())
                .productNameEn(productSKU.getProductName_en())
                .productNameHin(productSKU.getProductName_hin())
                .genericProductId(productSKU.getGenericProductId())
                .genericProductNames(productSKU.getGenericProductNames())
                .genericProductVarietyId(productSKU.getGenericProductVarietyId())
                .genericProductVarietyNames(productSKU.getGenericProductVarietyNames())
                .productSKUId(productSKU.getProductSKUId())
                .productSKUNames(productSKU.getProductSKUNames())
                .traderCenterProductId(productSKU.getTraderCenterProductId())
                .tmaChannelProductId(productSKU.getTmaChannelProductId())
                .categoryCodes(productSKU.getCategoryCodes())
                .categoryNames(productSKU.getCategoryNames())
                .subCategoryCodes(productSKU.getSubCategoryCodes())
                .subCategoryNames(productSKU.getSubCategoryNames())
                .traderId(productSKU.getTraderId())
                .traderName(productSKU.getTraderName())
                .channelId(productSKU.getChannelId())
                .channelName(productSKU.getChannelName())
                .channelCode(productSKU.getChannelCode())
                .marketId(productSKU.getMarketId())
                .marketName(productSKU.getMarketName())
                .marketCode(productSKU.getMarketCode())
                .availableQuantity(productSKU.getAvailableQuantity())
                .serviceableArea(productSKU.getServiceableArea())
                .tenantId(productSKU.getTenantId())
                .isActive(productSKU.getIsActive()).build();
    }

    public ProductSKU fromDTO(ProductSKUDTO productSKUDTO){
        return ProductSKU.builder()
                .id(productSKUDTO.getId())
                .productId(productSKUDTO.getProductId())
                .productCode(productSKUDTO.getProductCode())
                .productNames(productSKUDTO.getProductNames())
                .productName_en(productSKUDTO.getProductNameEn())
                .productName_hin(productSKUDTO.getProductNameHin())
                .genericProductId(productSKUDTO.getGenericProductId())
                .genericProductNames(productSKUDTO.getGenericProductNames())
                .genericProductVarietyId(productSKUDTO.getGenericProductVarietyId())
                .genericProductVarietyNames(productSKUDTO.getGenericProductVarietyNames())
                .productSKUId(productSKUDTO.getProductSKUId())
                .productSKUNames(productSKUDTO.getProductSKUNames())
                .traderCenterProductId(productSKUDTO.getTraderCenterProductId())
                .tmaChannelProductId(productSKUDTO.getTmaChannelProductId())
                .categoryCodes(productSKUDTO.getCategoryCodes())
                .categoryNames(productSKUDTO.getCategoryNames())
                .subCategoryCodes(productSKUDTO.getSubCategoryCodes())
                .subCategoryNames(productSKUDTO.getSubCategoryNames())
                .traderId(productSKUDTO.getTraderId())
                .traderName(productSKUDTO.getTraderName())
                .channelId(productSKUDTO.getChannelId())
                .channelName(productSKUDTO.getChannelName())
                .channelCode(productSKUDTO.getChannelCode())
                .marketId(productSKUDTO.getMarketId())
                .marketName(productSKUDTO.getMarketName())
                .marketCode(productSKUDTO.getMarketCode())
                .availableQuantity(productSKUDTO.getAvailableQuantity())
                .serviceableArea(productSKUDTO.getServiceableArea())
                .tenantId(productSKUDTO.getTenantId())
                .isActive(productSKUDTO.getIsActive()).build();

    }

}
