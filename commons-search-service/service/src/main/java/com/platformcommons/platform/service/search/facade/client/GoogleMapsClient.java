package com.platformcommons.platform.service.search.facade.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        url = "${commons.client.google.map.url:https://maps.googleapis.com}",
        name = "googleMapsClient")
public interface GoogleMapsClient {


    @GetMapping("/maps/api/place/textsearch/json")
    ResponseEntity<Object> searchLocation(@RequestParam("query") String query,@RequestParam("key") String key);
}
