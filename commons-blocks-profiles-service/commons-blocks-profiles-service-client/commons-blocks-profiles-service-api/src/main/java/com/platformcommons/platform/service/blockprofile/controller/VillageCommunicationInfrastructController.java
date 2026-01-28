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


import com.platformcommons.platform.service.blockprofile.api.VillageCommunicationInfrastructControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageCommunicationInfrastructDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageCommunicationInfrastructFacadeExt;


@RestController
@Slf4j
public class VillageCommunicationInfrastructController implements VillageCommunicationInfrastructControllerApi {

    private final VillageCommunicationInfrastructFacadeExt villageCommunicationInfrastructFacadeExt;

    /**
     * Constructs a VillageCommunicationInfrastructController with the specified facade.
     *
     * @param villageCommunicationInfrastructFacadeExt The VillageCommunicationInfrastruct facade extension to be used
     */
    public VillageCommunicationInfrastructController(VillageCommunicationInfrastructFacadeExt villageCommunicationInfrastructFacadeExt) {
        this.villageCommunicationInfrastructFacadeExt =villageCommunicationInfrastructFacadeExt;
    }

    /**
     * Creates a new VillageCommunicationInfrastruct.
     *
     * @param villageCommunicationInfrastructDTO The VillageCommunicationInfrastruct data to create
     * @return ResponseEntity containing the ID of the created VillageCommunicationInfrastruct
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageCommunicationInfrastructDTO villageCommunicationInfrastructDTO) {
        log.debug("Entry - create(VillageCommunicationInfrastructDTO={})" , villageCommunicationInfrastructDTO);
        Long id = villageCommunicationInfrastructFacadeExt.save(villageCommunicationInfrastructDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageCommunicationInfrastructUri(id)).body(id);
    }

    /**
     * Updates an existing VillageCommunicationInfrastruct.
     *
     * @param villageCommunicationInfrastructDTO The VillageCommunicationInfrastruct data to update
     * @return ResponseEntity containing the updated VillageCommunicationInfrastruct data
     */
    @Override
    public ResponseEntity<VillageCommunicationInfrastructDTO> update(@RequestBody VillageCommunicationInfrastructDTO villageCommunicationInfrastructDTO) {
        log.debug("Entry - update(VillageCommunicationInfrastructDTO={})", villageCommunicationInfrastructDTO);
        VillageCommunicationInfrastructDTO updated = villageCommunicationInfrastructFacadeExt.update(villageCommunicationInfrastructDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageCommunicationInfrastructs.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageCommunicationInfrastructs
     */
    @Override
    public ResponseEntity<PageDTO<VillageCommunicationInfrastructDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageCommunicationInfrastructDTO> result = villageCommunicationInfrastructFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageCommunicationInfrastruct by their ID.
     *
     * @param id The ID of the VillageCommunicationInfrastruct to retrieve
     * @return ResponseEntity containing the VillageCommunicationInfrastruct data
     */
    @Override
    public ResponseEntity<VillageCommunicationInfrastructDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommunicationInfrastructDTO dto = villageCommunicationInfrastructFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageCommunicationInfrastruct by their ID with an optional reason.
     *
     * @param id     The ID of the VillageCommunicationInfrastruct to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageCommunicationInfrastructFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageCommunicationInfrastructDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageCommunicationInfrastructDTO> result = villageCommunicationInfrastructFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageCommunicationInfrastructUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
