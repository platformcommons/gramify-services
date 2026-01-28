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


import com.platformcommons.platform.service.blockprofile.api.VillageWaterSupplyGapControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageWaterSupplyGapDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageWaterSupplyGapFacadeExt;


@RestController
@Slf4j
public class VillageWaterSupplyGapController implements VillageWaterSupplyGapControllerApi {

    private final VillageWaterSupplyGapFacadeExt villageWaterSupplyGapFacadeExt;

    /**
     * Constructs a VillageWaterSupplyGapController with the specified facade.
     *
     * @param villageWaterSupplyGapFacadeExt The VillageWaterSupplyGap facade extension to be used
     */
    public VillageWaterSupplyGapController(VillageWaterSupplyGapFacadeExt villageWaterSupplyGapFacadeExt) {
        this.villageWaterSupplyGapFacadeExt =villageWaterSupplyGapFacadeExt;
    }

    /**
     * Creates a new VillageWaterSupplyGap.
     *
     * @param villageWaterSupplyGapDTO The VillageWaterSupplyGap data to create
     * @return ResponseEntity containing the ID of the created VillageWaterSupplyGap
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageWaterSupplyGapDTO villageWaterSupplyGapDTO) {
        log.debug("Entry - create(VillageWaterSupplyGapDTO={})" , villageWaterSupplyGapDTO);
        Long id = villageWaterSupplyGapFacadeExt.save(villageWaterSupplyGapDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageWaterSupplyGapUri(id)).body(id);
    }

    /**
     * Updates an existing VillageWaterSupplyGap.
     *
     * @param villageWaterSupplyGapDTO The VillageWaterSupplyGap data to update
     * @return ResponseEntity containing the updated VillageWaterSupplyGap data
     */
    @Override
    public ResponseEntity<VillageWaterSupplyGapDTO> update(@RequestBody VillageWaterSupplyGapDTO villageWaterSupplyGapDTO) {
        log.debug("Entry - update(VillageWaterSupplyGapDTO={})", villageWaterSupplyGapDTO);
        VillageWaterSupplyGapDTO updated = villageWaterSupplyGapFacadeExt.update(villageWaterSupplyGapDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageWaterSupplyGaps.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageWaterSupplyGaps
     */
    @Override
    public ResponseEntity<PageDTO<VillageWaterSupplyGapDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageWaterSupplyGapDTO> result = villageWaterSupplyGapFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageWaterSupplyGap by their ID.
     *
     * @param id The ID of the VillageWaterSupplyGap to retrieve
     * @return ResponseEntity containing the VillageWaterSupplyGap data
     */
    @Override
    public ResponseEntity<VillageWaterSupplyGapDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageWaterSupplyGapDTO dto = villageWaterSupplyGapFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageWaterSupplyGap by their ID with an optional reason.
     *
     * @param id     The ID of the VillageWaterSupplyGap to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageWaterSupplyGapFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageWaterSupplyGapDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageWaterSupplyGapDTO> result = villageWaterSupplyGapFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageWaterSupplyGapUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
