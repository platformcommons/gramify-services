package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.client.AppAPI;
import com.platformcommons.platform.service.search.dto.AppDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.facade.AppFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "App")
public class AppController implements AppAPI {
    @Autowired
    private AppFacade appFacade;


    @Override
    public ResponseEntity<PageDTO<AppDTO>> readApp(String keyword, Integer page, Integer size) {
        PageDTO<AppDTO> results=appFacade.readAppByName(keyword, page, size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "", tags = {"App"})
    @GetMapping("/api/v1/app/facet-page")
    public ResponseEntity<FacetPageDTO<AppDTO>> getFacet(@RequestParam Set<String> fields,
                                                         @RequestParam(required = false) String searchTerm,
                                                         @RequestParam Integer page,
                                                         @RequestParam Integer size,
                                                         @RequestParam(required = false) List<String> domainCodes,
                                                         @RequestParam(required = false) List<String> subDomainCodes,
                                                         @RequestParam(required = false) List<Long> useCaseIds,
                                                         @RequestParam(required = false) String sortBy,
                                                         @RequestParam(required = false) String direction) {
        FacetPageDTO<AppDTO> facetPageDTO = appFacade.getResultWithFacetAndFilter(searchTerm,fields, domainCodes, subDomainCodes, useCaseIds, page, size, sortBy, direction);
        return new ResponseEntity<>(facetPageDTO, facetPageDTO.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }

    @ApiOperation(value = "",tags = {"App"})
    @GetMapping("/api/v1/app")
    public ResponseEntity<Set<AppDTO>> getByIds(@RequestParam Set<Long> ids){
        return ResponseEntity.status(HttpStatus.OK).body(appFacade.getByIds(ids));
    }


    @ApiOperation(value = "",tags = {"App"})
    @PatchMapping("/api/v1/app/slug")
    public ResponseEntity<AppDTO> updateAppSlug(@RequestParam Long appId, String slug){
        return ResponseEntity.status(HttpStatus.OK).body(appFacade.updateAppSlug(appId,slug));
    }

}
