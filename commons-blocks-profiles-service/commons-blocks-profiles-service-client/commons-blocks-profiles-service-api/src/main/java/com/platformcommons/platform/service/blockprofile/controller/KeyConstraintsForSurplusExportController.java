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


import com.platformcommons.platform.service.blockprofile.api.KeyConstraintsForSurplusExportControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.KeyConstraintsForSurplusExportDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.KeyConstraintsForSurplusExportFacadeExt;


@RestController
@Slf4j
public class KeyConstraintsForSurplusExportController implements KeyConstraintsForSurplusExportControllerApi {

    private final KeyConstraintsForSurplusExportFacadeExt keyConstraintsForSurplusExportFacadeExt;

    /**
     * Constructs a KeyConstraintsForSurplusExportController with the specified facade.
     *
     * @param keyConstraintsForSurplusExportFacadeExt The KeyConstraintsForSurplusExport facade extension to be used
     */
    public KeyConstraintsForSurplusExportController(KeyConstraintsForSurplusExportFacadeExt keyConstraintsForSurplusExportFacadeExt) {
        this.keyConstraintsForSurplusExportFacadeExt =keyConstraintsForSurplusExportFacadeExt;
    }

    /**
     * Creates a new KeyConstraintsForSurplusExport.
     *
     * @param keyConstraintsForSurplusExportDTO The KeyConstraintsForSurplusExport data to create
     * @return ResponseEntity containing the ID of the created KeyConstraintsForSurplusExport
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody KeyConstraintsForSurplusExportDTO keyConstraintsForSurplusExportDTO) {
        log.debug("Entry - create(KeyConstraintsForSurplusExportDTO={})" , keyConstraintsForSurplusExportDTO);
        Long id = keyConstraintsForSurplusExportFacadeExt.save(keyConstraintsForSurplusExportDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createKeyConstraintsForSurplusExportUri(id)).body(id);
    }

    /**
     * Updates an existing KeyConstraintsForSurplusExport.
     *
     * @param keyConstraintsForSurplusExportDTO The KeyConstraintsForSurplusExport data to update
     * @return ResponseEntity containing the updated KeyConstraintsForSurplusExport data
     */
    @Override
    public ResponseEntity<KeyConstraintsForSurplusExportDTO> update(@RequestBody KeyConstraintsForSurplusExportDTO keyConstraintsForSurplusExportDTO) {
        log.debug("Entry - update(KeyConstraintsForSurplusExportDTO={})", keyConstraintsForSurplusExportDTO);
        KeyConstraintsForSurplusExportDTO updated = keyConstraintsForSurplusExportFacadeExt.update(keyConstraintsForSurplusExportDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of KeyConstraintsForSurplusExports.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of KeyConstraintsForSurplusExports
     */
    @Override
    public ResponseEntity<PageDTO<KeyConstraintsForSurplusExportDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<KeyConstraintsForSurplusExportDTO> result = keyConstraintsForSurplusExportFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a KeyConstraintsForSurplusExport by their ID.
     *
     * @param id The ID of the KeyConstraintsForSurplusExport to retrieve
     * @return ResponseEntity containing the KeyConstraintsForSurplusExport data
     */
    @Override
    public ResponseEntity<KeyConstraintsForSurplusExportDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        KeyConstraintsForSurplusExportDTO dto = keyConstraintsForSurplusExportFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a KeyConstraintsForSurplusExport by their ID with an optional reason.
     *
     * @param id     The ID of the KeyConstraintsForSurplusExport to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        keyConstraintsForSurplusExportFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<KeyConstraintsForSurplusExportDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<KeyConstraintsForSurplusExportDTO> result = keyConstraintsForSurplusExportFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createKeyConstraintsForSurplusExportUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
