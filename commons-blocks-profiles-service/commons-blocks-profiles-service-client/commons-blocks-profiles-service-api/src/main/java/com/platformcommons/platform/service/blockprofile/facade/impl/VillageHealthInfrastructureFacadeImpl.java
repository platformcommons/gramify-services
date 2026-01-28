package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageHealthInfrastructureFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageHealthInfrastructureProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageHealthInfrastructureServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageHealthInfrastructureFacadeImpl implements VillageHealthInfrastructureFacade {

    private final VillageHealthInfrastructureServiceExt serviceExt;
    private final VillageHealthInfrastructureProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEHEALTHINFRASTRUCTURE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHEALTHINFRASTRUCTURE.CREATE";
    private static final String VILLAGEHEALTHINFRASTRUCTURE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHEALTHINFRASTRUCTURE.UPDATED";
    private static final String VILLAGEHEALTHINFRASTRUCTURE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHEALTHINFRASTRUCTURE.DELETE";
    private static final String VILLAGEHEALTHINFRASTRUCTURE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHEALTHINFRASTRUCTURE.GET";

    public VillageHealthInfrastructureFacadeImpl(VillageHealthInfrastructureServiceExt serviceExt, VillageHealthInfrastructureProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageHealthInfrastructure entry in the system.
     *
     * @param VillageHealthInfrastructureDTO The VillageHealthInfrastructure information to be saved
     * @return The saved VillageHealthInfrastructure data
     */
    @Override
    public VillageHealthInfrastructureDTO save(VillageHealthInfrastructureDTO VillageHealthInfrastructureDTO) {
        log.debug("Entry - save(VillageHealthInfrastructureDTO={})", VillageHealthInfrastructureDTO);
        evaluator.evaluate(VILLAGEHEALTHINFRASTRUCTURE_CREATE, new HashMap<>());
        VillageHealthInfrastructureDTO = preHookSave(VillageHealthInfrastructureDTO);
        VillageHealthInfrastructureDTO dto = serviceExt.save(VillageHealthInfrastructureDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageHealthInfrastructure entry.
     *
     * @param VillageHealthInfrastructureDTO The VillageHealthInfrastructure information to be updated
     * @return The updated VillageHealthInfrastructure data
     */
    @Override
    public VillageHealthInfrastructureDTO update(VillageHealthInfrastructureDTO VillageHealthInfrastructureDTO) {
        log.debug("Entry - update(VillageHealthInfrastructureDTO={})", VillageHealthInfrastructureDTO);
        evaluator.evaluate(VILLAGEHEALTHINFRASTRUCTURE_UPDATE, new HashMap<>());
        VillageHealthInfrastructureDTO = preHookUpdate(VillageHealthInfrastructureDTO);
        VillageHealthInfrastructureDTO dto = serviceExt.update(VillageHealthInfrastructureDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageHealthInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHealthInfrastructures
     */
    @Override
    public PageDTO<VillageHealthInfrastructureDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEHEALTHINFRASTRUCTURE_GET, new HashMap<>());
        PageDTO<VillageHealthInfrastructureDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageHealthInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHealthInfrastructure to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEHEALTHINFRASTRUCTURE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageHealthInfrastructureDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageHealthInfrastructure by their ID.
     *
     * @param id The ID of the VillageHealthInfrastructure to retrieve
     * @return The VillageHealthInfrastructure with the specified ID
     */
    @Override
    public VillageHealthInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEHEALTHINFRASTRUCTURE_GET, new HashMap<>());
        VillageHealthInfrastructureDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageHealthInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHealthInfrastructureDTO
     */
    @Override
    public Set<VillageHealthInfrastructureDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEHEALTHINFRASTRUCTURE_GET, new HashMap<>());
        Set<VillageHealthInfrastructureDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of commonHealthIssueList to a VillageHealthInfrastructure identified by their ID.
     *
     * @param id               The ID of the VillageHealthInfrastructure to add hobbies to
     * @param commonHealthIssueList  to be added
     * @since 1.0.0
     */
    @Override
    public void addCommonHealthIssueToVillageHealthInfrastructure(Long id, List<CommonHealthIssueDTO> commonHealthIssueList){
        serviceExt.addCommonHealthIssueToVillageHealthInfrastructure(id,commonHealthIssueList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageHealthInfrastructureDTO postHookSave(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected VillageHealthInfrastructureDTO preHookSave(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected VillageHealthInfrastructureDTO postHookUpdate(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected VillageHealthInfrastructureDTO preHookUpdate(VillageHealthInfrastructureDTO VillageHealthInfrastructureDTO) {
        return VillageHealthInfrastructureDTO;
    }

    protected VillageHealthInfrastructureDTO postHookDelete(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHealthInfrastructureDTO postHookGetById(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHealthInfrastructureDTO> postHookGetAll(PageDTO<VillageHealthInfrastructureDTO> result) {
        return result;
    }
}
