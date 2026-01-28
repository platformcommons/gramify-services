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


import com.platformcommons.platform.service.blockprofile.api.SchemeImplementationChallengeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.SchemeImplementationChallengeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.SchemeImplementationChallengeFacadeExt;


@RestController
@Slf4j
public class SchemeImplementationChallengeController implements SchemeImplementationChallengeControllerApi {

    private final SchemeImplementationChallengeFacadeExt schemeImplementationChallengeFacadeExt;

    /**
     * Constructs a SchemeImplementationChallengeController with the specified facade.
     *
     * @param schemeImplementationChallengeFacadeExt The SchemeImplementationChallenge facade extension to be used
     */
    public SchemeImplementationChallengeController(SchemeImplementationChallengeFacadeExt schemeImplementationChallengeFacadeExt) {
        this.schemeImplementationChallengeFacadeExt =schemeImplementationChallengeFacadeExt;
    }

    /**
     * Creates a new SchemeImplementationChallenge.
     *
     * @param schemeImplementationChallengeDTO The SchemeImplementationChallenge data to create
     * @return ResponseEntity containing the ID of the created SchemeImplementationChallenge
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody SchemeImplementationChallengeDTO schemeImplementationChallengeDTO) {
        log.debug("Entry - create(SchemeImplementationChallengeDTO={})" , schemeImplementationChallengeDTO);
        Long id = schemeImplementationChallengeFacadeExt.save(schemeImplementationChallengeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createSchemeImplementationChallengeUri(id)).body(id);
    }

    /**
     * Updates an existing SchemeImplementationChallenge.
     *
     * @param schemeImplementationChallengeDTO The SchemeImplementationChallenge data to update
     * @return ResponseEntity containing the updated SchemeImplementationChallenge data
     */
    @Override
    public ResponseEntity<SchemeImplementationChallengeDTO> update(@RequestBody SchemeImplementationChallengeDTO schemeImplementationChallengeDTO) {
        log.debug("Entry - update(SchemeImplementationChallengeDTO={})", schemeImplementationChallengeDTO);
        SchemeImplementationChallengeDTO updated = schemeImplementationChallengeFacadeExt.update(schemeImplementationChallengeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of SchemeImplementationChallenges.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of SchemeImplementationChallenges
     */
    @Override
    public ResponseEntity<PageDTO<SchemeImplementationChallengeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<SchemeImplementationChallengeDTO> result = schemeImplementationChallengeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a SchemeImplementationChallenge by their ID.
     *
     * @param id The ID of the SchemeImplementationChallenge to retrieve
     * @return ResponseEntity containing the SchemeImplementationChallenge data
     */
    @Override
    public ResponseEntity<SchemeImplementationChallengeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        SchemeImplementationChallengeDTO dto = schemeImplementationChallengeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a SchemeImplementationChallenge by their ID with an optional reason.
     *
     * @param id     The ID of the SchemeImplementationChallenge to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        schemeImplementationChallengeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<SchemeImplementationChallengeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<SchemeImplementationChallengeDTO> result = schemeImplementationChallengeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createSchemeImplementationChallengeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
