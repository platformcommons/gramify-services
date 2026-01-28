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


import com.platformcommons.platform.service.blockprofile.api.VillageIrrigationSystemTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageIrrigationSystemTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageIrrigationSystemTypeFacadeExt;


@RestController
@Slf4j
public class VillageIrrigationSystemTypeController implements VillageIrrigationSystemTypeControllerApi {

    private final VillageIrrigationSystemTypeFacadeExt villageIrrigationSystemTypeFacadeExt;

    /**
     * Constructs a VillageIrrigationSystemTypeController with the specified facade.
     *
     * @param villageIrrigationSystemTypeFacadeExt The VillageIrrigationSystemType facade extension to be used
     */
    public VillageIrrigationSystemTypeController(VillageIrrigationSystemTypeFacadeExt villageIrrigationSystemTypeFacadeExt) {
        this.villageIrrigationSystemTypeFacadeExt =villageIrrigationSystemTypeFacadeExt;
    }

    /**
     * Creates a new VillageIrrigationSystemType.
     *
     * @param villageIrrigationSystemTypeDTO The VillageIrrigationSystemType data to create
     * @return ResponseEntity containing the ID of the created VillageIrrigationSystemType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageIrrigationSystemTypeDTO villageIrrigationSystemTypeDTO) {
        log.debug("Entry - create(VillageIrrigationSystemTypeDTO={})" , villageIrrigationSystemTypeDTO);
        Long id = villageIrrigationSystemTypeFacadeExt.save(villageIrrigationSystemTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageIrrigationSystemTypeUri(id)).body(id);
    }

    /**
     * Updates an existing VillageIrrigationSystemType.
     *
     * @param villageIrrigationSystemTypeDTO The VillageIrrigationSystemType data to update
     * @return ResponseEntity containing the updated VillageIrrigationSystemType data
     */
    @Override
    public ResponseEntity<VillageIrrigationSystemTypeDTO> update(@RequestBody VillageIrrigationSystemTypeDTO villageIrrigationSystemTypeDTO) {
        log.debug("Entry - update(VillageIrrigationSystemTypeDTO={})", villageIrrigationSystemTypeDTO);
        VillageIrrigationSystemTypeDTO updated = villageIrrigationSystemTypeFacadeExt.update(villageIrrigationSystemTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageIrrigationSystemTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageIrrigationSystemTypes
     */
    @Override
    public ResponseEntity<PageDTO<VillageIrrigationSystemTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageIrrigationSystemTypeDTO> result = villageIrrigationSystemTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageIrrigationSystemType by their ID.
     *
     * @param id The ID of the VillageIrrigationSystemType to retrieve
     * @return ResponseEntity containing the VillageIrrigationSystemType data
     */
    @Override
    public ResponseEntity<VillageIrrigationSystemTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageIrrigationSystemTypeDTO dto = villageIrrigationSystemTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageIrrigationSystemType by their ID with an optional reason.
     *
     * @param id     The ID of the VillageIrrigationSystemType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageIrrigationSystemTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageIrrigationSystemTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageIrrigationSystemTypeDTO> result = villageIrrigationSystemTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageIrrigationSystemTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
