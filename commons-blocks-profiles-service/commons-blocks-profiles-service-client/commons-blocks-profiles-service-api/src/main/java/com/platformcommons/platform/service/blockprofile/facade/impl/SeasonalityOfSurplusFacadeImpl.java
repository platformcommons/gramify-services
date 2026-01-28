package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.SeasonalityOfSurplusFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.SeasonalityOfSurplusProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.SeasonalityOfSurplusServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class SeasonalityOfSurplusFacadeImpl implements SeasonalityOfSurplusFacade {

    private final SeasonalityOfSurplusServiceExt serviceExt;
    private final SeasonalityOfSurplusProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String SEASONALITYOFSURPLUS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEASONALITYOFSURPLUS.CREATE";
    private static final String SEASONALITYOFSURPLUS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEASONALITYOFSURPLUS.UPDATED";
    private static final String SEASONALITYOFSURPLUS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEASONALITYOFSURPLUS.DELETE";
    private static final String SEASONALITYOFSURPLUS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SEASONALITYOFSURPLUS.GET";

    public SeasonalityOfSurplusFacadeImpl(SeasonalityOfSurplusServiceExt serviceExt, SeasonalityOfSurplusProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new SeasonalityOfSurplus entry in the system.
     *
     * @param SeasonalityOfSurplusDTO The SeasonalityOfSurplus information to be saved
     * @return The saved SeasonalityOfSurplus data
     */
    @Override
    public SeasonalityOfSurplusDTO save(SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO) {
        log.debug("Entry - save(SeasonalityOfSurplusDTO={})", SeasonalityOfSurplusDTO);
        evaluator.evaluate(SEASONALITYOFSURPLUS_CREATE, new HashMap<>());
        SeasonalityOfSurplusDTO = preHookSave(SeasonalityOfSurplusDTO);
        SeasonalityOfSurplusDTO dto = serviceExt.save(SeasonalityOfSurplusDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing SeasonalityOfSurplus entry.
     *
     * @param SeasonalityOfSurplusDTO The SeasonalityOfSurplus information to be updated
     * @return The updated SeasonalityOfSurplus data
     */
    @Override
    public SeasonalityOfSurplusDTO update(SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO) {
        log.debug("Entry - update(SeasonalityOfSurplusDTO={})", SeasonalityOfSurplusDTO);
        evaluator.evaluate(SEASONALITYOFSURPLUS_UPDATE, new HashMap<>());
        SeasonalityOfSurplusDTO = preHookUpdate(SeasonalityOfSurplusDTO);
        SeasonalityOfSurplusDTO dto = serviceExt.update(SeasonalityOfSurplusDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of SeasonalityOfSurpluss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SeasonalityOfSurpluss
     */
    @Override
    public PageDTO<SeasonalityOfSurplusDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(SEASONALITYOFSURPLUS_GET, new HashMap<>());
        PageDTO<SeasonalityOfSurplusDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a SeasonalityOfSurplus by their ID with a specified reason.
     *
     * @param id     The ID of the SeasonalityOfSurplus to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(SEASONALITYOFSURPLUS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        SeasonalityOfSurplusDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a SeasonalityOfSurplus by their ID.
     *
     * @param id The ID of the SeasonalityOfSurplus to retrieve
     * @return The SeasonalityOfSurplus with the specified ID
     */
    @Override
    public SeasonalityOfSurplusDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(SEASONALITYOFSURPLUS_GET, new HashMap<>());
        SeasonalityOfSurplusDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all SeasonalityOfSurpluss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SeasonalityOfSurplusDTO
     */
    @Override
    public Set<SeasonalityOfSurplusDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(SEASONALITYOFSURPLUS_GET, new HashMap<>());
        Set<SeasonalityOfSurplusDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SeasonalityOfSurplusDTO postHookSave(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected SeasonalityOfSurplusDTO preHookSave(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected SeasonalityOfSurplusDTO postHookUpdate(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected SeasonalityOfSurplusDTO preHookUpdate(SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO) {
        return SeasonalityOfSurplusDTO;
    }

    protected SeasonalityOfSurplusDTO postHookDelete(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SeasonalityOfSurplusDTO postHookGetById(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected PageDTO<SeasonalityOfSurplusDTO> postHookGetAll(PageDTO<SeasonalityOfSurplusDTO> result) {
        return result;
    }
}
