package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MachinesInDemandFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MachinesInDemandProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MachinesInDemandServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MachinesInDemandFacadeImpl implements MachinesInDemandFacade {

    private final MachinesInDemandServiceExt serviceExt;
    private final MachinesInDemandProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MACHINESINDEMAND_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MACHINESINDEMAND.CREATE";
    private static final String MACHINESINDEMAND_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MACHINESINDEMAND.UPDATED";
    private static final String MACHINESINDEMAND_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MACHINESINDEMAND.DELETE";
    private static final String MACHINESINDEMAND_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MACHINESINDEMAND.GET";

    public MachinesInDemandFacadeImpl(MachinesInDemandServiceExt serviceExt, MachinesInDemandProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MachinesInDemand entry in the system.
     *
     * @param MachinesInDemandDTO The MachinesInDemand information to be saved
     * @return The saved MachinesInDemand data
     */
    @Override
    public MachinesInDemandDTO save(MachinesInDemandDTO MachinesInDemandDTO) {
        log.debug("Entry - save(MachinesInDemandDTO={})", MachinesInDemandDTO);
        evaluator.evaluate(MACHINESINDEMAND_CREATE, new HashMap<>());
        MachinesInDemandDTO = preHookSave(MachinesInDemandDTO);
        MachinesInDemandDTO dto = serviceExt.save(MachinesInDemandDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MachinesInDemand entry.
     *
     * @param MachinesInDemandDTO The MachinesInDemand information to be updated
     * @return The updated MachinesInDemand data
     */
    @Override
    public MachinesInDemandDTO update(MachinesInDemandDTO MachinesInDemandDTO) {
        log.debug("Entry - update(MachinesInDemandDTO={})", MachinesInDemandDTO);
        evaluator.evaluate(MACHINESINDEMAND_UPDATE, new HashMap<>());
        MachinesInDemandDTO = preHookUpdate(MachinesInDemandDTO);
        MachinesInDemandDTO dto = serviceExt.update(MachinesInDemandDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MachinesInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MachinesInDemands
     */
    @Override
    public PageDTO<MachinesInDemandDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MACHINESINDEMAND_GET, new HashMap<>());
        PageDTO<MachinesInDemandDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MachinesInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the MachinesInDemand to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MACHINESINDEMAND_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MachinesInDemandDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MachinesInDemand by their ID.
     *
     * @param id The ID of the MachinesInDemand to retrieve
     * @return The MachinesInDemand with the specified ID
     */
    @Override
    public MachinesInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MACHINESINDEMAND_GET, new HashMap<>());
        MachinesInDemandDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MachinesInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MachinesInDemandDTO
     */
    @Override
    public Set<MachinesInDemandDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MACHINESINDEMAND_GET, new HashMap<>());
        Set<MachinesInDemandDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MachinesInDemandDTO postHookSave(MachinesInDemandDTO dto) {
        return dto;
    }

    protected MachinesInDemandDTO preHookSave(MachinesInDemandDTO dto) {
        return dto;
    }

    protected MachinesInDemandDTO postHookUpdate(MachinesInDemandDTO dto) {
        return dto;
    }

    protected MachinesInDemandDTO preHookUpdate(MachinesInDemandDTO MachinesInDemandDTO) {
        return MachinesInDemandDTO;
    }

    protected MachinesInDemandDTO postHookDelete(MachinesInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MachinesInDemandDTO postHookGetById(MachinesInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<MachinesInDemandDTO> postHookGetAll(PageDTO<MachinesInDemandDTO> result) {
        return result;
    }
}
