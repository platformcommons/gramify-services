package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageInfrastructureConstraintFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageInfrastructureConstraintProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageInfrastructureConstraintServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageInfrastructureConstraintFacadeImpl implements VillageInfrastructureConstraintFacade {

    private final VillageInfrastructureConstraintServiceExt serviceExt;
    private final VillageInfrastructureConstraintProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEINFRASTRUCTURECONSTRAINT_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINFRASTRUCTURECONSTRAINT.CREATE";
    private static final String VILLAGEINFRASTRUCTURECONSTRAINT_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINFRASTRUCTURECONSTRAINT.UPDATED";
    private static final String VILLAGEINFRASTRUCTURECONSTRAINT_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINFRASTRUCTURECONSTRAINT.DELETE";
    private static final String VILLAGEINFRASTRUCTURECONSTRAINT_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINFRASTRUCTURECONSTRAINT.GET";

    public VillageInfrastructureConstraintFacadeImpl(VillageInfrastructureConstraintServiceExt serviceExt, VillageInfrastructureConstraintProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageInfrastructureConstraint entry in the system.
     *
     * @param VillageInfrastructureConstraintDTO The VillageInfrastructureConstraint information to be saved
     * @return The saved VillageInfrastructureConstraint data
     */
    @Override
    public VillageInfrastructureConstraintDTO save(VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO) {
        log.debug("Entry - save(VillageInfrastructureConstraintDTO={})", VillageInfrastructureConstraintDTO);
        evaluator.evaluate(VILLAGEINFRASTRUCTURECONSTRAINT_CREATE, new HashMap<>());
        VillageInfrastructureConstraintDTO = preHookSave(VillageInfrastructureConstraintDTO);
        VillageInfrastructureConstraintDTO dto = serviceExt.save(VillageInfrastructureConstraintDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageInfrastructureConstraint entry.
     *
     * @param VillageInfrastructureConstraintDTO The VillageInfrastructureConstraint information to be updated
     * @return The updated VillageInfrastructureConstraint data
     */
    @Override
    public VillageInfrastructureConstraintDTO update(VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO) {
        log.debug("Entry - update(VillageInfrastructureConstraintDTO={})", VillageInfrastructureConstraintDTO);
        evaluator.evaluate(VILLAGEINFRASTRUCTURECONSTRAINT_UPDATE, new HashMap<>());
        VillageInfrastructureConstraintDTO = preHookUpdate(VillageInfrastructureConstraintDTO);
        VillageInfrastructureConstraintDTO dto = serviceExt.update(VillageInfrastructureConstraintDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageInfrastructureConstraints.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageInfrastructureConstraints
     */
    @Override
    public PageDTO<VillageInfrastructureConstraintDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEINFRASTRUCTURECONSTRAINT_GET, new HashMap<>());
        PageDTO<VillageInfrastructureConstraintDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageInfrastructureConstraint by their ID with a specified reason.
     *
     * @param id     The ID of the VillageInfrastructureConstraint to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEINFRASTRUCTURECONSTRAINT_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageInfrastructureConstraintDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageInfrastructureConstraint by their ID.
     *
     * @param id The ID of the VillageInfrastructureConstraint to retrieve
     * @return The VillageInfrastructureConstraint with the specified ID
     */
    @Override
    public VillageInfrastructureConstraintDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEINFRASTRUCTURECONSTRAINT_GET, new HashMap<>());
        VillageInfrastructureConstraintDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageInfrastructureConstraints by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageInfrastructureConstraintDTO
     */
    @Override
    public Set<VillageInfrastructureConstraintDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEINFRASTRUCTURECONSTRAINT_GET, new HashMap<>());
        Set<VillageInfrastructureConstraintDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of villageTopInfrastructureNeedList to a VillageInfrastructureConstraint identified by their ID.
     *
     * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
     * @param villageTopInfrastructureNeedList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageTopInfrastructureNeedToVillageInfrastructureConstraint(Long id, List<VillageTopInfrastructureNeedDTO> villageTopInfrastructureNeedList){
        serviceExt.addVillageTopInfrastructureNeedToVillageInfrastructureConstraint(id,villageTopInfrastructureNeedList);
    }
/**
     * Adds a list of villageTransportConnectivityIssList to a VillageInfrastructureConstraint identified by their ID.
     *
     * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
     * @param villageTransportConnectivityIssList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageTransportConnectivityIssToVillageInfrastructureConstraint(Long id, List<VillageTransportConnectivityIssDTO> villageTransportConnectivityIssList){
        serviceExt.addVillageTransportConnectivityIssToVillageInfrastructureConstraint(id,villageTransportConnectivityIssList);
    }
/**
     * Adds a list of villageWaterSupplyGapList to a VillageInfrastructureConstraint identified by their ID.
     *
     * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
     * @param villageWaterSupplyGapList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageWaterSupplyGapToVillageInfrastructureConstraint(Long id, List<VillageWaterSupplyGapDTO> villageWaterSupplyGapList){
        serviceExt.addVillageWaterSupplyGapToVillageInfrastructureConstraint(id,villageWaterSupplyGapList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageInfrastructureConstraintDTO postHookSave(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected VillageInfrastructureConstraintDTO preHookSave(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected VillageInfrastructureConstraintDTO postHookUpdate(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected VillageInfrastructureConstraintDTO preHookUpdate(VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO) {
        return VillageInfrastructureConstraintDTO;
    }

    protected VillageInfrastructureConstraintDTO postHookDelete(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageInfrastructureConstraintDTO postHookGetById(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected PageDTO<VillageInfrastructureConstraintDTO> postHookGetAll(PageDTO<VillageInfrastructureConstraintDTO> result) {
        return result;
    }
}
