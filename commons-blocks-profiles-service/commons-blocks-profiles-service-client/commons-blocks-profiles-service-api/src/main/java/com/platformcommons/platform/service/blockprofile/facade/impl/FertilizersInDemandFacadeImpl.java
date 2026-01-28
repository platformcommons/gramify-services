package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.FertilizersInDemandFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.FertilizersInDemandProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.FertilizersInDemandServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class FertilizersInDemandFacadeImpl implements FertilizersInDemandFacade {

    private final FertilizersInDemandServiceExt serviceExt;
    private final FertilizersInDemandProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String FERTILIZERSINDEMAND_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.FERTILIZERSINDEMAND.CREATE";
    private static final String FERTILIZERSINDEMAND_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.FERTILIZERSINDEMAND.UPDATED";
    private static final String FERTILIZERSINDEMAND_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.FERTILIZERSINDEMAND.DELETE";
    private static final String FERTILIZERSINDEMAND_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.FERTILIZERSINDEMAND.GET";

    public FertilizersInDemandFacadeImpl(FertilizersInDemandServiceExt serviceExt, FertilizersInDemandProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new FertilizersInDemand entry in the system.
     *
     * @param FertilizersInDemandDTO The FertilizersInDemand information to be saved
     * @return The saved FertilizersInDemand data
     */
    @Override
    public FertilizersInDemandDTO save(FertilizersInDemandDTO FertilizersInDemandDTO) {
        log.debug("Entry - save(FertilizersInDemandDTO={})", FertilizersInDemandDTO);
        evaluator.evaluate(FERTILIZERSINDEMAND_CREATE, new HashMap<>());
        FertilizersInDemandDTO = preHookSave(FertilizersInDemandDTO);
        FertilizersInDemandDTO dto = serviceExt.save(FertilizersInDemandDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing FertilizersInDemand entry.
     *
     * @param FertilizersInDemandDTO The FertilizersInDemand information to be updated
     * @return The updated FertilizersInDemand data
     */
    @Override
    public FertilizersInDemandDTO update(FertilizersInDemandDTO FertilizersInDemandDTO) {
        log.debug("Entry - update(FertilizersInDemandDTO={})", FertilizersInDemandDTO);
        evaluator.evaluate(FERTILIZERSINDEMAND_UPDATE, new HashMap<>());
        FertilizersInDemandDTO = preHookUpdate(FertilizersInDemandDTO);
        FertilizersInDemandDTO dto = serviceExt.update(FertilizersInDemandDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of FertilizersInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of FertilizersInDemands
     */
    @Override
    public PageDTO<FertilizersInDemandDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(FERTILIZERSINDEMAND_GET, new HashMap<>());
        PageDTO<FertilizersInDemandDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a FertilizersInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the FertilizersInDemand to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(FERTILIZERSINDEMAND_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        FertilizersInDemandDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a FertilizersInDemand by their ID.
     *
     * @param id The ID of the FertilizersInDemand to retrieve
     * @return The FertilizersInDemand with the specified ID
     */
    @Override
    public FertilizersInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(FERTILIZERSINDEMAND_GET, new HashMap<>());
        FertilizersInDemandDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all FertilizersInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of FertilizersInDemandDTO
     */
    @Override
    public Set<FertilizersInDemandDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(FERTILIZERSINDEMAND_GET, new HashMap<>());
        Set<FertilizersInDemandDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected FertilizersInDemandDTO postHookSave(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected FertilizersInDemandDTO preHookSave(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected FertilizersInDemandDTO postHookUpdate(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected FertilizersInDemandDTO preHookUpdate(FertilizersInDemandDTO FertilizersInDemandDTO) {
        return FertilizersInDemandDTO;
    }

    protected FertilizersInDemandDTO postHookDelete(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected FertilizersInDemandDTO postHookGetById(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<FertilizersInDemandDTO> postHookGetAll(PageDTO<FertilizersInDemandDTO> result) {
        return result;
    }
}
