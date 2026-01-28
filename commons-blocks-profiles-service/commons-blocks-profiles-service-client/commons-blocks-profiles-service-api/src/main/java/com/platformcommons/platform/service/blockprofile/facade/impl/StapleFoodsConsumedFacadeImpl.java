package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.StapleFoodsConsumedFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.StapleFoodsConsumedProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.StapleFoodsConsumedServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class StapleFoodsConsumedFacadeImpl implements StapleFoodsConsumedFacade {

    private final StapleFoodsConsumedServiceExt serviceExt;
    private final StapleFoodsConsumedProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String STAPLEFOODSCONSUMED_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STAPLEFOODSCONSUMED.CREATE";
    private static final String STAPLEFOODSCONSUMED_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STAPLEFOODSCONSUMED.UPDATED";
    private static final String STAPLEFOODSCONSUMED_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STAPLEFOODSCONSUMED.DELETE";
    private static final String STAPLEFOODSCONSUMED_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STAPLEFOODSCONSUMED.GET";

    public StapleFoodsConsumedFacadeImpl(StapleFoodsConsumedServiceExt serviceExt, StapleFoodsConsumedProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new StapleFoodsConsumed entry in the system.
     *
     * @param StapleFoodsConsumedDTO The StapleFoodsConsumed information to be saved
     * @return The saved StapleFoodsConsumed data
     */
    @Override
    public StapleFoodsConsumedDTO save(StapleFoodsConsumedDTO StapleFoodsConsumedDTO) {
        log.debug("Entry - save(StapleFoodsConsumedDTO={})", StapleFoodsConsumedDTO);
        evaluator.evaluate(STAPLEFOODSCONSUMED_CREATE, new HashMap<>());
        StapleFoodsConsumedDTO = preHookSave(StapleFoodsConsumedDTO);
        StapleFoodsConsumedDTO dto = serviceExt.save(StapleFoodsConsumedDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing StapleFoodsConsumed entry.
     *
     * @param StapleFoodsConsumedDTO The StapleFoodsConsumed information to be updated
     * @return The updated StapleFoodsConsumed data
     */
    @Override
    public StapleFoodsConsumedDTO update(StapleFoodsConsumedDTO StapleFoodsConsumedDTO) {
        log.debug("Entry - update(StapleFoodsConsumedDTO={})", StapleFoodsConsumedDTO);
        evaluator.evaluate(STAPLEFOODSCONSUMED_UPDATE, new HashMap<>());
        StapleFoodsConsumedDTO = preHookUpdate(StapleFoodsConsumedDTO);
        StapleFoodsConsumedDTO dto = serviceExt.update(StapleFoodsConsumedDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of StapleFoodsConsumeds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of StapleFoodsConsumeds
     */
    @Override
    public PageDTO<StapleFoodsConsumedDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(STAPLEFOODSCONSUMED_GET, new HashMap<>());
        PageDTO<StapleFoodsConsumedDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a StapleFoodsConsumed by their ID with a specified reason.
     *
     * @param id     The ID of the StapleFoodsConsumed to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(STAPLEFOODSCONSUMED_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        StapleFoodsConsumedDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a StapleFoodsConsumed by their ID.
     *
     * @param id The ID of the StapleFoodsConsumed to retrieve
     * @return The StapleFoodsConsumed with the specified ID
     */
    @Override
    public StapleFoodsConsumedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(STAPLEFOODSCONSUMED_GET, new HashMap<>());
        StapleFoodsConsumedDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all StapleFoodsConsumeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of StapleFoodsConsumedDTO
     */
    @Override
    public Set<StapleFoodsConsumedDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(STAPLEFOODSCONSUMED_GET, new HashMap<>());
        Set<StapleFoodsConsumedDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected StapleFoodsConsumedDTO postHookSave(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected StapleFoodsConsumedDTO preHookSave(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected StapleFoodsConsumedDTO postHookUpdate(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected StapleFoodsConsumedDTO preHookUpdate(StapleFoodsConsumedDTO StapleFoodsConsumedDTO) {
        return StapleFoodsConsumedDTO;
    }

    protected StapleFoodsConsumedDTO postHookDelete(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected StapleFoodsConsumedDTO postHookGetById(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected PageDTO<StapleFoodsConsumedDTO> postHookGetAll(PageDTO<StapleFoodsConsumedDTO> result) {
        return result;
    }
}
