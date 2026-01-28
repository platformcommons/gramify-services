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


import com.platformcommons.platform.service.blockprofile.api.LocalArtFormTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.LocalArtFormTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.LocalArtFormTypeFacadeExt;


@RestController
@Slf4j
public class LocalArtFormTypeController implements LocalArtFormTypeControllerApi {

    private final LocalArtFormTypeFacadeExt localArtFormTypeFacadeExt;

    /**
     * Constructs a LocalArtFormTypeController with the specified facade.
     *
     * @param localArtFormTypeFacadeExt The LocalArtFormType facade extension to be used
     */
    public LocalArtFormTypeController(LocalArtFormTypeFacadeExt localArtFormTypeFacadeExt) {
        this.localArtFormTypeFacadeExt =localArtFormTypeFacadeExt;
    }

    /**
     * Creates a new LocalArtFormType.
     *
     * @param localArtFormTypeDTO The LocalArtFormType data to create
     * @return ResponseEntity containing the ID of the created LocalArtFormType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody LocalArtFormTypeDTO localArtFormTypeDTO) {
        log.debug("Entry - create(LocalArtFormTypeDTO={})" , localArtFormTypeDTO);
        Long id = localArtFormTypeFacadeExt.save(localArtFormTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createLocalArtFormTypeUri(id)).body(id);
    }

    /**
     * Updates an existing LocalArtFormType.
     *
     * @param localArtFormTypeDTO The LocalArtFormType data to update
     * @return ResponseEntity containing the updated LocalArtFormType data
     */
    @Override
    public ResponseEntity<LocalArtFormTypeDTO> update(@RequestBody LocalArtFormTypeDTO localArtFormTypeDTO) {
        log.debug("Entry - update(LocalArtFormTypeDTO={})", localArtFormTypeDTO);
        LocalArtFormTypeDTO updated = localArtFormTypeFacadeExt.update(localArtFormTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of LocalArtFormTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of LocalArtFormTypes
     */
    @Override
    public ResponseEntity<PageDTO<LocalArtFormTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<LocalArtFormTypeDTO> result = localArtFormTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a LocalArtFormType by their ID.
     *
     * @param id The ID of the LocalArtFormType to retrieve
     * @return ResponseEntity containing the LocalArtFormType data
     */
    @Override
    public ResponseEntity<LocalArtFormTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        LocalArtFormTypeDTO dto = localArtFormTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a LocalArtFormType by their ID with an optional reason.
     *
     * @param id     The ID of the LocalArtFormType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        localArtFormTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<LocalArtFormTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<LocalArtFormTypeDTO> result = localArtFormTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createLocalArtFormTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
