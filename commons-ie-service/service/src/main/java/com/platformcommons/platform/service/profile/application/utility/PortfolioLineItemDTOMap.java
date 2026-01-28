package com.platformcommons.platform.service.profile.application.utility;

import com.platformcommons.platform.service.profile.domain.vo.CropPlanExcelVo;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioLineItemDTO;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioProductLineItemDTO;

import java.util.*;
import java.util.stream.Collectors;

public class PortfolioLineItemDTOMap {

    private final Map<String, Map<String, PortfolioLineItemDTO>> farmerlandPortfolioMap = new LinkedHashMap<>();

    public void createPortfolioLineItemIfNotPresent(CropPlanExcelVo cropPlanExcelVo) {
        final String farmerSerialNumber = cropPlanExcelVo.getFarmerSerialNumber();
        final String landSerialNumber   = cropPlanExcelVo.getLandSerialNumber();
        farmerlandPortfolioMap.put(farmerSerialNumber, farmerlandPortfolioMap.getOrDefault(farmerSerialNumber, new LinkedHashMap<>()));
        final Map<String, PortfolioLineItemDTO> landPortfolioMap = farmerlandPortfolioMap.get(farmerSerialNumber);
        final boolean created = landPortfolioMap.containsKey(landSerialNumber);
        if (!created) {
            final Set<String> practiceMasterCodes = (cropPlanExcelVo.getPracticeCodes()==null || cropPlanExcelVo.getPracticeCodes().isEmpty()) ?new HashSet<>():Arrays.stream(cropPlanExcelVo.getPracticeCodes().split(",")).collect(Collectors.toSet());
            landPortfolioMap.put(landSerialNumber, PortfolioLineItemDTO.builder()
                    .assetCode(landSerialNumber)
                    .portfolioProductLineItemList(new LinkedHashSet<>())
                    .practiceMasterCodes(practiceMasterCodes)
                    .build());
        }
    }

    private PortfolioLineItemDTO get(String farmerSerialNumber, String landSerialNumber) {
        return farmerlandPortfolioMap.get(farmerSerialNumber).get(landSerialNumber);
    }

    public void add(String farmerSerialNumber, String landSerialNumber, PortfolioProductLineItemDTO portfolioProductLineItemDTO) {
        get(farmerSerialNumber,landSerialNumber).getPortfolioProductLineItemList().add(portfolioProductLineItemDTO);
    }

    public Map<String, Set<PortfolioLineItemDTO>> getResult() {
        Map<String, Set<PortfolioLineItemDTO>> map = new LinkedHashMap<>();
        farmerlandPortfolioMap.forEach(
                (farmerSerialNumber, landPortfolioLineItemDTOMap) -> map.put(farmerSerialNumber,new LinkedHashSet<>(landPortfolioLineItemDTOMap.values()))
        );
        return map;
    }
}
