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

@FeignClient(name = "changemaker-opportunity-service${commons.service.changemaker-opportunity-service.context-path:/commons-changemaker-opportunity-service}",
        contextId = "ChangemakerClient")
public interface ChangemakerOpportunityClient {

    @PostMapping(value = "/api/v1/users/auto-approval/mail")
    ResponseEntity<Void> welcomeMailForTfiUserAutoApproval(@NotNull @Valid @RequestParam(value = "userId") Long userId,
                                                           @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);


}
