package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.CommonsLocationDTO;
import com.platformcommons.platform.service.search.dto.CompanyCodesRequestDTO;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataDTO;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataV2DTO;
import com.platformcommons.platform.service.search.facade.CompanyMasterDataFacade;
import com.platformcommons.platform.service.search.facade.CompanyMasterDataV2Facade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "CompanyMasterDataV2")
public class CompanyMasterDataV2Controller {

    @Autowired
    CompanyMasterDataV2Facade facade;


    @ApiOperation(value = "Get By SearchText",  response = CompanyMasterDataV2DTO.class, responseContainer = "Set", tags={ "CompanyMasterDataV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/companyV2/search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<CompanyMasterDataV2DTO>> getBySearchText(@RequestParam(value = "keyword") String searchText,
                                                                    @RequestParam(value = "size") Integer size,
                                                                    @RequestParam(value = "page") Integer page) {
        PageDTO<CompanyMasterDataV2DTO> result = facade.getBySearchText(searchText,page,size);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get By SearchText",
            notes = "Search flow:\n"
                    + "1. First searches company master data stored in the system.\n"
                    + "2. If no records are found, the system falls back to the external logo.dev search API.\n"
                    + "3. When data is returned from logo.dev, the `code` field will be null.",
            response = CompanyMasterDataV2DTO.class, responseContainer = "Set", tags={ "CompanyMasterDataV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v2/companyV2/search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<CompanyMasterDataV2DTO>> getBySearchTextV2(@RequestParam(value = "keyword") String searchText,
                                                                    @RequestParam(value = "size") Integer size,
                                                                    @RequestParam(value = "page") Integer page) {
        PageDTO<CompanyMasterDataV2DTO> result = facade.getBySearchTextV2(searchText,page,size);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }

    @Deprecated
    @ApiOperation(value = "Get By Company Codes In Bulk",  response = CompanyMasterDataV2DTO.class, responseContainer = "Set", tags={ "CompanyMasterDataV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/companyV2/code/bulk",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Map<String,CompanyMasterDataV2DTO>> getByCompanyCodesInBulk(@RequestParam(value = "companyCodes") Set<String> companyCodes,
                                                                               @RequestParam(value = "sortBy",required = false) String sortBy,
                                                                               @RequestParam(value = "direction",required = false) String direction) {
        Map<String,CompanyMasterDataV2DTO> result = facade.getByCompanyCodesInBulk(companyCodes,sortBy,direction);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Deprecated
    @ApiOperation(value = "Get By Company Codes In Bulk",  response = CompanyMasterDataV2DTO.class, responseContainer = "Set", tags={ "CompanyMasterDataV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/get/companyV2/code/bulk",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Map<String,CompanyMasterDataV2DTO>> getByCompanyCodesInBulkV2(@NotNull @Valid @RequestBody CompanyCodesRequestDTO companyCodesRequestDTO) {
        Map<String,CompanyMasterDataV2DTO> result = facade.getByCompanyCodesInBulk(companyCodesRequestDTO.getCompanyCodes(),
                companyCodesRequestDTO.getSortBy(),companyCodesRequestDTO.getDirection());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get By Company Codes In Bulk V3",  response = CompanyMasterDataV2DTO.class, responseContainer = "Set", tags={ "CompanyMasterDataV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/consent/get/companyV2/code/bulk",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Map<String,CompanyMasterDataV2DTO>> getByCompanyCodesInBulkV3(@NotNull @Valid @RequestBody CompanyCodesRequestDTO companyCodesRequestDTO) {
        Map<String,CompanyMasterDataV2DTO> result = facade.getByCompanyCodesInBulk(companyCodesRequestDTO.getCompanyCodes(),
                companyCodesRequestDTO.getSortBy(),companyCodesRequestDTO.getDirection());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @ApiOperation(value = "Save",  notes = "", response = CompanyMasterDataV2DTO.class, tags={ "CompanyMasterDataV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = CompanyMasterDataV2DTO.class) })
    @RequestMapping(value = "/api/v1/companyV2",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<CompanyMasterDataV2DTO> save(@Valid @RequestBody CompanyMasterDataV2DTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }

    @ApiOperation(value = "Delete By Code",  notes = "", response = Void.class, tags={ "CompanyMasterDataV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/api/v1/companyV2",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteByCode(@NotNull @RequestParam(value = "code") String code) {
        facade.deleteByCode(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @ApiOperation(value = "Put Update", notes = "", response = Void.class, tags={ "CompanyMasterDataV2" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/api/v1/companyV2",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> putUpdate(@Valid @RequestBody CompanyMasterDataV2DTO body) {
        facade.putUpdate(body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get By Code", notes = "", response = CompanyMasterDataV2DTO.class, tags={ "CompanyMasterDataV2" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class) })
    @RequestMapping(value = "/api/v1/companyV2/id/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<CompanyMasterDataV2DTO> getById(@RequestParam(name = "code") String code) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByCode(code));
    }

    @ApiOperation(value = "Get All By Pagination", notes = "", response = CompanyMasterDataV2DTO.class,responseContainer = "Set",
            tags={ "CompanyMasterDataV2" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class,responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/companyV2/page",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<CompanyMasterDataV2DTO>> getAllByPagination(@NotNull @RequestParam(name = "page") Integer page,
                                                              @NotNull @RequestParam(name = "size") Integer size,
                                                              @RequestParam(name = "sortBy",required = false) String sortBy,
                                                              @RequestParam(name = "direction",required = false) String direction) {
        PageDTO<CompanyMasterDataV2DTO> result = facade.getAllByPagination(page,size,sortBy,direction);
        return ResponseEntity.status(result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get By Company name", notes = "", response = CompanyMasterDataV2DTO.class, tags={ "CompanyMasterDataV2" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyMasterDataV2DTO.class) })
    @RequestMapping(value = "/api/v1/companyV2/company-name/{companyName}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<CompanyMasterDataV2DTO>> getByCompanyName(@PathVariable(name = "companyName") String companyName) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByCompanyName(companyName));
    }


}
