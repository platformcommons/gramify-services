package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.SurplusProduceTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.SurplusProduceTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.SurplusProduceTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class SurplusProduceTypeFacadeImpl implements SurplusProduceTypeFacade {

    private final SurplusProduceTypeServiceExt serviceExt;
    private final SurplusProduceTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String SURPLUSPRODUCETYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSPRODUCETYPE.CREATE";
    private static final String SURPLUSPRODUCETYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSPRODUCETYPE.UPDATED";
    private static final String SURPLUSPRODUCETYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSPRODUCETYPE.DELETE";
    private static final String SURPLUSPRODUCETYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SURPLUSPRODUCETYPE.GET";

    public SurplusProduceTypeFacadeImpl(SurplusProduceTypeServiceExt serviceExt, SurplusProduceTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new SurplusProduceType entry in the system.
     *
     * @param SurplusProduceTypeDTO The SurplusProduceType information to be saved
     * @return The saved SurplusProduceType data
     */
    @Override
    public SurplusProduceTypeDTO save(SurplusProduceTypeDTO SurplusProduceTypeDTO) {
        log.debug("Entry - save(SurplusProduceTypeDTO={})", SurplusProduceTypeDTO);
        evaluator.evaluate(SURPLUSPRODUCETYPE_CREATE, new HashMap<>());
        SurplusProduceTypeDTO = preHookSave(SurplusProduceTypeDTO);
        SurplusProduceTypeDTO dto = serviceExt.save(SurplusProduceTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing SurplusProduceType entry.
     *
     * @param SurplusProduceTypeDTO The SurplusProduceType information to be updated
     * @return The updated SurplusProduceType data
     */
    @Override
    public SurplusProduceTypeDTO update(SurplusProduceTypeDTO SurplusProduceTypeDTO) {
        log.debug("Entry - update(SurplusProduceTypeDTO={})", SurplusProduceTypeDTO);
        evaluator.evaluate(SURPLUSPRODUCETYPE_UPDATE, new HashMap<>());
        SurplusProduceTypeDTO = preHookUpdate(SurplusProduceTypeDTO);
        SurplusProduceTypeDTO dto = serviceExt.update(SurplusProduceTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of SurplusProduceTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SurplusProduceTypes
     */
    @Override
    public PageDTO<SurplusProduceTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(SURPLUSPRODUCETYPE_GET, new HashMap<>());
        PageDTO<SurplusProduceTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a SurplusProduceType by their ID with a specified reason.
     *
     * @param id     The ID of the SurplusProduceType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(SURPLUSPRODUCETYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        SurplusProduceTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a SurplusProduceType by their ID.
     *
     * @param id The ID of the SurplusProduceType to retrieve
     * @return The SurplusProduceType with the specified ID
     */
    @Override
    public SurplusProduceTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(SURPLUSPRODUCETYPE_GET, new HashMap<>());
        SurplusProduceTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all SurplusProduceTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SurplusProduceTypeDTO
     */
    @Override
    public Set<SurplusProduceTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(SURPLUSPRODUCETYPE_GET, new HashMap<>());
        Set<SurplusProduceTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SurplusProduceTypeDTO postHookSave(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected SurplusProduceTypeDTO preHookSave(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected SurplusProduceTypeDTO postHookUpdate(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected SurplusProduceTypeDTO preHookUpdate(SurplusProduceTypeDTO SurplusProduceTypeDTO) {
        return SurplusProduceTypeDTO;
    }

    protected SurplusProduceTypeDTO postHookDelete(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SurplusProduceTypeDTO postHookGetById(SurplusProduceTypeDTO dto) {
        return dto;
    }

    protected PageDTO<SurplusProduceTypeDTO> postHookGetAll(PageDTO<SurplusProduceTypeDTO> result) {
        return result;
    }
}
