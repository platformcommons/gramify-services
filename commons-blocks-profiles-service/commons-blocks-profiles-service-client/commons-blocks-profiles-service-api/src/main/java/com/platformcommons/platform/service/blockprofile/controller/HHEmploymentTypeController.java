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


import com.platformcommons.platform.service.blockprofile.api.HHEmploymentTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHEmploymentTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHEmploymentTypeFacadeExt;


@RestController
@Slf4j
public class HHEmploymentTypeController implements HHEmploymentTypeControllerApi {

    private final HHEmploymentTypeFacadeExt hHEmploymentTypeFacadeExt;

    /**
     * Constructs a HHEmploymentTypeController with the specified facade.
     *
     * @param hHEmploymentTypeFacadeExt The HHEmploymentType facade extension to be used
     */
    public HHEmploymentTypeController(HHEmploymentTypeFacadeExt hHEmploymentTypeFacadeExt) {
        this.hHEmploymentTypeFacadeExt =hHEmploymentTypeFacadeExt;
    }

    /**
     * Creates a new HHEmploymentType.
     *
     * @param hHEmploymentTypeDTO The HHEmploymentType data to create
     * @return ResponseEntity containing the ID of the created HHEmploymentType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHEmploymentTypeDTO hHEmploymentTypeDTO) {
        log.debug("Entry - create(HHEmploymentTypeDTO={})" , hHEmploymentTypeDTO);
        Long id = hHEmploymentTypeFacadeExt.save(hHEmploymentTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHEmploymentTypeUri(id)).body(id);
    }

    /**
     * Updates an existing HHEmploymentType.
     *
     * @param hHEmploymentTypeDTO The HHEmploymentType data to update
     * @return ResponseEntity containing the updated HHEmploymentType data
     */
    @Override
    public ResponseEntity<HHEmploymentTypeDTO> update(@RequestBody HHEmploymentTypeDTO hHEmploymentTypeDTO) {
        log.debug("Entry - update(HHEmploymentTypeDTO={})", hHEmploymentTypeDTO);
        HHEmploymentTypeDTO updated = hHEmploymentTypeFacadeExt.update(hHEmploymentTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHEmploymentTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHEmploymentTypes
     */
    @Override
    public ResponseEntity<PageDTO<HHEmploymentTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHEmploymentTypeDTO> result = hHEmploymentTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHEmploymentType by their ID.
     *
     * @param id The ID of the HHEmploymentType to retrieve
     * @return ResponseEntity containing the HHEmploymentType data
     */
    @Override
    public ResponseEntity<HHEmploymentTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEmploymentTypeDTO dto = hHEmploymentTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHEmploymentType by their ID with an optional reason.
     *
     * @param id     The ID of the HHEmploymentType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHEmploymentTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHEmploymentTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHEmploymentTypeDTO> result = hHEmploymentTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHEmploymentTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
