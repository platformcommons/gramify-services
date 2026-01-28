package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHSkilledWorkerTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHSkilledWorkerTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHSkilledWorkerTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHSkilledWorkerTypeFacadeImpl implements HHSkilledWorkerTypeFacade {

    private final HHSkilledWorkerTypeServiceExt serviceExt;
    private final HHSkilledWorkerTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHSKILLEDWORKERTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSKILLEDWORKERTYPE.CREATE";
    private static final String HHSKILLEDWORKERTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSKILLEDWORKERTYPE.UPDATED";
    private static final String HHSKILLEDWORKERTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSKILLEDWORKERTYPE.DELETE";
    private static final String HHSKILLEDWORKERTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSKILLEDWORKERTYPE.GET";

    public HHSkilledWorkerTypeFacadeImpl(HHSkilledWorkerTypeServiceExt serviceExt, HHSkilledWorkerTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHSkilledWorkerType entry in the system.
     *
     * @param HHSkilledWorkerTypeDTO The HHSkilledWorkerType information to be saved
     * @return The saved HHSkilledWorkerType data
     */
    @Override
    public HHSkilledWorkerTypeDTO save(HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO) {
        log.debug("Entry - save(HHSkilledWorkerTypeDTO={})", HHSkilledWorkerTypeDTO);
        evaluator.evaluate(HHSKILLEDWORKERTYPE_CREATE, new HashMap<>());
        HHSkilledWorkerTypeDTO = preHookSave(HHSkilledWorkerTypeDTO);
        HHSkilledWorkerTypeDTO dto = serviceExt.save(HHSkilledWorkerTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHSkilledWorkerType entry.
     *
     * @param HHSkilledWorkerTypeDTO The HHSkilledWorkerType information to be updated
     * @return The updated HHSkilledWorkerType data
     */
    @Override
    public HHSkilledWorkerTypeDTO update(HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO) {
        log.debug("Entry - update(HHSkilledWorkerTypeDTO={})", HHSkilledWorkerTypeDTO);
        evaluator.evaluate(HHSKILLEDWORKERTYPE_UPDATE, new HashMap<>());
        HHSkilledWorkerTypeDTO = preHookUpdate(HHSkilledWorkerTypeDTO);
        HHSkilledWorkerTypeDTO dto = serviceExt.update(HHSkilledWorkerTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHSkilledWorkerTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHSkilledWorkerTypes
     */
    @Override
    public PageDTO<HHSkilledWorkerTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHSKILLEDWORKERTYPE_GET, new HashMap<>());
        PageDTO<HHSkilledWorkerTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHSkilledWorkerType by their ID with a specified reason.
     *
     * @param id     The ID of the HHSkilledWorkerType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHSKILLEDWORKERTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHSkilledWorkerTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHSkilledWorkerType by their ID.
     *
     * @param id The ID of the HHSkilledWorkerType to retrieve
     * @return The HHSkilledWorkerType with the specified ID
     */
    @Override
    public HHSkilledWorkerTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHSKILLEDWORKERTYPE_GET, new HashMap<>());
        HHSkilledWorkerTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHSkilledWorkerTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSkilledWorkerTypeDTO
     */
    @Override
    public Set<HHSkilledWorkerTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHSKILLEDWORKERTYPE_GET, new HashMap<>());
        Set<HHSkilledWorkerTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHSkilledWorkerTypeDTO postHookSave(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected HHSkilledWorkerTypeDTO preHookSave(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected HHSkilledWorkerTypeDTO postHookUpdate(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected HHSkilledWorkerTypeDTO preHookUpdate(HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO) {
        return HHSkilledWorkerTypeDTO;
    }

    protected HHSkilledWorkerTypeDTO postHookDelete(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHSkilledWorkerTypeDTO postHookGetById(HHSkilledWorkerTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHSkilledWorkerTypeDTO> postHookGetAll(PageDTO<HHSkilledWorkerTypeDTO> result) {
        return result;
    }
}
