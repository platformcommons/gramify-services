package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.CommonsLocationDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductDTO;
import com.platformcommons.platform.service.search.facade.TMAChannelProductFacade;
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
import java.util.Set;

@RestController
@Tag(name = "TMAChannelProduct")
public class TMAChannelProductController {

    @Autowired
    private TMAChannelProductFacade facade;

    @ApiOperation(value = "", nickname = "saveTMAChannelProduct", notes = "", response = TMAChannelProductDTO.class, tags={ "TMAChannelProduct", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = TMAChannelProductDTO.class) })
    @RequestMapping(value = "/api/v1/tma-channel-product",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<TMAChannelProductDTO> saveTMAChannelProduct(@Valid @RequestBody TMAChannelProductDTO tmaChannelProductDTO) {
        return new ResponseEntity<>(facade.save(tmaChannelProductDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "save Multiple TMAChannelProducts", nickname = "saveMultipleTMAChannelProducts", notes = "", tags={ "TMAChannelProduct", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/api/v1/tma-channel-product/multiple",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> saveMultipleTMAChannelProducts(@Valid @RequestBody Set<TMAChannelProductDTO> tmaChannelProductDTOS) {
        facade.saveAll(tmaChannelProductDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get TMAChannelProduct By VarietyId in Page", nickname = "getByGenericProductVarietyId", notes = "", response = TMAChannelProductDTO.class, responseContainer = "Set", tags={ "TMAChannelProduct", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TMAChannelProductDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/tma-channel-product/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<TMAChannelProductDTO>> getByGenericProductVarietyId(@NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                               @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                               @Valid @RequestParam(value = "sortBy", required = false) String  sortBy,
                                                                               @Valid @RequestParam(value = "direction", required = false) String direction,
                                                                               @Valid @RequestParam(value = "marketCode", required = true) String marketCode,
                                                                               @Valid @RequestParam(value = "channelCode", required = true) String channelCode,
                                                                               @Valid @RequestParam(value = "varietyId", required = false) Long varietyId,
                                                                               @Valid @RequestParam(value = "excludeVarietyIds", required = false) Set<Long> excludeVarietyIds,
                                                                               @Valid @RequestParam(value = "traderId", required = false) Long traderId){
        if(varietyId == null && traderId == null){
            throw new InvalidInputException("Either one of varietyId or traderId required ");
        }
        else{
            PageDTO<TMAChannelProductDTO> results = facade.getByGenericProductVarietyId(marketCode, channelCode, varietyId,excludeVarietyIds, traderId, page, size, sortBy, direction);
            return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
        }
    }

    @ApiOperation(value = "Get TMAChannelProduct By genericProductVarietyIds", nickname = "getByVarietyIdWithOutPage", notes = "", response = TMAChannelProductDTO.class, responseContainer = "Set", tags={ "TMAChannelProduct", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TMAChannelProductDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/tma-channel-product/gpv",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<TMAChannelProductDTO>> getByVarietyIdWithOutPage(@Valid @RequestParam(value = "marketId", required = true) Long marketId,
                                                                           @Valid @RequestParam(value = "channelId", required = true) Long channelId,
                                                                           @Valid @RequestParam(value = "genericProductVarietyIds", required = true) Set<Long> genericProductVarietyIds){

        return new ResponseEntity<>(facade.getByVarietyIdWithOutPage(marketId,channelId,genericProductVarietyIds), HttpStatus.OK);
    }

    @ApiOperation(value = "Get TMAChannelProduct By VarietyId in Page V2", nickname = "getByGenericProductVarietyIdV2", notes = "", response = TMAChannelProductDTO.class, responseContainer = "Set", tags={ "TMAChannelProduct", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TMAChannelProductDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v2/tma-channel-product/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<TMAChannelProductDTO>> getByGenericProductVarietyIdV2(@NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                               @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                               @Valid @RequestParam(value = "sortBy", required = false) String  sortBy,
                                                                               @Valid @RequestParam(value = "direction", required = false) String direction,
                                                                               @Valid @RequestParam(value = "marketCode", required = true) String marketCode,
                                                                               @Valid @RequestParam(value = "channelCode", required = true) String channelCode,
                                                                               @Valid @RequestParam(value = "varietyId", required = false) Long varietyId,
                                                                               @Valid @RequestParam(value = "excludeVarietyIds", required = false) Set<Long> excludeVarietyIds,
                                                                               @Valid @RequestParam(value = "traderId", required = false) Long traderId,
                                                                                 @Valid @RequestParam(value = "parentClassificationCode", required = false) String parentClassificationCode,
                                                                                 @Valid @RequestParam(value = "sourceTraderType",required = false) String sourceTraderType,
                                                                                 @Valid @RequestParam(value = "transactionType", required = false) String transactionType,
                                                                                 @Valid @RequestParam(value = "solutionId", required = false) Long solutionId,
                                                                                 @Valid @RequestParam(value = "categoryCodes", required = false) Set<String> categoryCodes,
                                                                                 @Valid @RequestParam(value = "subCategoryCodes", required = false) Set<String> subCategoryCodes,
                                                                                 @Valid @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                                 @Valid @RequestParam(value = "linkedSolutionResourceIds", required = false) Set<Long> linkedSolutionResourceIds){
        if(varietyId == null && traderId == null && solutionId== null){
            throw new InvalidInputException("Either one of varietyId or traderId or solutionId required ");
        }
        else{
            PageDTO<TMAChannelProductDTO> results = facade.getByGenericProductVarietyIdV2(marketCode, channelCode, varietyId,
                    excludeVarietyIds, traderId, page, size, sortBy, direction,parentClassificationCode,sourceTraderType,
                    transactionType,solutionId,categoryCodes,subCategoryCodes,searchTerm,linkedSolutionResourceIds);
            return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
        }

    }

    @ApiOperation(
            value = "Reindex TMA Channel Product",
            notes = "Triggers reindexing of all TMA Channel Products.",
            response = Void.class,
            tags = {"TMAChannelProduct"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content - Reindex completed successfully")
    })
    @RequestMapping(value = "/api/v2/tma-channel-product/re-index",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> reindex () {
        facade.reindex();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
