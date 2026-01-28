package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.iam.dto.MarketConfigDTO;
import com.platformcommons.platform.service.iam.dto.MarketPartnerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;


@FeignClient(name = "commons-opportunity-service${commons.service.commons-opportunity-service.context-path:/commons-opportunity-service}",
        contextId = "OpportunityClient")
public interface OpportunityClient {

    @GetMapping("api/v1/market-configs")
    ResponseEntity<MarketConfigDTO> getByMarketUUID(@RequestParam("marketUUID") String marketUUID,
                                                    @RequestParam(value = "appContext",required = false) String appContext);

    @PostMapping("/api/v1/market-configs")
    ResponseEntity<Long> save(@RequestBody MarketConfigDTO marketConfigDTO, @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);


    @PostMapping("/api/v1/market-configs/market-partners")
    ResponseEntity<Void> addMarketPartnerToMarketByMarketUUID(@RequestBody MarketPartnerDTO body,
                                                              @RequestParam String marketUUID,
                                                              @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);

    @GetMapping("/api/v1/market-configs/tenant-linked/all")
    ResponseEntity<Set<MarketConfigDTO>> getAllMarketConfigsAndPartnerLinkedToTenantIds(@RequestParam(value = "tenantIds") Set<Long> tenantIds,
                                                                                        @RequestParam(value = "appContext",required = false) String appContext,
                                                                                        @RequestParam(value = "partnerStatus", required = false) String partnerStatus,
                                                                                        @RequestHeader Map<String,String> headers);

}
