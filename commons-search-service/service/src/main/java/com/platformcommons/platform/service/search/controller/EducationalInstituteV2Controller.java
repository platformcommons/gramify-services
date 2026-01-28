package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataV2DTO;
import com.platformcommons.platform.service.search.dto.EducationalInstituteV2DTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.facade.EducationalInstituteV2Facade;
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
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "EducationalInstituteV2")
public class EducationalInstituteV2Controller {

    @Autowired
    private EducationalInstituteV2Facade facade;

    @ApiOperation(value = "Save",  notes = "", response = EducationalInstituteV2DTO.class, tags={ "EducationalInstituteV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = EducationalInstituteV2DTO.class) })
    @RequestMapping(value = "/api/v1/educationV2",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<EducationalInstituteV2DTO> save(@NotNull @Valid @RequestBody EducationalInstituteV2DTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }

    @ApiOperation(value = "Delete By Code",  notes = "", response = Void.class, tags={ "EducationalInstituteV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/api/v1/educationV2",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteByCode(@NotNull @RequestParam(value = "code") String code) {
        facade.deleteByCode(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get By SearchText",  response = EducationalInstituteV2DTO.class, responseContainer = "Set", tags={ "EducationalInstituteV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = EducationalInstituteV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/educationV2/search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<EducationalInstituteV2DTO>> getBySearchText(@RequestParam(value = "keyword", required = false) String searchText,
                                                                            @RequestParam(value = "instituteTypeSet") Set<String> instituteTypeSet,
                                                                            @RequestParam(value = "size") Integer size,
                                                                            @RequestParam(value = "page") Integer page) {

        FacetPageDTO<EducationalInstituteV2DTO> result = facade.getBySearchText(searchText,instituteTypeSet,page,size);
        return ResponseEntity.status(result.isHasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get By Education Institute Codes In Bulk",  response = EducationalInstituteV2DTO.class,
            responseContainer = "Set", tags={ "EducationalInstituteV2", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = EducationalInstituteV2DTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/educationV2/code/bulk",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Map<String,EducationalInstituteV2DTO>> getByEducationInstituteCodesInBulk(@RequestParam(value = "educationInstituteCodes") Set<String> educationInstituteCodes) {
        Map<String,EducationalInstituteV2DTO> result = facade.getByEducationInstituteCodesInBulk(educationInstituteCodes);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
