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


import com.platformcommons.platform.service.blockprofile.api.SupportNeededForNicheGrowthControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.SupportNeededForNicheGrowthDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.SupportNeededForNicheGrowthFacadeExt;


@RestController
@Slf4j
public class SupportNeededForNicheGrowthController implements SupportNeededForNicheGrowthControllerApi {

    private final SupportNeededForNicheGrowthFacadeExt supportNeededForNicheGrowthFacadeExt;

    /**
     * Constructs a SupportNeededForNicheGrowthController with the specified facade.
     *
     * @param supportNeededForNicheGrowthFacadeExt The SupportNeededForNicheGrowth facade extension to be used
     */
    public SupportNeededForNicheGrowthController(SupportNeededForNicheGrowthFacadeExt supportNeededForNicheGrowthFacadeExt) {
        this.supportNeededForNicheGrowthFacadeExt =supportNeededForNicheGrowthFacadeExt;
    }

    /**
     * Creates a new SupportNeededForNicheGrowth.
     *
     * @param supportNeededForNicheGrowthDTO The SupportNeededForNicheGrowth data to create
     * @return ResponseEntity containing the ID of the created SupportNeededForNicheGrowth
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody SupportNeededForNicheGrowthDTO supportNeededForNicheGrowthDTO) {
        log.debug("Entry - create(SupportNeededForNicheGrowthDTO={})" , supportNeededForNicheGrowthDTO);
        Long id = supportNeededForNicheGrowthFacadeExt.save(supportNeededForNicheGrowthDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createSupportNeededForNicheGrowthUri(id)).body(id);
    }

    /**
     * Updates an existing SupportNeededForNicheGrowth.
     *
     * @param supportNeededForNicheGrowthDTO The SupportNeededForNicheGrowth data to update
     * @return ResponseEntity containing the updated SupportNeededForNicheGrowth data
     */
    @Override
    public ResponseEntity<SupportNeededForNicheGrowthDTO> update(@RequestBody SupportNeededForNicheGrowthDTO supportNeededForNicheGrowthDTO) {
        log.debug("Entry - update(SupportNeededForNicheGrowthDTO={})", supportNeededForNicheGrowthDTO);
        SupportNeededForNicheGrowthDTO updated = supportNeededForNicheGrowthFacadeExt.update(supportNeededForNicheGrowthDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of SupportNeededForNicheGrowths.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of SupportNeededForNicheGrowths
     */
    @Override
    public ResponseEntity<PageDTO<SupportNeededForNicheGrowthDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<SupportNeededForNicheGrowthDTO> result = supportNeededForNicheGrowthFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a SupportNeededForNicheGrowth by their ID.
     *
     * @param id The ID of the SupportNeededForNicheGrowth to retrieve
     * @return ResponseEntity containing the SupportNeededForNicheGrowth data
     */
    @Override
    public ResponseEntity<SupportNeededForNicheGrowthDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        SupportNeededForNicheGrowthDTO dto = supportNeededForNicheGrowthFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a SupportNeededForNicheGrowth by their ID with an optional reason.
     *
     * @param id     The ID of the SupportNeededForNicheGrowth to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        supportNeededForNicheGrowthFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<SupportNeededForNicheGrowthDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<SupportNeededForNicheGrowthDTO> result = supportNeededForNicheGrowthFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createSupportNeededForNicheGrowthUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
