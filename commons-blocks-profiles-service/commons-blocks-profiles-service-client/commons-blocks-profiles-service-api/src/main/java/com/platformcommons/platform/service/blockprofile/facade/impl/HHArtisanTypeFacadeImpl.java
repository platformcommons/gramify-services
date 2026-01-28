package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHArtisanTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHArtisanTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHArtisanTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHArtisanTypeFacadeImpl implements HHArtisanTypeFacade {

    private final HHArtisanTypeServiceExt serviceExt;
    private final HHArtisanTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHARTISANTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHARTISANTYPE.CREATE";
    private static final String HHARTISANTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHARTISANTYPE.UPDATED";
    private static final String HHARTISANTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHARTISANTYPE.DELETE";
    private static final String HHARTISANTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHARTISANTYPE.GET";

    public HHArtisanTypeFacadeImpl(HHArtisanTypeServiceExt serviceExt, HHArtisanTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHArtisanType entry in the system.
     *
     * @param HHArtisanTypeDTO The HHArtisanType information to be saved
     * @return The saved HHArtisanType data
     */
    @Override
    public HHArtisanTypeDTO save(HHArtisanTypeDTO HHArtisanTypeDTO) {
        log.debug("Entry - save(HHArtisanTypeDTO={})", HHArtisanTypeDTO);
        evaluator.evaluate(HHARTISANTYPE_CREATE, new HashMap<>());
        HHArtisanTypeDTO = preHookSave(HHArtisanTypeDTO);
        HHArtisanTypeDTO dto = serviceExt.save(HHArtisanTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHArtisanType entry.
     *
     * @param HHArtisanTypeDTO The HHArtisanType information to be updated
     * @return The updated HHArtisanType data
     */
    @Override
    public HHArtisanTypeDTO update(HHArtisanTypeDTO HHArtisanTypeDTO) {
        log.debug("Entry - update(HHArtisanTypeDTO={})", HHArtisanTypeDTO);
        evaluator.evaluate(HHARTISANTYPE_UPDATE, new HashMap<>());
        HHArtisanTypeDTO = preHookUpdate(HHArtisanTypeDTO);
        HHArtisanTypeDTO dto = serviceExt.update(HHArtisanTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHArtisanTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHArtisanTypes
     */
    @Override
    public PageDTO<HHArtisanTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHARTISANTYPE_GET, new HashMap<>());
        PageDTO<HHArtisanTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHArtisanType by their ID with a specified reason.
     *
     * @param id     The ID of the HHArtisanType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHARTISANTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHArtisanTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHArtisanType by their ID.
     *
     * @param id The ID of the HHArtisanType to retrieve
     * @return The HHArtisanType with the specified ID
     */
    @Override
    public HHArtisanTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHARTISANTYPE_GET, new HashMap<>());
        HHArtisanTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHArtisanTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHArtisanTypeDTO
     */
    @Override
    public Set<HHArtisanTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHARTISANTYPE_GET, new HashMap<>());
        Set<HHArtisanTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHArtisanTypeDTO postHookSave(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected HHArtisanTypeDTO preHookSave(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected HHArtisanTypeDTO postHookUpdate(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected HHArtisanTypeDTO preHookUpdate(HHArtisanTypeDTO HHArtisanTypeDTO) {
        return HHArtisanTypeDTO;
    }

    protected HHArtisanTypeDTO postHookDelete(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHArtisanTypeDTO postHookGetById(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHArtisanTypeDTO> postHookGetAll(PageDTO<HHArtisanTypeDTO> result) {
        return result;
    }
}
