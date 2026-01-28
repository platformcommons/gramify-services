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


import com.platformcommons.platform.service.blockprofile.api.HHEnterpriseTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHEnterpriseTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHEnterpriseTypeFacadeExt;


@RestController
@Slf4j
public class HHEnterpriseTypeController implements HHEnterpriseTypeControllerApi {

    private final HHEnterpriseTypeFacadeExt hHEnterpriseTypeFacadeExt;

    /**
     * Constructs a HHEnterpriseTypeController with the specified facade.
     *
     * @param hHEnterpriseTypeFacadeExt The HHEnterpriseType facade extension to be used
     */
    public HHEnterpriseTypeController(HHEnterpriseTypeFacadeExt hHEnterpriseTypeFacadeExt) {
        this.hHEnterpriseTypeFacadeExt =hHEnterpriseTypeFacadeExt;
    }

    /**
     * Creates a new HHEnterpriseType.
     *
     * @param hHEnterpriseTypeDTO The HHEnterpriseType data to create
     * @return ResponseEntity containing the ID of the created HHEnterpriseType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHEnterpriseTypeDTO hHEnterpriseTypeDTO) {
        log.debug("Entry - create(HHEnterpriseTypeDTO={})" , hHEnterpriseTypeDTO);
        Long id = hHEnterpriseTypeFacadeExt.save(hHEnterpriseTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHEnterpriseTypeUri(id)).body(id);
    }

    /**
     * Updates an existing HHEnterpriseType.
     *
     * @param hHEnterpriseTypeDTO The HHEnterpriseType data to update
     * @return ResponseEntity containing the updated HHEnterpriseType data
     */
    @Override
    public ResponseEntity<HHEnterpriseTypeDTO> update(@RequestBody HHEnterpriseTypeDTO hHEnterpriseTypeDTO) {
        log.debug("Entry - update(HHEnterpriseTypeDTO={})", hHEnterpriseTypeDTO);
        HHEnterpriseTypeDTO updated = hHEnterpriseTypeFacadeExt.update(hHEnterpriseTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHEnterpriseTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHEnterpriseTypes
     */
    @Override
    public ResponseEntity<PageDTO<HHEnterpriseTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHEnterpriseTypeDTO> result = hHEnterpriseTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHEnterpriseType by their ID.
     *
     * @param id The ID of the HHEnterpriseType to retrieve
     * @return ResponseEntity containing the HHEnterpriseType data
     */
    @Override
    public ResponseEntity<HHEnterpriseTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEnterpriseTypeDTO dto = hHEnterpriseTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHEnterpriseType by their ID with an optional reason.
     *
     * @param id     The ID of the HHEnterpriseType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHEnterpriseTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHEnterpriseTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHEnterpriseTypeDTO> result = hHEnterpriseTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHEnterpriseTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
