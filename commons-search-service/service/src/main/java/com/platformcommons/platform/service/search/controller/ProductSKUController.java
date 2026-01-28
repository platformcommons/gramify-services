package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.domain.ProductSKU;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.ProductSKUDTO;
import com.platformcommons.platform.service.search.facade.ProductSKUFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "ProductSKU")
public class ProductSKUController {

    @Autowired
    private ProductSKUFacade productSKUFacade;

    @ApiOperation(value = "", nickname = "saveProductSKU", notes = "", response = ProductSKU.class, tags={ "ProductSKU", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = ProductSKU.class) })
    @RequestMapping(value = "/api/v1/product-sku",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<ProductSKUDTO> saveProductSKU(@Valid @RequestBody ProductSKUDTO productSKUDTO){
        return new ResponseEntity<>(productSKUFacade.saveProductSKU(productSKUDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "", nickname = "searchProductSKU", notes = "", response = ProductSKU.class, responseContainer = "Set", tags={ "ProductSKU", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProductSKU.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/product-sku/search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<ProductSKUDTO>> searchProductSKU(@Valid @RequestParam(value = "keyword", required = false) String keyword,
                                                            @RequestParam(value = "page") Integer page,
                                                            @RequestParam(value = "size") Integer size){
        PageDTO<ProductSKUDTO> list = productSKUFacade.searchProductSKU(keyword,page,size);
        return ResponseEntity.status(list.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(list);
    }

    @ApiOperation(value = "", nickname = "getFacet", notes = "", response = ProductSKU.class, responseContainer = "Set", tags={ "ProductSKU", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProductSKU.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/product-sku/facet-page",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<FacetPageDTO<ProductSKUDTO>> getFacet(@RequestParam Set<String> fields,
                                                         @RequestParam(required = false) String searchTerm,
                                                         @RequestParam Integer page,
                                                         @RequestParam Integer size,
                                                         @RequestParam(required = false) String sortBy,
                                                         @RequestParam(required = false) String direction,
                                                         @RequestParam(required = false) List<Long> tmaChannelProductIds
                                                         ) {
        FacetPageDTO<ProductSKUDTO> facetPageDTO = productSKUFacade.getResultWithFacetAndFilter(searchTerm,fields, tmaChannelProductIds, page, size, sortBy, direction);
        return new ResponseEntity<>(facetPageDTO, facetPageDTO.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }

}
