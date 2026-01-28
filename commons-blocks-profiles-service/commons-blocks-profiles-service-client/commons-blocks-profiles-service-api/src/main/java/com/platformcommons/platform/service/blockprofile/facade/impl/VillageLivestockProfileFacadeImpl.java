package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageLivestockProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageLivestockProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageLivestockProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageLivestockProfileFacadeImpl implements VillageLivestockProfileFacade {

    private final VillageLivestockProfileServiceExt serviceExt;
    private final VillageLivestockProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGELIVESTOCKPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELIVESTOCKPROFILE.CREATE";
    private static final String VILLAGELIVESTOCKPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELIVESTOCKPROFILE.UPDATED";
    private static final String VILLAGELIVESTOCKPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELIVESTOCKPROFILE.DELETE";
    private static final String VILLAGELIVESTOCKPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELIVESTOCKPROFILE.GET";

    public VillageLivestockProfileFacadeImpl(VillageLivestockProfileServiceExt serviceExt, VillageLivestockProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageLivestockProfile entry in the system.
     *
     * @param VillageLivestockProfileDTO The VillageLivestockProfile information to be saved
     * @return The saved VillageLivestockProfile data
     */
    @Override
    public VillageLivestockProfileDTO save(VillageLivestockProfileDTO VillageLivestockProfileDTO) {
        log.debug("Entry - save(VillageLivestockProfileDTO={})", VillageLivestockProfileDTO);
        evaluator.evaluate(VILLAGELIVESTOCKPROFILE_CREATE, new HashMap<>());
        VillageLivestockProfileDTO = preHookSave(VillageLivestockProfileDTO);
        VillageLivestockProfileDTO dto = serviceExt.save(VillageLivestockProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageLivestockProfile entry.
     *
     * @param VillageLivestockProfileDTO The VillageLivestockProfile information to be updated
     * @return The updated VillageLivestockProfile data
     */
    @Override
    public VillageLivestockProfileDTO update(VillageLivestockProfileDTO VillageLivestockProfileDTO) {
        log.debug("Entry - update(VillageLivestockProfileDTO={})", VillageLivestockProfileDTO);
        evaluator.evaluate(VILLAGELIVESTOCKPROFILE_UPDATE, new HashMap<>());
        VillageLivestockProfileDTO = preHookUpdate(VillageLivestockProfileDTO);
        VillageLivestockProfileDTO dto = serviceExt.update(VillageLivestockProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageLivestockProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageLivestockProfiles
     */
    @Override
    public PageDTO<VillageLivestockProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGELIVESTOCKPROFILE_GET, new HashMap<>());
        PageDTO<VillageLivestockProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageLivestockProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageLivestockProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGELIVESTOCKPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageLivestockProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageLivestockProfile by their ID.
     *
     * @param id The ID of the VillageLivestockProfile to retrieve
     * @return The VillageLivestockProfile with the specified ID
     */
    @Override
    public VillageLivestockProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGELIVESTOCKPROFILE_GET, new HashMap<>());
        VillageLivestockProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageLivestockProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageLivestockProfileDTO
     */
    @Override
    public Set<VillageLivestockProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGELIVESTOCKPROFILE_GET, new HashMap<>());
        Set<VillageLivestockProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of productionSeasonalityList to a VillageLivestockProfile identified by their ID.
     *
     * @param id               The ID of the VillageLivestockProfile to add hobbies to
     * @param productionSeasonalityList  to be added
     * @since 1.0.0
     */
    @Override
    public void addProductionSeasonalityToVillageLivestockProfile(Long id, List<ProductionSeasonalityDTO> productionSeasonalityList){
        serviceExt.addProductionSeasonalityToVillageLivestockProfile(id,productionSeasonalityList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageLivestockProfileDTO postHookSave(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected VillageLivestockProfileDTO preHookSave(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected VillageLivestockProfileDTO postHookUpdate(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected VillageLivestockProfileDTO preHookUpdate(VillageLivestockProfileDTO VillageLivestockProfileDTO) {
        return VillageLivestockProfileDTO;
    }

    protected VillageLivestockProfileDTO postHookDelete(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageLivestockProfileDTO postHookGetById(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageLivestockProfileDTO> postHookGetAll(PageDTO<VillageLivestockProfileDTO> result) {
        return result;
    }
}
