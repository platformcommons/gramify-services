package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHEmploymentTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHEmploymentTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHEmploymentTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHEmploymentTypeFacadeImpl implements HHEmploymentTypeFacade {

    private final HHEmploymentTypeServiceExt serviceExt;
    private final HHEmploymentTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHEMPLOYMENTTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHEMPLOYMENTTYPE.CREATE";
    private static final String HHEMPLOYMENTTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHEMPLOYMENTTYPE.UPDATED";
    private static final String HHEMPLOYMENTTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHEMPLOYMENTTYPE.DELETE";
    private static final String HHEMPLOYMENTTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHEMPLOYMENTTYPE.GET";

    public HHEmploymentTypeFacadeImpl(HHEmploymentTypeServiceExt serviceExt, HHEmploymentTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHEmploymentType entry in the system.
     *
     * @param HHEmploymentTypeDTO The HHEmploymentType information to be saved
     * @return The saved HHEmploymentType data
     */
    @Override
    public HHEmploymentTypeDTO save(HHEmploymentTypeDTO HHEmploymentTypeDTO) {
        log.debug("Entry - save(HHEmploymentTypeDTO={})", HHEmploymentTypeDTO);
        evaluator.evaluate(HHEMPLOYMENTTYPE_CREATE, new HashMap<>());
        HHEmploymentTypeDTO = preHookSave(HHEmploymentTypeDTO);
        HHEmploymentTypeDTO dto = serviceExt.save(HHEmploymentTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHEmploymentType entry.
     *
     * @param HHEmploymentTypeDTO The HHEmploymentType information to be updated
     * @return The updated HHEmploymentType data
     */
    @Override
    public HHEmploymentTypeDTO update(HHEmploymentTypeDTO HHEmploymentTypeDTO) {
        log.debug("Entry - update(HHEmploymentTypeDTO={})", HHEmploymentTypeDTO);
        evaluator.evaluate(HHEMPLOYMENTTYPE_UPDATE, new HashMap<>());
        HHEmploymentTypeDTO = preHookUpdate(HHEmploymentTypeDTO);
        HHEmploymentTypeDTO dto = serviceExt.update(HHEmploymentTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHEmploymentTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEmploymentTypes
     */
    @Override
    public PageDTO<HHEmploymentTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHEMPLOYMENTTYPE_GET, new HashMap<>());
        PageDTO<HHEmploymentTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHEmploymentType by their ID with a specified reason.
     *
     * @param id     The ID of the HHEmploymentType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHEMPLOYMENTTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHEmploymentTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHEmploymentType by their ID.
     *
     * @param id The ID of the HHEmploymentType to retrieve
     * @return The HHEmploymentType with the specified ID
     */
    @Override
    public HHEmploymentTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHEMPLOYMENTTYPE_GET, new HashMap<>());
        HHEmploymentTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHEmploymentTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEmploymentTypeDTO
     */
    @Override
    public Set<HHEmploymentTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHEMPLOYMENTTYPE_GET, new HashMap<>());
        Set<HHEmploymentTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEmploymentTypeDTO postHookSave(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected HHEmploymentTypeDTO preHookSave(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected HHEmploymentTypeDTO postHookUpdate(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected HHEmploymentTypeDTO preHookUpdate(HHEmploymentTypeDTO HHEmploymentTypeDTO) {
        return HHEmploymentTypeDTO;
    }

    protected HHEmploymentTypeDTO postHookDelete(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEmploymentTypeDTO postHookGetById(HHEmploymentTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHEmploymentTypeDTO> postHookGetAll(PageDTO<HHEmploymentTypeDTO> result) {
        return result;
    }
}
