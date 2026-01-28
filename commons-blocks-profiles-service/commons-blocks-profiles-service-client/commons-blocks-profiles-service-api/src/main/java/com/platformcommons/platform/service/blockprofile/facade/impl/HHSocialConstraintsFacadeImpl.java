package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHSocialConstraintsFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHSocialConstraintsProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHSocialConstraintsServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHSocialConstraintsFacadeImpl implements HHSocialConstraintsFacade {

    private final HHSocialConstraintsServiceExt serviceExt;
    private final HHSocialConstraintsProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHSOCIALCONSTRAINTS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALCONSTRAINTS.CREATE";
    private static final String HHSOCIALCONSTRAINTS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALCONSTRAINTS.UPDATED";
    private static final String HHSOCIALCONSTRAINTS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALCONSTRAINTS.DELETE";
    private static final String HHSOCIALCONSTRAINTS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALCONSTRAINTS.GET";

    public HHSocialConstraintsFacadeImpl(HHSocialConstraintsServiceExt serviceExt, HHSocialConstraintsProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHSocialConstraints entry in the system.
     *
     * @param HHSocialConstraintsDTO The HHSocialConstraints information to be saved
     * @return The saved HHSocialConstraints data
     */
    @Override
    public HHSocialConstraintsDTO save(HHSocialConstraintsDTO HHSocialConstraintsDTO) {
        log.debug("Entry - save(HHSocialConstraintsDTO={})", HHSocialConstraintsDTO);
        evaluator.evaluate(HHSOCIALCONSTRAINTS_CREATE, new HashMap<>());
        HHSocialConstraintsDTO = preHookSave(HHSocialConstraintsDTO);
        HHSocialConstraintsDTO dto = serviceExt.save(HHSocialConstraintsDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHSocialConstraints entry.
     *
     * @param HHSocialConstraintsDTO The HHSocialConstraints information to be updated
     * @return The updated HHSocialConstraints data
     */
    @Override
    public HHSocialConstraintsDTO update(HHSocialConstraintsDTO HHSocialConstraintsDTO) {
        log.debug("Entry - update(HHSocialConstraintsDTO={})", HHSocialConstraintsDTO);
        evaluator.evaluate(HHSOCIALCONSTRAINTS_UPDATE, new HashMap<>());
        HHSocialConstraintsDTO = preHookUpdate(HHSocialConstraintsDTO);
        HHSocialConstraintsDTO dto = serviceExt.update(HHSocialConstraintsDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHSocialConstraintss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHSocialConstraintss
     */
    @Override
    public PageDTO<HHSocialConstraintsDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHSOCIALCONSTRAINTS_GET, new HashMap<>());
        PageDTO<HHSocialConstraintsDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHSocialConstraints by their ID with a specified reason.
     *
     * @param id     The ID of the HHSocialConstraints to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHSOCIALCONSTRAINTS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHSocialConstraintsDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHSocialConstraints by their ID.
     *
     * @param id The ID of the HHSocialConstraints to retrieve
     * @return The HHSocialConstraints with the specified ID
     */
    @Override
    public HHSocialConstraintsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHSOCIALCONSTRAINTS_GET, new HashMap<>());
        HHSocialConstraintsDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHSocialConstraintss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSocialConstraintsDTO
     */
    @Override
    public Set<HHSocialConstraintsDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHSOCIALCONSTRAINTS_GET, new HashMap<>());
        Set<HHSocialConstraintsDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHSocialConstraintsDTO postHookSave(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected HHSocialConstraintsDTO preHookSave(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected HHSocialConstraintsDTO postHookUpdate(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected HHSocialConstraintsDTO preHookUpdate(HHSocialConstraintsDTO HHSocialConstraintsDTO) {
        return HHSocialConstraintsDTO;
    }

    protected HHSocialConstraintsDTO postHookDelete(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHSocialConstraintsDTO postHookGetById(HHSocialConstraintsDTO dto) {
        return dto;
    }

    protected PageDTO<HHSocialConstraintsDTO> postHookGetAll(PageDTO<HHSocialConstraintsDTO> result) {
        return result;
    }
}
