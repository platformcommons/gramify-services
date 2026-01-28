package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyDTO;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.ProductSKU;
import com.platformcommons.platform.service.search.dto.*;
import com.platformcommons.platform.service.search.facade.GenericProductVarietyFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "GenericProductVariety")
public class GenericProductVarietyController {

    @Autowired
    private GenericProductVarietyFacade facade;

    @ApiOperation(value = "", nickname = "saveProductSKU", notes = "", response = GenericProductVarietySearchDTO.class, tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = ProductSKU.class) })
    @RequestMapping(value = "/api/v1/generic-product-variety",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<GenericProductVarietySearchDTO> saveProductSKU(@Valid @RequestBody GenericProductVarietySearchDTO genericProductVarietySearchDTO) {
        return new ResponseEntity<>(facade.save(genericProductVarietySearchDTO), HttpStatus.CREATED);
    }


    @ApiOperation(value = "", nickname = "getFacet", notes = "", response = GenericProductVarietySearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductVarietySearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/generic-product-variety/facet-page",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<FacetPageDTO<GenericProductVarietySearchDTO>> getFacet(@RequestParam Set<String> fields,
                                                                @RequestParam(required = false) String searchTerm,
                                                                @RequestParam Integer page,
                                                                @RequestParam Integer size,
                                                                @RequestParam(required = false) String sortBy,
                                                                @RequestParam(required = false) String direction) {
        FacetPageDTO<GenericProductVarietySearchDTO> facetPageDTO = facade.filterSearch(searchTerm,fields, page, size, sortBy, direction);
        return new ResponseEntity<>(facetPageDTO, facetPageDTO.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }

    @ApiOperation(value = "", nickname = "getFacetWithFilters", notes = "", response = GenericProductVarietySearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductVarietySearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/generic-product-variety/facet-page/filter",
            produces = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<FacetPageDTO<GenericProductVarietySearchDTO>> getFacetWithFilters(@Valid @RequestBody GenericProductVarietyFilterDTO body) {
        FacetPageDTO<GenericProductVarietySearchDTO> facetPageDTO = facade.getFacetWithFilters(body);
        return new ResponseEntity<>(facetPageDTO, facetPageDTO.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }



    @ApiOperation(value = "", nickname = "getFacetWithFilters", notes = "", response = GenericProductVarietySearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductVarietySearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/generic-product-variety/filter-search/{marketCode}/{channelCode}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<FacetPageDTO<GenericProductVarietySearchDTO>> filterSearch(@PathVariable("channelCode") String channelCode,
                                                                                     @PathVariable("marketCode") String marketCode,
                                                                                     @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                                     @RequestParam(value = "categories", required = false) Set<String> categoryCodes,
                                                                                     @RequestParam(value = "subCategories", required = false) Set<String> subCategoryCodes,
                                                                                     @RequestParam(value = "fields", required = true) Set<String> fields,
                                                                                     @RequestParam(value = "maxSellerCount", required = false) Long maxSellerCount,
                                                                                     @RequestParam(value = "priceStartRange", required = false) Double priceStartRange,
                                                                                     @RequestParam(value = "priceEndRange", required = false) Double priceEndRange,
                                                                                     @RequestParam(value = "quantityStartRange", required = false) Double quantityStartRange,
                                                                                     @RequestParam(value = "quantityEndRange", required = false) Double quantityEndRange,
                                                                                     @RequestParam(value = "genericProductVarietyId", required = false) Set<Long> genericProductVarietyId,
                                                                                     @RequestParam(value = "genericProductId", required = false) Set<Long> genericProductId,
                                                                                     @RequestParam(value = "excludeGenericProductVarietyId", required = false) Long excludeGenericProductVarietyId,
                                                                                     @RequestParam Integer page,
                                                                                     @RequestParam Integer size,
                                                                                     @RequestParam(required = false) String sortBy,
                                                                                     @RequestParam(required = false) String direction) {
        FacetPageDTO<GenericProductVarietySearchDTO> facetPageDTO = facade.getFacetWithFilters(channelCode,marketCode,searchTerm,categoryCodes,
                subCategoryCodes,fields,maxSellerCount,priceStartRange,priceEndRange,quantityStartRange,quantityEndRange,genericProductVarietyId,genericProductId,excludeGenericProductVarietyId,page,size,sortBy,direction);
        return new ResponseEntity<>(facetPageDTO, facetPageDTO.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }


    @ApiOperation(value = "", nickname = "getByGenericProductVarietyId", notes = "", response = GenericProductVarietySearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductVarietySearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/generic-product-variety/id/{marketCode}/{channelCode}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<GenericProductVarietySearchDTO> getByGenericProductVarietyId(@RequestParam Long genericProductVarietyId,
                                                                                 @PathVariable("channelCode") String channelCode,
                                                                                 @PathVariable("marketCode") String marketCode){

       return  ResponseEntity.status(HttpStatus.OK).body(facade.getByGenericProductVarietyId(genericProductVarietyId,channelCode,marketCode));
    }

    @ApiOperation(value = "get Generic Products", nickname = "getGenericProducts", notes = "", response = GenericProductSearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductSearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/generic-product-variety/generic-products/{marketCode}/{channelCode}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Set<GenericProductSearchDTO>> getGenericProducts(@PathVariable("marketCode") String marketCode,
                                                                           @PathVariable("channelCode") String channelCode,
                                                                           @Valid @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                                                           @Valid @RequestParam(value = "subCategoryCode", required = false) String subCategoryCode,
                                                                           @Valid @RequestParam(value = "searchTerm", required = false) String searchTerm){

        Set<GenericProductSearchDTO> results = facade.getGenericProducts(marketCode,channelCode,categoryCode,subCategoryCode,searchTerm);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "", nickname = "getFacetWithFiltersV2", notes = "", response = GenericProductVarietySearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductVarietySearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v2/generic-product-variety/filter-search/{marketCode}/{channelCode}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<FacetPageDTO<GenericProductVarietySearchDTO>> filterSearchV2(@PathVariable("channelCode") String channelCode,
                                                                                     @PathVariable("marketCode") String marketCode,
                                                                                     @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                                     @RequestParam(value = "categories", required = false) Set<String> categoryCodes,
                                                                                     @RequestParam(value = "subCategories", required = false) Set<String> subCategoryCodes,
                                                                                     @RequestParam(value = "fields", required = true) Set<String> fields,
                                                                                     @RequestParam(value = "maxSellerCount", required = false) Long maxSellerCount,
                                                                                     @RequestParam(value = "priceStartRange", required = false) Double priceStartRange,
                                                                                     @RequestParam(value = "priceEndRange", required = false) Double priceEndRange,
                                                                                     @RequestParam(value = "quantityStartRange", required = false) Double quantityStartRange,
                                                                                     @RequestParam(value = "quantityEndRange", required = false) Double quantityEndRange,
                                                                                     @RequestParam(value = "genericProductVarietyId", required = false) Set<Long> genericProductVarietyId,
                                                                                     @RequestParam(value = "genericProductId", required = false) Set<Long> genericProductId,
                                                                                     @RequestParam(value = "excludeGenericProductVarietyId", required = false) Long excludeGenericProductVarietyId,
                                                                                       @RequestParam(value = "excludeCategoryCodes", required = false) Set<String> excludeCategoryCodes,
                                                                                     @RequestParam Integer page,
                                                                                     @RequestParam Integer size,
                                                                                     @RequestParam(required = false) String sortBy,
                                                                                     @RequestParam(required = false) String direction,
                                                                                     @RequestParam(value = "parentClassificationCode", required = true) String parentClassificationCode,
                                                                                     @RequestParam(value = "sourceTraderType",required = false) String sourceTraderType,
                                                                                     @RequestParam(value = "transactionType", required = false) String transactionType,
                                                                                       @RequestParam(value = "solutionId", required = false) Long solutionId) {
        FacetPageDTO<GenericProductVarietySearchDTO> facetPageDTO = facade.getFacetWithFiltersV2(channelCode,marketCode,searchTerm,categoryCodes,
                subCategoryCodes,fields,maxSellerCount,priceStartRange,priceEndRange,quantityStartRange,quantityEndRange,genericProductVarietyId,
                genericProductId,excludeGenericProductVarietyId,excludeCategoryCodes,page,size,sortBy,direction,parentClassificationCode,sourceTraderType,transactionType,solutionId);
        return new ResponseEntity<>(facetPageDTO, facetPageDTO.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }

    @ApiOperation(value = "get Generic Products V2", nickname = "getGenericProductsV2", notes = "", response = GenericProductSearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductSearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v2/generic-product-variety/generic-products/{marketCode}/{channelCode}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Set<GenericProductSearchDTO>> getGenericProductsV2(@PathVariable("marketCode") String marketCode,
                                                                           @PathVariable("channelCode") String channelCode,
                                                                           @Valid @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                                                           @Valid @RequestParam(value = "subCategoryCode", required = false) String subCategoryCode,
                                                                           @Valid @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                             @Valid @RequestParam(value = "parentClassificationCode", required = false) String parentClassificationCode,
                                                                             @Valid @RequestParam(value = "sourceTraderType",required = false) String sourceTraderType,
                                                                             @Valid @RequestParam(value = "transactionType", required = false) String transactionType){

        Set<GenericProductSearchDTO> results = facade.getGenericProductsV2(marketCode,channelCode,categoryCode,subCategoryCode,searchTerm,parentClassificationCode,sourceTraderType,transactionType);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "get Generic Product Varieties", nickname = "getGenericProductVarieties", notes = "",
            response = GenericProductVarietySearchDTO.class, responseContainer = "Set", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductVarietySearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/generic-product-variety",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Set<GenericProductVarietySearchDTO>> getGenericProductVarieties(@Valid @RequestParam(value ="marketCode") String marketCode,
                                                                                          @Valid @RequestParam(value ="channelCode") String channelCode,
                                                                                          @Valid @RequestParam(value = "genericProductVarietyCodes", required = false) Set<String> genericProductVarietyCodes){

        Set<GenericProductVarietySearchDTO> results = facade.getGenericProductVarieties(marketCode,channelCode,genericProductVarietyCodes);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(
            value = "Reindex Generic Product Variety",
            notes = "Triggers reindexing of all Generic Products Varieties.",
            response = Void.class,
            tags = {"GenericProductVariety"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content - Reindex completed successfully")
    })
    @RequestMapping(value = "/api/v1/generic-product-variety/re-index",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> reindex () {
        facade.reindex();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "refresh Generic Product Varieties for a market and channel", nickname = "refreshGenericProductVarieties", notes = "", tags={ "GenericProductVariety", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "api/v1/generic-product-variety/refresh",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<Void> refreshGenericProductVarieties(@Valid @RequestParam(value ="marketId") Long marketId,
                                                           @Valid @RequestParam(value ="channelId") Long channelId,
                                                           @Valid @RequestParam(value = "genericProductVarietyIds") Set<Long> genericProductVarietyIds){

        facade.refreshGenericProductVarieties(marketId,channelId,genericProductVarietyIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
