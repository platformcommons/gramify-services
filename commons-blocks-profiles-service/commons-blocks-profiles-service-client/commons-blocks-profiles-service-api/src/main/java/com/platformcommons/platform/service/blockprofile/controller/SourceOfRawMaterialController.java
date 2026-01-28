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


import com.platformcommons.platform.service.blockprofile.api.SourceOfRawMaterialControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.SourceOfRawMaterialDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.SourceOfRawMaterialFacadeExt;


@RestController
@Slf4j
public class SourceOfRawMaterialController implements SourceOfRawMaterialControllerApi {

    private final SourceOfRawMaterialFacadeExt sourceOfRawMaterialFacadeExt;

    /**
     * Constructs a SourceOfRawMaterialController with the specified facade.
     *
     * @param sourceOfRawMaterialFacadeExt The SourceOfRawMaterial facade extension to be used
     */
    public SourceOfRawMaterialController(SourceOfRawMaterialFacadeExt sourceOfRawMaterialFacadeExt) {
        this.sourceOfRawMaterialFacadeExt =sourceOfRawMaterialFacadeExt;
    }

    /**
     * Creates a new SourceOfRawMaterial.
     *
     * @param sourceOfRawMaterialDTO The SourceOfRawMaterial data to create
     * @return ResponseEntity containing the ID of the created SourceOfRawMaterial
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody SourceOfRawMaterialDTO sourceOfRawMaterialDTO) {
        log.debug("Entry - create(SourceOfRawMaterialDTO={})" , sourceOfRawMaterialDTO);
        Long id = sourceOfRawMaterialFacadeExt.save(sourceOfRawMaterialDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createSourceOfRawMaterialUri(id)).body(id);
    }

    /**
     * Updates an existing SourceOfRawMaterial.
     *
     * @param sourceOfRawMaterialDTO The SourceOfRawMaterial data to update
     * @return ResponseEntity containing the updated SourceOfRawMaterial data
     */
    @Override
    public ResponseEntity<SourceOfRawMaterialDTO> update(@RequestBody SourceOfRawMaterialDTO sourceOfRawMaterialDTO) {
        log.debug("Entry - update(SourceOfRawMaterialDTO={})", sourceOfRawMaterialDTO);
        SourceOfRawMaterialDTO updated = sourceOfRawMaterialFacadeExt.update(sourceOfRawMaterialDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of SourceOfRawMaterials.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of SourceOfRawMaterials
     */
    @Override
    public ResponseEntity<PageDTO<SourceOfRawMaterialDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<SourceOfRawMaterialDTO> result = sourceOfRawMaterialFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a SourceOfRawMaterial by their ID.
     *
     * @param id The ID of the SourceOfRawMaterial to retrieve
     * @return ResponseEntity containing the SourceOfRawMaterial data
     */
    @Override
    public ResponseEntity<SourceOfRawMaterialDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        SourceOfRawMaterialDTO dto = sourceOfRawMaterialFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a SourceOfRawMaterial by their ID with an optional reason.
     *
     * @param id     The ID of the SourceOfRawMaterial to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        sourceOfRawMaterialFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<SourceOfRawMaterialDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<SourceOfRawMaterialDTO> result = sourceOfRawMaterialFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createSourceOfRawMaterialUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
