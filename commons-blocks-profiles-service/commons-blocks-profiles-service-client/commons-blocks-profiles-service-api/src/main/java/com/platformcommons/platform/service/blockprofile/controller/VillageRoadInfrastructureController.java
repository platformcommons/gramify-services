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


import com.platformcommons.platform.service.blockprofile.api.VillageRoadInfrastructureControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageRoadInfrastructureDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageRoadInfrastructureFacadeExt;


@RestController
@Slf4j
public class VillageRoadInfrastructureController implements VillageRoadInfrastructureControllerApi {

    private final VillageRoadInfrastructureFacadeExt villageRoadInfrastructureFacadeExt;

    /**
     * Constructs a VillageRoadInfrastructureController with the specified facade.
     *
     * @param villageRoadInfrastructureFacadeExt The VillageRoadInfrastructure facade extension to be used
     */
    public VillageRoadInfrastructureController(VillageRoadInfrastructureFacadeExt villageRoadInfrastructureFacadeExt) {
        this.villageRoadInfrastructureFacadeExt =villageRoadInfrastructureFacadeExt;
    }

    /**
     * Creates a new VillageRoadInfrastructure.
     *
     * @param villageRoadInfrastructureDTO The VillageRoadInfrastructure data to create
     * @return ResponseEntity containing the ID of the created VillageRoadInfrastructure
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageRoadInfrastructureDTO villageRoadInfrastructureDTO) {
        log.debug("Entry - create(VillageRoadInfrastructureDTO={})" , villageRoadInfrastructureDTO);
        Long id = villageRoadInfrastructureFacadeExt.save(villageRoadInfrastructureDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageRoadInfrastructureUri(id)).body(id);
    }

    /**
     * Updates an existing VillageRoadInfrastructure.
     *
     * @param villageRoadInfrastructureDTO The VillageRoadInfrastructure data to update
     * @return ResponseEntity containing the updated VillageRoadInfrastructure data
     */
    @Override
    public ResponseEntity<VillageRoadInfrastructureDTO> update(@RequestBody VillageRoadInfrastructureDTO villageRoadInfrastructureDTO) {
        log.debug("Entry - update(VillageRoadInfrastructureDTO={})", villageRoadInfrastructureDTO);
        VillageRoadInfrastructureDTO updated = villageRoadInfrastructureFacadeExt.update(villageRoadInfrastructureDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageRoadInfrastructures.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageRoadInfrastructures
     */
    @Override
    public ResponseEntity<PageDTO<VillageRoadInfrastructureDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageRoadInfrastructureDTO> result = villageRoadInfrastructureFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageRoadInfrastructure by their ID.
     *
     * @param id The ID of the VillageRoadInfrastructure to retrieve
     * @return ResponseEntity containing the VillageRoadInfrastructure data
     */
    @Override
    public ResponseEntity<VillageRoadInfrastructureDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageRoadInfrastructureDTO dto = villageRoadInfrastructureFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageRoadInfrastructure by their ID with an optional reason.
     *
     * @param id     The ID of the VillageRoadInfrastructure to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageRoadInfrastructureFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageRoadInfrastructureDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageRoadInfrastructureDTO> result = villageRoadInfrastructureFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageRoadInfrastructureUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
