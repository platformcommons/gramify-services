package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.person.dto.FarmerCropPlanDTO;
import com.platformcommons.platform.service.profile.facade.PortfolioFacadeV2;
import com.platformcommons.platform.service.profile.facade.mapper.PortfolioToFarmerCropPlanMapper;
import com.platformcommons.platform.service.profile.messaging.producer.FarmerCropPlanDTOEventProducer;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioDTO;
import com.platformcommons.platform.service.sdk.portfolio.facade.PortfolioFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Component
@Transactional
public class PortfolioFacadeV2Impl implements PortfolioFacadeV2 {

    @Autowired
    private PortfolioFacade portfolioFacade;

    @Autowired
    private PortfolioToFarmerCropPlanMapper portfolioToFarmerCropPlanMapper;

    @Autowired
    private FarmerCropPlanDTOEventProducer farmerCropPlanDTOEventProducer;

    @Override
    public Long createPortfolio(PortfolioDTO portfolioDTO) {
        PortfolioDTO createdPortfolioDTO = portfolioFacade.createPortfolio(portfolioDTO);

        if(portfolioDTO.getOwnerEntityType().equals("ENTITY_TYPE.IE")){
            Set<FarmerCropPlanDTO> farmerCropPlanDTOSet = portfolioToFarmerCropPlanMapper.fromPortfolioToFarmerCropPlanDTO(createdPortfolioDTO);
            farmerCropPlanDTOSet.forEach(farmerCropPlanDTO -> farmerCropPlanDTOEventProducer.farmerCropPlanCreated(farmerCropPlanDTO));
        }
        return createdPortfolioDTO.getId();
    }
}
