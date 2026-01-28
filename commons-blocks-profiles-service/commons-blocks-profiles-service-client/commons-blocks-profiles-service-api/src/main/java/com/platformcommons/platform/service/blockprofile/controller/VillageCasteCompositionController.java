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


import com.platformcommons.platform.service.blockprofile.api.VillageCasteCompositionControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageCasteCompositionDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageCasteCompositionFacadeExt;


@RestController
@Slf4j
public class VillageCasteCompositionController implements VillageCasteCompositionControllerApi {

    private final VillageCasteCompositionFacadeExt villageCasteCompositionFacadeExt;

    /**
     * Constructs a VillageCasteCompositionController with the specified facade.
     *
     * @param villageCasteCompositionFacadeExt The VillageCasteComposition facade extension to be used
     */
    public VillageCasteCompositionController(VillageCasteCompositionFacadeExt villageCasteCompositionFacadeExt) {
        this.villageCasteCompositionFacadeExt =villageCasteCompositionFacadeExt;
    }

    /**
     * Creates a new VillageCasteComposition.
     *
     * @param villageCasteCompositionDTO The VillageCasteComposition data to create
     * @return ResponseEntity containing the ID of the created VillageCasteComposition
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageCasteCompositionDTO villageCasteCompositionDTO) {
        log.debug("Entry - create(VillageCasteCompositionDTO={})" , villageCasteCompositionDTO);
        Long id = villageCasteCompositionFacadeExt.save(villageCasteCompositionDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageCasteCompositionUri(id)).body(id);
    }

    /**
     * Updates an existing VillageCasteComposition.
     *
     * @param villageCasteCompositionDTO The VillageCasteComposition data to update
     * @return ResponseEntity containing the updated VillageCasteComposition data
     */
    @Override
    public ResponseEntity<VillageCasteCompositionDTO> update(@RequestBody VillageCasteCompositionDTO villageCasteCompositionDTO) {
        log.debug("Entry - update(VillageCasteCompositionDTO={})", villageCasteCompositionDTO);
        VillageCasteCompositionDTO updated = villageCasteCompositionFacadeExt.update(villageCasteCompositionDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageCasteCompositions.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageCasteCompositions
     */
    @Override
    public ResponseEntity<PageDTO<VillageCasteCompositionDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageCasteCompositionDTO> result = villageCasteCompositionFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageCasteComposition by their ID.
     *
     * @param id The ID of the VillageCasteComposition to retrieve
     * @return ResponseEntity containing the VillageCasteComposition data
     */
    @Override
    public ResponseEntity<VillageCasteCompositionDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCasteCompositionDTO dto = villageCasteCompositionFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageCasteComposition by their ID with an optional reason.
     *
     * @param id     The ID of the VillageCasteComposition to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageCasteCompositionFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageCasteCompositionDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageCasteCompositionDTO> result = villageCasteCompositionFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageCasteCompositionUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
