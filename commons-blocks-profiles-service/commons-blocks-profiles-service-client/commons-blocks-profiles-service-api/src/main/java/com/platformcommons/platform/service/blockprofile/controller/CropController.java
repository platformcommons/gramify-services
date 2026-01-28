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


import com.platformcommons.platform.service.blockprofile.api.CropControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.CropDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.CropFacadeExt;


@RestController
@Slf4j
public class CropController implements CropControllerApi {

    private final CropFacadeExt cropFacadeExt;

    /**
     * Constructs a CropController with the specified facade.
     *
     * @param cropFacadeExt The Crop facade extension to be used
     */
    public CropController(CropFacadeExt cropFacadeExt) {
        this.cropFacadeExt =cropFacadeExt;
    }

    /**
     * Creates a new Crop.
     *
     * @param cropDTO The Crop data to create
     * @return ResponseEntity containing the ID of the created Crop
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody CropDTO cropDTO) {
        log.debug("Entry - create(CropDTO={})" , cropDTO);
        Long id = cropFacadeExt.save(cropDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createCropUri(id)).body(id);
    }

    /**
     * Updates an existing Crop.
     *
     * @param cropDTO The Crop data to update
     * @return ResponseEntity containing the updated Crop data
     */
    @Override
    public ResponseEntity<CropDTO> update(@RequestBody CropDTO cropDTO) {
        log.debug("Entry - update(CropDTO={})", cropDTO);
        CropDTO updated = cropFacadeExt.update(cropDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of Crops.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of Crops
     */
    @Override
    public ResponseEntity<PageDTO<CropDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<CropDTO> result = cropFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a Crop by their ID.
     *
     * @param id The ID of the Crop to retrieve
     * @return ResponseEntity containing the Crop data
     */
    @Override
    public ResponseEntity<CropDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        CropDTO dto = cropFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a Crop by their ID with an optional reason.
     *
     * @param id     The ID of the Crop to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        cropFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<CropDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<CropDTO> result = cropFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createCropUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
