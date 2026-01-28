package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageCommunicationInfrastructFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageCommunicationInfrastructProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageCommunicationInfrastructServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageCommunicationInfrastructFacadeImpl implements VillageCommunicationInfrastructFacade {

    private final VillageCommunicationInfrastructServiceExt serviceExt;
    private final VillageCommunicationInfrastructProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECOMMUNICATIONINFRASTRUCT_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMUNICATIONINFRASTRUCT.CREATE";
    private static final String VILLAGECOMMUNICATIONINFRASTRUCT_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMUNICATIONINFRASTRUCT.UPDATED";
    private static final String VILLAGECOMMUNICATIONINFRASTRUCT_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMUNICATIONINFRASTRUCT.DELETE";
    private static final String VILLAGECOMMUNICATIONINFRASTRUCT_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMUNICATIONINFRASTRUCT.GET";

    public VillageCommunicationInfrastructFacadeImpl(VillageCommunicationInfrastructServiceExt serviceExt, VillageCommunicationInfrastructProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageCommunicationInfrastruct entry in the system.
     *
     * @param VillageCommunicationInfrastructDTO The VillageCommunicationInfrastruct information to be saved
     * @return The saved VillageCommunicationInfrastruct data
     */
    @Override
    public VillageCommunicationInfrastructDTO save(VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO) {
        log.debug("Entry - save(VillageCommunicationInfrastructDTO={})", VillageCommunicationInfrastructDTO);
        evaluator.evaluate(VILLAGECOMMUNICATIONINFRASTRUCT_CREATE, new HashMap<>());
        VillageCommunicationInfrastructDTO = preHookSave(VillageCommunicationInfrastructDTO);
        VillageCommunicationInfrastructDTO dto = serviceExt.save(VillageCommunicationInfrastructDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageCommunicationInfrastruct entry.
     *
     * @param VillageCommunicationInfrastructDTO The VillageCommunicationInfrastruct information to be updated
     * @return The updated VillageCommunicationInfrastruct data
     */
    @Override
    public VillageCommunicationInfrastructDTO update(VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO) {
        log.debug("Entry - update(VillageCommunicationInfrastructDTO={})", VillageCommunicationInfrastructDTO);
        evaluator.evaluate(VILLAGECOMMUNICATIONINFRASTRUCT_UPDATE, new HashMap<>());
        VillageCommunicationInfrastructDTO = preHookUpdate(VillageCommunicationInfrastructDTO);
        VillageCommunicationInfrastructDTO dto = serviceExt.update(VillageCommunicationInfrastructDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageCommunicationInfrastructs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommunicationInfrastructs
     */
    @Override
    public PageDTO<VillageCommunicationInfrastructDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECOMMUNICATIONINFRASTRUCT_GET, new HashMap<>());
        PageDTO<VillageCommunicationInfrastructDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageCommunicationInfrastruct by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommunicationInfrastruct to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECOMMUNICATIONINFRASTRUCT_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageCommunicationInfrastructDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageCommunicationInfrastruct by their ID.
     *
     * @param id The ID of the VillageCommunicationInfrastruct to retrieve
     * @return The VillageCommunicationInfrastruct with the specified ID
     */
    @Override
    public VillageCommunicationInfrastructDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECOMMUNICATIONINFRASTRUCT_GET, new HashMap<>());
        VillageCommunicationInfrastructDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageCommunicationInfrastructs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommunicationInfrastructDTO
     */
    @Override
    public Set<VillageCommunicationInfrastructDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECOMMUNICATIONINFRASTRUCT_GET, new HashMap<>());
        Set<VillageCommunicationInfrastructDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommunicationInfrastructDTO postHookSave(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected VillageCommunicationInfrastructDTO preHookSave(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected VillageCommunicationInfrastructDTO postHookUpdate(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected VillageCommunicationInfrastructDTO preHookUpdate(VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO) {
        return VillageCommunicationInfrastructDTO;
    }

    protected VillageCommunicationInfrastructDTO postHookDelete(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommunicationInfrastructDTO postHookGetById(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommunicationInfrastructDTO> postHookGetAll(PageDTO<VillageCommunicationInfrastructDTO> result) {
        return result;
    }
}
