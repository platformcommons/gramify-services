package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.service.opportunity.dto.MarketConfigDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@FeignClient(name = "commons-opportunity-service${commons.service.commons-opportunity-service.context-path:/commons-opportunity-service}",
        contextId = "CommonsOpportunityClient")
public interface CommonsOpportunityClient {

    @GetMapping("api/v1/market-configs")
    ResponseEntity<MarketConfigDTO> getByMarketUUID(@RequestParam("marketUUID") String marketUUID,
                                                    @RequestParam(value = "appContext",required = false) String appContext);

}
