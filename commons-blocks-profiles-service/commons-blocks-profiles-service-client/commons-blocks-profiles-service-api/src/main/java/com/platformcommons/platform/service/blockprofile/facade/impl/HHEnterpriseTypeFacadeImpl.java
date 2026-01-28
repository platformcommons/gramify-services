package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHEnterpriseTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHEnterpriseTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHEnterpriseTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHEnterpriseTypeFacadeImpl implements HHEnterpriseTypeFacade {

    private final HHEnterpriseTypeServiceExt serviceExt;
    private final HHEnterpriseTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHENTERPRISETYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHENTERPRISETYPE.CREATE";
    private static final String HHENTERPRISETYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHENTERPRISETYPE.UPDATED";
    private static final String HHENTERPRISETYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHENTERPRISETYPE.DELETE";
    private static final String HHENTERPRISETYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHENTERPRISETYPE.GET";

    public HHEnterpriseTypeFacadeImpl(HHEnterpriseTypeServiceExt serviceExt, HHEnterpriseTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHEnterpriseType entry in the system.
     *
     * @param HHEnterpriseTypeDTO The HHEnterpriseType information to be saved
     * @return The saved HHEnterpriseType data
     */
    @Override
    public HHEnterpriseTypeDTO save(HHEnterpriseTypeDTO HHEnterpriseTypeDTO) {
        log.debug("Entry - save(HHEnterpriseTypeDTO={})", HHEnterpriseTypeDTO);
        evaluator.evaluate(HHENTERPRISETYPE_CREATE, new HashMap<>());
        HHEnterpriseTypeDTO = preHookSave(HHEnterpriseTypeDTO);
        HHEnterpriseTypeDTO dto = serviceExt.save(HHEnterpriseTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHEnterpriseType entry.
     *
     * @param HHEnterpriseTypeDTO The HHEnterpriseType information to be updated
     * @return The updated HHEnterpriseType data
     */
    @Override
    public HHEnterpriseTypeDTO update(HHEnterpriseTypeDTO HHEnterpriseTypeDTO) {
        log.debug("Entry - update(HHEnterpriseTypeDTO={})", HHEnterpriseTypeDTO);
        evaluator.evaluate(HHENTERPRISETYPE_UPDATE, new HashMap<>());
        HHEnterpriseTypeDTO = preHookUpdate(HHEnterpriseTypeDTO);
        HHEnterpriseTypeDTO dto = serviceExt.update(HHEnterpriseTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHEnterpriseTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEnterpriseTypes
     */
    @Override
    public PageDTO<HHEnterpriseTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHENTERPRISETYPE_GET, new HashMap<>());
        PageDTO<HHEnterpriseTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHEnterpriseType by their ID with a specified reason.
     *
     * @param id     The ID of the HHEnterpriseType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHENTERPRISETYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHEnterpriseTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHEnterpriseType by their ID.
     *
     * @param id The ID of the HHEnterpriseType to retrieve
     * @return The HHEnterpriseType with the specified ID
     */
    @Override
    public HHEnterpriseTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHENTERPRISETYPE_GET, new HashMap<>());
        HHEnterpriseTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHEnterpriseTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEnterpriseTypeDTO
     */
    @Override
    public Set<HHEnterpriseTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHENTERPRISETYPE_GET, new HashMap<>());
        Set<HHEnterpriseTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEnterpriseTypeDTO postHookSave(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected HHEnterpriseTypeDTO preHookSave(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected HHEnterpriseTypeDTO postHookUpdate(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected HHEnterpriseTypeDTO preHookUpdate(HHEnterpriseTypeDTO HHEnterpriseTypeDTO) {
        return HHEnterpriseTypeDTO;
    }

    protected HHEnterpriseTypeDTO postHookDelete(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEnterpriseTypeDTO postHookGetById(HHEnterpriseTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHEnterpriseTypeDTO> postHookGetAll(PageDTO<HHEnterpriseTypeDTO> result) {
        return result;
    }
}
