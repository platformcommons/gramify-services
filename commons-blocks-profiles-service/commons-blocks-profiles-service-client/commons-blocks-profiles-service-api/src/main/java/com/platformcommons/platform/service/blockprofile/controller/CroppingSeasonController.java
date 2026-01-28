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


import com.platformcommons.platform.service.blockprofile.api.CroppingSeasonControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.CroppingSeasonDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.CroppingSeasonFacadeExt;


@RestController
@Slf4j
public class CroppingSeasonController implements CroppingSeasonControllerApi {

    private final CroppingSeasonFacadeExt croppingSeasonFacadeExt;

    /**
     * Constructs a CroppingSeasonController with the specified facade.
     *
     * @param croppingSeasonFacadeExt The CroppingSeason facade extension to be used
     */
    public CroppingSeasonController(CroppingSeasonFacadeExt croppingSeasonFacadeExt) {
        this.croppingSeasonFacadeExt =croppingSeasonFacadeExt;
    }

    /**
     * Creates a new CroppingSeason.
     *
     * @param croppingSeasonDTO The CroppingSeason data to create
     * @return ResponseEntity containing the ID of the created CroppingSeason
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody CroppingSeasonDTO croppingSeasonDTO) {
        log.debug("Entry - create(CroppingSeasonDTO={})" , croppingSeasonDTO);
        Long id = croppingSeasonFacadeExt.save(croppingSeasonDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createCroppingSeasonUri(id)).body(id);
    }

    /**
     * Updates an existing CroppingSeason.
     *
     * @param croppingSeasonDTO The CroppingSeason data to update
     * @return ResponseEntity containing the updated CroppingSeason data
     */
    @Override
    public ResponseEntity<CroppingSeasonDTO> update(@RequestBody CroppingSeasonDTO croppingSeasonDTO) {
        log.debug("Entry - update(CroppingSeasonDTO={})", croppingSeasonDTO);
        CroppingSeasonDTO updated = croppingSeasonFacadeExt.update(croppingSeasonDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of CroppingSeasons.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of CroppingSeasons
     */
    @Override
    public ResponseEntity<PageDTO<CroppingSeasonDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<CroppingSeasonDTO> result = croppingSeasonFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a CroppingSeason by their ID.
     *
     * @param id The ID of the CroppingSeason to retrieve
     * @return ResponseEntity containing the CroppingSeason data
     */
    @Override
    public ResponseEntity<CroppingSeasonDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        CroppingSeasonDTO dto = croppingSeasonFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a CroppingSeason by their ID with an optional reason.
     *
     * @param id     The ID of the CroppingSeason to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        croppingSeasonFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<CroppingSeasonDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<CroppingSeasonDTO> result = croppingSeasonFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createCroppingSeasonUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
