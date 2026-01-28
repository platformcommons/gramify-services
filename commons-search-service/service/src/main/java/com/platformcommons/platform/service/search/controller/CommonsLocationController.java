package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.domain.CommonsLocation;
import com.platformcommons.platform.service.search.dto.*;
import com.platformcommons.platform.service.search.facade.CommonsLocationFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "CommonsLocation")
public class CommonsLocationController {

    @Autowired
    private CommonsLocationFacade facade;

    @ApiOperation(value = "Save",  response = CommonsLocationDTO.class, tags={ "CommonsLocation" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = CommonsLocationDTO.class) })
    @RequestMapping(value = "/api/v1/commons-location",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<CommonsLocationDTO> save(@Valid @RequestBody CommonsLocationDTO commonsLocationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(commonsLocationDTO));
    }

    @ApiOperation(value = "Get By Search Text In Pagination", notes = "", response = CommonsLocationDTO.class, responseContainer = "Set",
            tags={ "CommonsLocation", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonsLocationDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/commons-location/search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<CommonsLocationDTO>> getLocationByAddressLabel(@RequestParam(value = "keyword",required = false) String searchText,
                                                                          @RequestParam(value = "size") Integer size,
                                                                          @RequestParam(value = "page") Integer page,
                                                                          @RequestParam(value = "cityData",required = false) Boolean cityData,
                                                                          @RequestParam(value = "stateData",required = false) Boolean stateData,
                                                                          @RequestParam(value = "countryData",required = false) Boolean countryData,
                                                                          @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                          @RequestParam(value = "direction",required = false) String direction) {
        PageDTO<CommonsLocationDTO> result = facade.getLocationByAddressLabel(searchText,page,size,cityData,stateData,countryData,
                sortBy,direction);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get Commons Locations By Address Label", notes = "", response = CommonsLocationDTO.class, tags={ "CommonsLocation" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonsLocationDTO.class) })
    @RequestMapping(value = "api/v1/commons-location/address-label/{addressLabel}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<CommonsLocationDTO>> getByAddressLabel(@PathVariable(name = "addressLabel") String addressLabel) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByAddressLabel(addressLabel));
    }


    @ApiOperation(value = "Get By Location Codes In Bulk",  response = CommonsLocationDTO.class, responseContainer = "Set", tags={ "CommonsLocation", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonsLocationDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/consent/get/commons-location/code/bulk",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Map<String,CommonsLocationDTO>> getByLocationCodesInBulkV2(@NotNull @Valid @RequestBody CommonsLocationRequestDTO commonsLocationRequestDTO) {
        Map<String,CommonsLocationDTO> result = facade.getByLocationCodesInBulk(commonsLocationRequestDTO.getDataCodes(),
                commonsLocationRequestDTO.getSortBy(),commonsLocationRequestDTO.getDirection(),commonsLocationRequestDTO.getDataCodesField());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get By Search From Google", notes = "",
            tags={ "CommonsLocation", })
    @RequestMapping(value = "api/v1/commons-location/google/search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Object> getLocationByAddressLabel(@RequestParam(value = "keyword",required = true) String searchText) {
        Object result = facade.searchLocationByGoogle(searchText);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



    @ApiOperation(value = "Get By Search Text In Pagination", notes = "", response = CommonsLocationDTO.class, responseContainer = "Set",
            tags={ "CommonsLocation", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonsLocationDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v2/commons-location/search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<CommonsLocationDTO>> getLocationByAddressLabel(@RequestParam(value = "keyword",required = false) String searchText,
                                                                          @RequestParam(value = "countryCode",required = false) String countryCode,
                                                                          @RequestParam(value = "size") Integer size,
                                                                          @RequestParam(value = "page") Integer page,
                                                                          @RequestParam(value = "cityData", required = false) Boolean cityData,
                                                                          @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                          @RequestParam(value = "direction",required = false) String direction) {
        PageDTO<CommonsLocationDTO> result = facade.getLocationBySearchTerm(searchText,countryCode ,page,size,cityData,sortBy,direction);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }


    @ApiOperation(value = "Get By Search Text In Pagination", notes = "", response = CommonsLocationDTO.class, responseContainer = "Set",
            tags={ "CommonsLocation", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonsLocationDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/commons-location/re-index",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<PageDTO<CommonsLocationDTO>> reindex() {
        facade.reindex();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Get Location By Code", notes = "Get a location by its code in the format REGION_TYPE.CODE (e.g., REGION_CITY.ABC)",
            response = CommonsLocationDTO.class, tags={ "CommonsLocation" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonsLocationDTO.class),
            @ApiResponse(code = 404, message = "Location not found") })
    @RequestMapping(value = "api/v1/commons-location/code",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<CommonsLocationDTO> getLocationByCode(@RequestParam(name = "locationCode", required = true) @NotBlank String locationCode) {
        CommonsLocationDTO result = facade.getLocationByCode(locationCode);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
