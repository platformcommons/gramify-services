package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ConsumptionFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ConsumptionProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ConsumptionServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ConsumptionFacadeImpl implements ConsumptionFacade {

    private final ConsumptionServiceExt serviceExt;
    private final ConsumptionProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String CONSUMPTION_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CONSUMPTION.CREATE";
    private static final String CONSUMPTION_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CONSUMPTION.UPDATED";
    private static final String CONSUMPTION_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CONSUMPTION.DELETE";
    private static final String CONSUMPTION_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CONSUMPTION.GET";

    public ConsumptionFacadeImpl(ConsumptionServiceExt serviceExt, ConsumptionProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new Consumption entry in the system.
     *
     * @param ConsumptionDTO The Consumption information to be saved
     * @return The saved Consumption data
     */
    @Override
    public ConsumptionDTO save(ConsumptionDTO ConsumptionDTO) {
        log.debug("Entry - save(ConsumptionDTO={})", ConsumptionDTO);
        evaluator.evaluate(CONSUMPTION_CREATE, new HashMap<>());
        ConsumptionDTO = preHookSave(ConsumptionDTO);
        ConsumptionDTO dto = serviceExt.save(ConsumptionDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing Consumption entry.
     *
     * @param ConsumptionDTO The Consumption information to be updated
     * @return The updated Consumption data
     */
    @Override
    public ConsumptionDTO update(ConsumptionDTO ConsumptionDTO) {
        log.debug("Entry - update(ConsumptionDTO={})", ConsumptionDTO);
        evaluator.evaluate(CONSUMPTION_UPDATE, new HashMap<>());
        ConsumptionDTO = preHookUpdate(ConsumptionDTO);
        ConsumptionDTO dto = serviceExt.update(ConsumptionDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of Consumptions.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Consumptions
     */
    @Override
    public PageDTO<ConsumptionDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(CONSUMPTION_GET, new HashMap<>());
        PageDTO<ConsumptionDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a Consumption by their ID with a specified reason.
     *
     * @param id     The ID of the Consumption to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(CONSUMPTION_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ConsumptionDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a Consumption by their ID.
     *
     * @param id The ID of the Consumption to retrieve
     * @return The Consumption with the specified ID
     */
    @Override
    public ConsumptionDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(CONSUMPTION_GET, new HashMap<>());
        ConsumptionDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all Consumptions by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ConsumptionDTO
     */
    @Override
    public Set<ConsumptionDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(CONSUMPTION_GET, new HashMap<>());
        Set<ConsumptionDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ConsumptionDTO postHookSave(ConsumptionDTO dto) {
        return dto;
    }

    protected ConsumptionDTO preHookSave(ConsumptionDTO dto) {
        return dto;
    }

    protected ConsumptionDTO postHookUpdate(ConsumptionDTO dto) {
        return dto;
    }

    protected ConsumptionDTO preHookUpdate(ConsumptionDTO ConsumptionDTO) {
        return ConsumptionDTO;
    }

    protected ConsumptionDTO postHookDelete(ConsumptionDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ConsumptionDTO postHookGetById(ConsumptionDTO dto) {
        return dto;
    }

    protected PageDTO<ConsumptionDTO> postHookGetAll(PageDTO<ConsumptionDTO> result) {
        return result;
    }
}
