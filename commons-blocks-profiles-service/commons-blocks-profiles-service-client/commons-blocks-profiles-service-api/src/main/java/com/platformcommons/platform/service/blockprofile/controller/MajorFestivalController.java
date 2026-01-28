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


import com.platformcommons.platform.service.blockprofile.api.MajorFestivalControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.MajorFestivalDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.MajorFestivalFacadeExt;


@RestController
@Slf4j
public class MajorFestivalController implements MajorFestivalControllerApi {

    private final MajorFestivalFacadeExt majorFestivalFacadeExt;

    /**
     * Constructs a MajorFestivalController with the specified facade.
     *
     * @param majorFestivalFacadeExt The MajorFestival facade extension to be used
     */
    public MajorFestivalController(MajorFestivalFacadeExt majorFestivalFacadeExt) {
        this.majorFestivalFacadeExt =majorFestivalFacadeExt;
    }

    /**
     * Creates a new MajorFestival.
     *
     * @param majorFestivalDTO The MajorFestival data to create
     * @return ResponseEntity containing the ID of the created MajorFestival
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody MajorFestivalDTO majorFestivalDTO) {
        log.debug("Entry - create(MajorFestivalDTO={})" , majorFestivalDTO);
        Long id = majorFestivalFacadeExt.save(majorFestivalDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createMajorFestivalUri(id)).body(id);
    }

    /**
     * Updates an existing MajorFestival.
     *
     * @param majorFestivalDTO The MajorFestival data to update
     * @return ResponseEntity containing the updated MajorFestival data
     */
    @Override
    public ResponseEntity<MajorFestivalDTO> update(@RequestBody MajorFestivalDTO majorFestivalDTO) {
        log.debug("Entry - update(MajorFestivalDTO={})", majorFestivalDTO);
        MajorFestivalDTO updated = majorFestivalFacadeExt.update(majorFestivalDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of MajorFestivals.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of MajorFestivals
     */
    @Override
    public ResponseEntity<PageDTO<MajorFestivalDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<MajorFestivalDTO> result = majorFestivalFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a MajorFestival by their ID.
     *
     * @param id The ID of the MajorFestival to retrieve
     * @return ResponseEntity containing the MajorFestival data
     */
    @Override
    public ResponseEntity<MajorFestivalDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        MajorFestivalDTO dto = majorFestivalFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a MajorFestival by their ID with an optional reason.
     *
     * @param id     The ID of the MajorFestival to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        majorFestivalFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<MajorFestivalDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<MajorFestivalDTO> result = majorFestivalFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createMajorFestivalUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
