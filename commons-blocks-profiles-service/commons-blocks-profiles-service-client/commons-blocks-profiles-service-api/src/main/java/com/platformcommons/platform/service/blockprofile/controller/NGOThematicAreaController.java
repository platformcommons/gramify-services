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


import com.platformcommons.platform.service.blockprofile.api.NGOThematicAreaControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.NGOThematicAreaDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.NGOThematicAreaFacadeExt;


@RestController
@Slf4j
public class NGOThematicAreaController implements NGOThematicAreaControllerApi {

    private final NGOThematicAreaFacadeExt nGOThematicAreaFacadeExt;

    /**
     * Constructs a NGOThematicAreaController with the specified facade.
     *
     * @param nGOThematicAreaFacadeExt The NGOThematicArea facade extension to be used
     */
    public NGOThematicAreaController(NGOThematicAreaFacadeExt nGOThematicAreaFacadeExt) {
        this.nGOThematicAreaFacadeExt =nGOThematicAreaFacadeExt;
    }

    /**
     * Creates a new NGOThematicArea.
     *
     * @param nGOThematicAreaDTO The NGOThematicArea data to create
     * @return ResponseEntity containing the ID of the created NGOThematicArea
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody NGOThematicAreaDTO nGOThematicAreaDTO) {
        log.debug("Entry - create(NGOThematicAreaDTO={})" , nGOThematicAreaDTO);
        Long id = nGOThematicAreaFacadeExt.save(nGOThematicAreaDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createNGOThematicAreaUri(id)).body(id);
    }

    /**
     * Updates an existing NGOThematicArea.
     *
     * @param nGOThematicAreaDTO The NGOThematicArea data to update
     * @return ResponseEntity containing the updated NGOThematicArea data
     */
    @Override
    public ResponseEntity<NGOThematicAreaDTO> update(@RequestBody NGOThematicAreaDTO nGOThematicAreaDTO) {
        log.debug("Entry - update(NGOThematicAreaDTO={})", nGOThematicAreaDTO);
        NGOThematicAreaDTO updated = nGOThematicAreaFacadeExt.update(nGOThematicAreaDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of NGOThematicAreas.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of NGOThematicAreas
     */
    @Override
    public ResponseEntity<PageDTO<NGOThematicAreaDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<NGOThematicAreaDTO> result = nGOThematicAreaFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a NGOThematicArea by their ID.
     *
     * @param id The ID of the NGOThematicArea to retrieve
     * @return ResponseEntity containing the NGOThematicArea data
     */
    @Override
    public ResponseEntity<NGOThematicAreaDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        NGOThematicAreaDTO dto = nGOThematicAreaFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a NGOThematicArea by their ID with an optional reason.
     *
     * @param id     The ID of the NGOThematicArea to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        nGOThematicAreaFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<NGOThematicAreaDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<NGOThematicAreaDTO> result = nGOThematicAreaFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createNGOThematicAreaUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
