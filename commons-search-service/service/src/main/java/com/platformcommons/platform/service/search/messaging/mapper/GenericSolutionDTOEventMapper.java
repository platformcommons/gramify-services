package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.product.dto.SolutionDTO;
import com.platformcommons.platform.service.search.domain.GenericSolution;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class GenericSolutionDTOEventMapper {

    public GenericSolution fromDTO(SolutionDTO solutionDTO){
        return GenericSolution.builder()
                .id(String.valueOf(solutionDTO.getId()))
                .createdTimestamp(new Date().getTime())
                .lastModifiedTimestamp(new Date().getTime())
                .titles(solutionDTO.getTitle()!=null?solutionDTO.getTitle().stream()
                        .collect(Collectors.toMap(MLTextDTO::getLanguageCode, MLTextDTO::getText)):null)
                .tagCodes(MapperUtil.getTagCodes(solutionDTO.getSolutionTagList()))
                .tagLabels(MapperUtil.getTagLabels(solutionDTO.getSolutionTagList()))
                .categoryCodes(MapperUtil.getClassificationCodes(solutionDTO.getClassificationList()))
                .categoryNames(MapperUtil.getClassificationNames(solutionDTO.getClassificationList()))
                .subCategoryCodes(MapperUtil.getClassificationCodes(solutionDTO.getSubClassificationList()))
                .subCategoryNames(MapperUtil.getClassificationNames(solutionDTO.getSubClassificationList()))
                .genericProductId(solutionDTO.getGenericProduct()!=null?solutionDTO.getGenericProduct().getId():null)
                .genericProductCode(solutionDTO.getGenericProduct()!=null?solutionDTO.getGenericProduct().getCode():null)
                .genericProductName(MapperUtil.getEngLabel(solutionDTO.getGenericProduct()!=null?solutionDTO.getGenericProduct().getName():null))
                .genericProductVarietyId(solutionDTO.getGenericProductVariety()!=null?solutionDTO.getGenericProductVariety().getId():null)
                .genericProductVarietyCode(solutionDTO.getGenericProductVariety()!=null?solutionDTO.getGenericProductVariety().getVarietyCode():null)
                .genericProductVarietyName(MapperUtil.getEngLabel(solutionDTO.getGenericProductVariety()!=null?solutionDTO.getGenericProductVariety().getName():null))
                .marketId(solutionDTO.getMarketId()!=null?solutionDTO.getMarketId():null)
                .status(solutionDTO.getStatus()!=null?solutionDTO.getStatus():null)
                .solutionType(solutionDTO.getSolutionType()!=null?solutionDTO.getSolutionType().getCode():null)
                .build();
    }


}
