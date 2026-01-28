package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHGovernanceAspirationFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHGovernanceAspirationProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHGovernanceAspirationServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHGovernanceAspirationFacadeImpl implements HHGovernanceAspirationFacade {

    private final HHGovernanceAspirationServiceExt serviceExt;
    private final HHGovernanceAspirationProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHGOVERNANCEASPIRATION_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHGOVERNANCEASPIRATION.CREATE";
    private static final String HHGOVERNANCEASPIRATION_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHGOVERNANCEASPIRATION.UPDATED";
    private static final String HHGOVERNANCEASPIRATION_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHGOVERNANCEASPIRATION.DELETE";
    private static final String HHGOVERNANCEASPIRATION_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHGOVERNANCEASPIRATION.GET";

    public HHGovernanceAspirationFacadeImpl(HHGovernanceAspirationServiceExt serviceExt, HHGovernanceAspirationProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHGovernanceAspiration entry in the system.
     *
     * @param HHGovernanceAspirationDTO The HHGovernanceAspiration information to be saved
     * @return The saved HHGovernanceAspiration data
     */
    @Override
    public HHGovernanceAspirationDTO save(HHGovernanceAspirationDTO HHGovernanceAspirationDTO) {
        log.debug("Entry - save(HHGovernanceAspirationDTO={})", HHGovernanceAspirationDTO);
        evaluator.evaluate(HHGOVERNANCEASPIRATION_CREATE, new HashMap<>());
        HHGovernanceAspirationDTO = preHookSave(HHGovernanceAspirationDTO);
        HHGovernanceAspirationDTO dto = serviceExt.save(HHGovernanceAspirationDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHGovernanceAspiration entry.
     *
     * @param HHGovernanceAspirationDTO The HHGovernanceAspiration information to be updated
     * @return The updated HHGovernanceAspiration data
     */
    @Override
    public HHGovernanceAspirationDTO update(HHGovernanceAspirationDTO HHGovernanceAspirationDTO) {
        log.debug("Entry - update(HHGovernanceAspirationDTO={})", HHGovernanceAspirationDTO);
        evaluator.evaluate(HHGOVERNANCEASPIRATION_UPDATE, new HashMap<>());
        HHGovernanceAspirationDTO = preHookUpdate(HHGovernanceAspirationDTO);
        HHGovernanceAspirationDTO dto = serviceExt.update(HHGovernanceAspirationDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHGovernanceAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHGovernanceAspirations
     */
    @Override
    public PageDTO<HHGovernanceAspirationDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHGOVERNANCEASPIRATION_GET, new HashMap<>());
        PageDTO<HHGovernanceAspirationDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHGovernanceAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHGovernanceAspiration to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHGOVERNANCEASPIRATION_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHGovernanceAspirationDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHGovernanceAspiration by their ID.
     *
     * @param id The ID of the HHGovernanceAspiration to retrieve
     * @return The HHGovernanceAspiration with the specified ID
     */
    @Override
    public HHGovernanceAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHGOVERNANCEASPIRATION_GET, new HashMap<>());
        HHGovernanceAspirationDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHGovernanceAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHGovernanceAspirationDTO
     */
    @Override
    public Set<HHGovernanceAspirationDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHGOVERNANCEASPIRATION_GET, new HashMap<>());
        Set<HHGovernanceAspirationDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHGovernanceAspirationDTO postHookSave(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected HHGovernanceAspirationDTO preHookSave(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected HHGovernanceAspirationDTO postHookUpdate(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected HHGovernanceAspirationDTO preHookUpdate(HHGovernanceAspirationDTO HHGovernanceAspirationDTO) {
        return HHGovernanceAspirationDTO;
    }

    protected HHGovernanceAspirationDTO postHookDelete(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHGovernanceAspirationDTO postHookGetById(HHGovernanceAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHGovernanceAspirationDTO> postHookGetAll(PageDTO<HHGovernanceAspirationDTO> result) {
        return result;
    }
}
