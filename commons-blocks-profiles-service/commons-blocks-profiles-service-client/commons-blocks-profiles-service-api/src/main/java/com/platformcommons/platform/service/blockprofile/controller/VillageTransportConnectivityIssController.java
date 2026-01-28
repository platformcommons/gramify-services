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


import com.platformcommons.platform.service.blockprofile.api.VillageTransportConnectivityIssControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageTransportConnectivityIssDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageTransportConnectivityIssFacadeExt;


@RestController
@Slf4j
public class VillageTransportConnectivityIssController implements VillageTransportConnectivityIssControllerApi {

    private final VillageTransportConnectivityIssFacadeExt villageTransportConnectivityIssFacadeExt;

    /**
     * Constructs a VillageTransportConnectivityIssController with the specified facade.
     *
     * @param villageTransportConnectivityIssFacadeExt The VillageTransportConnectivityIss facade extension to be used
     */
    public VillageTransportConnectivityIssController(VillageTransportConnectivityIssFacadeExt villageTransportConnectivityIssFacadeExt) {
        this.villageTransportConnectivityIssFacadeExt =villageTransportConnectivityIssFacadeExt;
    }

    /**
     * Creates a new VillageTransportConnectivityIss.
     *
     * @param villageTransportConnectivityIssDTO The VillageTransportConnectivityIss data to create
     * @return ResponseEntity containing the ID of the created VillageTransportConnectivityIss
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageTransportConnectivityIssDTO villageTransportConnectivityIssDTO) {
        log.debug("Entry - create(VillageTransportConnectivityIssDTO={})" , villageTransportConnectivityIssDTO);
        Long id = villageTransportConnectivityIssFacadeExt.save(villageTransportConnectivityIssDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageTransportConnectivityIssUri(id)).body(id);
    }

    /**
     * Updates an existing VillageTransportConnectivityIss.
     *
     * @param villageTransportConnectivityIssDTO The VillageTransportConnectivityIss data to update
     * @return ResponseEntity containing the updated VillageTransportConnectivityIss data
     */
    @Override
    public ResponseEntity<VillageTransportConnectivityIssDTO> update(@RequestBody VillageTransportConnectivityIssDTO villageTransportConnectivityIssDTO) {
        log.debug("Entry - update(VillageTransportConnectivityIssDTO={})", villageTransportConnectivityIssDTO);
        VillageTransportConnectivityIssDTO updated = villageTransportConnectivityIssFacadeExt.update(villageTransportConnectivityIssDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageTransportConnectivityIsss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageTransportConnectivityIsss
     */
    @Override
    public ResponseEntity<PageDTO<VillageTransportConnectivityIssDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageTransportConnectivityIssDTO> result = villageTransportConnectivityIssFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageTransportConnectivityIss by their ID.
     *
     * @param id The ID of the VillageTransportConnectivityIss to retrieve
     * @return ResponseEntity containing the VillageTransportConnectivityIss data
     */
    @Override
    public ResponseEntity<VillageTransportConnectivityIssDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageTransportConnectivityIssDTO dto = villageTransportConnectivityIssFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageTransportConnectivityIss by their ID with an optional reason.
     *
     * @param id     The ID of the VillageTransportConnectivityIss to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageTransportConnectivityIssFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageTransportConnectivityIssDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageTransportConnectivityIssDTO> result = villageTransportConnectivityIssFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageTransportConnectivityIssUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
