package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageElectricityInfrastructurFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageElectricityInfrastructurProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageElectricityInfrastructurServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageElectricityInfrastructurFacadeImpl implements VillageElectricityInfrastructurFacade {

    private final VillageElectricityInfrastructurServiceExt serviceExt;
    private final VillageElectricityInfrastructurProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEELECTRICITYINFRASTRUCTUR_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEELECTRICITYINFRASTRUCTUR.CREATE";
    private static final String VILLAGEELECTRICITYINFRASTRUCTUR_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEELECTRICITYINFRASTRUCTUR.UPDATED";
    private static final String VILLAGEELECTRICITYINFRASTRUCTUR_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEELECTRICITYINFRASTRUCTUR.DELETE";
    private static final String VILLAGEELECTRICITYINFRASTRUCTUR_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEELECTRICITYINFRASTRUCTUR.GET";

    public VillageElectricityInfrastructurFacadeImpl(VillageElectricityInfrastructurServiceExt serviceExt, VillageElectricityInfrastructurProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageElectricityInfrastructur entry in the system.
     *
     * @param VillageElectricityInfrastructurDTO The VillageElectricityInfrastructur information to be saved
     * @return The saved VillageElectricityInfrastructur data
     */
    @Override
    public VillageElectricityInfrastructurDTO save(VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO) {
        log.debug("Entry - save(VillageElectricityInfrastructurDTO={})", VillageElectricityInfrastructurDTO);
        evaluator.evaluate(VILLAGEELECTRICITYINFRASTRUCTUR_CREATE, new HashMap<>());
        VillageElectricityInfrastructurDTO = preHookSave(VillageElectricityInfrastructurDTO);
        VillageElectricityInfrastructurDTO dto = serviceExt.save(VillageElectricityInfrastructurDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageElectricityInfrastructur entry.
     *
     * @param VillageElectricityInfrastructurDTO The VillageElectricityInfrastructur information to be updated
     * @return The updated VillageElectricityInfrastructur data
     */
    @Override
    public VillageElectricityInfrastructurDTO update(VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO) {
        log.debug("Entry - update(VillageElectricityInfrastructurDTO={})", VillageElectricityInfrastructurDTO);
        evaluator.evaluate(VILLAGEELECTRICITYINFRASTRUCTUR_UPDATE, new HashMap<>());
        VillageElectricityInfrastructurDTO = preHookUpdate(VillageElectricityInfrastructurDTO);
        VillageElectricityInfrastructurDTO dto = serviceExt.update(VillageElectricityInfrastructurDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageElectricityInfrastructurs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageElectricityInfrastructurs
     */
    @Override
    public PageDTO<VillageElectricityInfrastructurDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEELECTRICITYINFRASTRUCTUR_GET, new HashMap<>());
        PageDTO<VillageElectricityInfrastructurDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageElectricityInfrastructur by their ID with a specified reason.
     *
     * @param id     The ID of the VillageElectricityInfrastructur to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEELECTRICITYINFRASTRUCTUR_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageElectricityInfrastructurDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageElectricityInfrastructur by their ID.
     *
     * @param id The ID of the VillageElectricityInfrastructur to retrieve
     * @return The VillageElectricityInfrastructur with the specified ID
     */
    @Override
    public VillageElectricityInfrastructurDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEELECTRICITYINFRASTRUCTUR_GET, new HashMap<>());
        VillageElectricityInfrastructurDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageElectricityInfrastructurs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageElectricityInfrastructurDTO
     */
    @Override
    public Set<VillageElectricityInfrastructurDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEELECTRICITYINFRASTRUCTUR_GET, new HashMap<>());
        Set<VillageElectricityInfrastructurDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of powerCutSeasonList to a VillageElectricityInfrastructur identified by their ID.
     *
     * @param id               The ID of the VillageElectricityInfrastructur to add hobbies to
     * @param powerCutSeasonList  to be added
     * @since 1.0.0
     */
    @Override
    public void addPowerCutSeasonToVillageElectricityInfrastructur(Long id, List<PowerCutSeasonDTO> powerCutSeasonList){
        serviceExt.addPowerCutSeasonToVillageElectricityInfrastructur(id,powerCutSeasonList);
    }
/**
     * Adds a list of renewableInfraTypeList to a VillageElectricityInfrastructur identified by their ID.
     *
     * @param id               The ID of the VillageElectricityInfrastructur to add hobbies to
     * @param renewableInfraTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addRenewableInfraTypeToVillageElectricityInfrastructur(Long id, List<RenewableInfraTypeDTO> renewableInfraTypeList){
        serviceExt.addRenewableInfraTypeToVillageElectricityInfrastructur(id,renewableInfraTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageElectricityInfrastructurDTO postHookSave(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected VillageElectricityInfrastructurDTO preHookSave(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected VillageElectricityInfrastructurDTO postHookUpdate(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected VillageElectricityInfrastructurDTO preHookUpdate(VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO) {
        return VillageElectricityInfrastructurDTO;
    }

    protected VillageElectricityInfrastructurDTO postHookDelete(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageElectricityInfrastructurDTO postHookGetById(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected PageDTO<VillageElectricityInfrastructurDTO> postHookGetAll(PageDTO<VillageElectricityInfrastructurDTO> result) {
        return result;
    }
}
