package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.PesticidesInDemandFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.PesticidesInDemandProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.PesticidesInDemandServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class PesticidesInDemandFacadeImpl implements PesticidesInDemandFacade {

    private final PesticidesInDemandServiceExt serviceExt;
    private final PesticidesInDemandProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String PESTICIDESINDEMAND_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PESTICIDESINDEMAND.CREATE";
    private static final String PESTICIDESINDEMAND_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PESTICIDESINDEMAND.UPDATED";
    private static final String PESTICIDESINDEMAND_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PESTICIDESINDEMAND.DELETE";
    private static final String PESTICIDESINDEMAND_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PESTICIDESINDEMAND.GET";

    public PesticidesInDemandFacadeImpl(PesticidesInDemandServiceExt serviceExt, PesticidesInDemandProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new PesticidesInDemand entry in the system.
     *
     * @param PesticidesInDemandDTO The PesticidesInDemand information to be saved
     * @return The saved PesticidesInDemand data
     */
    @Override
    public PesticidesInDemandDTO save(PesticidesInDemandDTO PesticidesInDemandDTO) {
        log.debug("Entry - save(PesticidesInDemandDTO={})", PesticidesInDemandDTO);
        evaluator.evaluate(PESTICIDESINDEMAND_CREATE, new HashMap<>());
        PesticidesInDemandDTO = preHookSave(PesticidesInDemandDTO);
        PesticidesInDemandDTO dto = serviceExt.save(PesticidesInDemandDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing PesticidesInDemand entry.
     *
     * @param PesticidesInDemandDTO The PesticidesInDemand information to be updated
     * @return The updated PesticidesInDemand data
     */
    @Override
    public PesticidesInDemandDTO update(PesticidesInDemandDTO PesticidesInDemandDTO) {
        log.debug("Entry - update(PesticidesInDemandDTO={})", PesticidesInDemandDTO);
        evaluator.evaluate(PESTICIDESINDEMAND_UPDATE, new HashMap<>());
        PesticidesInDemandDTO = preHookUpdate(PesticidesInDemandDTO);
        PesticidesInDemandDTO dto = serviceExt.update(PesticidesInDemandDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of PesticidesInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of PesticidesInDemands
     */
    @Override
    public PageDTO<PesticidesInDemandDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(PESTICIDESINDEMAND_GET, new HashMap<>());
        PageDTO<PesticidesInDemandDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a PesticidesInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the PesticidesInDemand to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(PESTICIDESINDEMAND_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        PesticidesInDemandDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a PesticidesInDemand by their ID.
     *
     * @param id The ID of the PesticidesInDemand to retrieve
     * @return The PesticidesInDemand with the specified ID
     */
    @Override
    public PesticidesInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(PESTICIDESINDEMAND_GET, new HashMap<>());
        PesticidesInDemandDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all PesticidesInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PesticidesInDemandDTO
     */
    @Override
    public Set<PesticidesInDemandDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(PESTICIDESINDEMAND_GET, new HashMap<>());
        Set<PesticidesInDemandDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected PesticidesInDemandDTO postHookSave(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PesticidesInDemandDTO preHookSave(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PesticidesInDemandDTO postHookUpdate(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PesticidesInDemandDTO preHookUpdate(PesticidesInDemandDTO PesticidesInDemandDTO) {
        return PesticidesInDemandDTO;
    }

    protected PesticidesInDemandDTO postHookDelete(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected PesticidesInDemandDTO postHookGetById(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<PesticidesInDemandDTO> postHookGetAll(PageDTO<PesticidesInDemandDTO> result) {
        return result;
    }
}
