package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.GenericProductDTO;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.facade.GenericProductFacade;
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

@RequestMapping("/api/v1/generic-product")
@Tag(name = "GenericProduct")
@RestController
public class GenericProductController {

    @Autowired
    GenericProductFacade facade;

    @ApiOperation(value = "Get All GenericProducts", nickname = "GetAllGenericProducts", notes = "", response = GenericProduct.class, tags={ "GenericProduct", })
    @GetMapping
    public ResponseEntity<List<GenericProductDTO>> findAllGenericProducts() {
        List<GenericProductDTO> result= facade.findAllGenericProducts();
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "save Generic Product", nickname = "saveGenericProduct", notes = "", response = GenericProduct.class, tags={ "GenericProduct", })
    @PostMapping
    public ResponseEntity<GenericProductDTO> saveGenericProduct(@RequestBody GenericProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(dto));
    }

    @ApiOperation(value = "get GenericProduct by searchTerm", nickname = "readGenericProduct", notes = "",
            response = GenericProductDTO.class, responseContainer = "Set", tags={ "GenericProduct", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericProductDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<GenericProductDTO>> readGenericProduct(@RequestParam(value = "marketCode", required = true) String marketCode,
                                                                       @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                       @RequestParam(value = "categoryCodes", required = false) Set<String> categoryCodes,
                                                                       @RequestParam(value = "subCategoryCodes", required = false) Set<String> subCategoryCodes,
                                                                       @RequestParam(value = "fields", required = true) Set<String> fields,
                                                                       @NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                       @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                       @RequestParam(required = false) String sortBy,
                                                                       @RequestParam(required = false) String direction){
        FacetPageDTO<GenericProductDTO> facetPageDTO = facade.readGenericProducts(marketCode,searchTerm,categoryCodes,subCategoryCodes,fields,page,size,sortBy,direction);
        return new ResponseEntity<>(facetPageDTO, facetPageDTO.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }

    @ApiOperation(
            value = "Reindex Generic Product",
            notes = "Triggers reindexing of all Generic Products.",
            response = Void.class,
            tags = {"GenericProduct"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content - Reindex completed successfully")
    })
    @RequestMapping(value = "/re-index",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> reindex () {
        facade.reindex();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
