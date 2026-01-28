package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.OperatingDayFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.OperatingDayProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.OperatingDayServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class OperatingDayFacadeImpl implements OperatingDayFacade {

    private final OperatingDayServiceExt serviceExt;
    private final OperatingDayProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String OPERATINGDAY_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OPERATINGDAY.CREATE";
    private static final String OPERATINGDAY_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OPERATINGDAY.UPDATED";
    private static final String OPERATINGDAY_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OPERATINGDAY.DELETE";
    private static final String OPERATINGDAY_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OPERATINGDAY.GET";

    public OperatingDayFacadeImpl(OperatingDayServiceExt serviceExt, OperatingDayProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new OperatingDay entry in the system.
     *
     * @param OperatingDayDTO The OperatingDay information to be saved
     * @return The saved OperatingDay data
     */
    @Override
    public OperatingDayDTO save(OperatingDayDTO OperatingDayDTO) {
        log.debug("Entry - save(OperatingDayDTO={})", OperatingDayDTO);
        evaluator.evaluate(OPERATINGDAY_CREATE, new HashMap<>());
        OperatingDayDTO = preHookSave(OperatingDayDTO);
        OperatingDayDTO dto = serviceExt.save(OperatingDayDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing OperatingDay entry.
     *
     * @param OperatingDayDTO The OperatingDay information to be updated
     * @return The updated OperatingDay data
     */
    @Override
    public OperatingDayDTO update(OperatingDayDTO OperatingDayDTO) {
        log.debug("Entry - update(OperatingDayDTO={})", OperatingDayDTO);
        evaluator.evaluate(OPERATINGDAY_UPDATE, new HashMap<>());
        OperatingDayDTO = preHookUpdate(OperatingDayDTO);
        OperatingDayDTO dto = serviceExt.update(OperatingDayDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of OperatingDays.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of OperatingDays
     */
    @Override
    public PageDTO<OperatingDayDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(OPERATINGDAY_GET, new HashMap<>());
        PageDTO<OperatingDayDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a OperatingDay by their ID with a specified reason.
     *
     * @param id     The ID of the OperatingDay to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(OPERATINGDAY_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        OperatingDayDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a OperatingDay by their ID.
     *
     * @param id The ID of the OperatingDay to retrieve
     * @return The OperatingDay with the specified ID
     */
    @Override
    public OperatingDayDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(OPERATINGDAY_GET, new HashMap<>());
        OperatingDayDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all OperatingDays by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OperatingDayDTO
     */
    @Override
    public Set<OperatingDayDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(OPERATINGDAY_GET, new HashMap<>());
        Set<OperatingDayDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected OperatingDayDTO postHookSave(OperatingDayDTO dto) {
        return dto;
    }

    protected OperatingDayDTO preHookSave(OperatingDayDTO dto) {
        return dto;
    }

    protected OperatingDayDTO postHookUpdate(OperatingDayDTO dto) {
        return dto;
    }

    protected OperatingDayDTO preHookUpdate(OperatingDayDTO OperatingDayDTO) {
        return OperatingDayDTO;
    }

    protected OperatingDayDTO postHookDelete(OperatingDayDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected OperatingDayDTO postHookGetById(OperatingDayDTO dto) {
        return dto;
    }

    protected PageDTO<OperatingDayDTO> postHookGetAll(PageDTO<OperatingDayDTO> result) {
        return result;
    }
}
