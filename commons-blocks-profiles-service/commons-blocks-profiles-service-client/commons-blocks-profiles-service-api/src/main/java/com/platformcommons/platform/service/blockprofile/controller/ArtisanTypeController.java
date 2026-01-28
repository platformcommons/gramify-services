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


import com.platformcommons.platform.service.blockprofile.api.ArtisanTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ArtisanTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ArtisanTypeFacadeExt;


@RestController
@Slf4j
public class ArtisanTypeController implements ArtisanTypeControllerApi {

    private final ArtisanTypeFacadeExt artisanTypeFacadeExt;

    /**
     * Constructs a ArtisanTypeController with the specified facade.
     *
     * @param artisanTypeFacadeExt The ArtisanType facade extension to be used
     */
    public ArtisanTypeController(ArtisanTypeFacadeExt artisanTypeFacadeExt) {
        this.artisanTypeFacadeExt =artisanTypeFacadeExt;
    }

    /**
     * Creates a new ArtisanType.
     *
     * @param artisanTypeDTO The ArtisanType data to create
     * @return ResponseEntity containing the ID of the created ArtisanType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ArtisanTypeDTO artisanTypeDTO) {
        log.debug("Entry - create(ArtisanTypeDTO={})" , artisanTypeDTO);
        Long id = artisanTypeFacadeExt.save(artisanTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createArtisanTypeUri(id)).body(id);
    }

    /**
     * Updates an existing ArtisanType.
     *
     * @param artisanTypeDTO The ArtisanType data to update
     * @return ResponseEntity containing the updated ArtisanType data
     */
    @Override
    public ResponseEntity<ArtisanTypeDTO> update(@RequestBody ArtisanTypeDTO artisanTypeDTO) {
        log.debug("Entry - update(ArtisanTypeDTO={})", artisanTypeDTO);
        ArtisanTypeDTO updated = artisanTypeFacadeExt.update(artisanTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of ArtisanTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of ArtisanTypes
     */
    @Override
    public ResponseEntity<PageDTO<ArtisanTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ArtisanTypeDTO> result = artisanTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a ArtisanType by their ID.
     *
     * @param id The ID of the ArtisanType to retrieve
     * @return ResponseEntity containing the ArtisanType data
     */
    @Override
    public ResponseEntity<ArtisanTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ArtisanTypeDTO dto = artisanTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a ArtisanType by their ID with an optional reason.
     *
     * @param id     The ID of the ArtisanType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        artisanTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ArtisanTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ArtisanTypeDTO> result = artisanTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createArtisanTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
