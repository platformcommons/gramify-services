package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.CurrentStorageMethodFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.CurrentStorageMethodProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.CurrentStorageMethodServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class CurrentStorageMethodFacadeImpl implements CurrentStorageMethodFacade {

    private final CurrentStorageMethodServiceExt serviceExt;
    private final CurrentStorageMethodProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String CURRENTSTORAGEMETHOD_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CURRENTSTORAGEMETHOD.CREATE";
    private static final String CURRENTSTORAGEMETHOD_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CURRENTSTORAGEMETHOD.UPDATED";
    private static final String CURRENTSTORAGEMETHOD_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CURRENTSTORAGEMETHOD.DELETE";
    private static final String CURRENTSTORAGEMETHOD_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CURRENTSTORAGEMETHOD.GET";

    public CurrentStorageMethodFacadeImpl(CurrentStorageMethodServiceExt serviceExt, CurrentStorageMethodProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new CurrentStorageMethod entry in the system.
     *
     * @param CurrentStorageMethodDTO The CurrentStorageMethod information to be saved
     * @return The saved CurrentStorageMethod data
     */
    @Override
    public CurrentStorageMethodDTO save(CurrentStorageMethodDTO CurrentStorageMethodDTO) {
        log.debug("Entry - save(CurrentStorageMethodDTO={})", CurrentStorageMethodDTO);
        evaluator.evaluate(CURRENTSTORAGEMETHOD_CREATE, new HashMap<>());
        CurrentStorageMethodDTO = preHookSave(CurrentStorageMethodDTO);
        CurrentStorageMethodDTO dto = serviceExt.save(CurrentStorageMethodDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing CurrentStorageMethod entry.
     *
     * @param CurrentStorageMethodDTO The CurrentStorageMethod information to be updated
     * @return The updated CurrentStorageMethod data
     */
    @Override
    public CurrentStorageMethodDTO update(CurrentStorageMethodDTO CurrentStorageMethodDTO) {
        log.debug("Entry - update(CurrentStorageMethodDTO={})", CurrentStorageMethodDTO);
        evaluator.evaluate(CURRENTSTORAGEMETHOD_UPDATE, new HashMap<>());
        CurrentStorageMethodDTO = preHookUpdate(CurrentStorageMethodDTO);
        CurrentStorageMethodDTO dto = serviceExt.update(CurrentStorageMethodDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of CurrentStorageMethods.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CurrentStorageMethods
     */
    @Override
    public PageDTO<CurrentStorageMethodDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(CURRENTSTORAGEMETHOD_GET, new HashMap<>());
        PageDTO<CurrentStorageMethodDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a CurrentStorageMethod by their ID with a specified reason.
     *
     * @param id     The ID of the CurrentStorageMethod to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(CURRENTSTORAGEMETHOD_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        CurrentStorageMethodDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a CurrentStorageMethod by their ID.
     *
     * @param id The ID of the CurrentStorageMethod to retrieve
     * @return The CurrentStorageMethod with the specified ID
     */
    @Override
    public CurrentStorageMethodDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(CURRENTSTORAGEMETHOD_GET, new HashMap<>());
        CurrentStorageMethodDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all CurrentStorageMethods by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CurrentStorageMethodDTO
     */
    @Override
    public Set<CurrentStorageMethodDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(CURRENTSTORAGEMETHOD_GET, new HashMap<>());
        Set<CurrentStorageMethodDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CurrentStorageMethodDTO postHookSave(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected CurrentStorageMethodDTO preHookSave(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected CurrentStorageMethodDTO postHookUpdate(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected CurrentStorageMethodDTO preHookUpdate(CurrentStorageMethodDTO CurrentStorageMethodDTO) {
        return CurrentStorageMethodDTO;
    }

    protected CurrentStorageMethodDTO postHookDelete(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CurrentStorageMethodDTO postHookGetById(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected PageDTO<CurrentStorageMethodDTO> postHookGetAll(PageDTO<CurrentStorageMethodDTO> result) {
        return result;
    }
}
