package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.UseCase;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.facade.UseCaseFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Tag(name="UseCase")
public class UseCaseController {

    @Autowired
    private UseCaseFacade useCaseFacade;

    @ApiOperation(value = "",tags = {"UseCase"})
    @GetMapping("/api/v1/usecase/facet-page")
    public ResponseEntity<FacetPageDTO<UseCase>> getFacet(@RequestParam Set<String> fields, @RequestParam Integer page, @RequestParam Integer size){
        FacetPageDTO<UseCase> facetPageDTO =useCaseFacade.getResultWithFacet(fields,page,size);
        return new ResponseEntity<>( facetPageDTO,  facetPageDTO.isHasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK);
    }

    @ApiOperation(value = "",tags = {"UseCase"})
    @GetMapping("/api/v1/usecase/facet-page/filter")
    public ResponseEntity<FacetPageDTO<UseCase>> getFacetWithFilter(@RequestParam Set<String> fields,
                                                      @RequestParam Integer page,
                                                      @RequestParam Integer size,
                                                      @RequestParam(required = false) List<String> domainCodes,
                                                      @RequestParam(required = false) List<String> subDomainCodes,
                                                      @RequestParam(required = false) List<Long> useCaseIds,
                                                                    @RequestParam(required = false) String sortBy,
                                                                    @RequestParam(required = false) String direction){
        FacetPageDTO<UseCase> facetPageDTO =useCaseFacade.getResultWithFacetAndFilter(fields,domainCodes,subDomainCodes,useCaseIds,page,size,sortBy,direction);
        return new ResponseEntity<>( facetPageDTO,  facetPageDTO.isHasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK);
    }
}
