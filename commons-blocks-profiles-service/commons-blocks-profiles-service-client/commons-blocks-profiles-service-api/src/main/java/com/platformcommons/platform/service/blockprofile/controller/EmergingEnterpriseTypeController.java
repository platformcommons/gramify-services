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


import com.platformcommons.platform.service.blockprofile.api.EmergingEnterpriseTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.EmergingEnterpriseTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.EmergingEnterpriseTypeFacadeExt;


@RestController
@Slf4j
public class EmergingEnterpriseTypeController implements EmergingEnterpriseTypeControllerApi {

    private final EmergingEnterpriseTypeFacadeExt emergingEnterpriseTypeFacadeExt;

    /**
     * Constructs a EmergingEnterpriseTypeController with the specified facade.
     *
     * @param emergingEnterpriseTypeFacadeExt The EmergingEnterpriseType facade extension to be used
     */
    public EmergingEnterpriseTypeController(EmergingEnterpriseTypeFacadeExt emergingEnterpriseTypeFacadeExt) {
        this.emergingEnterpriseTypeFacadeExt =emergingEnterpriseTypeFacadeExt;
    }

    /**
     * Creates a new EmergingEnterpriseType.
     *
     * @param emergingEnterpriseTypeDTO The EmergingEnterpriseType data to create
     * @return ResponseEntity containing the ID of the created EmergingEnterpriseType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody EmergingEnterpriseTypeDTO emergingEnterpriseTypeDTO) {
        log.debug("Entry - create(EmergingEnterpriseTypeDTO={})" , emergingEnterpriseTypeDTO);
        Long id = emergingEnterpriseTypeFacadeExt.save(emergingEnterpriseTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createEmergingEnterpriseTypeUri(id)).body(id);
    }

    /**
     * Updates an existing EmergingEnterpriseType.
     *
     * @param emergingEnterpriseTypeDTO The EmergingEnterpriseType data to update
     * @return ResponseEntity containing the updated EmergingEnterpriseType data
     */
    @Override
    public ResponseEntity<EmergingEnterpriseTypeDTO> update(@RequestBody EmergingEnterpriseTypeDTO emergingEnterpriseTypeDTO) {
        log.debug("Entry - update(EmergingEnterpriseTypeDTO={})", emergingEnterpriseTypeDTO);
        EmergingEnterpriseTypeDTO updated = emergingEnterpriseTypeFacadeExt.update(emergingEnterpriseTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of EmergingEnterpriseTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of EmergingEnterpriseTypes
     */
    @Override
    public ResponseEntity<PageDTO<EmergingEnterpriseTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<EmergingEnterpriseTypeDTO> result = emergingEnterpriseTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a EmergingEnterpriseType by their ID.
     *
     * @param id The ID of the EmergingEnterpriseType to retrieve
     * @return ResponseEntity containing the EmergingEnterpriseType data
     */
    @Override
    public ResponseEntity<EmergingEnterpriseTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        EmergingEnterpriseTypeDTO dto = emergingEnterpriseTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a EmergingEnterpriseType by their ID with an optional reason.
     *
     * @param id     The ID of the EmergingEnterpriseType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        emergingEnterpriseTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<EmergingEnterpriseTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<EmergingEnterpriseTypeDTO> result = emergingEnterpriseTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createEmergingEnterpriseTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
