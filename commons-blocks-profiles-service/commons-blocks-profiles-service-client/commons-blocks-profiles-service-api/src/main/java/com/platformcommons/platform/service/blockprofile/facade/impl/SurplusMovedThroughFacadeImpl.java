package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.SurplusMovedThroughFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.SurplusMovedThroughProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.SurplusMovedThroughServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class SurplusMovedThroughFacadeImpl implements SurplusMovedThroughFacade {

    private final SurplusMovedThroughServiceExt serviceExt;
    private final SurplusMovedThroughProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String SURPLUSMOVEDTHROUGH_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSMOVEDTHROUGH.CREATE";
    private static final String SURPLUSMOVEDTHROUGH_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSMOVEDTHROUGH.UPDATED";
    private static final String SURPLUSMOVEDTHROUGH_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSMOVEDTHROUGH.DELETE";
    private static final String SURPLUSMOVEDTHROUGH_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSMOVEDTHROUGH.GET";

    public SurplusMovedThroughFacadeImpl(SurplusMovedThroughServiceExt serviceExt, SurplusMovedThroughProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new SurplusMovedThrough entry in the system.
     *
     * @param SurplusMovedThroughDTO The SurplusMovedThrough information to be saved
     * @return The saved SurplusMovedThrough data
     */
    @Override
    public SurplusMovedThroughDTO save(SurplusMovedThroughDTO SurplusMovedThroughDTO) {
        log.debug("Entry - save(SurplusMovedThroughDTO={})", SurplusMovedThroughDTO);
        evaluator.evaluate(SURPLUSMOVEDTHROUGH_CREATE, new HashMap<>());
        SurplusMovedThroughDTO = preHookSave(SurplusMovedThroughDTO);
        SurplusMovedThroughDTO dto = serviceExt.save(SurplusMovedThroughDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing SurplusMovedThrough entry.
     *
     * @param SurplusMovedThroughDTO The SurplusMovedThrough information to be updated
     * @return The updated SurplusMovedThrough data
     */
    @Override
    public SurplusMovedThroughDTO update(SurplusMovedThroughDTO SurplusMovedThroughDTO) {
        log.debug("Entry - update(SurplusMovedThroughDTO={})", SurplusMovedThroughDTO);
        evaluator.evaluate(SURPLUSMOVEDTHROUGH_UPDATE, new HashMap<>());
        SurplusMovedThroughDTO = preHookUpdate(SurplusMovedThroughDTO);
        SurplusMovedThroughDTO dto = serviceExt.update(SurplusMovedThroughDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of SurplusMovedThroughs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SurplusMovedThroughs
     */
    @Override
    public PageDTO<SurplusMovedThroughDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(SURPLUSMOVEDTHROUGH_GET, new HashMap<>());
        PageDTO<SurplusMovedThroughDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a SurplusMovedThrough by their ID with a specified reason.
     *
     * @param id     The ID of the SurplusMovedThrough to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(SURPLUSMOVEDTHROUGH_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        SurplusMovedThroughDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a SurplusMovedThrough by their ID.
     *
     * @param id The ID of the SurplusMovedThrough to retrieve
     * @return The SurplusMovedThrough with the specified ID
     */
    @Override
    public SurplusMovedThroughDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(SURPLUSMOVEDTHROUGH_GET, new HashMap<>());
        SurplusMovedThroughDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all SurplusMovedThroughs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SurplusMovedThroughDTO
     */
    @Override
    public Set<SurplusMovedThroughDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(SURPLUSMOVEDTHROUGH_GET, new HashMap<>());
        Set<SurplusMovedThroughDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SurplusMovedThroughDTO postHookSave(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected SurplusMovedThroughDTO preHookSave(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected SurplusMovedThroughDTO postHookUpdate(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected SurplusMovedThroughDTO preHookUpdate(SurplusMovedThroughDTO SurplusMovedThroughDTO) {
        return SurplusMovedThroughDTO;
    }

    protected SurplusMovedThroughDTO postHookDelete(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SurplusMovedThroughDTO postHookGetById(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected PageDTO<SurplusMovedThroughDTO> postHookGetAll(PageDTO<SurplusMovedThroughDTO> result) {
        return result;
    }
}
