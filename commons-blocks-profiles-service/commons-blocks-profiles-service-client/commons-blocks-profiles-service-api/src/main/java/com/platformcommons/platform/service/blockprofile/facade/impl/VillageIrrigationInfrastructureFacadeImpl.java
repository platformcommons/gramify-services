package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageIrrigationInfrastructureFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageIrrigationInfrastructureProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageIrrigationInfrastructureServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageIrrigationInfrastructureFacadeImpl implements VillageIrrigationInfrastructureFacade {

    private final VillageIrrigationInfrastructureServiceExt serviceExt;
    private final VillageIrrigationInfrastructureProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEIRRIGATIONINFRASTRUCTURE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONINFRASTRUCTURE.CREATE";
    private static final String VILLAGEIRRIGATIONINFRASTRUCTURE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONINFRASTRUCTURE.UPDATED";
    private static final String VILLAGEIRRIGATIONINFRASTRUCTURE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONINFRASTRUCTURE.DELETE";
    private static final String VILLAGEIRRIGATIONINFRASTRUCTURE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONINFRASTRUCTURE.GET";

    public VillageIrrigationInfrastructureFacadeImpl(VillageIrrigationInfrastructureServiceExt serviceExt, VillageIrrigationInfrastructureProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageIrrigationInfrastructure entry in the system.
     *
     * @param VillageIrrigationInfrastructureDTO The VillageIrrigationInfrastructure information to be saved
     * @return The saved VillageIrrigationInfrastructure data
     */
    @Override
    public VillageIrrigationInfrastructureDTO save(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO) {
        log.debug("Entry - save(VillageIrrigationInfrastructureDTO={})", VillageIrrigationInfrastructureDTO);
        evaluator.evaluate(VILLAGEIRRIGATIONINFRASTRUCTURE_CREATE, new HashMap<>());
        VillageIrrigationInfrastructureDTO = preHookSave(VillageIrrigationInfrastructureDTO);
        VillageIrrigationInfrastructureDTO dto = serviceExt.save(VillageIrrigationInfrastructureDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageIrrigationInfrastructure entry.
     *
     * @param VillageIrrigationInfrastructureDTO The VillageIrrigationInfrastructure information to be updated
     * @return The updated VillageIrrigationInfrastructure data
     */
    @Override
    public VillageIrrigationInfrastructureDTO update(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO) {
        log.debug("Entry - update(VillageIrrigationInfrastructureDTO={})", VillageIrrigationInfrastructureDTO);
        evaluator.evaluate(VILLAGEIRRIGATIONINFRASTRUCTURE_UPDATE, new HashMap<>());
        VillageIrrigationInfrastructureDTO = preHookUpdate(VillageIrrigationInfrastructureDTO);
        VillageIrrigationInfrastructureDTO dto = serviceExt.update(VillageIrrigationInfrastructureDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageIrrigationInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageIrrigationInfrastructures
     */
    @Override
    public PageDTO<VillageIrrigationInfrastructureDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEIRRIGATIONINFRASTRUCTURE_GET, new HashMap<>());
        PageDTO<VillageIrrigationInfrastructureDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageIrrigationInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageIrrigationInfrastructure to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEIRRIGATIONINFRASTRUCTURE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageIrrigationInfrastructureDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageIrrigationInfrastructure by their ID.
     *
     * @param id The ID of the VillageIrrigationInfrastructure to retrieve
     * @return The VillageIrrigationInfrastructure with the specified ID
     */
    @Override
    public VillageIrrigationInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEIRRIGATIONINFRASTRUCTURE_GET, new HashMap<>());
        VillageIrrigationInfrastructureDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageIrrigationInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageIrrigationInfrastructureDTO
     */
    @Override
    public Set<VillageIrrigationInfrastructureDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEIRRIGATIONINFRASTRUCTURE_GET, new HashMap<>());
        Set<VillageIrrigationInfrastructureDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageIrrigationInfrastructureDTO postHookSave(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageIrrigationInfrastructureDTO preHookSave(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageIrrigationInfrastructureDTO postHookUpdate(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageIrrigationInfrastructureDTO preHookUpdate(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO) {
        return VillageIrrigationInfrastructureDTO;
    }

    protected VillageIrrigationInfrastructureDTO postHookDelete(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageIrrigationInfrastructureDTO postHookGetById(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageIrrigationInfrastructureDTO> postHookGetAll(PageDTO<VillageIrrigationInfrastructureDTO> result) {
        return result;
    }
}
