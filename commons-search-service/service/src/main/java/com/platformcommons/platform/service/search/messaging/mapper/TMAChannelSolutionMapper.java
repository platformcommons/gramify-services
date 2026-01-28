package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.market.dto.PublishedSolutionDTO;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TMAChannelSolutionMapper {

    public TMAChannelSolution  map(PublishedSolutionDTO publishedSolutionDTO){
        return  TMAChannelSolution.builder()
                .id(String.valueOf(publishedSolutionDTO.getTmaChannelSolutionId()))
                .createdTimestamp(new Date().getTime())
                .lastModifiedTimestamp(new Date().getTime())
                .channelId(publishedSolutionDTO.getChannelId())
                .channelCode(publishedSolutionDTO.getChannelCode())
                .channelName(publishedSolutionDTO.getChannelName())
                .marketCode(publishedSolutionDTO.getMarketCode())
                .marketId(publishedSolutionDTO.getMarketId())
                .marketName(publishedSolutionDTO.getMarketName())
                .traderId(publishedSolutionDTO.getTraderId())
                .traderName(publishedSolutionDTO.getTraderName())
                .traderIconURL(publishedSolutionDTO.getTraderIconPic())
                .isActive(Boolean.TRUE)
                .categoryCodes(MapperUtil.getClassificationCodes(publishedSolutionDTO.getSolutionDTO().getClassificationList()))
                .categoryNames(MapperUtil.getClassificationNames(publishedSolutionDTO.getSolutionDTO().getClassificationList()))
                .subCategoryCodes(MapperUtil.getClassificationCodes(publishedSolutionDTO.getSolutionDTO().getSubClassificationList()))
                .subCategoryNames(MapperUtil.getClassificationNames(publishedSolutionDTO.getSolutionDTO().getSubClassificationList()))
                .genericProductId(publishedSolutionDTO.getSolutionDTO().getGenericProduct()!=null?publishedSolutionDTO.getSolutionDTO().getGenericProduct().getId():null)
                .genericProductCode(publishedSolutionDTO.getSolutionDTO().getGenericProduct()!=null?publishedSolutionDTO.getSolutionDTO().getGenericProduct().getCode():null)
                .genericProductName(MapperUtil.getEngLabel(publishedSolutionDTO.getSolutionDTO().getGenericProduct()!=null?publishedSolutionDTO.getSolutionDTO().getGenericProduct().getName():null))
                .genericProductVarietyId(publishedSolutionDTO.getSolutionDTO().getGenericProductVariety()!=null?publishedSolutionDTO.getSolutionDTO().getGenericProductVariety().getId():null)
                .genericProductVarietyCode(publishedSolutionDTO.getSolutionDTO().getGenericProductVariety()!=null?publishedSolutionDTO.getSolutionDTO().getGenericProductVariety().getVarietyCode():null)
                .genericProductVarietyName(MapperUtil.getEngLabel(publishedSolutionDTO.getSolutionDTO().getGenericProductVariety()!=null?publishedSolutionDTO.getSolutionDTO().getGenericProductVariety().getName():null))
                .titles(publishedSolutionDTO.getSolutionDTO().getTitle().stream()
                        .collect(Collectors.toMap(MLTextDTO::getLanguageCode, MLTextDTO::getText)))
                .baseSolutionId(publishedSolutionDTO.getSolutionDTO().getBaseSolutionId())
                .tmaChannelSolutionStatus(publishedSolutionDTO.getTmaChannelSolutionStatus())
                .traderCenterSolutionStatus(publishedSolutionDTO.getTraderCenterSolutionStatus())
                .tenantId(publishedSolutionDTO.getTenantId())
                .solutionId(publishedSolutionDTO.getSolutionDTO().getId())
                .tagCodes(MapperUtil.getTagCodes(publishedSolutionDTO.getSolutionDTO().getSolutionTagList()))
                .tagLabels(MapperUtil.getTagLabels(publishedSolutionDTO.getSolutionDTO().getSolutionTagList()))
                .tagCodesByType(MapperUtil.getTagCodesByTypeForSolutionTag(publishedSolutionDTO.getSolutionDTO().getSolutionTagList()))
                .tagLabelsByType(MapperUtil.getTagLabelsByTypeForSolutionTag(publishedSolutionDTO.getSolutionDTO().getSolutionTagList()))
                .tagLabelsByLang(MapperUtil.getTagLabelsByLangForSolutionTag(publishedSolutionDTO.getSolutionDTO().getSolutionTagList()))
                .solutionType(publishedSolutionDTO.getSolutionDTO().getSolutionType()!=null?publishedSolutionDTO.getSolutionDTO().getSolutionType().getCode():null)
                .solutionClass(publishedSolutionDTO.getSolutionDTO().getSolutionClass())
                .build();

    }
}
