package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.service.iam.dto.TFIUserVerificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "tfi-client",
        url = "${commons.platform.tfi.base-url:https://teachforindia--dev4.sandbox.my.salesforce-sites.com}"
        )
public interface TFIClient {

    @RequestMapping(value = "/Webhook/services/apexrest/alumniportal/signup", method = RequestMethod.POST)
    ResponseEntity<Map<String,String>> tfiUserVerification(@RequestBody TFIUserVerificationDTO tfiUserVerificationDTO,
                                                           @RequestHeader HttpHeaders headers);
}
