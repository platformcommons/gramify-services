package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.CommonConsumerGoodsPurchasedFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.CommonConsumerGoodsPurchasedProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.CommonConsumerGoodsPurchasedServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class CommonConsumerGoodsPurchasedFacadeImpl implements CommonConsumerGoodsPurchasedFacade {

    private final CommonConsumerGoodsPurchasedServiceExt serviceExt;
    private final CommonConsumerGoodsPurchasedProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String COMMONCONSUMERGOODSPURCHASED_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONCONSUMERGOODSPURCHASED.CREATE";
    private static final String COMMONCONSUMERGOODSPURCHASED_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONCONSUMERGOODSPURCHASED.UPDATED";
    private static final String COMMONCONSUMERGOODSPURCHASED_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONCONSUMERGOODSPURCHASED.DELETE";
    private static final String COMMONCONSUMERGOODSPURCHASED_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONCONSUMERGOODSPURCHASED.GET";

    public CommonConsumerGoodsPurchasedFacadeImpl(CommonConsumerGoodsPurchasedServiceExt serviceExt, CommonConsumerGoodsPurchasedProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new CommonConsumerGoodsPurchased entry in the system.
     *
     * @param CommonConsumerGoodsPurchasedDTO The CommonConsumerGoodsPurchased information to be saved
     * @return The saved CommonConsumerGoodsPurchased data
     */
    @Override
    public CommonConsumerGoodsPurchasedDTO save(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO) {
        log.debug("Entry - save(CommonConsumerGoodsPurchasedDTO={})", CommonConsumerGoodsPurchasedDTO);
        evaluator.evaluate(COMMONCONSUMERGOODSPURCHASED_CREATE, new HashMap<>());
        CommonConsumerGoodsPurchasedDTO = preHookSave(CommonConsumerGoodsPurchasedDTO);
        CommonConsumerGoodsPurchasedDTO dto = serviceExt.save(CommonConsumerGoodsPurchasedDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing CommonConsumerGoodsPurchased entry.
     *
     * @param CommonConsumerGoodsPurchasedDTO The CommonConsumerGoodsPurchased information to be updated
     * @return The updated CommonConsumerGoodsPurchased data
     */
    @Override
    public CommonConsumerGoodsPurchasedDTO update(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO) {
        log.debug("Entry - update(CommonConsumerGoodsPurchasedDTO={})", CommonConsumerGoodsPurchasedDTO);
        evaluator.evaluate(COMMONCONSUMERGOODSPURCHASED_UPDATE, new HashMap<>());
        CommonConsumerGoodsPurchasedDTO = preHookUpdate(CommonConsumerGoodsPurchasedDTO);
        CommonConsumerGoodsPurchasedDTO dto = serviceExt.update(CommonConsumerGoodsPurchasedDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of CommonConsumerGoodsPurchaseds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CommonConsumerGoodsPurchaseds
     */
    @Override
    public PageDTO<CommonConsumerGoodsPurchasedDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(COMMONCONSUMERGOODSPURCHASED_GET, new HashMap<>());
        PageDTO<CommonConsumerGoodsPurchasedDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a CommonConsumerGoodsPurchased by their ID with a specified reason.
     *
     * @param id     The ID of the CommonConsumerGoodsPurchased to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(COMMONCONSUMERGOODSPURCHASED_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        CommonConsumerGoodsPurchasedDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a CommonConsumerGoodsPurchased by their ID.
     *
     * @param id The ID of the CommonConsumerGoodsPurchased to retrieve
     * @return The CommonConsumerGoodsPurchased with the specified ID
     */
    @Override
    public CommonConsumerGoodsPurchasedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(COMMONCONSUMERGOODSPURCHASED_GET, new HashMap<>());
        CommonConsumerGoodsPurchasedDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all CommonConsumerGoodsPurchaseds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonConsumerGoodsPurchasedDTO
     */
    @Override
    public Set<CommonConsumerGoodsPurchasedDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(COMMONCONSUMERGOODSPURCHASED_GET, new HashMap<>());
        Set<CommonConsumerGoodsPurchasedDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CommonConsumerGoodsPurchasedDTO postHookSave(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected CommonConsumerGoodsPurchasedDTO preHookSave(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected CommonConsumerGoodsPurchasedDTO postHookUpdate(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected CommonConsumerGoodsPurchasedDTO preHookUpdate(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO) {
        return CommonConsumerGoodsPurchasedDTO;
    }

    protected CommonConsumerGoodsPurchasedDTO postHookDelete(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CommonConsumerGoodsPurchasedDTO postHookGetById(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected PageDTO<CommonConsumerGoodsPurchasedDTO> postHookGetAll(PageDTO<CommonConsumerGoodsPurchasedDTO> result) {
        return result;
    }
}
