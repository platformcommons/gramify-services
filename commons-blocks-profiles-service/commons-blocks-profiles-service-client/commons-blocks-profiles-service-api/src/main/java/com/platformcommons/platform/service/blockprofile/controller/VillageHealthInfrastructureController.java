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


import com.platformcommons.platform.service.blockprofile.api.VillageHealthInfrastructureControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageHealthInfrastructureDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageHealthInfrastructureFacadeExt;


@RestController
@Slf4j
public class VillageHealthInfrastructureController implements VillageHealthInfrastructureControllerApi {

    private final VillageHealthInfrastructureFacadeExt villageHealthInfrastructureFacadeExt;

    /**
     * Constructs a VillageHealthInfrastructureController with the specified facade.
     *
     * @param villageHealthInfrastructureFacadeExt The VillageHealthInfrastructure facade extension to be used
     */
    public VillageHealthInfrastructureController(VillageHealthInfrastructureFacadeExt villageHealthInfrastructureFacadeExt) {
        this.villageHealthInfrastructureFacadeExt =villageHealthInfrastructureFacadeExt;
    }

    /**
     * Creates a new VillageHealthInfrastructure.
     *
     * @param villageHealthInfrastructureDTO The VillageHealthInfrastructure data to create
     * @return ResponseEntity containing the ID of the created VillageHealthInfrastructure
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageHealthInfrastructureDTO villageHealthInfrastructureDTO) {
        log.debug("Entry - create(VillageHealthInfrastructureDTO={})" , villageHealthInfrastructureDTO);
        Long id = villageHealthInfrastructureFacadeExt.save(villageHealthInfrastructureDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageHealthInfrastructureUri(id)).body(id);
    }

    /**
     * Updates an existing VillageHealthInfrastructure.
     *
     * @param villageHealthInfrastructureDTO The VillageHealthInfrastructure data to update
     * @return ResponseEntity containing the updated VillageHealthInfrastructure data
     */
    @Override
    public ResponseEntity<VillageHealthInfrastructureDTO> update(@RequestBody VillageHealthInfrastructureDTO villageHealthInfrastructureDTO) {
        log.debug("Entry - update(VillageHealthInfrastructureDTO={})", villageHealthInfrastructureDTO);
        VillageHealthInfrastructureDTO updated = villageHealthInfrastructureFacadeExt.update(villageHealthInfrastructureDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageHealthInfrastructures.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageHealthInfrastructures
     */
    @Override
    public ResponseEntity<PageDTO<VillageHealthInfrastructureDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageHealthInfrastructureDTO> result = villageHealthInfrastructureFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageHealthInfrastructure by their ID.
     *
     * @param id The ID of the VillageHealthInfrastructure to retrieve
     * @return ResponseEntity containing the VillageHealthInfrastructure data
     */
    @Override
    public ResponseEntity<VillageHealthInfrastructureDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageHealthInfrastructureDTO dto = villageHealthInfrastructureFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageHealthInfrastructure by their ID with an optional reason.
     *
     * @param id     The ID of the VillageHealthInfrastructure to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageHealthInfrastructureFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageHealthInfrastructureDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageHealthInfrastructureDTO> result = villageHealthInfrastructureFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageHealthInfrastructureUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
