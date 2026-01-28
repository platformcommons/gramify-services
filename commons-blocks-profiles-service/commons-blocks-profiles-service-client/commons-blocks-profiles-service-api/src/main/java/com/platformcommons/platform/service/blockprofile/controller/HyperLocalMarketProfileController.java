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


import com.platformcommons.platform.service.blockprofile.api.HyperLocalMarketProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HyperLocalMarketProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HyperLocalMarketProfileFacadeExt;


@RestController
@Slf4j
public class HyperLocalMarketProfileController implements HyperLocalMarketProfileControllerApi {

    private final HyperLocalMarketProfileFacadeExt hyperLocalMarketProfileFacadeExt;

    /**
     * Constructs a HyperLocalMarketProfileController with the specified facade.
     *
     * @param hyperLocalMarketProfileFacadeExt The HyperLocalMarketProfile facade extension to be used
     */
    public HyperLocalMarketProfileController(HyperLocalMarketProfileFacadeExt hyperLocalMarketProfileFacadeExt) {
        this.hyperLocalMarketProfileFacadeExt =hyperLocalMarketProfileFacadeExt;
    }

    /**
     * Creates a new HyperLocalMarketProfile.
     *
     * @param hyperLocalMarketProfileDTO The HyperLocalMarketProfile data to create
     * @return ResponseEntity containing the ID of the created HyperLocalMarketProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HyperLocalMarketProfileDTO hyperLocalMarketProfileDTO) {
        log.debug("Entry - create(HyperLocalMarketProfileDTO={})" , hyperLocalMarketProfileDTO);
        Long id = hyperLocalMarketProfileFacadeExt.save(hyperLocalMarketProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHyperLocalMarketProfileUri(id)).body(id);
    }

    /**
     * Updates an existing HyperLocalMarketProfile.
     *
     * @param hyperLocalMarketProfileDTO The HyperLocalMarketProfile data to update
     * @return ResponseEntity containing the updated HyperLocalMarketProfile data
     */
    @Override
    public ResponseEntity<HyperLocalMarketProfileDTO> update(@RequestBody HyperLocalMarketProfileDTO hyperLocalMarketProfileDTO) {
        log.debug("Entry - update(HyperLocalMarketProfileDTO={})", hyperLocalMarketProfileDTO);
        HyperLocalMarketProfileDTO updated = hyperLocalMarketProfileFacadeExt.update(hyperLocalMarketProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HyperLocalMarketProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HyperLocalMarketProfiles
     */
    @Override
    public ResponseEntity<PageDTO<HyperLocalMarketProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HyperLocalMarketProfileDTO> result = hyperLocalMarketProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HyperLocalMarketProfile by their ID.
     *
     * @param id The ID of the HyperLocalMarketProfile to retrieve
     * @return ResponseEntity containing the HyperLocalMarketProfile data
     */
    @Override
    public ResponseEntity<HyperLocalMarketProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HyperLocalMarketProfileDTO dto = hyperLocalMarketProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HyperLocalMarketProfile by their ID with an optional reason.
     *
     * @param id     The ID of the HyperLocalMarketProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hyperLocalMarketProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HyperLocalMarketProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HyperLocalMarketProfileDTO> result = hyperLocalMarketProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHyperLocalMarketProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
