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


import com.platformcommons.platform.service.blockprofile.api.NonAgriculturalMarketActiviesControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.NonAgriculturalMarketActiviesDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.NonAgriculturalMarketActiviesFacadeExt;


@RestController
@Slf4j
public class NonAgriculturalMarketActiviesController implements NonAgriculturalMarketActiviesControllerApi {

    private final NonAgriculturalMarketActiviesFacadeExt nonAgriculturalMarketActiviesFacadeExt;

    /**
     * Constructs a NonAgriculturalMarketActiviesController with the specified facade.
     *
     * @param nonAgriculturalMarketActiviesFacadeExt The NonAgriculturalMarketActivies facade extension to be used
     */
    public NonAgriculturalMarketActiviesController(NonAgriculturalMarketActiviesFacadeExt nonAgriculturalMarketActiviesFacadeExt) {
        this.nonAgriculturalMarketActiviesFacadeExt =nonAgriculturalMarketActiviesFacadeExt;
    }

    /**
     * Creates a new NonAgriculturalMarketActivies.
     *
     * @param nonAgriculturalMarketActiviesDTO The NonAgriculturalMarketActivies data to create
     * @return ResponseEntity containing the ID of the created NonAgriculturalMarketActivies
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody NonAgriculturalMarketActiviesDTO nonAgriculturalMarketActiviesDTO) {
        log.debug("Entry - create(NonAgriculturalMarketActiviesDTO={})" , nonAgriculturalMarketActiviesDTO);
        Long id = nonAgriculturalMarketActiviesFacadeExt.save(nonAgriculturalMarketActiviesDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createNonAgriculturalMarketActiviesUri(id)).body(id);
    }

    /**
     * Updates an existing NonAgriculturalMarketActivies.
     *
     * @param nonAgriculturalMarketActiviesDTO The NonAgriculturalMarketActivies data to update
     * @return ResponseEntity containing the updated NonAgriculturalMarketActivies data
     */
    @Override
    public ResponseEntity<NonAgriculturalMarketActiviesDTO> update(@RequestBody NonAgriculturalMarketActiviesDTO nonAgriculturalMarketActiviesDTO) {
        log.debug("Entry - update(NonAgriculturalMarketActiviesDTO={})", nonAgriculturalMarketActiviesDTO);
        NonAgriculturalMarketActiviesDTO updated = nonAgriculturalMarketActiviesFacadeExt.update(nonAgriculturalMarketActiviesDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of NonAgriculturalMarketActiviess.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of NonAgriculturalMarketActiviess
     */
    @Override
    public ResponseEntity<PageDTO<NonAgriculturalMarketActiviesDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<NonAgriculturalMarketActiviesDTO> result = nonAgriculturalMarketActiviesFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a NonAgriculturalMarketActivies by their ID.
     *
     * @param id The ID of the NonAgriculturalMarketActivies to retrieve
     * @return ResponseEntity containing the NonAgriculturalMarketActivies data
     */
    @Override
    public ResponseEntity<NonAgriculturalMarketActiviesDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        NonAgriculturalMarketActiviesDTO dto = nonAgriculturalMarketActiviesFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a NonAgriculturalMarketActivies by their ID with an optional reason.
     *
     * @param id     The ID of the NonAgriculturalMarketActivies to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        nonAgriculturalMarketActiviesFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<NonAgriculturalMarketActiviesDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<NonAgriculturalMarketActiviesDTO> result = nonAgriculturalMarketActiviesFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createNonAgriculturalMarketActiviesUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
