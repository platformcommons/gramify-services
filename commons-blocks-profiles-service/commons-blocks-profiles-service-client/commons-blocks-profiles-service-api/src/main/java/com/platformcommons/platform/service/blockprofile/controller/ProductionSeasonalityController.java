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


import com.platformcommons.platform.service.blockprofile.api.ProductionSeasonalityControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonalityDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ProductionSeasonalityFacadeExt;


@RestController
@Slf4j
public class ProductionSeasonalityController implements ProductionSeasonalityControllerApi {

    private final ProductionSeasonalityFacadeExt productionSeasonalityFacadeExt;

    /**
     * Constructs a ProductionSeasonalityController with the specified facade.
     *
     * @param productionSeasonalityFacadeExt The ProductionSeasonality facade extension to be used
     */
    public ProductionSeasonalityController(ProductionSeasonalityFacadeExt productionSeasonalityFacadeExt) {
        this.productionSeasonalityFacadeExt =productionSeasonalityFacadeExt;
    }

    /**
     * Creates a new ProductionSeasonality.
     *
     * @param productionSeasonalityDTO The ProductionSeasonality data to create
     * @return ResponseEntity containing the ID of the created ProductionSeasonality
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ProductionSeasonalityDTO productionSeasonalityDTO) {
        log.debug("Entry - create(ProductionSeasonalityDTO={})" , productionSeasonalityDTO);
        Long id = productionSeasonalityFacadeExt.save(productionSeasonalityDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createProductionSeasonalityUri(id)).body(id);
    }

    /**
     * Updates an existing ProductionSeasonality.
     *
     * @param productionSeasonalityDTO The ProductionSeasonality data to update
     * @return ResponseEntity containing the updated ProductionSeasonality data
     */
    @Override
    public ResponseEntity<ProductionSeasonalityDTO> update(@RequestBody ProductionSeasonalityDTO productionSeasonalityDTO) {
        log.debug("Entry - update(ProductionSeasonalityDTO={})", productionSeasonalityDTO);
        ProductionSeasonalityDTO updated = productionSeasonalityFacadeExt.update(productionSeasonalityDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of ProductionSeasonalitys.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of ProductionSeasonalitys
     */
    @Override
    public ResponseEntity<PageDTO<ProductionSeasonalityDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ProductionSeasonalityDTO> result = productionSeasonalityFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a ProductionSeasonality by their ID.
     *
     * @param id The ID of the ProductionSeasonality to retrieve
     * @return ResponseEntity containing the ProductionSeasonality data
     */
    @Override
    public ResponseEntity<ProductionSeasonalityDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ProductionSeasonalityDTO dto = productionSeasonalityFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a ProductionSeasonality by their ID with an optional reason.
     *
     * @param id     The ID of the ProductionSeasonality to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        productionSeasonalityFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ProductionSeasonalityDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ProductionSeasonalityDTO> result = productionSeasonalityFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createProductionSeasonalityUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
