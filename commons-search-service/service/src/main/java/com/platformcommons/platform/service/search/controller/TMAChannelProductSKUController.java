package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.domain.ProductSKU;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.GenericProductSearchDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO;
import com.platformcommons.platform.service.search.facade.TMAChannelProductSKUFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "TMAChannelProductSKU")
public class TMAChannelProductSKUController {

    @Autowired
    private TMAChannelProductSKUFacade facade;

    @ApiOperation(value = "", nickname = "saveTMAChannelProductSKU", notes = "", response = TMAChannelProductSKUDTO.class, tags={ "TMAChannelProductSKU", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = ProductSKU.class) })
    @RequestMapping(value = "/api/v1/tma-channel-product-sku",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<TMAChannelProductSKUDTO> saveTMAChannelProductSKU(@Valid @RequestBody TMAChannelProductSKUDTO tmaChannelProductSKUDTO) {
        return new ResponseEntity<>(facade.save(tmaChannelProductSKUDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "", nickname = "getPublishedSKUs", notes = "", response = TMAChannelProductSKUDTO.class, tags={ "TMAChannelProductSKU", })
    @RequestMapping(value = "/api/v1/tma-channel-product-sku",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<TMAChannelProductSKUDTO>> getPublishedSKUs(@RequestParam("channelCode") String channelCode,
                                                                           @RequestParam("marketCode") String marketCode,
                                                                           @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                           @RequestParam(value = "categories", required = false) Set<String> categoryCodes,
                                                                           @RequestParam(value = "subCategories", required = false) Set<String> subCategoryCodes,
                                                                           @RequestParam(value = "fields", required = true) Set<String> fields,
                                                                           @RequestParam(value = "genericProductId", required = false) Long genericProductId,
                                                                           @RequestParam(value = "genericProductVarietyId", required = false) Long genericProductVarietyId,
                                                                           @RequestParam(value = "traderId", required = false) Long traderId,
                                                                           @RequestParam Integer page,
                                                                           @RequestParam Integer size,
                                                                           @RequestParam(required = false) String sortBy,
                                                                           @RequestParam(required = false) String direction,
                                                                           @RequestParam(value = "solutionId", required = false) Long solutionId,
                                                                           @RequestParam(value = "productSubType", required = false) String productSubType,
                                                                           @RequestParam(value = "skuFactorCodes", required = false) Set<String> skuFactorCodes,
                                                                           @RequestParam(value = "locationCode", required = false) String locationCode,
                                                                           @RequestParam(value = "isTypeAsOffering", required = false) Boolean isTypeAsOffering,
                                                                           @RequestParam(value = "tagType", required = false) String tagType,
                                                                           @RequestParam(value = "tagCodes", required = false) Set<String> tagCodes) {
        return new ResponseEntity<>(facade.getPublishedSKUs(channelCode,marketCode,searchTerm,categoryCodes,subCategoryCodes,
                fields,genericProductId,genericProductVarietyId,traderId,page,size,sortBy,direction,solutionId,productSubType,
                skuFactorCodes,locationCode,isTypeAsOffering,tagType,tagCodes), HttpStatus.CREATED);
    }

    @ApiOperation(value = "get TMAChannelProductSkus", nickname = "getTMAChannelProductSkus", notes = "", response = TMAChannelProductSKUDTO.class, responseContainer = "Set", tags={ "TMAChannelProductSKU", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TMAChannelProductSKUDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/tma-channel-product-sku/ids",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,TMAChannelProductSKUDTO>> getTMAChannelProductSkusById(@RequestParam(value = "tmaChannelProductSkuIds", required = true) Set<Long> tmaChannelProductSkuIds){

        Map<Long,TMAChannelProductSKUDTO> results = facade.getTMAChannelProductSkusById(tmaChannelProductSkuIds);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(
            value = "Reindex TMA Channel Product SKU",
            notes = "Triggers reindexing of all TMA Channel Products.",
            response = Void.class,
            tags = {"TMAChannelProductSKU"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content - Reindex completed successfully")
    })
    @RequestMapping(value = "/api/v1/tma-channel-product-sku/re-index",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> reindex () {
        facade.reindex();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @ApiOperation(value = "refresh TmaChannel ProductSKUS for a market and channel", nickname = "refreshTmaChannelProductSKUS", notes = "", tags={ "TMAChannelProductSKU", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "api/v1/tma-channel-product-sku/refresh",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<Void> refreshTmaChannelProductSKUS(@Valid @RequestParam(value ="marketId") Long marketId,
                                                               @Valid @RequestParam(value ="channelId") Long channelId,
                                                               @Valid @RequestParam(value = "productSkuIds") Set<Long> productSkuIds){

        facade.refreshTmaChannelProductSKUS(marketId,channelId,productSkuIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Get TMAChannelProductSkus By ProductSkuIds", nickname = "getTMAChannelProductSkusByProductSkuIds", notes = "", response = TMAChannelProductSKUDTO.class, tags={ "TMAChannelProductSKU" })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TMAChannelProductSKUDTO.class)})
    @RequestMapping(
            value = "api/v1/tma-channel-product-sku/sku-ids",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long, TMAChannelProductSKUDTO>> getTMAChannelProductSkusByProductSkuIds(
            @RequestParam(value = "productSKUIds") Set<Long> productSKUIds,
            @RequestParam(value = "marketId") Long marketId,
            @RequestParam(value = "channelId") Long channelId) {
        return ResponseEntity.ok(facade.getTMAChannelProductSkusByProductSkuIds(productSKUIds, marketId, channelId));
    }

}
