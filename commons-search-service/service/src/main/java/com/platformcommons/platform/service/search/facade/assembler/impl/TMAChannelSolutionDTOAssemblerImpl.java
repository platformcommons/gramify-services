package com.platformcommons.platform.service.search.facade.assembler.impl;

import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution.TMAChannelSolutionBuilder;
import com.platformcommons.platform.service.search.dto.TMAChannelSolutionDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelSolutionDTO.TMAChannelSolutionDTOBuilder;

import java.util.*;
import javax.annotation.Generated;

import com.platformcommons.platform.service.search.facade.assembler.TMAChannelSolutionDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class TMAChannelSolutionDTOAssemblerImpl implements TMAChannelSolutionDTOAssembler {

    @Override
    public TMAChannelSolutionDTO toDTO(TMAChannelSolution tmaChannelSolution) {
        if ( tmaChannelSolution == null ) {
            return null;
        }

        TMAChannelSolutionDTOBuilder tMAChannelSolutionDTO = TMAChannelSolutionDTO.builder();

        tMAChannelSolutionDTO.isActive( tmaChannelSolution.getIsActive() );
        if ( tmaChannelSolution.getId() != null ) {
            tMAChannelSolutionDTO.id( Long.parseLong( tmaChannelSolution.getId() ) );
        }
        tMAChannelSolutionDTO.solutionId( tmaChannelSolution.getSolutionId() );
        Set<String> set = tmaChannelSolution.getTagCodes();
        if ( set != null ) {
            tMAChannelSolutionDTO.tagCodes( new HashSet<String>( set ) );
        }
        Set<String> set1 = tmaChannelSolution.getTagLabels();
        if ( set1 != null ) {
            tMAChannelSolutionDTO.tagLabels( new HashSet<String>( set1 ) );
        }
        Set<String> set2 = tmaChannelSolution.getCategoryCodes();
        if ( set2 != null ) {
            tMAChannelSolutionDTO.categoryCodes( new HashSet<String>( set2 ) );
        }
        Set<String> set3 = tmaChannelSolution.getCategoryNames();
        if ( set3 != null ) {
            tMAChannelSolutionDTO.categoryNames( new HashSet<String>( set3 ) );
        }
        Set<String> set4 = tmaChannelSolution.getSubCategoryCodes();
        if ( set4 != null ) {
            tMAChannelSolutionDTO.subCategoryCodes( new HashSet<String>( set4 ) );
        }
        Set<String> set5 = tmaChannelSolution.getSubCategoryNames();
        if ( set5 != null ) {
            tMAChannelSolutionDTO.subCategoryNames( new HashSet<String>( set5 ) );
        }
        tMAChannelSolutionDTO.genericProductId( tmaChannelSolution.getGenericProductId() );
        tMAChannelSolutionDTO.genericProductCode( tmaChannelSolution.getGenericProductCode() );
        tMAChannelSolutionDTO.genericProductName( tmaChannelSolution.getGenericProductName() );
        tMAChannelSolutionDTO.genericProductVarietyId( tmaChannelSolution.getGenericProductVarietyId() );
        tMAChannelSolutionDTO.genericProductVarietyCode( tmaChannelSolution.getGenericProductVarietyCode() );
        tMAChannelSolutionDTO.genericProductVarietyName( tmaChannelSolution.getGenericProductVarietyName() );
        tMAChannelSolutionDTO.traderId( tmaChannelSolution.getTraderId() );
        tMAChannelSolutionDTO.traderIconURL( tmaChannelSolution.getTraderIconURL() );
        tMAChannelSolutionDTO.traderName( tmaChannelSolution.getTraderName() );
        tMAChannelSolutionDTO.channelId( tmaChannelSolution.getChannelId() );
        tMAChannelSolutionDTO.channelName( tmaChannelSolution.getChannelName() );
        tMAChannelSolutionDTO.channelCode( tmaChannelSolution.getChannelCode() );
        tMAChannelSolutionDTO.marketId( tmaChannelSolution.getMarketId() );
        tMAChannelSolutionDTO.marketName( tmaChannelSolution.getMarketName() );
        tMAChannelSolutionDTO.marketCode( tmaChannelSolution.getMarketCode() );
        tMAChannelSolutionDTO.solutionType( tmaChannelSolution.getSolutionType() );
        tMAChannelSolutionDTO.solutionClass( tmaChannelSolution.getSolutionClass() );
        tMAChannelSolutionDTO.baseSolutionId( tmaChannelSolution.getBaseSolutionId() );
        Map<String, String> map = tmaChannelSolution.getTitles();
        if ( map != null ) {
            tMAChannelSolutionDTO.titles( new HashMap<String, String>( map ) );
        }
        tMAChannelSolutionDTO.tagCodesByType( stringListMapToStringSetMap( tmaChannelSolution.getTagCodesByType() ) );
        tMAChannelSolutionDTO.tagLabelsByType( stringListMapToStringSetMap( tmaChannelSolution.getTagLabelsByType() ) );
        tMAChannelSolutionDTO.tagLabelsByLang( stringListMapToStringSetMap( tmaChannelSolution.getTagLabelsByLang() ) );

        return tMAChannelSolutionDTO.build();
    }

    @Override
    public TMAChannelSolution fromDTO(TMAChannelSolutionDTO tmaChannelSolutionDTO) {
        if ( tmaChannelSolutionDTO == null ) {
            return null;
        }

        TMAChannelSolutionBuilder tMAChannelSolution = TMAChannelSolution.builder();

        if ( tmaChannelSolutionDTO.getId() != null ) {
            tMAChannelSolution.id( String.valueOf( tmaChannelSolutionDTO.getId() ) );
        }
        tMAChannelSolution.traderId( tmaChannelSolutionDTO.getTraderId() );
        tMAChannelSolution.traderName( tmaChannelSolutionDTO.getTraderName() );
        tMAChannelSolution.traderIconURL( tmaChannelSolutionDTO.getTraderIconURL() );
        tMAChannelSolution.marketId( tmaChannelSolutionDTO.getMarketId() );
        tMAChannelSolution.marketCode( tmaChannelSolutionDTO.getMarketCode() );
        tMAChannelSolution.marketName( tmaChannelSolutionDTO.getMarketName() );
        tMAChannelSolution.channelId( tmaChannelSolutionDTO.getChannelId() );
        tMAChannelSolution.channelCode( tmaChannelSolutionDTO.getChannelCode() );
        tMAChannelSolution.channelName( tmaChannelSolutionDTO.getChannelName() );
        tMAChannelSolution.isActive( tmaChannelSolutionDTO.getIsActive() );
        Map<String, String> map = tmaChannelSolutionDTO.getTitles();
        if ( map != null ) {
            tMAChannelSolution.titles( new HashMap<String, String>( map ) );
        }
        Set<String> set = tmaChannelSolutionDTO.getCategoryCodes();
        if ( set != null ) {
            tMAChannelSolution.categoryCodes( new HashSet<String>( set ) );
        }
        Set<String> set1 = tmaChannelSolutionDTO.getCategoryNames();
        if ( set1 != null ) {
            tMAChannelSolution.categoryNames( new HashSet<String>( set1 ) );
        }
        Set<String> set2 = tmaChannelSolutionDTO.getSubCategoryCodes();
        if ( set2 != null ) {
            tMAChannelSolution.subCategoryCodes( new HashSet<String>( set2 ) );
        }
        Set<String> set3 = tmaChannelSolutionDTO.getSubCategoryNames();
        if ( set3 != null ) {
            tMAChannelSolution.subCategoryNames( new HashSet<String>( set3 ) );
        }
        tMAChannelSolution.genericProductId( tmaChannelSolutionDTO.getGenericProductId() );
        tMAChannelSolution.genericProductCode( tmaChannelSolutionDTO.getGenericProductCode() );
        tMAChannelSolution.genericProductName( tmaChannelSolutionDTO.getGenericProductName() );
        tMAChannelSolution.genericProductVarietyId( tmaChannelSolutionDTO.getGenericProductVarietyId() );
        tMAChannelSolution.genericProductVarietyCode( tmaChannelSolutionDTO.getGenericProductVarietyCode() );
        tMAChannelSolution.genericProductVarietyName( tmaChannelSolutionDTO.getGenericProductVarietyName() );
        tMAChannelSolution.baseSolutionId( tmaChannelSolutionDTO.getBaseSolutionId() );
        tMAChannelSolution.solutionId( tmaChannelSolutionDTO.getSolutionId() );
        Set<String> set4 = tmaChannelSolutionDTO.getTagCodes();
        if ( set4 != null ) {
            tMAChannelSolution.tagCodes( new HashSet<String>( set4 ) );
        }
        Set<String> set5 = tmaChannelSolutionDTO.getTagLabels();
        if ( set5 != null ) {
            tMAChannelSolution.tagLabels( new HashSet<String>( set5 ) );
        }
        tMAChannelSolution.solutionType( tmaChannelSolutionDTO.getSolutionType() );
        tMAChannelSolution.solutionClass( tmaChannelSolutionDTO.getSolutionClass() );
        tMAChannelSolution.tagCodesByType( stringSetMapToStringListMap( tmaChannelSolutionDTO.getTagCodesByType() ) );
        tMAChannelSolution.tagLabelsByType( stringSetMapToStringListMap( tmaChannelSolutionDTO.getTagLabelsByType() ) );
        tMAChannelSolution.tagLabelsByLang( stringSetMapToStringListMap( tmaChannelSolutionDTO.getTagLabelsByLang() ) );

        return tMAChannelSolution.build();
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
