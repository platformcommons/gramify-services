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


import com.platformcommons.platform.service.blockprofile.api.OtherCommunityGroupControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.OtherCommunityGroupDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.OtherCommunityGroupFacadeExt;


@RestController
@Slf4j
public class OtherCommunityGroupController implements OtherCommunityGroupControllerApi {

    private final OtherCommunityGroupFacadeExt otherCommunityGroupFacadeExt;

    /**
     * Constructs a OtherCommunityGroupController with the specified facade.
     *
     * @param otherCommunityGroupFacadeExt The OtherCommunityGroup facade extension to be used
     */
    public OtherCommunityGroupController(OtherCommunityGroupFacadeExt otherCommunityGroupFacadeExt) {
        this.otherCommunityGroupFacadeExt =otherCommunityGroupFacadeExt;
    }

    /**
     * Creates a new OtherCommunityGroup.
     *
     * @param otherCommunityGroupDTO The OtherCommunityGroup data to create
     * @return ResponseEntity containing the ID of the created OtherCommunityGroup
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody OtherCommunityGroupDTO otherCommunityGroupDTO) {
        log.debug("Entry - create(OtherCommunityGroupDTO={})" , otherCommunityGroupDTO);
        Long id = otherCommunityGroupFacadeExt.save(otherCommunityGroupDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createOtherCommunityGroupUri(id)).body(id);
    }

    /**
     * Updates an existing OtherCommunityGroup.
     *
     * @param otherCommunityGroupDTO The OtherCommunityGroup data to update
     * @return ResponseEntity containing the updated OtherCommunityGroup data
     */
    @Override
    public ResponseEntity<OtherCommunityGroupDTO> update(@RequestBody OtherCommunityGroupDTO otherCommunityGroupDTO) {
        log.debug("Entry - update(OtherCommunityGroupDTO={})", otherCommunityGroupDTO);
        OtherCommunityGroupDTO updated = otherCommunityGroupFacadeExt.update(otherCommunityGroupDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of OtherCommunityGroups.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of OtherCommunityGroups
     */
    @Override
    public ResponseEntity<PageDTO<OtherCommunityGroupDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<OtherCommunityGroupDTO> result = otherCommunityGroupFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a OtherCommunityGroup by their ID.
     *
     * @param id The ID of the OtherCommunityGroup to retrieve
     * @return ResponseEntity containing the OtherCommunityGroup data
     */
    @Override
    public ResponseEntity<OtherCommunityGroupDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        OtherCommunityGroupDTO dto = otherCommunityGroupFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a OtherCommunityGroup by their ID with an optional reason.
     *
     * @param id     The ID of the OtherCommunityGroup to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        otherCommunityGroupFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<OtherCommunityGroupDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<OtherCommunityGroupDTO> result = otherCommunityGroupFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createOtherCommunityGroupUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
