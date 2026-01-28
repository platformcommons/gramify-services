package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageOtherInfrastructureFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageOtherInfrastructureProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageOtherInfrastructureServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageOtherInfrastructureFacadeImpl implements VillageOtherInfrastructureFacade {

    private final VillageOtherInfrastructureServiceExt serviceExt;
    private final VillageOtherInfrastructureProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEOTHERINFRASTRUCTURE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEOTHERINFRASTRUCTURE.CREATE";
    private static final String VILLAGEOTHERINFRASTRUCTURE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEOTHERINFRASTRUCTURE.UPDATED";
    private static final String VILLAGEOTHERINFRASTRUCTURE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEOTHERINFRASTRUCTURE.DELETE";
    private static final String VILLAGEOTHERINFRASTRUCTURE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEOTHERINFRASTRUCTURE.GET";

    public VillageOtherInfrastructureFacadeImpl(VillageOtherInfrastructureServiceExt serviceExt, VillageOtherInfrastructureProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageOtherInfrastructure entry in the system.
     *
     * @param VillageOtherInfrastructureDTO The VillageOtherInfrastructure information to be saved
     * @return The saved VillageOtherInfrastructure data
     */
    @Override
    public VillageOtherInfrastructureDTO save(VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO) {
        log.debug("Entry - save(VillageOtherInfrastructureDTO={})", VillageOtherInfrastructureDTO);
        evaluator.evaluate(VILLAGEOTHERINFRASTRUCTURE_CREATE, new HashMap<>());
        VillageOtherInfrastructureDTO = preHookSave(VillageOtherInfrastructureDTO);
        VillageOtherInfrastructureDTO dto = serviceExt.save(VillageOtherInfrastructureDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageOtherInfrastructure entry.
     *
     * @param VillageOtherInfrastructureDTO The VillageOtherInfrastructure information to be updated
     * @return The updated VillageOtherInfrastructure data
     */
    @Override
    public VillageOtherInfrastructureDTO update(VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO) {
        log.debug("Entry - update(VillageOtherInfrastructureDTO={})", VillageOtherInfrastructureDTO);
        evaluator.evaluate(VILLAGEOTHERINFRASTRUCTURE_UPDATE, new HashMap<>());
        VillageOtherInfrastructureDTO = preHookUpdate(VillageOtherInfrastructureDTO);
        VillageOtherInfrastructureDTO dto = serviceExt.update(VillageOtherInfrastructureDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageOtherInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageOtherInfrastructures
     */
    @Override
    public PageDTO<VillageOtherInfrastructureDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEOTHERINFRASTRUCTURE_GET, new HashMap<>());
        PageDTO<VillageOtherInfrastructureDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageOtherInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageOtherInfrastructure to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEOTHERINFRASTRUCTURE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageOtherInfrastructureDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageOtherInfrastructure by their ID.
     *
     * @param id The ID of the VillageOtherInfrastructure to retrieve
     * @return The VillageOtherInfrastructure with the specified ID
     */
    @Override
    public VillageOtherInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEOTHERINFRASTRUCTURE_GET, new HashMap<>());
        VillageOtherInfrastructureDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageOtherInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageOtherInfrastructureDTO
     */
    @Override
    public Set<VillageOtherInfrastructureDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEOTHERINFRASTRUCTURE_GET, new HashMap<>());
        Set<VillageOtherInfrastructureDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageOtherInfrastructureDTO postHookSave(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected VillageOtherInfrastructureDTO preHookSave(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected VillageOtherInfrastructureDTO postHookUpdate(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected VillageOtherInfrastructureDTO preHookUpdate(VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO) {
        return VillageOtherInfrastructureDTO;
    }

    protected VillageOtherInfrastructureDTO postHookDelete(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageOtherInfrastructureDTO postHookGetById(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageOtherInfrastructureDTO> postHookGetAll(PageDTO<VillageOtherInfrastructureDTO> result) {
        return result;
    }
}
