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


import com.platformcommons.platform.service.blockprofile.api.RawMaterialsNeededForIndustryControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.RawMaterialsNeededForIndustryDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.RawMaterialsNeededForIndustryFacadeExt;


@RestController
@Slf4j
public class RawMaterialsNeededForIndustryController implements RawMaterialsNeededForIndustryControllerApi {

    private final RawMaterialsNeededForIndustryFacadeExt rawMaterialsNeededForIndustryFacadeExt;

    /**
     * Constructs a RawMaterialsNeededForIndustryController with the specified facade.
     *
     * @param rawMaterialsNeededForIndustryFacadeExt The RawMaterialsNeededForIndustry facade extension to be used
     */
    public RawMaterialsNeededForIndustryController(RawMaterialsNeededForIndustryFacadeExt rawMaterialsNeededForIndustryFacadeExt) {
        this.rawMaterialsNeededForIndustryFacadeExt =rawMaterialsNeededForIndustryFacadeExt;
    }

    /**
     * Creates a new RawMaterialsNeededForIndustry.
     *
     * @param rawMaterialsNeededForIndustryDTO The RawMaterialsNeededForIndustry data to create
     * @return ResponseEntity containing the ID of the created RawMaterialsNeededForIndustry
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody RawMaterialsNeededForIndustryDTO rawMaterialsNeededForIndustryDTO) {
        log.debug("Entry - create(RawMaterialsNeededForIndustryDTO={})" , rawMaterialsNeededForIndustryDTO);
        Long id = rawMaterialsNeededForIndustryFacadeExt.save(rawMaterialsNeededForIndustryDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createRawMaterialsNeededForIndustryUri(id)).body(id);
    }

    /**
     * Updates an existing RawMaterialsNeededForIndustry.
     *
     * @param rawMaterialsNeededForIndustryDTO The RawMaterialsNeededForIndustry data to update
     * @return ResponseEntity containing the updated RawMaterialsNeededForIndustry data
     */
    @Override
    public ResponseEntity<RawMaterialsNeededForIndustryDTO> update(@RequestBody RawMaterialsNeededForIndustryDTO rawMaterialsNeededForIndustryDTO) {
        log.debug("Entry - update(RawMaterialsNeededForIndustryDTO={})", rawMaterialsNeededForIndustryDTO);
        RawMaterialsNeededForIndustryDTO updated = rawMaterialsNeededForIndustryFacadeExt.update(rawMaterialsNeededForIndustryDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of RawMaterialsNeededForIndustrys.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of RawMaterialsNeededForIndustrys
     */
    @Override
    public ResponseEntity<PageDTO<RawMaterialsNeededForIndustryDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<RawMaterialsNeededForIndustryDTO> result = rawMaterialsNeededForIndustryFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a RawMaterialsNeededForIndustry by their ID.
     *
     * @param id The ID of the RawMaterialsNeededForIndustry to retrieve
     * @return ResponseEntity containing the RawMaterialsNeededForIndustry data
     */
    @Override
    public ResponseEntity<RawMaterialsNeededForIndustryDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        RawMaterialsNeededForIndustryDTO dto = rawMaterialsNeededForIndustryFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a RawMaterialsNeededForIndustry by their ID with an optional reason.
     *
     * @param id     The ID of the RawMaterialsNeededForIndustry to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        rawMaterialsNeededForIndustryFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<RawMaterialsNeededForIndustryDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<RawMaterialsNeededForIndustryDTO> result = rawMaterialsNeededForIndustryFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createRawMaterialsNeededForIndustryUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
