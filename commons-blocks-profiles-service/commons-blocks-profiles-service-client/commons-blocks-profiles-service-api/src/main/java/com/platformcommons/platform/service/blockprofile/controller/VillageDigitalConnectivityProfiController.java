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


import com.platformcommons.platform.service.blockprofile.api.VillageDigitalConnectivityProfiControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageDigitalConnectivityProfiDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageDigitalConnectivityProfiFacadeExt;


@RestController
@Slf4j
public class VillageDigitalConnectivityProfiController implements VillageDigitalConnectivityProfiControllerApi {

    private final VillageDigitalConnectivityProfiFacadeExt villageDigitalConnectivityProfiFacadeExt;

    /**
     * Constructs a VillageDigitalConnectivityProfiController with the specified facade.
     *
     * @param villageDigitalConnectivityProfiFacadeExt The VillageDigitalConnectivityProfi facade extension to be used
     */
    public VillageDigitalConnectivityProfiController(VillageDigitalConnectivityProfiFacadeExt villageDigitalConnectivityProfiFacadeExt) {
        this.villageDigitalConnectivityProfiFacadeExt =villageDigitalConnectivityProfiFacadeExt;
    }

    /**
     * Creates a new VillageDigitalConnectivityProfi.
     *
     * @param villageDigitalConnectivityProfiDTO The VillageDigitalConnectivityProfi data to create
     * @return ResponseEntity containing the ID of the created VillageDigitalConnectivityProfi
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageDigitalConnectivityProfiDTO villageDigitalConnectivityProfiDTO) {
        log.debug("Entry - create(VillageDigitalConnectivityProfiDTO={})" , villageDigitalConnectivityProfiDTO);
        Long id = villageDigitalConnectivityProfiFacadeExt.save(villageDigitalConnectivityProfiDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageDigitalConnectivityProfiUri(id)).body(id);
    }

    /**
     * Updates an existing VillageDigitalConnectivityProfi.
     *
     * @param villageDigitalConnectivityProfiDTO The VillageDigitalConnectivityProfi data to update
     * @return ResponseEntity containing the updated VillageDigitalConnectivityProfi data
     */
    @Override
    public ResponseEntity<VillageDigitalConnectivityProfiDTO> update(@RequestBody VillageDigitalConnectivityProfiDTO villageDigitalConnectivityProfiDTO) {
        log.debug("Entry - update(VillageDigitalConnectivityProfiDTO={})", villageDigitalConnectivityProfiDTO);
        VillageDigitalConnectivityProfiDTO updated = villageDigitalConnectivityProfiFacadeExt.update(villageDigitalConnectivityProfiDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageDigitalConnectivityProfis.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageDigitalConnectivityProfis
     */
    @Override
    public ResponseEntity<PageDTO<VillageDigitalConnectivityProfiDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageDigitalConnectivityProfiDTO> result = villageDigitalConnectivityProfiFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageDigitalConnectivityProfi by their ID.
     *
     * @param id The ID of the VillageDigitalConnectivityProfi to retrieve
     * @return ResponseEntity containing the VillageDigitalConnectivityProfi data
     */
    @Override
    public ResponseEntity<VillageDigitalConnectivityProfiDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageDigitalConnectivityProfiDTO dto = villageDigitalConnectivityProfiFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageDigitalConnectivityProfi by their ID with an optional reason.
     *
     * @param id     The ID of the VillageDigitalConnectivityProfi to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageDigitalConnectivityProfiFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageDigitalConnectivityProfiDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageDigitalConnectivityProfiDTO> result = villageDigitalConnectivityProfiFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageDigitalConnectivityProfiUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
