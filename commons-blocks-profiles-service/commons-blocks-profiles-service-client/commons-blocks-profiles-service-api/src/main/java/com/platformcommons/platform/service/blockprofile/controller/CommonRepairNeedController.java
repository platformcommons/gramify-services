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


import com.platformcommons.platform.service.blockprofile.api.CommonRepairNeedControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.CommonRepairNeedDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.CommonRepairNeedFacadeExt;


@RestController
@Slf4j
public class CommonRepairNeedController implements CommonRepairNeedControllerApi {

    private final CommonRepairNeedFacadeExt commonRepairNeedFacadeExt;

    /**
     * Constructs a CommonRepairNeedController with the specified facade.
     *
     * @param commonRepairNeedFacadeExt The CommonRepairNeed facade extension to be used
     */
    public CommonRepairNeedController(CommonRepairNeedFacadeExt commonRepairNeedFacadeExt) {
        this.commonRepairNeedFacadeExt =commonRepairNeedFacadeExt;
    }

    /**
     * Creates a new CommonRepairNeed.
     *
     * @param commonRepairNeedDTO The CommonRepairNeed data to create
     * @return ResponseEntity containing the ID of the created CommonRepairNeed
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody CommonRepairNeedDTO commonRepairNeedDTO) {
        log.debug("Entry - create(CommonRepairNeedDTO={})" , commonRepairNeedDTO);
        Long id = commonRepairNeedFacadeExt.save(commonRepairNeedDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createCommonRepairNeedUri(id)).body(id);
    }

    /**
     * Updates an existing CommonRepairNeed.
     *
     * @param commonRepairNeedDTO The CommonRepairNeed data to update
     * @return ResponseEntity containing the updated CommonRepairNeed data
     */
    @Override
    public ResponseEntity<CommonRepairNeedDTO> update(@RequestBody CommonRepairNeedDTO commonRepairNeedDTO) {
        log.debug("Entry - update(CommonRepairNeedDTO={})", commonRepairNeedDTO);
        CommonRepairNeedDTO updated = commonRepairNeedFacadeExt.update(commonRepairNeedDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of CommonRepairNeeds.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of CommonRepairNeeds
     */
    @Override
    public ResponseEntity<PageDTO<CommonRepairNeedDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<CommonRepairNeedDTO> result = commonRepairNeedFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a CommonRepairNeed by their ID.
     *
     * @param id The ID of the CommonRepairNeed to retrieve
     * @return ResponseEntity containing the CommonRepairNeed data
     */
    @Override
    public ResponseEntity<CommonRepairNeedDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        CommonRepairNeedDTO dto = commonRepairNeedFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a CommonRepairNeed by their ID with an optional reason.
     *
     * @param id     The ID of the CommonRepairNeed to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        commonRepairNeedFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<CommonRepairNeedDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<CommonRepairNeedDTO> result = commonRepairNeedFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createCommonRepairNeedUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
