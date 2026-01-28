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


import com.platformcommons.platform.service.blockprofile.api.BlockVillageDemographicsProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.BlockVillageDemographicsProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.BlockVillageDemographicsProfileFacadeExt;


@RestController
@Slf4j
public class BlockVillageDemographicsProfileController implements BlockVillageDemographicsProfileControllerApi {

    private final BlockVillageDemographicsProfileFacadeExt blockVillageDemographicsProfileFacadeExt;

    /**
     * Constructs a BlockVillageDemographicsProfileController with the specified facade.
     *
     * @param blockVillageDemographicsProfileFacadeExt The BlockVillageDemographicsProfile facade extension to be used
     */
    public BlockVillageDemographicsProfileController(BlockVillageDemographicsProfileFacadeExt blockVillageDemographicsProfileFacadeExt) {
        this.blockVillageDemographicsProfileFacadeExt =blockVillageDemographicsProfileFacadeExt;
    }

    /**
     * Creates a new BlockVillageDemographicsProfile.
     *
     * @param blockVillageDemographicsProfileDTO The BlockVillageDemographicsProfile data to create
     * @return ResponseEntity containing the ID of the created BlockVillageDemographicsProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody BlockVillageDemographicsProfileDTO blockVillageDemographicsProfileDTO) {
        log.debug("Entry - create(BlockVillageDemographicsProfileDTO={})" , blockVillageDemographicsProfileDTO);
        Long id = blockVillageDemographicsProfileFacadeExt.save(blockVillageDemographicsProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createBlockVillageDemographicsProfileUri(id)).body(id);
    }

    /**
     * Updates an existing BlockVillageDemographicsProfile.
     *
     * @param blockVillageDemographicsProfileDTO The BlockVillageDemographicsProfile data to update
     * @return ResponseEntity containing the updated BlockVillageDemographicsProfile data
     */
    @Override
    public ResponseEntity<BlockVillageDemographicsProfileDTO> update(@RequestBody BlockVillageDemographicsProfileDTO blockVillageDemographicsProfileDTO) {
        log.debug("Entry - update(BlockVillageDemographicsProfileDTO={})", blockVillageDemographicsProfileDTO);
        BlockVillageDemographicsProfileDTO updated = blockVillageDemographicsProfileFacadeExt.update(blockVillageDemographicsProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of BlockVillageDemographicsProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of BlockVillageDemographicsProfiles
     */
    @Override
    public ResponseEntity<PageDTO<BlockVillageDemographicsProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<BlockVillageDemographicsProfileDTO> result = blockVillageDemographicsProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a BlockVillageDemographicsProfile by their ID.
     *
     * @param id The ID of the BlockVillageDemographicsProfile to retrieve
     * @return ResponseEntity containing the BlockVillageDemographicsProfile data
     */
    @Override
    public ResponseEntity<BlockVillageDemographicsProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        BlockVillageDemographicsProfileDTO dto = blockVillageDemographicsProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a BlockVillageDemographicsProfile by their ID with an optional reason.
     *
     * @param id     The ID of the BlockVillageDemographicsProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        blockVillageDemographicsProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<BlockVillageDemographicsProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<BlockVillageDemographicsProfileDTO> result = blockVillageDemographicsProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createBlockVillageDemographicsProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
