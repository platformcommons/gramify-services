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


import com.platformcommons.platform.service.blockprofile.api.WherePeopleGoForCommonIllnessControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.WherePeopleGoForCommonIllnessDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.WherePeopleGoForCommonIllnessFacadeExt;


@RestController
@Slf4j
public class WherePeopleGoForCommonIllnessController implements WherePeopleGoForCommonIllnessControllerApi {

    private final WherePeopleGoForCommonIllnessFacadeExt wherePeopleGoForCommonIllnessFacadeExt;

    /**
     * Constructs a WherePeopleGoForCommonIllnessController with the specified facade.
     *
     * @param wherePeopleGoForCommonIllnessFacadeExt The WherePeopleGoForCommonIllness facade extension to be used
     */
    public WherePeopleGoForCommonIllnessController(WherePeopleGoForCommonIllnessFacadeExt wherePeopleGoForCommonIllnessFacadeExt) {
        this.wherePeopleGoForCommonIllnessFacadeExt =wherePeopleGoForCommonIllnessFacadeExt;
    }

    /**
     * Creates a new WherePeopleGoForCommonIllness.
     *
     * @param wherePeopleGoForCommonIllnessDTO The WherePeopleGoForCommonIllness data to create
     * @return ResponseEntity containing the ID of the created WherePeopleGoForCommonIllness
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody WherePeopleGoForCommonIllnessDTO wherePeopleGoForCommonIllnessDTO) {
        log.debug("Entry - create(WherePeopleGoForCommonIllnessDTO={})" , wherePeopleGoForCommonIllnessDTO);
        Long id = wherePeopleGoForCommonIllnessFacadeExt.save(wherePeopleGoForCommonIllnessDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createWherePeopleGoForCommonIllnessUri(id)).body(id);
    }

    /**
     * Updates an existing WherePeopleGoForCommonIllness.
     *
     * @param wherePeopleGoForCommonIllnessDTO The WherePeopleGoForCommonIllness data to update
     * @return ResponseEntity containing the updated WherePeopleGoForCommonIllness data
     */
    @Override
    public ResponseEntity<WherePeopleGoForCommonIllnessDTO> update(@RequestBody WherePeopleGoForCommonIllnessDTO wherePeopleGoForCommonIllnessDTO) {
        log.debug("Entry - update(WherePeopleGoForCommonIllnessDTO={})", wherePeopleGoForCommonIllnessDTO);
        WherePeopleGoForCommonIllnessDTO updated = wherePeopleGoForCommonIllnessFacadeExt.update(wherePeopleGoForCommonIllnessDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of WherePeopleGoForCommonIllnesss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of WherePeopleGoForCommonIllnesss
     */
    @Override
    public ResponseEntity<PageDTO<WherePeopleGoForCommonIllnessDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<WherePeopleGoForCommonIllnessDTO> result = wherePeopleGoForCommonIllnessFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a WherePeopleGoForCommonIllness by their ID.
     *
     * @param id The ID of the WherePeopleGoForCommonIllness to retrieve
     * @return ResponseEntity containing the WherePeopleGoForCommonIllness data
     */
    @Override
    public ResponseEntity<WherePeopleGoForCommonIllnessDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        WherePeopleGoForCommonIllnessDTO dto = wherePeopleGoForCommonIllnessFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a WherePeopleGoForCommonIllness by their ID with an optional reason.
     *
     * @param id     The ID of the WherePeopleGoForCommonIllness to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        wherePeopleGoForCommonIllnessFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<WherePeopleGoForCommonIllnessDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<WherePeopleGoForCommonIllnessDTO> result = wherePeopleGoForCommonIllnessFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createWherePeopleGoForCommonIllnessUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
