package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ArtisanTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ArtisanTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ArtisanTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ArtisanTypeFacadeImpl implements ArtisanTypeFacade {

    private final ArtisanTypeServiceExt serviceExt;
    private final ArtisanTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String ARTISANTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ARTISANTYPE.CREATE";
    private static final String ARTISANTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ARTISANTYPE.UPDATED";
    private static final String ARTISANTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ARTISANTYPE.DELETE";
    private static final String ARTISANTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ARTISANTYPE.GET";

    public ArtisanTypeFacadeImpl(ArtisanTypeServiceExt serviceExt, ArtisanTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ArtisanType entry in the system.
     *
     * @param ArtisanTypeDTO The ArtisanType information to be saved
     * @return The saved ArtisanType data
     */
    @Override
    public ArtisanTypeDTO save(ArtisanTypeDTO ArtisanTypeDTO) {
        log.debug("Entry - save(ArtisanTypeDTO={})", ArtisanTypeDTO);
        evaluator.evaluate(ARTISANTYPE_CREATE, new HashMap<>());
        ArtisanTypeDTO = preHookSave(ArtisanTypeDTO);
        ArtisanTypeDTO dto = serviceExt.save(ArtisanTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ArtisanType entry.
     *
     * @param ArtisanTypeDTO The ArtisanType information to be updated
     * @return The updated ArtisanType data
     */
    @Override
    public ArtisanTypeDTO update(ArtisanTypeDTO ArtisanTypeDTO) {
        log.debug("Entry - update(ArtisanTypeDTO={})", ArtisanTypeDTO);
        evaluator.evaluate(ARTISANTYPE_UPDATE, new HashMap<>());
        ArtisanTypeDTO = preHookUpdate(ArtisanTypeDTO);
        ArtisanTypeDTO dto = serviceExt.update(ArtisanTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ArtisanTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ArtisanTypes
     */
    @Override
    public PageDTO<ArtisanTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(ARTISANTYPE_GET, new HashMap<>());
        PageDTO<ArtisanTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ArtisanType by their ID with a specified reason.
     *
     * @param id     The ID of the ArtisanType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(ARTISANTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ArtisanTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ArtisanType by their ID.
     *
     * @param id The ID of the ArtisanType to retrieve
     * @return The ArtisanType with the specified ID
     */
    @Override
    public ArtisanTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(ARTISANTYPE_GET, new HashMap<>());
        ArtisanTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ArtisanTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ArtisanTypeDTO
     */
    @Override
    public Set<ArtisanTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(ARTISANTYPE_GET, new HashMap<>());
        Set<ArtisanTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ArtisanTypeDTO postHookSave(ArtisanTypeDTO dto) {
        return dto;
    }

    protected ArtisanTypeDTO preHookSave(ArtisanTypeDTO dto) {
        return dto;
    }

    protected ArtisanTypeDTO postHookUpdate(ArtisanTypeDTO dto) {
        return dto;
    }

    protected ArtisanTypeDTO preHookUpdate(ArtisanTypeDTO ArtisanTypeDTO) {
        return ArtisanTypeDTO;
    }

    protected ArtisanTypeDTO postHookDelete(ArtisanTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ArtisanTypeDTO postHookGetById(ArtisanTypeDTO dto) {
        return dto;
    }

    protected PageDTO<ArtisanTypeDTO> postHookGetAll(PageDTO<ArtisanTypeDTO> result) {
        return result;
    }
}
