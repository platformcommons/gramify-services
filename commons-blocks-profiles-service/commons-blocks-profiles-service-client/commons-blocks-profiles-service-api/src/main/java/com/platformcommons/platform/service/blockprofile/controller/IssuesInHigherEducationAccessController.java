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


import com.platformcommons.platform.service.blockprofile.api.IssuesInHigherEducationAccessControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.IssuesInHigherEducationAccessDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.IssuesInHigherEducationAccessFacadeExt;


@RestController
@Slf4j
public class IssuesInHigherEducationAccessController implements IssuesInHigherEducationAccessControllerApi {

    private final IssuesInHigherEducationAccessFacadeExt issuesInHigherEducationAccessFacadeExt;

    /**
     * Constructs a IssuesInHigherEducationAccessController with the specified facade.
     *
     * @param issuesInHigherEducationAccessFacadeExt The IssuesInHigherEducationAccess facade extension to be used
     */
    public IssuesInHigherEducationAccessController(IssuesInHigherEducationAccessFacadeExt issuesInHigherEducationAccessFacadeExt) {
        this.issuesInHigherEducationAccessFacadeExt =issuesInHigherEducationAccessFacadeExt;
    }

    /**
     * Creates a new IssuesInHigherEducationAccess.
     *
     * @param issuesInHigherEducationAccessDTO The IssuesInHigherEducationAccess data to create
     * @return ResponseEntity containing the ID of the created IssuesInHigherEducationAccess
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody IssuesInHigherEducationAccessDTO issuesInHigherEducationAccessDTO) {
        log.debug("Entry - create(IssuesInHigherEducationAccessDTO={})" , issuesInHigherEducationAccessDTO);
        Long id = issuesInHigherEducationAccessFacadeExt.save(issuesInHigherEducationAccessDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createIssuesInHigherEducationAccessUri(id)).body(id);
    }

    /**
     * Updates an existing IssuesInHigherEducationAccess.
     *
     * @param issuesInHigherEducationAccessDTO The IssuesInHigherEducationAccess data to update
     * @return ResponseEntity containing the updated IssuesInHigherEducationAccess data
     */
    @Override
    public ResponseEntity<IssuesInHigherEducationAccessDTO> update(@RequestBody IssuesInHigherEducationAccessDTO issuesInHigherEducationAccessDTO) {
        log.debug("Entry - update(IssuesInHigherEducationAccessDTO={})", issuesInHigherEducationAccessDTO);
        IssuesInHigherEducationAccessDTO updated = issuesInHigherEducationAccessFacadeExt.update(issuesInHigherEducationAccessDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of IssuesInHigherEducationAccesss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of IssuesInHigherEducationAccesss
     */
    @Override
    public ResponseEntity<PageDTO<IssuesInHigherEducationAccessDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<IssuesInHigherEducationAccessDTO> result = issuesInHigherEducationAccessFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a IssuesInHigherEducationAccess by their ID.
     *
     * @param id The ID of the IssuesInHigherEducationAccess to retrieve
     * @return ResponseEntity containing the IssuesInHigherEducationAccess data
     */
    @Override
    public ResponseEntity<IssuesInHigherEducationAccessDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        IssuesInHigherEducationAccessDTO dto = issuesInHigherEducationAccessFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a IssuesInHigherEducationAccess by their ID with an optional reason.
     *
     * @param id     The ID of the IssuesInHigherEducationAccess to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        issuesInHigherEducationAccessFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<IssuesInHigherEducationAccessDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<IssuesInHigherEducationAccessDTO> result = issuesInHigherEducationAccessFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createIssuesInHigherEducationAccessUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
