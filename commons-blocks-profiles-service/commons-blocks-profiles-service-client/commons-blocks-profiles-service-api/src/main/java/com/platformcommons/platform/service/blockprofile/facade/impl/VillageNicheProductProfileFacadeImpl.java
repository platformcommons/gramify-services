package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageNicheProductProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageNicheProductProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageNicheProductProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageNicheProductProfileFacadeImpl implements VillageNicheProductProfileFacade {

    private final VillageNicheProductProfileServiceExt serviceExt;
    private final VillageNicheProductProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGENICHEPRODUCTPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGENICHEPRODUCTPROFILE.CREATE";
    private static final String VILLAGENICHEPRODUCTPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGENICHEPRODUCTPROFILE.UPDATED";
    private static final String VILLAGENICHEPRODUCTPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGENICHEPRODUCTPROFILE.DELETE";
    private static final String VILLAGENICHEPRODUCTPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGENICHEPRODUCTPROFILE.GET";

    public VillageNicheProductProfileFacadeImpl(VillageNicheProductProfileServiceExt serviceExt, VillageNicheProductProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageNicheProductProfile entry in the system.
     *
     * @param VillageNicheProductProfileDTO The VillageNicheProductProfile information to be saved
     * @return The saved VillageNicheProductProfile data
     */
    @Override
    public VillageNicheProductProfileDTO save(VillageNicheProductProfileDTO VillageNicheProductProfileDTO) {
        log.debug("Entry - save(VillageNicheProductProfileDTO={})", VillageNicheProductProfileDTO);
        evaluator.evaluate(VILLAGENICHEPRODUCTPROFILE_CREATE, new HashMap<>());
        VillageNicheProductProfileDTO = preHookSave(VillageNicheProductProfileDTO);
        VillageNicheProductProfileDTO dto = serviceExt.save(VillageNicheProductProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageNicheProductProfile entry.
     *
     * @param VillageNicheProductProfileDTO The VillageNicheProductProfile information to be updated
     * @return The updated VillageNicheProductProfile data
     */
    @Override
    public VillageNicheProductProfileDTO update(VillageNicheProductProfileDTO VillageNicheProductProfileDTO) {
        log.debug("Entry - update(VillageNicheProductProfileDTO={})", VillageNicheProductProfileDTO);
        evaluator.evaluate(VILLAGENICHEPRODUCTPROFILE_UPDATE, new HashMap<>());
        VillageNicheProductProfileDTO = preHookUpdate(VillageNicheProductProfileDTO);
        VillageNicheProductProfileDTO dto = serviceExt.update(VillageNicheProductProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageNicheProductProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageNicheProductProfiles
     */
    @Override
    public PageDTO<VillageNicheProductProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGENICHEPRODUCTPROFILE_GET, new HashMap<>());
        PageDTO<VillageNicheProductProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageNicheProductProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageNicheProductProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGENICHEPRODUCTPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageNicheProductProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageNicheProductProfile by their ID.
     *
     * @param id The ID of the VillageNicheProductProfile to retrieve
     * @return The VillageNicheProductProfile with the specified ID
     */
    @Override
    public VillageNicheProductProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGENICHEPRODUCTPROFILE_GET, new HashMap<>());
        VillageNicheProductProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageNicheProductProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageNicheProductProfileDTO
     */
    @Override
    public Set<VillageNicheProductProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGENICHEPRODUCTPROFILE_GET, new HashMap<>());
        Set<VillageNicheProductProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of mainNicheMarketList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param mainNicheMarketList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMainNicheMarketToVillageNicheProductProfile(Long id, List<MainNicheMarketDTO> mainNicheMarketList){
        serviceExt.addMainNicheMarketToVillageNicheProductProfile(id,mainNicheMarketList);
    }
/**
     * Adds a list of supportNeededForNicheGrowthList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param supportNeededForNicheGrowthList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSupportNeededForNicheGrowthToVillageNicheProductProfile(Long id, List<SupportNeededForNicheGrowthDTO> supportNeededForNicheGrowthList){
        serviceExt.addSupportNeededForNicheGrowthToVillageNicheProductProfile(id,supportNeededForNicheGrowthList);
    }
/**
     * Adds a list of nicheProductsAvailabilityList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param nicheProductsAvailabilityList  to be added
     * @since 1.0.0
     */
    @Override
    public void addNicheProductsAvailabilityToVillageNicheProductProfile(Long id, List<NicheProductsAvailabilityDTO> nicheProductsAvailabilityList){
        serviceExt.addNicheProductsAvailabilityToVillageNicheProductProfile(id,nicheProductsAvailabilityList);
    }
/**
     * Adds a list of nicheProductBuyerTypeList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param nicheProductBuyerTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addNicheProductBuyerTypeToVillageNicheProductProfile(Long id, List<NicheProductBuyerTypeDTO> nicheProductBuyerTypeList){
        serviceExt.addNicheProductBuyerTypeToVillageNicheProductProfile(id,nicheProductBuyerTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageNicheProductProfileDTO postHookSave(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected VillageNicheProductProfileDTO preHookSave(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected VillageNicheProductProfileDTO postHookUpdate(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected VillageNicheProductProfileDTO preHookUpdate(VillageNicheProductProfileDTO VillageNicheProductProfileDTO) {
        return VillageNicheProductProfileDTO;
    }

    protected VillageNicheProductProfileDTO postHookDelete(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageNicheProductProfileDTO postHookGetById(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageNicheProductProfileDTO> postHookGetAll(PageDTO<VillageNicheProductProfileDTO> result) {
        return result;
    }
}
