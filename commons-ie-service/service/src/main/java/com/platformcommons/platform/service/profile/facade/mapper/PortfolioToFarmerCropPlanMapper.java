package com.platformcommons.platform.service.profile.facade.mapper;

import com.platformcommons.platform.service.person.dto.FarmerCropPlanDTO;
import com.platformcommons.platform.service.person.dto.IeInfoVO;
import com.platformcommons.platform.service.profile.dto.DeliveryModeDTO;
import com.platformcommons.platform.service.profile.facade.IeFacade;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioDTO;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioLineItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PortfolioToFarmerCropPlanMapper {

    @Autowired
    private IeFacade ieFacade;

    public Set<FarmerCropPlanDTO> fromPortfolioToFarmerCropPlanDTO(PortfolioDTO portfolioDTO){
        IeInfoVO ieInfoVO = ieFacade.getInfoOfIe(portfolioDTO.getOwnerEntityId());
        Set<FarmerCropPlanDTO> farmerCropPlanDTOSet = new HashSet<>();

        Set<PortfolioLineItemDTO> portfolioLineItemDTOS = portfolioDTO.getPortfolioLineItemList();
        portfolioLineItemDTOS.forEach(portfolioLineItemDTO ->
                portfolioLineItemDTO.getPortfolioProductLineItemList().forEach(portfolioProductLineItemDTO -> {
                    FarmerCropPlanDTO farmerCropPlanDTO = FarmerCropPlanDTO.builder()
                            .portfolioId(portfolioDTO.getId())
                            .portfolioLineItemId(portfolioLineItemDTO.getId())
                            .portfolioProductLineItemId(portfolioProductLineItemDTO.getId())
                            .genericProductVarietyId(portfolioProductLineItemDTO.getGenericProductVarietyId())
                            .genericProductVarietyCode(portfolioProductLineItemDTO.getGenericProductVarietyCode())
                            .quantityUoMCode(portfolioProductLineItemDTO.getExpectedOutput().getUoM())
                            .quantity(portfolioProductLineItemDTO.getExpectedOutput().getValue())
                            .harvestStartDate(portfolioProductLineItemDTO.getHarvestStartDate())
                            .hardvestEndDate(portfolioProductLineItemDTO.getHarvestEndDate())
                            //.practicesFollowed()
                            .ieId(ieInfoVO.getId())
                            .ieName(ieInfoVO.getName())
                            .mobile(ieInfoVO.getMobile())
                            .email(ieInfoVO.getEmail())
                            .iconPic(ieInfoVO.getIconPic())
                            .onTraderCenterId(ieInfoVO.getOnTraderCenterId())
                            .taggedToWorknodeId(ieInfoVO.getTaggedToWorknodeId())
                            .taggedToWorkforceId(ieInfoVO.getTaggedToWorkforceId())
                            .deliveryModeList(
                                    ieInfoVO.getDeliveryModeList().stream()
                                            .map(ied -> DeliveryModeDTO.builder()
                                                    .uuid(ied.getUuid())
                                                    .appCreatedAt(ied.getAppCreatedAt())
                                                    .appLastModifiedAt(ied.getAppLastModifiedAt())
                                                    .id(ied.getId())
                                                    .code(ied.getCode())
                                                    .type(ied.getType())
                                                    .build()
                                            )
                                            .collect(Collectors.toSet())).build();
                    farmerCropPlanDTOSet.add(farmerCropPlanDTO);
                }));
        return farmerCropPlanDTOSet;
    }
}
