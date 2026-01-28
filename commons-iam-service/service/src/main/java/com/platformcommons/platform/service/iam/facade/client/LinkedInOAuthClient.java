package com.platformcommons.platform.service.iam.facade.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "LinkedInOAuthClient",
        url = "${commons.platform.oauth.linkedin.base-url:https://www.linkedin.com}"
)
public interface LinkedInOAuthClient {
    @GetMapping("/oauth/v2/accessToken")
    ResponseEntity<Object> getAccessToken(@RequestParam("grant_type") String grantType,
                                          @RequestParam("code") String code,
                                          @RequestParam("client_id") String clientId,
                                          @RequestParam("client_secret") String clientSecret,
                                          @RequestParam("redirect_uri") String redirectUri);
}