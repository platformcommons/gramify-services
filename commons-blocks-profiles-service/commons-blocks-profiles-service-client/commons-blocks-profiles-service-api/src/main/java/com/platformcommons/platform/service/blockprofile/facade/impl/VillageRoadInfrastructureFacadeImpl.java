package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageRoadInfrastructureFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageRoadInfrastructureProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageRoadInfrastructureServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageRoadInfrastructureFacadeImpl implements VillageRoadInfrastructureFacade {

    private final VillageRoadInfrastructureServiceExt serviceExt;
    private final VillageRoadInfrastructureProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEROADINFRASTRUCTURE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADINFRASTRUCTURE.CREATE";
    private static final String VILLAGEROADINFRASTRUCTURE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADINFRASTRUCTURE.UPDATED";
    private static final String VILLAGEROADINFRASTRUCTURE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADINFRASTRUCTURE.DELETE";
    private static final String VILLAGEROADINFRASTRUCTURE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADINFRASTRUCTURE.GET";

    public VillageRoadInfrastructureFacadeImpl(VillageRoadInfrastructureServiceExt serviceExt, VillageRoadInfrastructureProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageRoadInfrastructure entry in the system.
     *
     * @param VillageRoadInfrastructureDTO The VillageRoadInfrastructure information to be saved
     * @return The saved VillageRoadInfrastructure data
     */
    @Override
    public VillageRoadInfrastructureDTO save(VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO) {
        log.debug("Entry - save(VillageRoadInfrastructureDTO={})", VillageRoadInfrastructureDTO);
        evaluator.evaluate(VILLAGEROADINFRASTRUCTURE_CREATE, new HashMap<>());
        VillageRoadInfrastructureDTO = preHookSave(VillageRoadInfrastructureDTO);
        VillageRoadInfrastructureDTO dto = serviceExt.save(VillageRoadInfrastructureDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageRoadInfrastructure entry.
     *
     * @param VillageRoadInfrastructureDTO The VillageRoadInfrastructure information to be updated
     * @return The updated VillageRoadInfrastructure data
     */
    @Override
    public VillageRoadInfrastructureDTO update(VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO) {
        log.debug("Entry - update(VillageRoadInfrastructureDTO={})", VillageRoadInfrastructureDTO);
        evaluator.evaluate(VILLAGEROADINFRASTRUCTURE_UPDATE, new HashMap<>());
        VillageRoadInfrastructureDTO = preHookUpdate(VillageRoadInfrastructureDTO);
        VillageRoadInfrastructureDTO dto = serviceExt.update(VillageRoadInfrastructureDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageRoadInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageRoadInfrastructures
     */
    @Override
    public PageDTO<VillageRoadInfrastructureDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEROADINFRASTRUCTURE_GET, new HashMap<>());
        PageDTO<VillageRoadInfrastructureDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageRoadInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageRoadInfrastructure to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEROADINFRASTRUCTURE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageRoadInfrastructureDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageRoadInfrastructure by their ID.
     *
     * @param id The ID of the VillageRoadInfrastructure to retrieve
     * @return The VillageRoadInfrastructure with the specified ID
     */
    @Override
    public VillageRoadInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEROADINFRASTRUCTURE_GET, new HashMap<>());
        VillageRoadInfrastructureDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageRoadInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageRoadInfrastructureDTO
     */
    @Override
    public Set<VillageRoadInfrastructureDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEROADINFRASTRUCTURE_GET, new HashMap<>());
        Set<VillageRoadInfrastructureDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageRoadInfrastructureDTO postHookSave(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected VillageRoadInfrastructureDTO preHookSave(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected VillageRoadInfrastructureDTO postHookUpdate(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected VillageRoadInfrastructureDTO preHookUpdate(VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO) {
        return VillageRoadInfrastructureDTO;
    }

    protected VillageRoadInfrastructureDTO postHookDelete(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageRoadInfrastructureDTO postHookGetById(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageRoadInfrastructureDTO> postHookGetAll(PageDTO<VillageRoadInfrastructureDTO> result) {
        return result;
    }
}
