package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageConsumptionPatternFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageConsumptionPatternProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageConsumptionPatternServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageConsumptionPatternFacadeImpl implements VillageConsumptionPatternFacade {

    private final VillageConsumptionPatternServiceExt serviceExt;
    private final VillageConsumptionPatternProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECONSUMPTIONPATTERN_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECONSUMPTIONPATTERN.CREATE";
    private static final String VILLAGECONSUMPTIONPATTERN_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECONSUMPTIONPATTERN.UPDATED";
    private static final String VILLAGECONSUMPTIONPATTERN_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECONSUMPTIONPATTERN.DELETE";
    private static final String VILLAGECONSUMPTIONPATTERN_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECONSUMPTIONPATTERN.GET";

    public VillageConsumptionPatternFacadeImpl(VillageConsumptionPatternServiceExt serviceExt, VillageConsumptionPatternProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageConsumptionPattern entry in the system.
     *
     * @param VillageConsumptionPatternDTO The VillageConsumptionPattern information to be saved
     * @return The saved VillageConsumptionPattern data
     */
    @Override
    public VillageConsumptionPatternDTO save(VillageConsumptionPatternDTO VillageConsumptionPatternDTO) {
        log.debug("Entry - save(VillageConsumptionPatternDTO={})", VillageConsumptionPatternDTO);
        evaluator.evaluate(VILLAGECONSUMPTIONPATTERN_CREATE, new HashMap<>());
        VillageConsumptionPatternDTO = preHookSave(VillageConsumptionPatternDTO);
        VillageConsumptionPatternDTO dto = serviceExt.save(VillageConsumptionPatternDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageConsumptionPattern entry.
     *
     * @param VillageConsumptionPatternDTO The VillageConsumptionPattern information to be updated
     * @return The updated VillageConsumptionPattern data
     */
    @Override
    public VillageConsumptionPatternDTO update(VillageConsumptionPatternDTO VillageConsumptionPatternDTO) {
        log.debug("Entry - update(VillageConsumptionPatternDTO={})", VillageConsumptionPatternDTO);
        evaluator.evaluate(VILLAGECONSUMPTIONPATTERN_UPDATE, new HashMap<>());
        VillageConsumptionPatternDTO = preHookUpdate(VillageConsumptionPatternDTO);
        VillageConsumptionPatternDTO dto = serviceExt.update(VillageConsumptionPatternDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageConsumptionPatterns.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageConsumptionPatterns
     */
    @Override
    public PageDTO<VillageConsumptionPatternDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECONSUMPTIONPATTERN_GET, new HashMap<>());
        PageDTO<VillageConsumptionPatternDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageConsumptionPattern by their ID with a specified reason.
     *
     * @param id     The ID of the VillageConsumptionPattern to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECONSUMPTIONPATTERN_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageConsumptionPatternDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageConsumptionPattern by their ID.
     *
     * @param id The ID of the VillageConsumptionPattern to retrieve
     * @return The VillageConsumptionPattern with the specified ID
     */
    @Override
    public VillageConsumptionPatternDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECONSUMPTIONPATTERN_GET, new HashMap<>());
        VillageConsumptionPatternDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageConsumptionPatterns by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageConsumptionPatternDTO
     */
    @Override
    public Set<VillageConsumptionPatternDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECONSUMPTIONPATTERN_GET, new HashMap<>());
        Set<VillageConsumptionPatternDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of commonConsumerGoodsPurchasedList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param commonConsumerGoodsPurchasedList  to be added
     * @since 1.0.0
     */
    @Override
    public void addCommonConsumerGoodsPurchasedToVillageConsumptionPattern(Long id, List<CommonConsumerGoodsPurchasedDTO> commonConsumerGoodsPurchasedList){
        serviceExt.addCommonConsumerGoodsPurchasedToVillageConsumptionPattern(id,commonConsumerGoodsPurchasedList);
    }
/**
     * Adds a list of itemsUsuallyBoughtFromOutsideList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param itemsUsuallyBoughtFromOutsideList  to be added
     * @since 1.0.0
     */
    @Override
    public void addItemsUsuallyBoughtFromOutsideToVillageConsumptionPattern(Long id, List<ItemsUsuallyBoughtFromOutsideDTO> itemsUsuallyBoughtFromOutsideList){
        serviceExt.addItemsUsuallyBoughtFromOutsideToVillageConsumptionPattern(id,itemsUsuallyBoughtFromOutsideList);
    }
/**
     * Adds a list of stapleFoodsConsumedList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param stapleFoodsConsumedList  to be added
     * @since 1.0.0
     */
    @Override
    public void addStapleFoodsConsumedToVillageConsumptionPattern(Long id, List<StapleFoodsConsumedDTO> stapleFoodsConsumedList){
        serviceExt.addStapleFoodsConsumedToVillageConsumptionPattern(id,stapleFoodsConsumedList);
    }
/**
     * Adds a list of itemsUsuallyBoughtLocallyList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param itemsUsuallyBoughtLocallyList  to be added
     * @since 1.0.0
     */
    @Override
    public void addItemsUsuallyBoughtLocallyToVillageConsumptionPattern(Long id, List<ItemsUsuallyBoughtLocallyDTO> itemsUsuallyBoughtLocallyList){
        serviceExt.addItemsUsuallyBoughtLocallyToVillageConsumptionPattern(id,itemsUsuallyBoughtLocallyList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageConsumptionPatternDTO postHookSave(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected VillageConsumptionPatternDTO preHookSave(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected VillageConsumptionPatternDTO postHookUpdate(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected VillageConsumptionPatternDTO preHookUpdate(VillageConsumptionPatternDTO VillageConsumptionPatternDTO) {
        return VillageConsumptionPatternDTO;
    }

    protected VillageConsumptionPatternDTO postHookDelete(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageConsumptionPatternDTO postHookGetById(VillageConsumptionPatternDTO dto) {
        return dto;
    }

    protected PageDTO<VillageConsumptionPatternDTO> postHookGetAll(PageDTO<VillageConsumptionPatternDTO> result) {
        return result;
    }
}
