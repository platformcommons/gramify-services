package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@FeignClient(
    name = "commons-product-service${commons.service.commons-product-service.context-path:/commons-product-service}",
    contextId = "GenericProductVarietyClientV2"
)
public interface GenericProductVarietyClientV2 {
    @RequestMapping(
        value = {"/api/v1/generic-product-variety"},
        method = {RequestMethod.DELETE}
    )
    ResponseEntity<Void> deleteGenericProductVariety(@RequestParam(value = "id",required = true) @NotNull @Valid Long id, @RequestParam(value = "reason",required = true) @NotNull @Valid String reason);

    @RequestMapping(
        value = {"/api/v1/generic-product-variety/custom"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<GenericProductVarietyEventDTO> getCustomForGenericProductVariety(@RequestParam(value = "genericProductVarietyId",required = true) @NotNull @Valid Long genericProductVarietyId,
                                                                                    @RequestHeader("Authorization") String clientKey);

    @RequestMapping(
        value = {"/api/v1/generic-product-variety"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<GenericProductVarietyDTO> getGenericProductVariety(@RequestParam(value = "id",required = true) @NotNull @Valid Long id);

    @RequestMapping(
        value = {"/api/v1/generic-product-variety/page"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<PageDTO<GenericProductVarietyDTO>> getGenericProductVarietyPage(@RequestParam(value = "page",required = true) @NotNull @Valid Integer page, @RequestParam(value = "size",required = true) @NotNull @Valid Integer size);

    @RequestMapping(
        value = {"/api/v1/generic-product-variety"},
        method = {RequestMethod.POST}
    )
    ResponseEntity<Long> postGenericProductVariety(@RequestParam(value = "genericProductId",required = true) @NotNull @Valid Long genericProductId, @RequestBody @Valid GenericProductVarietyDTO body);

    @RequestMapping(
        value = {"/api/v1/generic-product-variety"},
        method = {RequestMethod.PUT}
    )
    ResponseEntity<GenericProductVarietyDTO> putGenericProductVariety(@RequestBody @Valid GenericProductVarietyDTO body);

    @RequestMapping(value = "/api/v2/generic-product-variety/custom",
            method = RequestMethod.GET)
    ResponseEntity<Set<GenericProductVarietyEventDTO>> getCustomForGenericProductVarietyV2(
             @RequestParam(value = "tagIdentifiers", required = true) Set<String> tagIdentifiers,
             @RequestHeader("Authorization") String clientKey);
}