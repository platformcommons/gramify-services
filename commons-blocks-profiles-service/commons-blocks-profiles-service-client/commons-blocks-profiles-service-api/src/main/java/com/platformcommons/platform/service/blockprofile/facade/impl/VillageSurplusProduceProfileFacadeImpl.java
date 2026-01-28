package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageSurplusProduceProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageSurplusProduceProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageSurplusProduceProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageSurplusProduceProfileFacadeImpl implements VillageSurplusProduceProfileFacade {

    private final VillageSurplusProduceProfileServiceExt serviceExt;
    private final VillageSurplusProduceProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGESURPLUSPRODUCEPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESURPLUSPRODUCEPROFILE.CREATE";
    private static final String VILLAGESURPLUSPRODUCEPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESURPLUSPRODUCEPROFILE.UPDATED";
    private static final String VILLAGESURPLUSPRODUCEPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESURPLUSPRODUCEPROFILE.DELETE";
    private static final String VILLAGESURPLUSPRODUCEPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESURPLUSPRODUCEPROFILE.GET";

    public VillageSurplusProduceProfileFacadeImpl(VillageSurplusProduceProfileServiceExt serviceExt, VillageSurplusProduceProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageSurplusProduceProfile entry in the system.
     *
     * @param VillageSurplusProduceProfileDTO The VillageSurplusProduceProfile information to be saved
     * @return The saved VillageSurplusProduceProfile data
     */
    @Override
    public VillageSurplusProduceProfileDTO save(VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO) {
        log.debug("Entry - save(VillageSurplusProduceProfileDTO={})", VillageSurplusProduceProfileDTO);
        evaluator.evaluate(VILLAGESURPLUSPRODUCEPROFILE_CREATE, new HashMap<>());
        VillageSurplusProduceProfileDTO = preHookSave(VillageSurplusProduceProfileDTO);
        VillageSurplusProduceProfileDTO dto = serviceExt.save(VillageSurplusProduceProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageSurplusProduceProfile entry.
     *
     * @param VillageSurplusProduceProfileDTO The VillageSurplusProduceProfile information to be updated
     * @return The updated VillageSurplusProduceProfile data
     */
    @Override
    public VillageSurplusProduceProfileDTO update(VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO) {
        log.debug("Entry - update(VillageSurplusProduceProfileDTO={})", VillageSurplusProduceProfileDTO);
        evaluator.evaluate(VILLAGESURPLUSPRODUCEPROFILE_UPDATE, new HashMap<>());
        VillageSurplusProduceProfileDTO = preHookUpdate(VillageSurplusProduceProfileDTO);
        VillageSurplusProduceProfileDTO dto = serviceExt.update(VillageSurplusProduceProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageSurplusProduceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSurplusProduceProfiles
     */
    @Override
    public PageDTO<VillageSurplusProduceProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGESURPLUSPRODUCEPROFILE_GET, new HashMap<>());
        PageDTO<VillageSurplusProduceProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageSurplusProduceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSurplusProduceProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGESURPLUSPRODUCEPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageSurplusProduceProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageSurplusProduceProfile by their ID.
     *
     * @param id The ID of the VillageSurplusProduceProfile to retrieve
     * @return The VillageSurplusProduceProfile with the specified ID
     */
    @Override
    public VillageSurplusProduceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGESURPLUSPRODUCEPROFILE_GET, new HashMap<>());
        VillageSurplusProduceProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageSurplusProduceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSurplusProduceProfileDTO
     */
    @Override
    public Set<VillageSurplusProduceProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGESURPLUSPRODUCEPROFILE_GET, new HashMap<>());
        Set<VillageSurplusProduceProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of surplusMovedThroughList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param surplusMovedThroughList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSurplusMovedThroughToVillageSurplusProduceProfile(Long id, List<SurplusMovedThroughDTO> surplusMovedThroughList){
        serviceExt.addSurplusMovedThroughToVillageSurplusProduceProfile(id,surplusMovedThroughList);
    }
/**
     * Adds a list of seasonalityOfSurplusList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param seasonalityOfSurplusList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSeasonalityOfSurplusToVillageSurplusProduceProfile(Long id, List<SeasonalityOfSurplusDTO> seasonalityOfSurplusList){
        serviceExt.addSeasonalityOfSurplusToVillageSurplusProduceProfile(id,seasonalityOfSurplusList);
    }
/**
     * Adds a list of keyConstraintsForSurplusExportList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param keyConstraintsForSurplusExportList  to be added
     * @since 1.0.0
     */
    @Override
    public void addKeyConstraintsForSurplusExportToVillageSurplusProduceProfile(Long id, List<KeyConstraintsForSurplusExportDTO> keyConstraintsForSurplusExportList){
        serviceExt.addKeyConstraintsForSurplusExportToVillageSurplusProduceProfile(id,keyConstraintsForSurplusExportList);
    }
/**
     * Adds a list of mainSurplusDestinationList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param mainSurplusDestinationList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMainSurplusDestinationToVillageSurplusProduceProfile(Long id, List<MainSurplusDestinationDTO> mainSurplusDestinationList){
        serviceExt.addMainSurplusDestinationToVillageSurplusProduceProfile(id,mainSurplusDestinationList);
    }
/**
     * Adds a list of surplusProduceTypeList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param surplusProduceTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSurplusProduceTypeToVillageSurplusProduceProfile(Long id, List<SurplusProduceTypeDTO> surplusProduceTypeList){
        serviceExt.addSurplusProduceTypeToVillageSurplusProduceProfile(id,surplusProduceTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageSurplusProduceProfileDTO postHookSave(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected VillageSurplusProduceProfileDTO preHookSave(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected VillageSurplusProduceProfileDTO postHookUpdate(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected VillageSurplusProduceProfileDTO preHookUpdate(VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO) {
        return VillageSurplusProduceProfileDTO;
    }

    protected VillageSurplusProduceProfileDTO postHookDelete(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSurplusProduceProfileDTO postHookGetById(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSurplusProduceProfileDTO> postHookGetAll(PageDTO<VillageSurplusProduceProfileDTO> result) {
        return result;
    }
}
