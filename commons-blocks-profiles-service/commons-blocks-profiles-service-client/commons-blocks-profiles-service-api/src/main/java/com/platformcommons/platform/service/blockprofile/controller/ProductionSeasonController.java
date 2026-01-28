package com.platformcommons.platform.service.blockprofile.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.*;


import com.platformcommons.platform.service.blockprofile.api.ProductionSeasonControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ProductionSeasonFacadeExt;


@RestController
@Slf4j
public class ProductionSeasonController implements ProductionSeasonControllerApi {

    private final ProductionSeasonFacadeExt productionSeasonFacadeExt;

    /**
     * Constructs a ProductionSeasonController with the specified facade.
     *
     * @param productionSeasonFacadeExt The ProductionSeason facade extension to be used
     */
    public ProductionSeasonController(ProductionSeasonFacadeExt productionSeasonFacadeExt) {
        this.productionSeasonFacadeExt =productionSeasonFacadeExt;
    }

    /**
     * Creates a new ProductionSeason.
     *
     * @param productionSeasonDTO The ProductionSeason data to create
     * @return ResponseEntity containing the ID of the created ProductionSeason
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ProductionSeasonDTO productionSeasonDTO) {
        log.debug("Entry - create(ProductionSeasonDTO={})" , productionSeasonDTO);
        Long id = productionSeasonFacadeExt.save(productionSeasonDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createProductionSeasonUri(id)).body(id);
    }

    /**
     * Updates an existing ProductionSeason.
     *
     * @param productionSeasonDTO The ProductionSeason data to update
     * @return ResponseEntity containing the updated ProductionSeason data
     */
    @Override
    public ResponseEntity<ProductionSeasonDTO> update(@RequestBody ProductionSeasonDTO productionSeasonDTO) {
        log.debug("Entry - update(ProductionSeasonDTO={})", productionSeasonDTO);
        ProductionSeasonDTO updated = productionSeasonFacadeExt.update(productionSeasonDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of ProductionSeasons.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of ProductionSeasons
     */
    @Override
    public ResponseEntity<PageDTO<ProductionSeasonDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ProductionSeasonDTO> result = productionSeasonFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a ProductionSeason by their ID.
     *
     * @param id The ID of the ProductionSeason to retrieve
     * @return ResponseEntity containing the ProductionSeason data
     */
    @Override
    public ResponseEntity<ProductionSeasonDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ProductionSeasonDTO dto = productionSeasonFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a ProductionSeason by their ID with an optional reason.
     *
     * @param id     The ID of the ProductionSeason to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        productionSeasonFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ProductionSeasonDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ProductionSeasonDTO> result = productionSeasonFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createProductionSeasonUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
