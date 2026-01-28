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


import com.platformcommons.platform.service.blockprofile.api.VillageEducationInfrastructureControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageEducationInfrastructureDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageEducationInfrastructureFacadeExt;


@RestController
@Slf4j
public class VillageEducationInfrastructureController implements VillageEducationInfrastructureControllerApi {

    private final VillageEducationInfrastructureFacadeExt villageEducationInfrastructureFacadeExt;

    /**
     * Constructs a VillageEducationInfrastructureController with the specified facade.
     *
     * @param villageEducationInfrastructureFacadeExt The VillageEducationInfrastructure facade extension to be used
     */
    public VillageEducationInfrastructureController(VillageEducationInfrastructureFacadeExt villageEducationInfrastructureFacadeExt) {
        this.villageEducationInfrastructureFacadeExt =villageEducationInfrastructureFacadeExt;
    }

    /**
     * Creates a new VillageEducationInfrastructure.
     *
     * @param villageEducationInfrastructureDTO The VillageEducationInfrastructure data to create
     * @return ResponseEntity containing the ID of the created VillageEducationInfrastructure
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageEducationInfrastructureDTO villageEducationInfrastructureDTO) {
        log.debug("Entry - create(VillageEducationInfrastructureDTO={})" , villageEducationInfrastructureDTO);
        Long id = villageEducationInfrastructureFacadeExt.save(villageEducationInfrastructureDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageEducationInfrastructureUri(id)).body(id);
    }

    /**
     * Updates an existing VillageEducationInfrastructure.
     *
     * @param villageEducationInfrastructureDTO The VillageEducationInfrastructure data to update
     * @return ResponseEntity containing the updated VillageEducationInfrastructure data
     */
    @Override
    public ResponseEntity<VillageEducationInfrastructureDTO> update(@RequestBody VillageEducationInfrastructureDTO villageEducationInfrastructureDTO) {
        log.debug("Entry - update(VillageEducationInfrastructureDTO={})", villageEducationInfrastructureDTO);
        VillageEducationInfrastructureDTO updated = villageEducationInfrastructureFacadeExt.update(villageEducationInfrastructureDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageEducationInfrastructures.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageEducationInfrastructures
     */
    @Override
    public ResponseEntity<PageDTO<VillageEducationInfrastructureDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageEducationInfrastructureDTO> result = villageEducationInfrastructureFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageEducationInfrastructure by their ID.
     *
     * @param id The ID of the VillageEducationInfrastructure to retrieve
     * @return ResponseEntity containing the VillageEducationInfrastructure data
     */
    @Override
    public ResponseEntity<VillageEducationInfrastructureDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageEducationInfrastructureDTO dto = villageEducationInfrastructureFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageEducationInfrastructure by their ID with an optional reason.
     *
     * @param id     The ID of the VillageEducationInfrastructure to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageEducationInfrastructureFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageEducationInfrastructureDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageEducationInfrastructureDTO> result = villageEducationInfrastructureFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageEducationInfrastructureUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
