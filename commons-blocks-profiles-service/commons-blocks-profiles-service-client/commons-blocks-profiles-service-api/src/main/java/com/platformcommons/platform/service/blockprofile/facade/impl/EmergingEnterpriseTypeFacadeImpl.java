package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.EmergingEnterpriseTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.EmergingEnterpriseTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.EmergingEnterpriseTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class EmergingEnterpriseTypeFacadeImpl implements EmergingEnterpriseTypeFacade {

    private final EmergingEnterpriseTypeServiceExt serviceExt;
    private final EmergingEnterpriseTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String EMERGINGENTERPRISETYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EMERGINGENTERPRISETYPE.CREATE";
    private static final String EMERGINGENTERPRISETYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EMERGINGENTERPRISETYPE.UPDATED";
    private static final String EMERGINGENTERPRISETYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EMERGINGENTERPRISETYPE.DELETE";
    private static final String EMERGINGENTERPRISETYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EMERGINGENTERPRISETYPE.GET";

    public EmergingEnterpriseTypeFacadeImpl(EmergingEnterpriseTypeServiceExt serviceExt, EmergingEnterpriseTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new EmergingEnterpriseType entry in the system.
     *
     * @param EmergingEnterpriseTypeDTO The EmergingEnterpriseType information to be saved
     * @return The saved EmergingEnterpriseType data
     */
    @Override
    public EmergingEnterpriseTypeDTO save(EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO) {
        log.debug("Entry - save(EmergingEnterpriseTypeDTO={})", EmergingEnterpriseTypeDTO);
        evaluator.evaluate(EMERGINGENTERPRISETYPE_CREATE, new HashMap<>());
        EmergingEnterpriseTypeDTO = preHookSave(EmergingEnterpriseTypeDTO);
        EmergingEnterpriseTypeDTO dto = serviceExt.save(EmergingEnterpriseTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing EmergingEnterpriseType entry.
     *
     * @param EmergingEnterpriseTypeDTO The EmergingEnterpriseType information to be updated
     * @return The updated EmergingEnterpriseType data
     */
    @Override
    public EmergingEnterpriseTypeDTO update(EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO) {
        log.debug("Entry - update(EmergingEnterpriseTypeDTO={})", EmergingEnterpriseTypeDTO);
        evaluator.evaluate(EMERGINGENTERPRISETYPE_UPDATE, new HashMap<>());
        EmergingEnterpriseTypeDTO = preHookUpdate(EmergingEnterpriseTypeDTO);
        EmergingEnterpriseTypeDTO dto = serviceExt.update(EmergingEnterpriseTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of EmergingEnterpriseTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of EmergingEnterpriseTypes
     */
    @Override
    public PageDTO<EmergingEnterpriseTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(EMERGINGENTERPRISETYPE_GET, new HashMap<>());
        PageDTO<EmergingEnterpriseTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a EmergingEnterpriseType by their ID with a specified reason.
     *
     * @param id     The ID of the EmergingEnterpriseType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(EMERGINGENTERPRISETYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        EmergingEnterpriseTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a EmergingEnterpriseType by their ID.
     *
     * @param id The ID of the EmergingEnterpriseType to retrieve
     * @return The EmergingEnterpriseType with the specified ID
     */
    @Override
    public EmergingEnterpriseTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(EMERGINGENTERPRISETYPE_GET, new HashMap<>());
        EmergingEnterpriseTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all EmergingEnterpriseTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of EmergingEnterpriseTypeDTO
     */
    @Override
    public Set<EmergingEnterpriseTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(EMERGINGENTERPRISETYPE_GET, new HashMap<>());
        Set<EmergingEnterpriseTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected EmergingEnterpriseTypeDTO postHookSave(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected EmergingEnterpriseTypeDTO preHookSave(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected EmergingEnterpriseTypeDTO postHookUpdate(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected EmergingEnterpriseTypeDTO preHookUpdate(EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO) {
        return EmergingEnterpriseTypeDTO;
    }

    protected EmergingEnterpriseTypeDTO postHookDelete(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected EmergingEnterpriseTypeDTO postHookGetById(EmergingEnterpriseTypeDTO dto) {
        return dto;
    }

    protected PageDTO<EmergingEnterpriseTypeDTO> postHookGetAll(PageDTO<EmergingEnterpriseTypeDTO> result) {
        return result;
    }
}
