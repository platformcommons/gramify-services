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


import com.platformcommons.platform.service.blockprofile.api.HHArtisanTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHArtisanTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHArtisanTypeFacadeExt;


@RestController
@Slf4j
public class HHArtisanTypeController implements HHArtisanTypeControllerApi {

    private final HHArtisanTypeFacadeExt hHArtisanTypeFacadeExt;

    /**
     * Constructs a HHArtisanTypeController with the specified facade.
     *
     * @param hHArtisanTypeFacadeExt The HHArtisanType facade extension to be used
     */
    public HHArtisanTypeController(HHArtisanTypeFacadeExt hHArtisanTypeFacadeExt) {
        this.hHArtisanTypeFacadeExt =hHArtisanTypeFacadeExt;
    }

    /**
     * Creates a new HHArtisanType.
     *
     * @param hHArtisanTypeDTO The HHArtisanType data to create
     * @return ResponseEntity containing the ID of the created HHArtisanType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHArtisanTypeDTO hHArtisanTypeDTO) {
        log.debug("Entry - create(HHArtisanTypeDTO={})" , hHArtisanTypeDTO);
        Long id = hHArtisanTypeFacadeExt.save(hHArtisanTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHArtisanTypeUri(id)).body(id);
    }

    /**
     * Updates an existing HHArtisanType.
     *
     * @param hHArtisanTypeDTO The HHArtisanType data to update
     * @return ResponseEntity containing the updated HHArtisanType data
     */
    @Override
    public ResponseEntity<HHArtisanTypeDTO> update(@RequestBody HHArtisanTypeDTO hHArtisanTypeDTO) {
        log.debug("Entry - update(HHArtisanTypeDTO={})", hHArtisanTypeDTO);
        HHArtisanTypeDTO updated = hHArtisanTypeFacadeExt.update(hHArtisanTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHArtisanTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHArtisanTypes
     */
    @Override
    public ResponseEntity<PageDTO<HHArtisanTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHArtisanTypeDTO> result = hHArtisanTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHArtisanType by their ID.
     *
     * @param id The ID of the HHArtisanType to retrieve
     * @return ResponseEntity containing the HHArtisanType data
     */
    @Override
    public ResponseEntity<HHArtisanTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHArtisanTypeDTO dto = hHArtisanTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHArtisanType by their ID with an optional reason.
     *
     * @param id     The ID of the HHArtisanType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHArtisanTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHArtisanTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHArtisanTypeDTO> result = hHArtisanTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHArtisanTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
