package com.platformcommons.platform.service.search.facade.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "commons-connect-service${commons.service.commons-connect-service.context-path:/commons-connect-service}",
        contextId = "Commons-Connect-Client")
public interface CommonsConnectClient {

    @GetMapping(value = "/api/v1/connections/bookmarks/to-users/all/me")
    ResponseEntity<List<Long>> getBookmarkedUserIdsForLoggedInUser(@RequestHeader("X-SESSIONID") String sessionId);

}