package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageIrrigationSystemTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageIrrigationSystemTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageIrrigationSystemTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageIrrigationSystemTypeFacadeImpl implements VillageIrrigationSystemTypeFacade {

    private final VillageIrrigationSystemTypeServiceExt serviceExt;
    private final VillageIrrigationSystemTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEIRRIGATIONSYSTEMTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONSYSTEMTYPE.CREATE";
    private static final String VILLAGEIRRIGATIONSYSTEMTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONSYSTEMTYPE.UPDATED";
    private static final String VILLAGEIRRIGATIONSYSTEMTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONSYSTEMTYPE.DELETE";
    private static final String VILLAGEIRRIGATIONSYSTEMTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEIRRIGATIONSYSTEMTYPE.GET";

    public VillageIrrigationSystemTypeFacadeImpl(VillageIrrigationSystemTypeServiceExt serviceExt, VillageIrrigationSystemTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageIrrigationSystemType entry in the system.
     *
     * @param VillageIrrigationSystemTypeDTO The VillageIrrigationSystemType information to be saved
     * @return The saved VillageIrrigationSystemType data
     */
    @Override
    public VillageIrrigationSystemTypeDTO save(VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO) {
        log.debug("Entry - save(VillageIrrigationSystemTypeDTO={})", VillageIrrigationSystemTypeDTO);
        evaluator.evaluate(VILLAGEIRRIGATIONSYSTEMTYPE_CREATE, new HashMap<>());
        VillageIrrigationSystemTypeDTO = preHookSave(VillageIrrigationSystemTypeDTO);
        VillageIrrigationSystemTypeDTO dto = serviceExt.save(VillageIrrigationSystemTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageIrrigationSystemType entry.
     *
     * @param VillageIrrigationSystemTypeDTO The VillageIrrigationSystemType information to be updated
     * @return The updated VillageIrrigationSystemType data
     */
    @Override
    public VillageIrrigationSystemTypeDTO update(VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO) {
        log.debug("Entry - update(VillageIrrigationSystemTypeDTO={})", VillageIrrigationSystemTypeDTO);
        evaluator.evaluate(VILLAGEIRRIGATIONSYSTEMTYPE_UPDATE, new HashMap<>());
        VillageIrrigationSystemTypeDTO = preHookUpdate(VillageIrrigationSystemTypeDTO);
        VillageIrrigationSystemTypeDTO dto = serviceExt.update(VillageIrrigationSystemTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageIrrigationSystemTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageIrrigationSystemTypes
     */
    @Override
    public PageDTO<VillageIrrigationSystemTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEIRRIGATIONSYSTEMTYPE_GET, new HashMap<>());
        PageDTO<VillageIrrigationSystemTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageIrrigationSystemType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageIrrigationSystemType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEIRRIGATIONSYSTEMTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageIrrigationSystemTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageIrrigationSystemType by their ID.
     *
     * @param id The ID of the VillageIrrigationSystemType to retrieve
     * @return The VillageIrrigationSystemType with the specified ID
     */
    @Override
    public VillageIrrigationSystemTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEIRRIGATIONSYSTEMTYPE_GET, new HashMap<>());
        VillageIrrigationSystemTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageIrrigationSystemTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageIrrigationSystemTypeDTO
     */
    @Override
    public Set<VillageIrrigationSystemTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEIRRIGATIONSYSTEMTYPE_GET, new HashMap<>());
        Set<VillageIrrigationSystemTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageIrrigationSystemTypeDTO postHookSave(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected VillageIrrigationSystemTypeDTO preHookSave(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected VillageIrrigationSystemTypeDTO postHookUpdate(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected VillageIrrigationSystemTypeDTO preHookUpdate(VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO) {
        return VillageIrrigationSystemTypeDTO;
    }

    protected VillageIrrigationSystemTypeDTO postHookDelete(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageIrrigationSystemTypeDTO postHookGetById(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageIrrigationSystemTypeDTO> postHookGetAll(PageDTO<VillageIrrigationSystemTypeDTO> result) {
        return result;
    }
}
