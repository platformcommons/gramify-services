package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageEducationInfrastructureFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageEducationInfrastructureProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageEducationInfrastructureServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageEducationInfrastructureFacadeImpl implements VillageEducationInfrastructureFacade {

    private final VillageEducationInfrastructureServiceExt serviceExt;
    private final VillageEducationInfrastructureProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEEDUCATIONINFRASTRUCTURE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONINFRASTRUCTURE.CREATE";
    private static final String VILLAGEEDUCATIONINFRASTRUCTURE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONINFRASTRUCTURE.UPDATED";
    private static final String VILLAGEEDUCATIONINFRASTRUCTURE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONINFRASTRUCTURE.DELETE";
    private static final String VILLAGEEDUCATIONINFRASTRUCTURE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONINFRASTRUCTURE.GET";

    public VillageEducationInfrastructureFacadeImpl(VillageEducationInfrastructureServiceExt serviceExt, VillageEducationInfrastructureProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageEducationInfrastructure entry in the system.
     *
     * @param VillageEducationInfrastructureDTO The VillageEducationInfrastructure information to be saved
     * @return The saved VillageEducationInfrastructure data
     */
    @Override
    public VillageEducationInfrastructureDTO save(VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO) {
        log.debug("Entry - save(VillageEducationInfrastructureDTO={})", VillageEducationInfrastructureDTO);
        evaluator.evaluate(VILLAGEEDUCATIONINFRASTRUCTURE_CREATE, new HashMap<>());
        VillageEducationInfrastructureDTO = preHookSave(VillageEducationInfrastructureDTO);
        VillageEducationInfrastructureDTO dto = serviceExt.save(VillageEducationInfrastructureDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageEducationInfrastructure entry.
     *
     * @param VillageEducationInfrastructureDTO The VillageEducationInfrastructure information to be updated
     * @return The updated VillageEducationInfrastructure data
     */
    @Override
    public VillageEducationInfrastructureDTO update(VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO) {
        log.debug("Entry - update(VillageEducationInfrastructureDTO={})", VillageEducationInfrastructureDTO);
        evaluator.evaluate(VILLAGEEDUCATIONINFRASTRUCTURE_UPDATE, new HashMap<>());
        VillageEducationInfrastructureDTO = preHookUpdate(VillageEducationInfrastructureDTO);
        VillageEducationInfrastructureDTO dto = serviceExt.update(VillageEducationInfrastructureDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageEducationInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageEducationInfrastructures
     */
    @Override
    public PageDTO<VillageEducationInfrastructureDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEEDUCATIONINFRASTRUCTURE_GET, new HashMap<>());
        PageDTO<VillageEducationInfrastructureDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageEducationInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageEducationInfrastructure to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEEDUCATIONINFRASTRUCTURE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageEducationInfrastructureDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageEducationInfrastructure by their ID.
     *
     * @param id The ID of the VillageEducationInfrastructure to retrieve
     * @return The VillageEducationInfrastructure with the specified ID
     */
    @Override
    public VillageEducationInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEEDUCATIONINFRASTRUCTURE_GET, new HashMap<>());
        VillageEducationInfrastructureDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageEducationInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEducationInfrastructureDTO
     */
    @Override
    public Set<VillageEducationInfrastructureDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEEDUCATIONINFRASTRUCTURE_GET, new HashMap<>());
        Set<VillageEducationInfrastructureDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of issuesInHigherEducationAccessList to a VillageEducationInfrastructure identified by their ID.
     *
     * @param id               The ID of the VillageEducationInfrastructure to add hobbies to
     * @param issuesInHigherEducationAccessList  to be added
     * @since 1.0.0
     */
    @Override
    public void addIssuesInHigherEducationAccessToVillageEducationInfrastructure(Long id, List<IssuesInHigherEducationAccessDTO> issuesInHigherEducationAccessList){
        serviceExt.addIssuesInHigherEducationAccessToVillageEducationInfrastructure(id,issuesInHigherEducationAccessList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageEducationInfrastructureDTO postHookSave(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageEducationInfrastructureDTO preHookSave(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageEducationInfrastructureDTO postHookUpdate(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageEducationInfrastructureDTO preHookUpdate(VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO) {
        return VillageEducationInfrastructureDTO;
    }

    protected VillageEducationInfrastructureDTO postHookDelete(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageEducationInfrastructureDTO postHookGetById(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageEducationInfrastructureDTO> postHookGetAll(PageDTO<VillageEducationInfrastructureDTO> result) {
        return result;
    }
}
