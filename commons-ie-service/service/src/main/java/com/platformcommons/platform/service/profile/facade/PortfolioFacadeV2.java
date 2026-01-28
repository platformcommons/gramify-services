package com.platformcommons.platform.service.profile.facade;

import com.netflix.appinfo.AbstractEurekaIdentity;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.dto.PersonPocDTO;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioDTO;

public interface PortfolioFacadeV2 {

    Long createPortfolio(PortfolioDTO portfolioDTO);
}
