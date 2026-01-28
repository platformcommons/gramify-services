package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.SeedsInDemandFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.SeedsInDemandProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.SeedsInDemandServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class SeedsInDemandFacadeImpl implements SeedsInDemandFacade {

    private final SeedsInDemandServiceExt serviceExt;
    private final SeedsInDemandProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String SEEDSINDEMAND_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEEDSINDEMAND.CREATE";
    private static final String SEEDSINDEMAND_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEEDSINDEMAND.UPDATED";
    private static final String SEEDSINDEMAND_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEEDSINDEMAND.DELETE";
    private static final String SEEDSINDEMAND_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEEDSINDEMAND.GET";

    public SeedsInDemandFacadeImpl(SeedsInDemandServiceExt serviceExt, SeedsInDemandProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new SeedsInDemand entry in the system.
     *
     * @param SeedsInDemandDTO The SeedsInDemand information to be saved
     * @return The saved SeedsInDemand data
     */
    @Override
    public SeedsInDemandDTO save(SeedsInDemandDTO SeedsInDemandDTO) {
        log.debug("Entry - save(SeedsInDemandDTO={})", SeedsInDemandDTO);
        evaluator.evaluate(SEEDSINDEMAND_CREATE, new HashMap<>());
        SeedsInDemandDTO = preHookSave(SeedsInDemandDTO);
        SeedsInDemandDTO dto = serviceExt.save(SeedsInDemandDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing SeedsInDemand entry.
     *
     * @param SeedsInDemandDTO The SeedsInDemand information to be updated
     * @return The updated SeedsInDemand data
     */
    @Override
    public SeedsInDemandDTO update(SeedsInDemandDTO SeedsInDemandDTO) {
        log.debug("Entry - update(SeedsInDemandDTO={})", SeedsInDemandDTO);
        evaluator.evaluate(SEEDSINDEMAND_UPDATE, new HashMap<>());
        SeedsInDemandDTO = preHookUpdate(SeedsInDemandDTO);
        SeedsInDemandDTO dto = serviceExt.update(SeedsInDemandDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of SeedsInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SeedsInDemands
     */
    @Override
    public PageDTO<SeedsInDemandDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(SEEDSINDEMAND_GET, new HashMap<>());
        PageDTO<SeedsInDemandDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a SeedsInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the SeedsInDemand to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(SEEDSINDEMAND_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        SeedsInDemandDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a SeedsInDemand by their ID.
     *
     * @param id The ID of the SeedsInDemand to retrieve
     * @return The SeedsInDemand with the specified ID
     */
    @Override
    public SeedsInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(SEEDSINDEMAND_GET, new HashMap<>());
        SeedsInDemandDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all SeedsInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SeedsInDemandDTO
     */
    @Override
    public Set<SeedsInDemandDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(SEEDSINDEMAND_GET, new HashMap<>());
        Set<SeedsInDemandDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SeedsInDemandDTO postHookSave(SeedsInDemandDTO dto) {
        return dto;
    }

    protected SeedsInDemandDTO preHookSave(SeedsInDemandDTO dto) {
        return dto;
    }

    protected SeedsInDemandDTO postHookUpdate(SeedsInDemandDTO dto) {
        return dto;
    }

    protected SeedsInDemandDTO preHookUpdate(SeedsInDemandDTO SeedsInDemandDTO) {
        return SeedsInDemandDTO;
    }

    protected SeedsInDemandDTO postHookDelete(SeedsInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SeedsInDemandDTO postHookGetById(SeedsInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<SeedsInDemandDTO> postHookGetAll(PageDTO<SeedsInDemandDTO> result) {
        return result;
    }
}
