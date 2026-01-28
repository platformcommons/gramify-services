package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.PowerCutSeasonFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.PowerCutSeasonProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.PowerCutSeasonServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class PowerCutSeasonFacadeImpl implements PowerCutSeasonFacade {

    private final PowerCutSeasonServiceExt serviceExt;
    private final PowerCutSeasonProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String POWERCUTSEASON_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.POWERCUTSEASON.CREATE";
    private static final String POWERCUTSEASON_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.POWERCUTSEASON.UPDATED";
    private static final String POWERCUTSEASON_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.POWERCUTSEASON.DELETE";
    private static final String POWERCUTSEASON_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.POWERCUTSEASON.GET";

    public PowerCutSeasonFacadeImpl(PowerCutSeasonServiceExt serviceExt, PowerCutSeasonProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new PowerCutSeason entry in the system.
     *
     * @param PowerCutSeasonDTO The PowerCutSeason information to be saved
     * @return The saved PowerCutSeason data
     */
    @Override
    public PowerCutSeasonDTO save(PowerCutSeasonDTO PowerCutSeasonDTO) {
        log.debug("Entry - save(PowerCutSeasonDTO={})", PowerCutSeasonDTO);
        evaluator.evaluate(POWERCUTSEASON_CREATE, new HashMap<>());
        PowerCutSeasonDTO = preHookSave(PowerCutSeasonDTO);
        PowerCutSeasonDTO dto = serviceExt.save(PowerCutSeasonDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing PowerCutSeason entry.
     *
     * @param PowerCutSeasonDTO The PowerCutSeason information to be updated
     * @return The updated PowerCutSeason data
     */
    @Override
    public PowerCutSeasonDTO update(PowerCutSeasonDTO PowerCutSeasonDTO) {
        log.debug("Entry - update(PowerCutSeasonDTO={})", PowerCutSeasonDTO);
        evaluator.evaluate(POWERCUTSEASON_UPDATE, new HashMap<>());
        PowerCutSeasonDTO = preHookUpdate(PowerCutSeasonDTO);
        PowerCutSeasonDTO dto = serviceExt.update(PowerCutSeasonDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of PowerCutSeasons.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of PowerCutSeasons
     */
    @Override
    public PageDTO<PowerCutSeasonDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(POWERCUTSEASON_GET, new HashMap<>());
        PageDTO<PowerCutSeasonDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a PowerCutSeason by their ID with a specified reason.
     *
     * @param id     The ID of the PowerCutSeason to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(POWERCUTSEASON_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        PowerCutSeasonDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a PowerCutSeason by their ID.
     *
     * @param id The ID of the PowerCutSeason to retrieve
     * @return The PowerCutSeason with the specified ID
     */
    @Override
    public PowerCutSeasonDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(POWERCUTSEASON_GET, new HashMap<>());
        PowerCutSeasonDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all PowerCutSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PowerCutSeasonDTO
     */
    @Override
    public Set<PowerCutSeasonDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(POWERCUTSEASON_GET, new HashMap<>());
        Set<PowerCutSeasonDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected PowerCutSeasonDTO postHookSave(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PowerCutSeasonDTO preHookSave(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PowerCutSeasonDTO postHookUpdate(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PowerCutSeasonDTO preHookUpdate(PowerCutSeasonDTO PowerCutSeasonDTO) {
        return PowerCutSeasonDTO;
    }

    protected PowerCutSeasonDTO postHookDelete(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected PowerCutSeasonDTO postHookGetById(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PageDTO<PowerCutSeasonDTO> postHookGetAll(PageDTO<PowerCutSeasonDTO> result) {
        return result;
    }
}
