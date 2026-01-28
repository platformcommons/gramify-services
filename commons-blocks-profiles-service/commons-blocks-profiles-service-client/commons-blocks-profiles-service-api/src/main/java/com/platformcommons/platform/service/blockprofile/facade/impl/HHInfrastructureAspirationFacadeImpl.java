package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHInfrastructureAspirationFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHInfrastructureAspirationProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHInfrastructureAspirationServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHInfrastructureAspirationFacadeImpl implements HHInfrastructureAspirationFacade {

    private final HHInfrastructureAspirationServiceExt serviceExt;
    private final HHInfrastructureAspirationProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHINFRASTRUCTUREASPIRATION_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHINFRASTRUCTUREASPIRATION.CREATE";
    private static final String HHINFRASTRUCTUREASPIRATION_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHINFRASTRUCTUREASPIRATION.UPDATED";
    private static final String HHINFRASTRUCTUREASPIRATION_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHINFRASTRUCTUREASPIRATION.DELETE";
    private static final String HHINFRASTRUCTUREASPIRATION_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHINFRASTRUCTUREASPIRATION.GET";

    public HHInfrastructureAspirationFacadeImpl(HHInfrastructureAspirationServiceExt serviceExt, HHInfrastructureAspirationProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHInfrastructureAspiration entry in the system.
     *
     * @param HHInfrastructureAspirationDTO The HHInfrastructureAspiration information to be saved
     * @return The saved HHInfrastructureAspiration data
     */
    @Override
    public HHInfrastructureAspirationDTO save(HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO) {
        log.debug("Entry - save(HHInfrastructureAspirationDTO={})", HHInfrastructureAspirationDTO);
        evaluator.evaluate(HHINFRASTRUCTUREASPIRATION_CREATE, new HashMap<>());
        HHInfrastructureAspirationDTO = preHookSave(HHInfrastructureAspirationDTO);
        HHInfrastructureAspirationDTO dto = serviceExt.save(HHInfrastructureAspirationDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHInfrastructureAspiration entry.
     *
     * @param HHInfrastructureAspirationDTO The HHInfrastructureAspiration information to be updated
     * @return The updated HHInfrastructureAspiration data
     */
    @Override
    public HHInfrastructureAspirationDTO update(HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO) {
        log.debug("Entry - update(HHInfrastructureAspirationDTO={})", HHInfrastructureAspirationDTO);
        evaluator.evaluate(HHINFRASTRUCTUREASPIRATION_UPDATE, new HashMap<>());
        HHInfrastructureAspirationDTO = preHookUpdate(HHInfrastructureAspirationDTO);
        HHInfrastructureAspirationDTO dto = serviceExt.update(HHInfrastructureAspirationDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHInfrastructureAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHInfrastructureAspirations
     */
    @Override
    public PageDTO<HHInfrastructureAspirationDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHINFRASTRUCTUREASPIRATION_GET, new HashMap<>());
        PageDTO<HHInfrastructureAspirationDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHInfrastructureAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHInfrastructureAspiration to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHINFRASTRUCTUREASPIRATION_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHInfrastructureAspirationDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHInfrastructureAspiration by their ID.
     *
     * @param id The ID of the HHInfrastructureAspiration to retrieve
     * @return The HHInfrastructureAspiration with the specified ID
     */
    @Override
    public HHInfrastructureAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHINFRASTRUCTUREASPIRATION_GET, new HashMap<>());
        HHInfrastructureAspirationDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHInfrastructureAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHInfrastructureAspirationDTO
     */
    @Override
    public Set<HHInfrastructureAspirationDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHINFRASTRUCTUREASPIRATION_GET, new HashMap<>());
        Set<HHInfrastructureAspirationDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHInfrastructureAspirationList to a HHInfrastructureAspiration identified by their ID.
     *
     * @param id               The ID of the HHInfrastructureAspiration to add hobbies to
     * @param hHInfrastructureAspirationList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHInfrastructureAspirationToHHInfrastructureAspiration(Long id, List<HHInfrastructureAspirationDTO> hHInfrastructureAspirationList){
        serviceExt.addHHInfrastructureAspirationToHHInfrastructureAspiration(id,hHInfrastructureAspirationList);
    }

    /*Hooks to be overridden by subclasses*/
    protected HHInfrastructureAspirationDTO postHookSave(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected HHInfrastructureAspirationDTO preHookSave(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected HHInfrastructureAspirationDTO postHookUpdate(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected HHInfrastructureAspirationDTO preHookUpdate(HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO) {
        return HHInfrastructureAspirationDTO;
    }

    protected HHInfrastructureAspirationDTO postHookDelete(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHInfrastructureAspirationDTO postHookGetById(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHInfrastructureAspirationDTO> postHookGetAll(PageDTO<HHInfrastructureAspirationDTO> result) {
        return result;
    }
}
