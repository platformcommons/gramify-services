package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.market.dto.TraderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "commons-market-service${commons.service.commons-market-service.context-path:/commons-market-service}",
        contextId = "MarketClient"
)
public interface MarketClient {


    @RequestMapping(value = "/api/v1/trader-onboard",
            method = RequestMethod.POST)
    ResponseEntity<Long> onBoardTraderWithDefaultMarketConfig(@RequestBody TraderDTO traderDTO, @RequestParam Long marketId,
                                                              @RequestParam(required = false) Boolean isTraderAsUser,
                                                              @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId) ;
}
