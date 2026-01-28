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


import com.platformcommons.platform.service.blockprofile.api.VillageConsumptionPatternControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageConsumptionPatternDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageConsumptionPatternFacadeExt;


@RestController
@Slf4j
public class VillageConsumptionPatternController implements VillageConsumptionPatternControllerApi {

    private final VillageConsumptionPatternFacadeExt villageConsumptionPatternFacadeExt;

    /**
     * Constructs a VillageConsumptionPatternController with the specified facade.
     *
     * @param villageConsumptionPatternFacadeExt The VillageConsumptionPattern facade extension to be used
     */
    public VillageConsumptionPatternController(VillageConsumptionPatternFacadeExt villageConsumptionPatternFacadeExt) {
        this.villageConsumptionPatternFacadeExt =villageConsumptionPatternFacadeExt;
    }

    /**
     * Creates a new VillageConsumptionPattern.
     *
     * @param villageConsumptionPatternDTO The VillageConsumptionPattern data to create
     * @return ResponseEntity containing the ID of the created VillageConsumptionPattern
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageConsumptionPatternDTO villageConsumptionPatternDTO) {
        log.debug("Entry - create(VillageConsumptionPatternDTO={})" , villageConsumptionPatternDTO);
        Long id = villageConsumptionPatternFacadeExt.save(villageConsumptionPatternDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageConsumptionPatternUri(id)).body(id);
    }

    /**
     * Updates an existing VillageConsumptionPattern.
     *
     * @param villageConsumptionPatternDTO The VillageConsumptionPattern data to update
     * @return ResponseEntity containing the updated VillageConsumptionPattern data
     */
    @Override
    public ResponseEntity<VillageConsumptionPatternDTO> update(@RequestBody VillageConsumptionPatternDTO villageConsumptionPatternDTO) {
        log.debug("Entry - update(VillageConsumptionPatternDTO={})", villageConsumptionPatternDTO);
        VillageConsumptionPatternDTO updated = villageConsumptionPatternFacadeExt.update(villageConsumptionPatternDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageConsumptionPatterns.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageConsumptionPatterns
     */
    @Override
    public ResponseEntity<PageDTO<VillageConsumptionPatternDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageConsumptionPatternDTO> result = villageConsumptionPatternFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageConsumptionPattern by their ID.
     *
     * @param id The ID of the VillageConsumptionPattern to retrieve
     * @return ResponseEntity containing the VillageConsumptionPattern data
     */
    @Override
    public ResponseEntity<VillageConsumptionPatternDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageConsumptionPatternDTO dto = villageConsumptionPatternFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageConsumptionPattern by their ID with an optional reason.
     *
     * @param id     The ID of the VillageConsumptionPattern to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageConsumptionPatternFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageConsumptionPatternDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageConsumptionPatternDTO> result = villageConsumptionPatternFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageConsumptionPatternUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
