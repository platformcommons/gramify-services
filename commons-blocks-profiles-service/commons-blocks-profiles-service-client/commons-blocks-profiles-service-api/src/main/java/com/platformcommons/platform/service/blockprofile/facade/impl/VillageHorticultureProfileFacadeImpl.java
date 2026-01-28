package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageHorticultureProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageHorticultureProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageHorticultureProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageHorticultureProfileFacadeImpl implements VillageHorticultureProfileFacade {

    private final VillageHorticultureProfileServiceExt serviceExt;
    private final VillageHorticultureProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEHORTICULTUREPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHORTICULTUREPROFILE.CREATE";
    private static final String VILLAGEHORTICULTUREPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHORTICULTUREPROFILE.UPDATED";
    private static final String VILLAGEHORTICULTUREPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHORTICULTUREPROFILE.DELETE";
    private static final String VILLAGEHORTICULTUREPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHORTICULTUREPROFILE.GET";

    public VillageHorticultureProfileFacadeImpl(VillageHorticultureProfileServiceExt serviceExt, VillageHorticultureProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageHorticultureProfile entry in the system.
     *
     * @param VillageHorticultureProfileDTO The VillageHorticultureProfile information to be saved
     * @return The saved VillageHorticultureProfile data
     */
    @Override
    public VillageHorticultureProfileDTO save(VillageHorticultureProfileDTO VillageHorticultureProfileDTO) {
        log.debug("Entry - save(VillageHorticultureProfileDTO={})", VillageHorticultureProfileDTO);
        evaluator.evaluate(VILLAGEHORTICULTUREPROFILE_CREATE, new HashMap<>());
        VillageHorticultureProfileDTO = preHookSave(VillageHorticultureProfileDTO);
        VillageHorticultureProfileDTO dto = serviceExt.save(VillageHorticultureProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageHorticultureProfile entry.
     *
     * @param VillageHorticultureProfileDTO The VillageHorticultureProfile information to be updated
     * @return The updated VillageHorticultureProfile data
     */
    @Override
    public VillageHorticultureProfileDTO update(VillageHorticultureProfileDTO VillageHorticultureProfileDTO) {
        log.debug("Entry - update(VillageHorticultureProfileDTO={})", VillageHorticultureProfileDTO);
        evaluator.evaluate(VILLAGEHORTICULTUREPROFILE_UPDATE, new HashMap<>());
        VillageHorticultureProfileDTO = preHookUpdate(VillageHorticultureProfileDTO);
        VillageHorticultureProfileDTO dto = serviceExt.update(VillageHorticultureProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageHorticultureProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHorticultureProfiles
     */
    @Override
    public PageDTO<VillageHorticultureProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEHORTICULTUREPROFILE_GET, new HashMap<>());
        PageDTO<VillageHorticultureProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageHorticultureProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHorticultureProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEHORTICULTUREPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageHorticultureProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageHorticultureProfile by their ID.
     *
     * @param id The ID of the VillageHorticultureProfile to retrieve
     * @return The VillageHorticultureProfile with the specified ID
     */
    @Override
    public VillageHorticultureProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEHORTICULTUREPROFILE_GET, new HashMap<>());
        VillageHorticultureProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageHorticultureProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHorticultureProfileDTO
     */
    @Override
    public Set<VillageHorticultureProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEHORTICULTUREPROFILE_GET, new HashMap<>());
        Set<VillageHorticultureProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of horticultureProductList to a VillageHorticultureProfile identified by their ID.
     *
     * @param id               The ID of the VillageHorticultureProfile to add hobbies to
     * @param horticultureProductList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHorticultureProductToVillageHorticultureProfile(Long id, List<HorticultureProductDTO> horticultureProductList){
        serviceExt.addHorticultureProductToVillageHorticultureProfile(id,horticultureProductList);
    }
/**
     * Adds a list of productionSeasonList to a VillageHorticultureProfile identified by their ID.
     *
     * @param id               The ID of the VillageHorticultureProfile to add hobbies to
     * @param productionSeasonList  to be added
     * @since 1.0.0
     */
    @Override
    public void addProductionSeasonToVillageHorticultureProfile(Long id, List<ProductionSeasonDTO> productionSeasonList){
        serviceExt.addProductionSeasonToVillageHorticultureProfile(id,productionSeasonList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageHorticultureProfileDTO postHookSave(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected VillageHorticultureProfileDTO preHookSave(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected VillageHorticultureProfileDTO postHookUpdate(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected VillageHorticultureProfileDTO preHookUpdate(VillageHorticultureProfileDTO VillageHorticultureProfileDTO) {
        return VillageHorticultureProfileDTO;
    }

    protected VillageHorticultureProfileDTO postHookDelete(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHorticultureProfileDTO postHookGetById(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHorticultureProfileDTO> postHookGetAll(PageDTO<VillageHorticultureProfileDTO> result) {
        return result;
    }
}
