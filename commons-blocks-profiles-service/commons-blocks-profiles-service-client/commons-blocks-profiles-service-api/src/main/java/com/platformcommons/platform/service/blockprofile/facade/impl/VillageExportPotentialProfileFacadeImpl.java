package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageExportPotentialProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageExportPotentialProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageExportPotentialProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageExportPotentialProfileFacadeImpl implements VillageExportPotentialProfileFacade {

    private final VillageExportPotentialProfileServiceExt serviceExt;
    private final VillageExportPotentialProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEEXPORTPOTENTIALPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEXPORTPOTENTIALPROFILE.CREATE";
    private static final String VILLAGEEXPORTPOTENTIALPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEXPORTPOTENTIALPROFILE.UPDATED";
    private static final String VILLAGEEXPORTPOTENTIALPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEXPORTPOTENTIALPROFILE.DELETE";
    private static final String VILLAGEEXPORTPOTENTIALPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEXPORTPOTENTIALPROFILE.GET";

    public VillageExportPotentialProfileFacadeImpl(VillageExportPotentialProfileServiceExt serviceExt, VillageExportPotentialProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageExportPotentialProfile entry in the system.
     *
     * @param VillageExportPotentialProfileDTO The VillageExportPotentialProfile information to be saved
     * @return The saved VillageExportPotentialProfile data
     */
    @Override
    public VillageExportPotentialProfileDTO save(VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO) {
        log.debug("Entry - save(VillageExportPotentialProfileDTO={})", VillageExportPotentialProfileDTO);
        evaluator.evaluate(VILLAGEEXPORTPOTENTIALPROFILE_CREATE, new HashMap<>());
        VillageExportPotentialProfileDTO = preHookSave(VillageExportPotentialProfileDTO);
        VillageExportPotentialProfileDTO dto = serviceExt.save(VillageExportPotentialProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageExportPotentialProfile entry.
     *
     * @param VillageExportPotentialProfileDTO The VillageExportPotentialProfile information to be updated
     * @return The updated VillageExportPotentialProfile data
     */
    @Override
    public VillageExportPotentialProfileDTO update(VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO) {
        log.debug("Entry - update(VillageExportPotentialProfileDTO={})", VillageExportPotentialProfileDTO);
        evaluator.evaluate(VILLAGEEXPORTPOTENTIALPROFILE_UPDATE, new HashMap<>());
        VillageExportPotentialProfileDTO = preHookUpdate(VillageExportPotentialProfileDTO);
        VillageExportPotentialProfileDTO dto = serviceExt.update(VillageExportPotentialProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageExportPotentialProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageExportPotentialProfiles
     */
    @Override
    public PageDTO<VillageExportPotentialProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEEXPORTPOTENTIALPROFILE_GET, new HashMap<>());
        PageDTO<VillageExportPotentialProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageExportPotentialProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageExportPotentialProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEEXPORTPOTENTIALPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageExportPotentialProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageExportPotentialProfile by their ID.
     *
     * @param id The ID of the VillageExportPotentialProfile to retrieve
     * @return The VillageExportPotentialProfile with the specified ID
     */
    @Override
    public VillageExportPotentialProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEEXPORTPOTENTIALPROFILE_GET, new HashMap<>());
        VillageExportPotentialProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageExportPotentialProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageExportPotentialProfileDTO
     */
    @Override
    public Set<VillageExportPotentialProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEEXPORTPOTENTIALPROFILE_GET, new HashMap<>());
        Set<VillageExportPotentialProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of nicheProductBuyerTypeList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param nicheProductBuyerTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addNicheProductBuyerTypeToVillageExportPotentialProfile(Long id, List<NicheProductBuyerTypeDTO> nicheProductBuyerTypeList){
        serviceExt.addNicheProductBuyerTypeToVillageExportPotentialProfile(id,nicheProductBuyerTypeList);
    }
/**
     * Adds a list of surplusProduceTypeList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param surplusProduceTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSurplusProduceTypeToVillageExportPotentialProfile(Long id, List<SurplusProduceTypeDTO> surplusProduceTypeList){
        serviceExt.addSurplusProduceTypeToVillageExportPotentialProfile(id,surplusProduceTypeList);
    }
/**
     * Adds a list of mainSurplusMarketsOutsideBlockList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param mainSurplusMarketsOutsideBlockList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMainSurplusMarketsOutsideBlockToVillageExportPotentialProfile(Long id, List<MainSurplusMarketsOutsideBlockDTO> mainSurplusMarketsOutsideBlockList){
        serviceExt.addMainSurplusMarketsOutsideBlockToVillageExportPotentialProfile(id,mainSurplusMarketsOutsideBlockList);
    }
/**
     * Adds a list of nicheProductsAvailabilityList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param nicheProductsAvailabilityList  to be added
     * @since 1.0.0
     */
    @Override
    public void addNicheProductsAvailabilityToVillageExportPotentialProfile(Long id, List<NicheProductsAvailabilityDTO> nicheProductsAvailabilityList){
        serviceExt.addNicheProductsAvailabilityToVillageExportPotentialProfile(id,nicheProductsAvailabilityList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageExportPotentialProfileDTO postHookSave(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected VillageExportPotentialProfileDTO preHookSave(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected VillageExportPotentialProfileDTO postHookUpdate(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected VillageExportPotentialProfileDTO preHookUpdate(VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO) {
        return VillageExportPotentialProfileDTO;
    }

    protected VillageExportPotentialProfileDTO postHookDelete(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageExportPotentialProfileDTO postHookGetById(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageExportPotentialProfileDTO> postHookGetAll(PageDTO<VillageExportPotentialProfileDTO> result) {
        return result;
    }
}
