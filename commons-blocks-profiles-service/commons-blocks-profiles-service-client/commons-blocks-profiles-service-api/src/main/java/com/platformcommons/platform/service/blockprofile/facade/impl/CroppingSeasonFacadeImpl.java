package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.CroppingSeasonFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.CroppingSeasonProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.CroppingSeasonServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class CroppingSeasonFacadeImpl implements CroppingSeasonFacade {

    private final CroppingSeasonServiceExt serviceExt;
    private final CroppingSeasonProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String CROPPINGSEASON_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROPPINGSEASON.CREATE";
    private static final String CROPPINGSEASON_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROPPINGSEASON.UPDATED";
    private static final String CROPPINGSEASON_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROPPINGSEASON.DELETE";
    private static final String CROPPINGSEASON_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROPPINGSEASON.GET";

    public CroppingSeasonFacadeImpl(CroppingSeasonServiceExt serviceExt, CroppingSeasonProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new CroppingSeason entry in the system.
     *
     * @param CroppingSeasonDTO The CroppingSeason information to be saved
     * @return The saved CroppingSeason data
     */
    @Override
    public CroppingSeasonDTO save(CroppingSeasonDTO CroppingSeasonDTO) {
        log.debug("Entry - save(CroppingSeasonDTO={})", CroppingSeasonDTO);
        evaluator.evaluate(CROPPINGSEASON_CREATE, new HashMap<>());
        CroppingSeasonDTO = preHookSave(CroppingSeasonDTO);
        CroppingSeasonDTO dto = serviceExt.save(CroppingSeasonDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing CroppingSeason entry.
     *
     * @param CroppingSeasonDTO The CroppingSeason information to be updated
     * @return The updated CroppingSeason data
     */
    @Override
    public CroppingSeasonDTO update(CroppingSeasonDTO CroppingSeasonDTO) {
        log.debug("Entry - update(CroppingSeasonDTO={})", CroppingSeasonDTO);
        evaluator.evaluate(CROPPINGSEASON_UPDATE, new HashMap<>());
        CroppingSeasonDTO = preHookUpdate(CroppingSeasonDTO);
        CroppingSeasonDTO dto = serviceExt.update(CroppingSeasonDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of CroppingSeasons.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CroppingSeasons
     */
    @Override
    public PageDTO<CroppingSeasonDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(CROPPINGSEASON_GET, new HashMap<>());
        PageDTO<CroppingSeasonDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a CroppingSeason by their ID with a specified reason.
     *
     * @param id     The ID of the CroppingSeason to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(CROPPINGSEASON_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        CroppingSeasonDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a CroppingSeason by their ID.
     *
     * @param id The ID of the CroppingSeason to retrieve
     * @return The CroppingSeason with the specified ID
     */
    @Override
    public CroppingSeasonDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(CROPPINGSEASON_GET, new HashMap<>());
        CroppingSeasonDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all CroppingSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CroppingSeasonDTO
     */
    @Override
    public Set<CroppingSeasonDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(CROPPINGSEASON_GET, new HashMap<>());
        Set<CroppingSeasonDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CroppingSeasonDTO postHookSave(CroppingSeasonDTO dto) {
        return dto;
    }

    protected CroppingSeasonDTO preHookSave(CroppingSeasonDTO dto) {
        return dto;
    }

    protected CroppingSeasonDTO postHookUpdate(CroppingSeasonDTO dto) {
        return dto;
    }

    protected CroppingSeasonDTO preHookUpdate(CroppingSeasonDTO CroppingSeasonDTO) {
        return CroppingSeasonDTO;
    }

    protected CroppingSeasonDTO postHookDelete(CroppingSeasonDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CroppingSeasonDTO postHookGetById(CroppingSeasonDTO dto) {
        return dto;
    }

    protected PageDTO<CroppingSeasonDTO> postHookGetAll(PageDTO<CroppingSeasonDTO> result) {
        return result;
    }
}
