package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@FeignClient(
    name = "commons-product-service${commons.service.commons-product-service.context-path:/commons-product-service}",
    contextId = "ClassificationClientV2"
)
public interface ClassificationClientV2 {

    @RequestMapping(value = "/api/v2/classification/parent",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Map<String,Object>> getChildClassificationByParentCodeAndTraderTypeInterest(@NotNull @Valid @RequestParam(value = "parentClassificationCode", required = true) String parentClassificationCode,
                                                                                                       @NotNull  @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                                                       @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                                                       @Valid @RequestParam(value = "productType", required = false) String productType,
                                                                                                       @Valid @RequestParam(value = "context", required = false) String context,
                                                                                                       @Valid @RequestParam(value = "contextType", required = false) String contextType,
                                                                                                       @Valid @RequestParam(value = "sourceTraderType", required = false) String sourceTraderType,
                                                                                                       @Valid @RequestParam(value = "targetTraderType", required = false) String targetTraderType,
                                                                                                       @Valid @RequestParam(value = "transactionType", required = false) String transactionType,
                                                                                                       @RequestHeader("X-SESSIONID") String sessionId);


    @RequestMapping(value = "/api/v2/classification/parent",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Map<String,Object>> getChildClassificationByParentCodeAndTraderTypeInterestWithAppKey(@NotNull @Valid @RequestParam(value = "parentClassificationCode", required = true) String parentClassificationCode,
                                                                                               @NotNull  @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                                               @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                                               @Valid @RequestParam(value = "productType", required = false) String productType,
                                                                                               @Valid @RequestParam(value = "context", required = false) String context,
                                                                                               @Valid @RequestParam(value = "contextType", required = false) String contextType,
                                                                                               @Valid @RequestParam(value = "sourceTraderType", required = false) String sourceTraderType,
                                                                                               @Valid @RequestParam(value = "targetTraderType", required = false) String targetTraderType,
                                                                                               @Valid @RequestParam(value = "transactionType", required = false) String transactionType,
                                                                                               @RequestHeader("Authorization") String appKey);

    @RequestMapping(value = "/api/v1/product-sku/multiple",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long, ProductSKUDTO>> getProductSKUs(@NotNull @Valid @RequestParam(value = "productSKUIds", required = true) Set<Long> productSKUIds,
                                                                   @RequestHeader("X-SESSIONID") String sessionId) ;

    @RequestMapping(value = "/api/v1/product-sku/multiple",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<Map<Long, ProductSKUDTO>> getProductSKUsWithAppKey(@RequestHeader("Authorization") String appKey,
                                                            @RequestParam(value = "productSKUIds", required = true) Set<Long> productSKUIds);


    @RequestMapping(value = "/api/v1/solution/solution-tag",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Map<Long, Set<SolutionTagDTO>>> getSolutionsWithSolutionTagsWithAppKey(@RequestHeader("Authorization") String appKey,
                                                                                @RequestParam(value = "solutionIds", required = true) Set<Long> solutionIds);

}