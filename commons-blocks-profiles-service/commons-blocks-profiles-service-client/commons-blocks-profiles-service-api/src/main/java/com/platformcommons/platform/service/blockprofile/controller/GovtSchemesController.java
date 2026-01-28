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


import com.platformcommons.platform.service.blockprofile.api.GovtSchemesControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.GovtSchemesDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.GovtSchemesFacadeExt;


@RestController
@Slf4j
public class GovtSchemesController implements GovtSchemesControllerApi {

    private final GovtSchemesFacadeExt govtSchemesFacadeExt;

    /**
     * Constructs a GovtSchemesController with the specified facade.
     *
     * @param govtSchemesFacadeExt The GovtSchemes facade extension to be used
     */
    public GovtSchemesController(GovtSchemesFacadeExt govtSchemesFacadeExt) {
        this.govtSchemesFacadeExt =govtSchemesFacadeExt;
    }

    /**
     * Creates a new GovtSchemes.
     *
     * @param govtSchemesDTO The GovtSchemes data to create
     * @return ResponseEntity containing the ID of the created GovtSchemes
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody GovtSchemesDTO govtSchemesDTO) {
        log.debug("Entry - create(GovtSchemesDTO={})" , govtSchemesDTO);
        Long id = govtSchemesFacadeExt.save(govtSchemesDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createGovtSchemesUri(id)).body(id);
    }

    /**
     * Updates an existing GovtSchemes.
     *
     * @param govtSchemesDTO The GovtSchemes data to update
     * @return ResponseEntity containing the updated GovtSchemes data
     */
    @Override
    public ResponseEntity<GovtSchemesDTO> update(@RequestBody GovtSchemesDTO govtSchemesDTO) {
        log.debug("Entry - update(GovtSchemesDTO={})", govtSchemesDTO);
        GovtSchemesDTO updated = govtSchemesFacadeExt.update(govtSchemesDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of GovtSchemess.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of GovtSchemess
     */
    @Override
    public ResponseEntity<PageDTO<GovtSchemesDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<GovtSchemesDTO> result = govtSchemesFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a GovtSchemes by their ID.
     *
     * @param id The ID of the GovtSchemes to retrieve
     * @return ResponseEntity containing the GovtSchemes data
     */
    @Override
    public ResponseEntity<GovtSchemesDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        GovtSchemesDTO dto = govtSchemesFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a GovtSchemes by their ID with an optional reason.
     *
     * @param id     The ID of the GovtSchemes to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        govtSchemesFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<GovtSchemesDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<GovtSchemesDTO> result = govtSchemesFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createGovtSchemesUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
