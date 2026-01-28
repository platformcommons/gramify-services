package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.CommonRepairNeedFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.CommonRepairNeedProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.CommonRepairNeedServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class CommonRepairNeedFacadeImpl implements CommonRepairNeedFacade {

    private final CommonRepairNeedServiceExt serviceExt;
    private final CommonRepairNeedProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String COMMONREPAIRNEED_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONREPAIRNEED.CREATE";
    private static final String COMMONREPAIRNEED_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONREPAIRNEED.UPDATED";
    private static final String COMMONREPAIRNEED_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONREPAIRNEED.DELETE";
    private static final String COMMONREPAIRNEED_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONREPAIRNEED.GET";

    public CommonRepairNeedFacadeImpl(CommonRepairNeedServiceExt serviceExt, CommonRepairNeedProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new CommonRepairNeed entry in the system.
     *
     * @param CommonRepairNeedDTO The CommonRepairNeed information to be saved
     * @return The saved CommonRepairNeed data
     */
    @Override
    public CommonRepairNeedDTO save(CommonRepairNeedDTO CommonRepairNeedDTO) {
        log.debug("Entry - save(CommonRepairNeedDTO={})", CommonRepairNeedDTO);
        evaluator.evaluate(COMMONREPAIRNEED_CREATE, new HashMap<>());
        CommonRepairNeedDTO = preHookSave(CommonRepairNeedDTO);
        CommonRepairNeedDTO dto = serviceExt.save(CommonRepairNeedDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing CommonRepairNeed entry.
     *
     * @param CommonRepairNeedDTO The CommonRepairNeed information to be updated
     * @return The updated CommonRepairNeed data
     */
    @Override
    public CommonRepairNeedDTO update(CommonRepairNeedDTO CommonRepairNeedDTO) {
        log.debug("Entry - update(CommonRepairNeedDTO={})", CommonRepairNeedDTO);
        evaluator.evaluate(COMMONREPAIRNEED_UPDATE, new HashMap<>());
        CommonRepairNeedDTO = preHookUpdate(CommonRepairNeedDTO);
        CommonRepairNeedDTO dto = serviceExt.update(CommonRepairNeedDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of CommonRepairNeeds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CommonRepairNeeds
     */
    @Override
    public PageDTO<CommonRepairNeedDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(COMMONREPAIRNEED_GET, new HashMap<>());
        PageDTO<CommonRepairNeedDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a CommonRepairNeed by their ID with a specified reason.
     *
     * @param id     The ID of the CommonRepairNeed to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(COMMONREPAIRNEED_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        CommonRepairNeedDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a CommonRepairNeed by their ID.
     *
     * @param id The ID of the CommonRepairNeed to retrieve
     * @return The CommonRepairNeed with the specified ID
     */
    @Override
    public CommonRepairNeedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(COMMONREPAIRNEED_GET, new HashMap<>());
        CommonRepairNeedDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all CommonRepairNeeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonRepairNeedDTO
     */
    @Override
    public Set<CommonRepairNeedDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(COMMONREPAIRNEED_GET, new HashMap<>());
        Set<CommonRepairNeedDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CommonRepairNeedDTO postHookSave(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected CommonRepairNeedDTO preHookSave(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected CommonRepairNeedDTO postHookUpdate(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected CommonRepairNeedDTO preHookUpdate(CommonRepairNeedDTO CommonRepairNeedDTO) {
        return CommonRepairNeedDTO;
    }

    protected CommonRepairNeedDTO postHookDelete(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CommonRepairNeedDTO postHookGetById(CommonRepairNeedDTO dto) {
        return dto;
    }

    protected PageDTO<CommonRepairNeedDTO> postHookGetAll(PageDTO<CommonRepairNeedDTO> result) {
        return result;
    }
}
