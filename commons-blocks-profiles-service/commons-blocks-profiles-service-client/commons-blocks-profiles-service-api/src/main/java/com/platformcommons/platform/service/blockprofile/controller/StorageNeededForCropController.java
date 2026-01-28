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


import com.platformcommons.platform.service.blockprofile.api.StorageNeededForCropControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.StorageNeededForCropDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.StorageNeededForCropFacadeExt;


@RestController
@Slf4j
public class StorageNeededForCropController implements StorageNeededForCropControllerApi {

    private final StorageNeededForCropFacadeExt storageNeededForCropFacadeExt;

    /**
     * Constructs a StorageNeededForCropController with the specified facade.
     *
     * @param storageNeededForCropFacadeExt The StorageNeededForCrop facade extension to be used
     */
    public StorageNeededForCropController(StorageNeededForCropFacadeExt storageNeededForCropFacadeExt) {
        this.storageNeededForCropFacadeExt =storageNeededForCropFacadeExt;
    }

    /**
     * Creates a new StorageNeededForCrop.
     *
     * @param storageNeededForCropDTO The StorageNeededForCrop data to create
     * @return ResponseEntity containing the ID of the created StorageNeededForCrop
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody StorageNeededForCropDTO storageNeededForCropDTO) {
        log.debug("Entry - create(StorageNeededForCropDTO={})" , storageNeededForCropDTO);
        Long id = storageNeededForCropFacadeExt.save(storageNeededForCropDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createStorageNeededForCropUri(id)).body(id);
    }

    /**
     * Updates an existing StorageNeededForCrop.
     *
     * @param storageNeededForCropDTO The StorageNeededForCrop data to update
     * @return ResponseEntity containing the updated StorageNeededForCrop data
     */
    @Override
    public ResponseEntity<StorageNeededForCropDTO> update(@RequestBody StorageNeededForCropDTO storageNeededForCropDTO) {
        log.debug("Entry - update(StorageNeededForCropDTO={})", storageNeededForCropDTO);
        StorageNeededForCropDTO updated = storageNeededForCropFacadeExt.update(storageNeededForCropDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of StorageNeededForCrops.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of StorageNeededForCrops
     */
    @Override
    public ResponseEntity<PageDTO<StorageNeededForCropDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<StorageNeededForCropDTO> result = storageNeededForCropFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a StorageNeededForCrop by their ID.
     *
     * @param id The ID of the StorageNeededForCrop to retrieve
     * @return ResponseEntity containing the StorageNeededForCrop data
     */
    @Override
    public ResponseEntity<StorageNeededForCropDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        StorageNeededForCropDTO dto = storageNeededForCropFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a StorageNeededForCrop by their ID with an optional reason.
     *
     * @param id     The ID of the StorageNeededForCrop to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        storageNeededForCropFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<StorageNeededForCropDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<StorageNeededForCropDTO> result = storageNeededForCropFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createStorageNeededForCropUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
