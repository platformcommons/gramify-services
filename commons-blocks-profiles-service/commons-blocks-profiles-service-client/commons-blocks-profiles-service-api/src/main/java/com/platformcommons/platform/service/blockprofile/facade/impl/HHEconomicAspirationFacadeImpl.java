package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHEconomicAspirationFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHEconomicAspirationProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHEconomicAspirationServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHEconomicAspirationFacadeImpl implements HHEconomicAspirationFacade {

    private final HHEconomicAspirationServiceExt serviceExt;
    private final HHEconomicAspirationProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHECONOMICASPIRATION_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICASPIRATION.CREATE";
    private static final String HHECONOMICASPIRATION_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICASPIRATION.UPDATED";
    private static final String HHECONOMICASPIRATION_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICASPIRATION.DELETE";
    private static final String HHECONOMICASPIRATION_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICASPIRATION.GET";

    public HHEconomicAspirationFacadeImpl(HHEconomicAspirationServiceExt serviceExt, HHEconomicAspirationProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHEconomicAspiration entry in the system.
     *
     * @param HHEconomicAspirationDTO The HHEconomicAspiration information to be saved
     * @return The saved HHEconomicAspiration data
     */
    @Override
    public HHEconomicAspirationDTO save(HHEconomicAspirationDTO HHEconomicAspirationDTO) {
        log.debug("Entry - save(HHEconomicAspirationDTO={})", HHEconomicAspirationDTO);
        evaluator.evaluate(HHECONOMICASPIRATION_CREATE, new HashMap<>());
        HHEconomicAspirationDTO = preHookSave(HHEconomicAspirationDTO);
        HHEconomicAspirationDTO dto = serviceExt.save(HHEconomicAspirationDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHEconomicAspiration entry.
     *
     * @param HHEconomicAspirationDTO The HHEconomicAspiration information to be updated
     * @return The updated HHEconomicAspiration data
     */
    @Override
    public HHEconomicAspirationDTO update(HHEconomicAspirationDTO HHEconomicAspirationDTO) {
        log.debug("Entry - update(HHEconomicAspirationDTO={})", HHEconomicAspirationDTO);
        evaluator.evaluate(HHECONOMICASPIRATION_UPDATE, new HashMap<>());
        HHEconomicAspirationDTO = preHookUpdate(HHEconomicAspirationDTO);
        HHEconomicAspirationDTO dto = serviceExt.update(HHEconomicAspirationDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHEconomicAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEconomicAspirations
     */
    @Override
    public PageDTO<HHEconomicAspirationDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHECONOMICASPIRATION_GET, new HashMap<>());
        PageDTO<HHEconomicAspirationDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHEconomicAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHEconomicAspiration to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHECONOMICASPIRATION_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHEconomicAspirationDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHEconomicAspiration by their ID.
     *
     * @param id The ID of the HHEconomicAspiration to retrieve
     * @return The HHEconomicAspiration with the specified ID
     */
    @Override
    public HHEconomicAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHECONOMICASPIRATION_GET, new HashMap<>());
        HHEconomicAspirationDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHEconomicAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEconomicAspirationDTO
     */
    @Override
    public Set<HHEconomicAspirationDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHECONOMICASPIRATION_GET, new HashMap<>());
        Set<HHEconomicAspirationDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEconomicAspirationDTO postHookSave(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected HHEconomicAspirationDTO preHookSave(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected HHEconomicAspirationDTO postHookUpdate(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected HHEconomicAspirationDTO preHookUpdate(HHEconomicAspirationDTO HHEconomicAspirationDTO) {
        return HHEconomicAspirationDTO;
    }

    protected HHEconomicAspirationDTO postHookDelete(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEconomicAspirationDTO postHookGetById(HHEconomicAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHEconomicAspirationDTO> postHookGetAll(PageDTO<HHEconomicAspirationDTO> result) {
        return result;
    }
}
