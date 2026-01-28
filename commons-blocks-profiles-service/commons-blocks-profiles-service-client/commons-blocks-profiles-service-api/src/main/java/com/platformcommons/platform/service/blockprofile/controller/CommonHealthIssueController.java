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


import com.platformcommons.platform.service.blockprofile.api.CommonHealthIssueControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.CommonHealthIssueDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.CommonHealthIssueFacadeExt;


@RestController
@Slf4j
public class CommonHealthIssueController implements CommonHealthIssueControllerApi {

    private final CommonHealthIssueFacadeExt commonHealthIssueFacadeExt;

    /**
     * Constructs a CommonHealthIssueController with the specified facade.
     *
     * @param commonHealthIssueFacadeExt The CommonHealthIssue facade extension to be used
     */
    public CommonHealthIssueController(CommonHealthIssueFacadeExt commonHealthIssueFacadeExt) {
        this.commonHealthIssueFacadeExt =commonHealthIssueFacadeExt;
    }

    /**
     * Creates a new CommonHealthIssue.
     *
     * @param commonHealthIssueDTO The CommonHealthIssue data to create
     * @return ResponseEntity containing the ID of the created CommonHealthIssue
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody CommonHealthIssueDTO commonHealthIssueDTO) {
        log.debug("Entry - create(CommonHealthIssueDTO={})" , commonHealthIssueDTO);
        Long id = commonHealthIssueFacadeExt.save(commonHealthIssueDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createCommonHealthIssueUri(id)).body(id);
    }

    /**
     * Updates an existing CommonHealthIssue.
     *
     * @param commonHealthIssueDTO The CommonHealthIssue data to update
     * @return ResponseEntity containing the updated CommonHealthIssue data
     */
    @Override
    public ResponseEntity<CommonHealthIssueDTO> update(@RequestBody CommonHealthIssueDTO commonHealthIssueDTO) {
        log.debug("Entry - update(CommonHealthIssueDTO={})", commonHealthIssueDTO);
        CommonHealthIssueDTO updated = commonHealthIssueFacadeExt.update(commonHealthIssueDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of CommonHealthIssues.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of CommonHealthIssues
     */
    @Override
    public ResponseEntity<PageDTO<CommonHealthIssueDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<CommonHealthIssueDTO> result = commonHealthIssueFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a CommonHealthIssue by their ID.
     *
     * @param id The ID of the CommonHealthIssue to retrieve
     * @return ResponseEntity containing the CommonHealthIssue data
     */
    @Override
    public ResponseEntity<CommonHealthIssueDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        CommonHealthIssueDTO dto = commonHealthIssueFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a CommonHealthIssue by their ID with an optional reason.
     *
     * @param id     The ID of the CommonHealthIssue to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        commonHealthIssueFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<CommonHealthIssueDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<CommonHealthIssueDTO> result = commonHealthIssueFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createCommonHealthIssueUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
