package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageTopInfrastructureNeedFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageTopInfrastructureNeedProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageTopInfrastructureNeedServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageTopInfrastructureNeedFacadeImpl implements VillageTopInfrastructureNeedFacade {

    private final VillageTopInfrastructureNeedServiceExt serviceExt;
    private final VillageTopInfrastructureNeedProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGETOPINFRASTRUCTURENEED_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETOPINFRASTRUCTURENEED.CREATE";
    private static final String VILLAGETOPINFRASTRUCTURENEED_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETOPINFRASTRUCTURENEED.UPDATED";
    private static final String VILLAGETOPINFRASTRUCTURENEED_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETOPINFRASTRUCTURENEED.DELETE";
    private static final String VILLAGETOPINFRASTRUCTURENEED_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETOPINFRASTRUCTURENEED.GET";

    public VillageTopInfrastructureNeedFacadeImpl(VillageTopInfrastructureNeedServiceExt serviceExt, VillageTopInfrastructureNeedProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageTopInfrastructureNeed entry in the system.
     *
     * @param VillageTopInfrastructureNeedDTO The VillageTopInfrastructureNeed information to be saved
     * @return The saved VillageTopInfrastructureNeed data
     */
    @Override
    public VillageTopInfrastructureNeedDTO save(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO) {
        log.debug("Entry - save(VillageTopInfrastructureNeedDTO={})", VillageTopInfrastructureNeedDTO);
        evaluator.evaluate(VILLAGETOPINFRASTRUCTURENEED_CREATE, new HashMap<>());
        VillageTopInfrastructureNeedDTO = preHookSave(VillageTopInfrastructureNeedDTO);
        VillageTopInfrastructureNeedDTO dto = serviceExt.save(VillageTopInfrastructureNeedDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageTopInfrastructureNeed entry.
     *
     * @param VillageTopInfrastructureNeedDTO The VillageTopInfrastructureNeed information to be updated
     * @return The updated VillageTopInfrastructureNeed data
     */
    @Override
    public VillageTopInfrastructureNeedDTO update(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO) {
        log.debug("Entry - update(VillageTopInfrastructureNeedDTO={})", VillageTopInfrastructureNeedDTO);
        evaluator.evaluate(VILLAGETOPINFRASTRUCTURENEED_UPDATE, new HashMap<>());
        VillageTopInfrastructureNeedDTO = preHookUpdate(VillageTopInfrastructureNeedDTO);
        VillageTopInfrastructureNeedDTO dto = serviceExt.update(VillageTopInfrastructureNeedDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageTopInfrastructureNeeds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageTopInfrastructureNeeds
     */
    @Override
    public PageDTO<VillageTopInfrastructureNeedDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGETOPINFRASTRUCTURENEED_GET, new HashMap<>());
        PageDTO<VillageTopInfrastructureNeedDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageTopInfrastructureNeed by their ID with a specified reason.
     *
     * @param id     The ID of the VillageTopInfrastructureNeed to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGETOPINFRASTRUCTURENEED_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageTopInfrastructureNeedDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageTopInfrastructureNeed by their ID.
     *
     * @param id The ID of the VillageTopInfrastructureNeed to retrieve
     * @return The VillageTopInfrastructureNeed with the specified ID
     */
    @Override
    public VillageTopInfrastructureNeedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGETOPINFRASTRUCTURENEED_GET, new HashMap<>());
        VillageTopInfrastructureNeedDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageTopInfrastructureNeeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageTopInfrastructureNeedDTO
     */
    @Override
    public Set<VillageTopInfrastructureNeedDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGETOPINFRASTRUCTURENEED_GET, new HashMap<>());
        Set<VillageTopInfrastructureNeedDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageTopInfrastructureNeedDTO postHookSave(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected VillageTopInfrastructureNeedDTO preHookSave(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected VillageTopInfrastructureNeedDTO postHookUpdate(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected VillageTopInfrastructureNeedDTO preHookUpdate(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO) {
        return VillageTopInfrastructureNeedDTO;
    }

    protected VillageTopInfrastructureNeedDTO postHookDelete(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageTopInfrastructureNeedDTO postHookGetById(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected PageDTO<VillageTopInfrastructureNeedDTO> postHookGetAll(PageDTO<VillageTopInfrastructureNeedDTO> result) {
        return result;
    }
}
