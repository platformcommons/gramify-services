package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.NonAgriculturalMarketActiviesFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.NonAgriculturalMarketActiviesProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.NonAgriculturalMarketActiviesServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class NonAgriculturalMarketActiviesFacadeImpl implements NonAgriculturalMarketActiviesFacade {

    private final NonAgriculturalMarketActiviesServiceExt serviceExt;
    private final NonAgriculturalMarketActiviesProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String NONAGRICULTURALMARKETACTIVIES_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NONAGRICULTURALMARKETACTIVIES.CREATE";
    private static final String NONAGRICULTURALMARKETACTIVIES_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NONAGRICULTURALMARKETACTIVIES.UPDATED";
    private static final String NONAGRICULTURALMARKETACTIVIES_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NONAGRICULTURALMARKETACTIVIES.DELETE";
    private static final String NONAGRICULTURALMARKETACTIVIES_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NONAGRICULTURALMARKETACTIVIES.GET";

    public NonAgriculturalMarketActiviesFacadeImpl(NonAgriculturalMarketActiviesServiceExt serviceExt, NonAgriculturalMarketActiviesProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new NonAgriculturalMarketActivies entry in the system.
     *
     * @param NonAgriculturalMarketActiviesDTO The NonAgriculturalMarketActivies information to be saved
     * @return The saved NonAgriculturalMarketActivies data
     */
    @Override
    public NonAgriculturalMarketActiviesDTO save(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO) {
        log.debug("Entry - save(NonAgriculturalMarketActiviesDTO={})", NonAgriculturalMarketActiviesDTO);
        evaluator.evaluate(NONAGRICULTURALMARKETACTIVIES_CREATE, new HashMap<>());
        NonAgriculturalMarketActiviesDTO = preHookSave(NonAgriculturalMarketActiviesDTO);
        NonAgriculturalMarketActiviesDTO dto = serviceExt.save(NonAgriculturalMarketActiviesDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing NonAgriculturalMarketActivies entry.
     *
     * @param NonAgriculturalMarketActiviesDTO The NonAgriculturalMarketActivies information to be updated
     * @return The updated NonAgriculturalMarketActivies data
     */
    @Override
    public NonAgriculturalMarketActiviesDTO update(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO) {
        log.debug("Entry - update(NonAgriculturalMarketActiviesDTO={})", NonAgriculturalMarketActiviesDTO);
        evaluator.evaluate(NONAGRICULTURALMARKETACTIVIES_UPDATE, new HashMap<>());
        NonAgriculturalMarketActiviesDTO = preHookUpdate(NonAgriculturalMarketActiviesDTO);
        NonAgriculturalMarketActiviesDTO dto = serviceExt.update(NonAgriculturalMarketActiviesDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of NonAgriculturalMarketActiviess.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NonAgriculturalMarketActiviess
     */
    @Override
    public PageDTO<NonAgriculturalMarketActiviesDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(NONAGRICULTURALMARKETACTIVIES_GET, new HashMap<>());
        PageDTO<NonAgriculturalMarketActiviesDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a NonAgriculturalMarketActivies by their ID with a specified reason.
     *
     * @param id     The ID of the NonAgriculturalMarketActivies to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(NONAGRICULTURALMARKETACTIVIES_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        NonAgriculturalMarketActiviesDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a NonAgriculturalMarketActivies by their ID.
     *
     * @param id The ID of the NonAgriculturalMarketActivies to retrieve
     * @return The NonAgriculturalMarketActivies with the specified ID
     */
    @Override
    public NonAgriculturalMarketActiviesDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(NONAGRICULTURALMARKETACTIVIES_GET, new HashMap<>());
        NonAgriculturalMarketActiviesDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all NonAgriculturalMarketActiviess by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NonAgriculturalMarketActiviesDTO
     */
    @Override
    public Set<NonAgriculturalMarketActiviesDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(NONAGRICULTURALMARKETACTIVIES_GET, new HashMap<>());
        Set<NonAgriculturalMarketActiviesDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of otherIndustryTypeList to a NonAgriculturalMarketActivies identified by their ID.
     *
     * @param id               The ID of the NonAgriculturalMarketActivies to add hobbies to
     * @param otherIndustryTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addOtherIndustryTypeToNonAgriculturalMarketActivies(Long id, List<OtherIndustryTypeDTO> otherIndustryTypeList){
        serviceExt.addOtherIndustryTypeToNonAgriculturalMarketActivies(id,otherIndustryTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected NonAgriculturalMarketActiviesDTO postHookSave(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected NonAgriculturalMarketActiviesDTO preHookSave(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected NonAgriculturalMarketActiviesDTO postHookUpdate(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected NonAgriculturalMarketActiviesDTO preHookUpdate(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO) {
        return NonAgriculturalMarketActiviesDTO;
    }

    protected NonAgriculturalMarketActiviesDTO postHookDelete(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NonAgriculturalMarketActiviesDTO postHookGetById(NonAgriculturalMarketActiviesDTO dto) {
        return dto;
    }

    protected PageDTO<NonAgriculturalMarketActiviesDTO> postHookGetAll(PageDTO<NonAgriculturalMarketActiviesDTO> result) {
        return result;
    }
}
