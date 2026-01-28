package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.NGOThematicAreaFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.NGOThematicAreaProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.NGOThematicAreaServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class NGOThematicAreaFacadeImpl implements NGOThematicAreaFacade {

    private final NGOThematicAreaServiceExt serviceExt;
    private final NGOThematicAreaProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String NGOTHEMATICAREA_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NGOTHEMATICAREA.CREATE";
    private static final String NGOTHEMATICAREA_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NGOTHEMATICAREA.UPDATED";
    private static final String NGOTHEMATICAREA_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NGOTHEMATICAREA.DELETE";
    private static final String NGOTHEMATICAREA_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NGOTHEMATICAREA.GET";

    public NGOThematicAreaFacadeImpl(NGOThematicAreaServiceExt serviceExt, NGOThematicAreaProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new NGOThematicArea entry in the system.
     *
     * @param NGOThematicAreaDTO The NGOThematicArea information to be saved
     * @return The saved NGOThematicArea data
     */
    @Override
    public NGOThematicAreaDTO save(NGOThematicAreaDTO NGOThematicAreaDTO) {
        log.debug("Entry - save(NGOThematicAreaDTO={})", NGOThematicAreaDTO);
        evaluator.evaluate(NGOTHEMATICAREA_CREATE, new HashMap<>());
        NGOThematicAreaDTO = preHookSave(NGOThematicAreaDTO);
        NGOThematicAreaDTO dto = serviceExt.save(NGOThematicAreaDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing NGOThematicArea entry.
     *
     * @param NGOThematicAreaDTO The NGOThematicArea information to be updated
     * @return The updated NGOThematicArea data
     */
    @Override
    public NGOThematicAreaDTO update(NGOThematicAreaDTO NGOThematicAreaDTO) {
        log.debug("Entry - update(NGOThematicAreaDTO={})", NGOThematicAreaDTO);
        evaluator.evaluate(NGOTHEMATICAREA_UPDATE, new HashMap<>());
        NGOThematicAreaDTO = preHookUpdate(NGOThematicAreaDTO);
        NGOThematicAreaDTO dto = serviceExt.update(NGOThematicAreaDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of NGOThematicAreas.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NGOThematicAreas
     */
    @Override
    public PageDTO<NGOThematicAreaDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(NGOTHEMATICAREA_GET, new HashMap<>());
        PageDTO<NGOThematicAreaDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a NGOThematicArea by their ID with a specified reason.
     *
     * @param id     The ID of the NGOThematicArea to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(NGOTHEMATICAREA_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        NGOThematicAreaDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a NGOThematicArea by their ID.
     *
     * @param id The ID of the NGOThematicArea to retrieve
     * @return The NGOThematicArea with the specified ID
     */
    @Override
    public NGOThematicAreaDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(NGOTHEMATICAREA_GET, new HashMap<>());
        NGOThematicAreaDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all NGOThematicAreas by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NGOThematicAreaDTO
     */
    @Override
    public Set<NGOThematicAreaDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(NGOTHEMATICAREA_GET, new HashMap<>());
        Set<NGOThematicAreaDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected NGOThematicAreaDTO postHookSave(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected NGOThematicAreaDTO preHookSave(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected NGOThematicAreaDTO postHookUpdate(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected NGOThematicAreaDTO preHookUpdate(NGOThematicAreaDTO NGOThematicAreaDTO) {
        return NGOThematicAreaDTO;
    }

    protected NGOThematicAreaDTO postHookDelete(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NGOThematicAreaDTO postHookGetById(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected PageDTO<NGOThematicAreaDTO> postHookGetAll(PageDTO<NGOThematicAreaDTO> result) {
        return result;
    }
}
