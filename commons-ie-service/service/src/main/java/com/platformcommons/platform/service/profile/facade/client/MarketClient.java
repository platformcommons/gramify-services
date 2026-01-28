package com.platformcommons.platform.service.profile.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.market.dto.TraderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "commons-market-service${commons.service.commons-market-service.context-path:/commons-market-service}",
        contextId = "MarketClient"
)
public interface MarketClient {

    @RequestMapping(
            value = {"/api/v1/trader/current-tenant"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<TraderDTO> getCurrentTenantTrader(@RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

}
