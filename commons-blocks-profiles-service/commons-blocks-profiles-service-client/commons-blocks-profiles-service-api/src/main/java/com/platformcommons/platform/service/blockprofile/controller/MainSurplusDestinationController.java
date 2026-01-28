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


import com.platformcommons.platform.service.blockprofile.api.MainSurplusDestinationControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.MainSurplusDestinationDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.MainSurplusDestinationFacadeExt;


@RestController
@Slf4j
public class MainSurplusDestinationController implements MainSurplusDestinationControllerApi {

    private final MainSurplusDestinationFacadeExt mainSurplusDestinationFacadeExt;

    /**
     * Constructs a MainSurplusDestinationController with the specified facade.
     *
     * @param mainSurplusDestinationFacadeExt The MainSurplusDestination facade extension to be used
     */
    public MainSurplusDestinationController(MainSurplusDestinationFacadeExt mainSurplusDestinationFacadeExt) {
        this.mainSurplusDestinationFacadeExt =mainSurplusDestinationFacadeExt;
    }

    /**
     * Creates a new MainSurplusDestination.
     *
     * @param mainSurplusDestinationDTO The MainSurplusDestination data to create
     * @return ResponseEntity containing the ID of the created MainSurplusDestination
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody MainSurplusDestinationDTO mainSurplusDestinationDTO) {
        log.debug("Entry - create(MainSurplusDestinationDTO={})" , mainSurplusDestinationDTO);
        Long id = mainSurplusDestinationFacadeExt.save(mainSurplusDestinationDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createMainSurplusDestinationUri(id)).body(id);
    }

    /**
     * Updates an existing MainSurplusDestination.
     *
     * @param mainSurplusDestinationDTO The MainSurplusDestination data to update
     * @return ResponseEntity containing the updated MainSurplusDestination data
     */
    @Override
    public ResponseEntity<MainSurplusDestinationDTO> update(@RequestBody MainSurplusDestinationDTO mainSurplusDestinationDTO) {
        log.debug("Entry - update(MainSurplusDestinationDTO={})", mainSurplusDestinationDTO);
        MainSurplusDestinationDTO updated = mainSurplusDestinationFacadeExt.update(mainSurplusDestinationDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of MainSurplusDestinations.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of MainSurplusDestinations
     */
    @Override
    public ResponseEntity<PageDTO<MainSurplusDestinationDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<MainSurplusDestinationDTO> result = mainSurplusDestinationFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a MainSurplusDestination by their ID.
     *
     * @param id The ID of the MainSurplusDestination to retrieve
     * @return ResponseEntity containing the MainSurplusDestination data
     */
    @Override
    public ResponseEntity<MainSurplusDestinationDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        MainSurplusDestinationDTO dto = mainSurplusDestinationFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a MainSurplusDestination by their ID with an optional reason.
     *
     * @param id     The ID of the MainSurplusDestination to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        mainSurplusDestinationFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<MainSurplusDestinationDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<MainSurplusDestinationDTO> result = mainSurplusDestinationFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createMainSurplusDestinationUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
