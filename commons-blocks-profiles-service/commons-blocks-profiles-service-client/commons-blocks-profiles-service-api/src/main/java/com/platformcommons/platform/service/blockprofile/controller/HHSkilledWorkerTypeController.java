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


import com.platformcommons.platform.service.blockprofile.api.HHSkilledWorkerTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHSkilledWorkerTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHSkilledWorkerTypeFacadeExt;


@RestController
@Slf4j
public class HHSkilledWorkerTypeController implements HHSkilledWorkerTypeControllerApi {

    private final HHSkilledWorkerTypeFacadeExt hHSkilledWorkerTypeFacadeExt;

    /**
     * Constructs a HHSkilledWorkerTypeController with the specified facade.
     *
     * @param hHSkilledWorkerTypeFacadeExt The HHSkilledWorkerType facade extension to be used
     */
    public HHSkilledWorkerTypeController(HHSkilledWorkerTypeFacadeExt hHSkilledWorkerTypeFacadeExt) {
        this.hHSkilledWorkerTypeFacadeExt =hHSkilledWorkerTypeFacadeExt;
    }

    /**
     * Creates a new HHSkilledWorkerType.
     *
     * @param hHSkilledWorkerTypeDTO The HHSkilledWorkerType data to create
     * @return ResponseEntity containing the ID of the created HHSkilledWorkerType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHSkilledWorkerTypeDTO hHSkilledWorkerTypeDTO) {
        log.debug("Entry - create(HHSkilledWorkerTypeDTO={})" , hHSkilledWorkerTypeDTO);
        Long id = hHSkilledWorkerTypeFacadeExt.save(hHSkilledWorkerTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHSkilledWorkerTypeUri(id)).body(id);
    }

    /**
     * Updates an existing HHSkilledWorkerType.
     *
     * @param hHSkilledWorkerTypeDTO The HHSkilledWorkerType data to update
     * @return ResponseEntity containing the updated HHSkilledWorkerType data
     */
    @Override
    public ResponseEntity<HHSkilledWorkerTypeDTO> update(@RequestBody HHSkilledWorkerTypeDTO hHSkilledWorkerTypeDTO) {
        log.debug("Entry - update(HHSkilledWorkerTypeDTO={})", hHSkilledWorkerTypeDTO);
        HHSkilledWorkerTypeDTO updated = hHSkilledWorkerTypeFacadeExt.update(hHSkilledWorkerTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHSkilledWorkerTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHSkilledWorkerTypes
     */
    @Override
    public ResponseEntity<PageDTO<HHSkilledWorkerTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHSkilledWorkerTypeDTO> result = hHSkilledWorkerTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHSkilledWorkerType by their ID.
     *
     * @param id The ID of the HHSkilledWorkerType to retrieve
     * @return ResponseEntity containing the HHSkilledWorkerType data
     */
    @Override
    public ResponseEntity<HHSkilledWorkerTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHSkilledWorkerTypeDTO dto = hHSkilledWorkerTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHSkilledWorkerType by their ID with an optional reason.
     *
     * @param id     The ID of the HHSkilledWorkerType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHSkilledWorkerTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHSkilledWorkerTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHSkilledWorkerTypeDTO> result = hHSkilledWorkerTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHSkilledWorkerTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
