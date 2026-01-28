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


import com.platformcommons.platform.service.blockprofile.api.OtherIndustryTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.OtherIndustryTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.OtherIndustryTypeFacadeExt;


@RestController
@Slf4j
public class OtherIndustryTypeController implements OtherIndustryTypeControllerApi {

    private final OtherIndustryTypeFacadeExt otherIndustryTypeFacadeExt;

    /**
     * Constructs a OtherIndustryTypeController with the specified facade.
     *
     * @param otherIndustryTypeFacadeExt The OtherIndustryType facade extension to be used
     */
    public OtherIndustryTypeController(OtherIndustryTypeFacadeExt otherIndustryTypeFacadeExt) {
        this.otherIndustryTypeFacadeExt =otherIndustryTypeFacadeExt;
    }

    /**
     * Creates a new OtherIndustryType.
     *
     * @param otherIndustryTypeDTO The OtherIndustryType data to create
     * @return ResponseEntity containing the ID of the created OtherIndustryType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody OtherIndustryTypeDTO otherIndustryTypeDTO) {
        log.debug("Entry - create(OtherIndustryTypeDTO={})" , otherIndustryTypeDTO);
        Long id = otherIndustryTypeFacadeExt.save(otherIndustryTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createOtherIndustryTypeUri(id)).body(id);
    }

    /**
     * Updates an existing OtherIndustryType.
     *
     * @param otherIndustryTypeDTO The OtherIndustryType data to update
     * @return ResponseEntity containing the updated OtherIndustryType data
     */
    @Override
    public ResponseEntity<OtherIndustryTypeDTO> update(@RequestBody OtherIndustryTypeDTO otherIndustryTypeDTO) {
        log.debug("Entry - update(OtherIndustryTypeDTO={})", otherIndustryTypeDTO);
        OtherIndustryTypeDTO updated = otherIndustryTypeFacadeExt.update(otherIndustryTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of OtherIndustryTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of OtherIndustryTypes
     */
    @Override
    public ResponseEntity<PageDTO<OtherIndustryTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<OtherIndustryTypeDTO> result = otherIndustryTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a OtherIndustryType by their ID.
     *
     * @param id The ID of the OtherIndustryType to retrieve
     * @return ResponseEntity containing the OtherIndustryType data
     */
    @Override
    public ResponseEntity<OtherIndustryTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        OtherIndustryTypeDTO dto = otherIndustryTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a OtherIndustryType by their ID with an optional reason.
     *
     * @param id     The ID of the OtherIndustryType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        otherIndustryTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<OtherIndustryTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<OtherIndustryTypeDTO> result = otherIndustryTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createOtherIndustryTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
