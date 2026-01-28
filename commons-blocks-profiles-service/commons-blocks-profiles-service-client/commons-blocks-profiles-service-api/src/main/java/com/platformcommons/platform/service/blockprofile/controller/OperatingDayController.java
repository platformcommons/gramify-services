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


import com.platformcommons.platform.service.blockprofile.api.OperatingDayControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.OperatingDayDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.OperatingDayFacadeExt;


@RestController
@Slf4j
public class OperatingDayController implements OperatingDayControllerApi {

    private final OperatingDayFacadeExt operatingDayFacadeExt;

    /**
     * Constructs a OperatingDayController with the specified facade.
     *
     * @param operatingDayFacadeExt The OperatingDay facade extension to be used
     */
    public OperatingDayController(OperatingDayFacadeExt operatingDayFacadeExt) {
        this.operatingDayFacadeExt =operatingDayFacadeExt;
    }

    /**
     * Creates a new OperatingDay.
     *
     * @param operatingDayDTO The OperatingDay data to create
     * @return ResponseEntity containing the ID of the created OperatingDay
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody OperatingDayDTO operatingDayDTO) {
        log.debug("Entry - create(OperatingDayDTO={})" , operatingDayDTO);
        Long id = operatingDayFacadeExt.save(operatingDayDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createOperatingDayUri(id)).body(id);
    }

    /**
     * Updates an existing OperatingDay.
     *
     * @param operatingDayDTO The OperatingDay data to update
     * @return ResponseEntity containing the updated OperatingDay data
     */
    @Override
    public ResponseEntity<OperatingDayDTO> update(@RequestBody OperatingDayDTO operatingDayDTO) {
        log.debug("Entry - update(OperatingDayDTO={})", operatingDayDTO);
        OperatingDayDTO updated = operatingDayFacadeExt.update(operatingDayDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of OperatingDays.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of OperatingDays
     */
    @Override
    public ResponseEntity<PageDTO<OperatingDayDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<OperatingDayDTO> result = operatingDayFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a OperatingDay by their ID.
     *
     * @param id The ID of the OperatingDay to retrieve
     * @return ResponseEntity containing the OperatingDay data
     */
    @Override
    public ResponseEntity<OperatingDayDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        OperatingDayDTO dto = operatingDayFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a OperatingDay by their ID with an optional reason.
     *
     * @param id     The ID of the OperatingDay to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        operatingDayFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<OperatingDayDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<OperatingDayDTO> result = operatingDayFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createOperatingDayUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
