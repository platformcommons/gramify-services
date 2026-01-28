package com.platformcommons.platform.service.profile.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.person.dto.ptld.UserWrapperDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "TLDClient",
        url = "${commons.platform.tld.base-url}"
)
public interface TldClient {

    @PostMapping("/ptld/api/workforce/userWrapper/v1")
    UserWrapperDTO postAba(@RequestBody UserWrapperDTO userWrapperDTO,@RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

}