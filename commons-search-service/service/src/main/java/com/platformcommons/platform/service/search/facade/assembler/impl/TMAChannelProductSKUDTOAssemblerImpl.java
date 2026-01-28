package com.platformcommons.platform.service.search.facade.assembler.impl;

import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU.TMAChannelProductSKUBuilder;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO.TMAChannelProductSKUDTOBuilder;

import java.util.*;
import javax.annotation.Generated;

import com.platformcommons.platform.service.search.facade.assembler.TMAChannelProductSKUDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class TMAChannelProductSKUDTOAssemblerImpl implements TMAChannelProductSKUDTOAssembler {

    @Override
    public TMAChannelProductSKU fromDTO(TMAChannelProductSKUDTO tmaChannelProductSKUDTO) {
        if ( tmaChannelProductSKUDTO == null ) {
            return null;
        }

        TMAChannelProductSKUBuilder tMAChannelProductSKU = TMAChannelProductSKU.builder();

        tMAChannelProductSKU.id( tmaChannelProductSKUDTO.getId() );
        tMAChannelProductSKU.genericProductId( tmaChannelProductSKUDTO.getGenericProductId() );
        tMAChannelProductSKU.genericProductCode( tmaChannelProductSKUDTO.getGenericProductCode() );
        tMAChannelProductSKU.genericProductName( tmaChannelProductSKUDTO.getGenericProductName() );
        tMAChannelProductSKU.genericProductVarietyId( tmaChannelProductSKUDTO.getGenericProductVarietyId() );
        tMAChannelProductSKU.genericProductVarietyCode( tmaChannelProductSKUDTO.getGenericProductVarietyCode() );
        tMAChannelProductSKU.genericProductVarietyName( tmaChannelProductSKUDTO.getGenericProductVarietyName() );
        tMAChannelProductSKU.productId( tmaChannelProductSKUDTO.getProductId() );
        tMAChannelProductSKU.productCode( tmaChannelProductSKUDTO.getProductCode() );
        tMAChannelProductSKU.productName( tmaChannelProductSKUDTO.getProductName() );
        Set<String> set = tmaChannelProductSKUDTO.getCategoryCodes();
        if ( set != null ) {
            tMAChannelProductSKU.categoryCodes( new HashSet<String>( set ) );
        }
        Set<String> set1 = tmaChannelProductSKUDTO.getCategoryNames();
        if ( set1 != null ) {
            tMAChannelProductSKU.categoryNames( new HashSet<String>( set1 ) );
        }
        Set<String> set2 = tmaChannelProductSKUDTO.getSubCategoryCodes();
        if ( set2 != null ) {
            tMAChannelProductSKU.subCategoryCodes( new HashSet<String>( set2 ) );
        }
        Set<String> set3 = tmaChannelProductSKUDTO.getSubCategoryNames();
        if ( set3 != null ) {
            tMAChannelProductSKU.subCategoryNames( new HashSet<String>( set3 ) );
        }
        tMAChannelProductSKU.productSKUId( tmaChannelProductSKUDTO.getProductSKUId() );
        tMAChannelProductSKU.productSKUCode( tmaChannelProductSKUDTO.getProductSKUCode() );
        tMAChannelProductSKU.productSKUName( tmaChannelProductSKUDTO.getProductSKUName() );
        tMAChannelProductSKU.marketId( tmaChannelProductSKUDTO.getMarketId() );
        tMAChannelProductSKU.marketCode( tmaChannelProductSKUDTO.getMarketCode() );
        tMAChannelProductSKU.marketName( tmaChannelProductSKUDTO.getMarketName() );
        tMAChannelProductSKU.traderId( tmaChannelProductSKUDTO.getTraderId() );
        tMAChannelProductSKU.traderName( tmaChannelProductSKUDTO.getTraderName() );
        tMAChannelProductSKU.traderIconURL( tmaChannelProductSKUDTO.getTraderIconURL() );
        tMAChannelProductSKU.tenantId( tmaChannelProductSKUDTO.getTenantId() );
        tMAChannelProductSKU.isActive( tmaChannelProductSKUDTO.getIsActive() );
        tMAChannelProductSKU.skuMRP( tmaChannelProductSKUDTO.getSkuMRP() );
        Set<String> set4 = tmaChannelProductSKUDTO.getServingAreaCodes();
        if ( set4 != null ) {
            tMAChannelProductSKU.servingAreaCodes( new HashSet<String>( set4 ) );
        }
        tMAChannelProductSKU.availableQuantity( tmaChannelProductSKUDTO.getAvailableQuantity() );
        tMAChannelProductSKU.availableQuantityUOM( tmaChannelProductSKUDTO.getAvailableQuantityUOM() );
        tMAChannelProductSKU.skuMRPCurrency( tmaChannelProductSKUDTO.getSkuMRPCurrency() );
        tMAChannelProductSKU.skuDiscountedMRP( tmaChannelProductSKUDTO.getSkuDiscountedMRP() );
        tMAChannelProductSKU.skuDiscountedMRPCurrency( tmaChannelProductSKUDTO.getSkuDiscountedMRPCurrency() );
        tMAChannelProductSKU.channelId( tmaChannelProductSKUDTO.getChannelId() );
        tMAChannelProductSKU.channelCode( tmaChannelProductSKUDTO.getChannelCode() );
        tMAChannelProductSKU.channelName( tmaChannelProductSKUDTO.getChannelName() );
        tMAChannelProductSKU.defaultSKUTmaChannelProductId( tmaChannelProductSKUDTO.getDefaultSKUTmaChannelProductId() );
        tMAChannelProductSKU.skuTmaChannelProductId( tmaChannelProductSKUDTO.getSkuTmaChannelProductId() );
        tMAChannelProductSKU.productType( tmaChannelProductSKUDTO.getProductType() );
        tMAChannelProductSKU.productSubType( tmaChannelProductSKUDTO.getProductSubType() );
        Set<Long> set5 = tmaChannelProductSKUDTO.getUnApprovedSolutionIds();
        if ( set5 != null ) {
            tMAChannelProductSKU.unApprovedSolutionIds( new HashSet<Long>( set5 ) );
        }
        Set<String> set6 = tmaChannelProductSKUDTO.getTagCodes();
        if ( set6 != null ) {
            tMAChannelProductSKU.tagCodes( new HashSet<String>( set6 ) );
        }
        Set<String> set7 = tmaChannelProductSKUDTO.getTagLabels();
        if ( set7 != null ) {
            tMAChannelProductSKU.tagLabels( new HashSet<String>( set7 ) );
        }
        Set<String> set8 = tmaChannelProductSKUDTO.getSkuFactorCodes();
        if ( set8 != null ) {
            tMAChannelProductSKU.skuFactorCodes( new HashSet<String>( set8 ) );
        }

        tMAChannelProductSKU.tagCodesByType( stringSetMapToStringListMap( tmaChannelProductSKUDTO.getTagCodesByType() ) );
        tMAChannelProductSKU.tagLabelsByLang( stringSetMapToStringListMap( tmaChannelProductSKUDTO.getTagLabelsByLang() ) );



        return tMAChannelProductSKU.build();
    }

    @Override
    public TMAChannelProductSKUDTO toDTO(TMAChannelProductSKU tmaChannelProductSKU) {
        if ( tmaChannelProductSKU == null ) {
            return null;
        }

        TMAChannelProductSKUDTOBuilder tMAChannelProductSKUDTO = TMAChannelProductSKUDTO.builder();

        tMAChannelProductSKUDTO.id( tmaChannelProductSKU.getId() );
        tMAChannelProductSKUDTO.genericProductId( tmaChannelProductSKU.getGenericProductId() );
        tMAChannelProductSKUDTO.genericProductCode( tmaChannelProductSKU.getGenericProductCode() );
        tMAChannelProductSKUDTO.genericProductName( tmaChannelProductSKU.getGenericProductName() );
        tMAChannelProductSKUDTO.genericProductVarietyId( tmaChannelProductSKU.getGenericProductVarietyId() );
        tMAChannelProductSKUDTO.genericProductVarietyCode( tmaChannelProductSKU.getGenericProductVarietyCode() );
        tMAChannelProductSKUDTO.genericProductVarietyName( tmaChannelProductSKU.getGenericProductVarietyName() );
        tMAChannelProductSKUDTO.productId( tmaChannelProductSKU.getProductId() );
        tMAChannelProductSKUDTO.productCode( tmaChannelProductSKU.getProductCode() );
        tMAChannelProductSKUDTO.productName( tmaChannelProductSKU.getProductName() );
        Set<String> set = tmaChannelProductSKU.getCategoryCodes();
        if ( set != null ) {
            tMAChannelProductSKUDTO.categoryCodes( new HashSet<String>( set ) );
        }
        Set<String> set1 = tmaChannelProductSKU.getCategoryNames();
        if ( set1 != null ) {
            tMAChannelProductSKUDTO.categoryNames( new HashSet<String>( set1 ) );
        }
        Set<String> set2 = tmaChannelProductSKU.getSubCategoryCodes();
        if ( set2 != null ) {
            tMAChannelProductSKUDTO.subCategoryCodes( new HashSet<String>( set2 ) );
        }
        Set<String> set3 = tmaChannelProductSKU.getSubCategoryNames();
        if ( set3 != null ) {
            tMAChannelProductSKUDTO.subCategoryNames( new HashSet<String>( set3 ) );
        }
        tMAChannelProductSKUDTO.productSKUId( tmaChannelProductSKU.getProductSKUId() );
        tMAChannelProductSKUDTO.productSKUCode( tmaChannelProductSKU.getProductSKUCode() );
        tMAChannelProductSKUDTO.productSKUName( tmaChannelProductSKU.getProductSKUName() );
        tMAChannelProductSKUDTO.marketId( tmaChannelProductSKU.getMarketId() );
        tMAChannelProductSKUDTO.marketCode( tmaChannelProductSKU.getMarketCode() );
        tMAChannelProductSKUDTO.marketName( tmaChannelProductSKU.getMarketName() );
        tMAChannelProductSKUDTO.traderId( tmaChannelProductSKU.getTraderId() );
        tMAChannelProductSKUDTO.traderName( tmaChannelProductSKU.getTraderName() );
        tMAChannelProductSKUDTO.traderIconURL( tmaChannelProductSKU.getTraderIconURL() );
        tMAChannelProductSKUDTO.tenantId( tmaChannelProductSKU.getTenantId() );
        tMAChannelProductSKUDTO.isActive( tmaChannelProductSKU.getIsActive() );
        tMAChannelProductSKUDTO.skuMRP( tmaChannelProductSKU.getSkuMRP() );
        Set<String> set4 = tmaChannelProductSKU.getServingAreaCodes();
        if ( set4 != null ) {
            tMAChannelProductSKUDTO.servingAreaCodes( new HashSet<String>( set4 ) );
        }
        tMAChannelProductSKUDTO.availableQuantity( tmaChannelProductSKU.getAvailableQuantity() );
        tMAChannelProductSKUDTO.availableQuantityUOM( tmaChannelProductSKU.getAvailableQuantityUOM() );
        tMAChannelProductSKUDTO.skuMRPCurrency( tmaChannelProductSKU.getSkuMRPCurrency() );
        tMAChannelProductSKUDTO.skuDiscountedMRP( tmaChannelProductSKU.getSkuDiscountedMRP() );
        tMAChannelProductSKUDTO.skuDiscountedMRPCurrency( tmaChannelProductSKU.getSkuDiscountedMRPCurrency() );
        tMAChannelProductSKUDTO.channelId( tmaChannelProductSKU.getChannelId() );
        tMAChannelProductSKUDTO.channelCode( tmaChannelProductSKU.getChannelCode() );
        tMAChannelProductSKUDTO.channelName( tmaChannelProductSKU.getChannelName() );
        tMAChannelProductSKUDTO.defaultSKUTmaChannelProductId( tmaChannelProductSKU.getDefaultSKUTmaChannelProductId() );
        tMAChannelProductSKUDTO.skuTmaChannelProductId( tmaChannelProductSKU.getSkuTmaChannelProductId() );
        tMAChannelProductSKUDTO.productType( tmaChannelProductSKU.getProductType() );
        tMAChannelProductSKUDTO.productSubType( tmaChannelProductSKU.getProductSubType() );
        Set<Long> set5 = tmaChannelProductSKU.getApprovedForSolutionIds();
        if ( set5 != null ) {
            tMAChannelProductSKUDTO.approvedForSolutionIds( new HashSet<Long>( set5 ) );
        }
        Set<Long> set6 = tmaChannelProductSKU.getUnApprovedSolutionIds();
        if ( set6 != null ) {
            tMAChannelProductSKUDTO.unApprovedSolutionIds( new HashSet<Long>( set6 ) );
        }
        Set<String> set7 = tmaChannelProductSKU.getTagCodes();
        if ( set7 != null ) {
            tMAChannelProductSKUDTO.tagCodes( new HashSet<String>( set7 ) );
        }
        Set<String> set8 = tmaChannelProductSKU.getTagLabels();
        if ( set8 != null ) {
            tMAChannelProductSKUDTO.tagLabels( new HashSet<String>( set8 ) );
        }
        tMAChannelProductSKUDTO.tagCodesByType( stringListMapToStringSetMap( tmaChannelProductSKU.getTagCodesByType() ) );
        tMAChannelProductSKUDTO.tagLabelsByLang( stringListMapToStringSetMap( tmaChannelProductSKU.getTagLabelsByLang() ) );
        Set<String> set9 = tmaChannelProductSKU.getSkuFactorCodes();
        if ( set9 != null ) {
            tMAChannelProductSKUDTO.skuFactorCodes( new HashSet<String>( set9 ) );
        }

        return tMAChannelProductSKUDTO.build();
    }

    @Override
    public Set<TMAChannelProductSKUDTO> toDTOs(Set<TMAChannelProductSKU> tmaChannelProductSKUS) {
        if ( tmaChannelProductSKUS == null ) {
            return null;
        }

        Set<TMAChannelProductSKUDTO> set = new HashSet<TMAChannelProductSKUDTO>( Math.max( (int) ( tmaChannelProductSKUS.size() / .75f ) + 1, 16 ) );
        for ( TMAChannelProductSKU tMAChannelProductSKU : tmaChannelProductSKUS ) {
            set.add( toDTO( tMAChannelProductSKU ) );
        }

        return set;
    }

    protected Map<String, Set<String>> stringListMapToStringSetMap(Map<String, List<String>> map) {
        if (map == null) {
            return null;
        }

        Map<String, Set<String>> result = new HashMap<>(Math.max((int) (map.size() / .75f) + 1, 16));

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> listValue = entry.getValue();
            // Wrap into LinkedHashSet to remove duplicates + preserve insertion order
            Set<String> setValue = (listValue != null) ? new LinkedHashSet<>(listValue) : null;
            result.put(key, setValue);
        }

        return result;
    }

    protected Map<String, List<String>> stringSetMapToStringListMap(Map<String, Set<String>> map) {
        if (map == null) {
            return null;
        }

        Map<String, List<String>> result = new HashMap<>(Math.max((int) (map.size() / .75f) + 1, 16));

        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            Set<String> setValue = entry.getValue();
            // Wrap into new ArrayList to preserve iteration order from Set
            List<String> listValue = (setValue != null) ? new ArrayList<>(setValue) : null;
            result.put(key, listValue);
        }

        return result;
    }
}

