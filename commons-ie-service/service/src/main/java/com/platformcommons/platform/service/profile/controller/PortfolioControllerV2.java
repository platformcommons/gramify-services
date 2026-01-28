package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.profile.facade.PortfolioFacadeV2;
import com.platformcommons.platform.service.sdk.portfolio.dto.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolios")
@Api(tags = { "Portfolio" })
public class PortfolioControllerV2 {

	private static final Logger LOG = LoggerFactory.getLogger(PortfolioControllerV2.class);

	@Autowired
	private PortfolioFacadeV2 portfolioFacadeV2;


	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("")
	@ApiOperation(value = "Creates portfolio")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Portfolio has successfully been created"),
			@ApiResponse(code = 409, message = "Portfolio already exists") })
	public Long createPortfolio(
			@ApiParam(required = true, value = "Required PortfolioDTO to save Portfolio") @RequestBody PortfolioDTO portfolioDTO) {
		return portfolioFacadeV2.createPortfolio(portfolioDTO);
	}
}
