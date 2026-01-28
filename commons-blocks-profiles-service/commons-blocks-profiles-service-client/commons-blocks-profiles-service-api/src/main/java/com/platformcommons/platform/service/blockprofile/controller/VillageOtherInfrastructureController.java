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


import com.platformcommons.platform.service.blockprofile.api.VillageOtherInfrastructureControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageOtherInfrastructureDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageOtherInfrastructureFacadeExt;


@RestController
@Slf4j
public class VillageOtherInfrastructureController implements VillageOtherInfrastructureControllerApi {

    private final VillageOtherInfrastructureFacadeExt villageOtherInfrastructureFacadeExt;

    /**
     * Constructs a VillageOtherInfrastructureController with the specified facade.
     *
     * @param villageOtherInfrastructureFacadeExt The VillageOtherInfrastructure facade extension to be used
     */
    public VillageOtherInfrastructureController(VillageOtherInfrastructureFacadeExt villageOtherInfrastructureFacadeExt) {
        this.villageOtherInfrastructureFacadeExt =villageOtherInfrastructureFacadeExt;
    }

    /**
     * Creates a new VillageOtherInfrastructure.
     *
     * @param villageOtherInfrastructureDTO The VillageOtherInfrastructure data to create
     * @return ResponseEntity containing the ID of the created VillageOtherInfrastructure
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageOtherInfrastructureDTO villageOtherInfrastructureDTO) {
        log.debug("Entry - create(VillageOtherInfrastructureDTO={})" , villageOtherInfrastructureDTO);
        Long id = villageOtherInfrastructureFacadeExt.save(villageOtherInfrastructureDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageOtherInfrastructureUri(id)).body(id);
    }

    /**
     * Updates an existing VillageOtherInfrastructure.
     *
     * @param villageOtherInfrastructureDTO The VillageOtherInfrastructure data to update
     * @return ResponseEntity containing the updated VillageOtherInfrastructure data
     */
    @Override
    public ResponseEntity<VillageOtherInfrastructureDTO> update(@RequestBody VillageOtherInfrastructureDTO villageOtherInfrastructureDTO) {
        log.debug("Entry - update(VillageOtherInfrastructureDTO={})", villageOtherInfrastructureDTO);
        VillageOtherInfrastructureDTO updated = villageOtherInfrastructureFacadeExt.update(villageOtherInfrastructureDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageOtherInfrastructures.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageOtherInfrastructures
     */
    @Override
    public ResponseEntity<PageDTO<VillageOtherInfrastructureDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageOtherInfrastructureDTO> result = villageOtherInfrastructureFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageOtherInfrastructure by their ID.
     *
     * @param id The ID of the VillageOtherInfrastructure to retrieve
     * @return ResponseEntity containing the VillageOtherInfrastructure data
     */
    @Override
    public ResponseEntity<VillageOtherInfrastructureDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageOtherInfrastructureDTO dto = villageOtherInfrastructureFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageOtherInfrastructure by their ID with an optional reason.
     *
     * @param id     The ID of the VillageOtherInfrastructure to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageOtherInfrastructureFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageOtherInfrastructureDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageOtherInfrastructureDTO> result = villageOtherInfrastructureFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageOtherInfrastructureUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
