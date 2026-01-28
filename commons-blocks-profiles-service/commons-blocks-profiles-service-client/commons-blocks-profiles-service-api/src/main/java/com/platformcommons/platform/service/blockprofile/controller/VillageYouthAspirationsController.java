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


import com.platformcommons.platform.service.blockprofile.api.VillageYouthAspirationsControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageYouthAspirationsDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageYouthAspirationsFacadeExt;


@RestController
@Slf4j
public class VillageYouthAspirationsController implements VillageYouthAspirationsControllerApi {

    private final VillageYouthAspirationsFacadeExt villageYouthAspirationsFacadeExt;

    /**
     * Constructs a VillageYouthAspirationsController with the specified facade.
     *
     * @param villageYouthAspirationsFacadeExt The VillageYouthAspirations facade extension to be used
     */
    public VillageYouthAspirationsController(VillageYouthAspirationsFacadeExt villageYouthAspirationsFacadeExt) {
        this.villageYouthAspirationsFacadeExt =villageYouthAspirationsFacadeExt;
    }

    /**
     * Creates a new VillageYouthAspirations.
     *
     * @param villageYouthAspirationsDTO The VillageYouthAspirations data to create
     * @return ResponseEntity containing the ID of the created VillageYouthAspirations
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageYouthAspirationsDTO villageYouthAspirationsDTO) {
        log.debug("Entry - create(VillageYouthAspirationsDTO={})" , villageYouthAspirationsDTO);
        Long id = villageYouthAspirationsFacadeExt.save(villageYouthAspirationsDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageYouthAspirationsUri(id)).body(id);
    }

    /**
     * Updates an existing VillageYouthAspirations.
     *
     * @param villageYouthAspirationsDTO The VillageYouthAspirations data to update
     * @return ResponseEntity containing the updated VillageYouthAspirations data
     */
    @Override
    public ResponseEntity<VillageYouthAspirationsDTO> update(@RequestBody VillageYouthAspirationsDTO villageYouthAspirationsDTO) {
        log.debug("Entry - update(VillageYouthAspirationsDTO={})", villageYouthAspirationsDTO);
        VillageYouthAspirationsDTO updated = villageYouthAspirationsFacadeExt.update(villageYouthAspirationsDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageYouthAspirationss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageYouthAspirationss
     */
    @Override
    public ResponseEntity<PageDTO<VillageYouthAspirationsDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageYouthAspirationsDTO> result = villageYouthAspirationsFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageYouthAspirations by their ID.
     *
     * @param id The ID of the VillageYouthAspirations to retrieve
     * @return ResponseEntity containing the VillageYouthAspirations data
     */
    @Override
    public ResponseEntity<VillageYouthAspirationsDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageYouthAspirationsDTO dto = villageYouthAspirationsFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageYouthAspirations by their ID with an optional reason.
     *
     * @param id     The ID of the VillageYouthAspirations to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageYouthAspirationsFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageYouthAspirationsDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageYouthAspirationsDTO> result = villageYouthAspirationsFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageYouthAspirationsUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
