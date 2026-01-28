package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MajorFestivalFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MajorFestivalProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MajorFestivalServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MajorFestivalFacadeImpl implements MajorFestivalFacade {

    private final MajorFestivalServiceExt serviceExt;
    private final MajorFestivalProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MAJORFESTIVAL_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAJORFESTIVAL.CREATE";
    private static final String MAJORFESTIVAL_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAJORFESTIVAL.UPDATED";
    private static final String MAJORFESTIVAL_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAJORFESTIVAL.DELETE";
    private static final String MAJORFESTIVAL_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAJORFESTIVAL.GET";

    public MajorFestivalFacadeImpl(MajorFestivalServiceExt serviceExt, MajorFestivalProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MajorFestival entry in the system.
     *
     * @param MajorFestivalDTO The MajorFestival information to be saved
     * @return The saved MajorFestival data
     */
    @Override
    public MajorFestivalDTO save(MajorFestivalDTO MajorFestivalDTO) {
        log.debug("Entry - save(MajorFestivalDTO={})", MajorFestivalDTO);
        evaluator.evaluate(MAJORFESTIVAL_CREATE, new HashMap<>());
        MajorFestivalDTO = preHookSave(MajorFestivalDTO);
        MajorFestivalDTO dto = serviceExt.save(MajorFestivalDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MajorFestival entry.
     *
     * @param MajorFestivalDTO The MajorFestival information to be updated
     * @return The updated MajorFestival data
     */
    @Override
    public MajorFestivalDTO update(MajorFestivalDTO MajorFestivalDTO) {
        log.debug("Entry - update(MajorFestivalDTO={})", MajorFestivalDTO);
        evaluator.evaluate(MAJORFESTIVAL_UPDATE, new HashMap<>());
        MajorFestivalDTO = preHookUpdate(MajorFestivalDTO);
        MajorFestivalDTO dto = serviceExt.update(MajorFestivalDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MajorFestivals.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MajorFestivals
     */
    @Override
    public PageDTO<MajorFestivalDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MAJORFESTIVAL_GET, new HashMap<>());
        PageDTO<MajorFestivalDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MajorFestival by their ID with a specified reason.
     *
     * @param id     The ID of the MajorFestival to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MAJORFESTIVAL_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MajorFestivalDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MajorFestival by their ID.
     *
     * @param id The ID of the MajorFestival to retrieve
     * @return The MajorFestival with the specified ID
     */
    @Override
    public MajorFestivalDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MAJORFESTIVAL_GET, new HashMap<>());
        MajorFestivalDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MajorFestivals by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MajorFestivalDTO
     */
    @Override
    public Set<MajorFestivalDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MAJORFESTIVAL_GET, new HashMap<>());
        Set<MajorFestivalDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MajorFestivalDTO postHookSave(MajorFestivalDTO dto) {
        return dto;
    }

    protected MajorFestivalDTO preHookSave(MajorFestivalDTO dto) {
        return dto;
    }

    protected MajorFestivalDTO postHookUpdate(MajorFestivalDTO dto) {
        return dto;
    }

    protected MajorFestivalDTO preHookUpdate(MajorFestivalDTO MajorFestivalDTO) {
        return MajorFestivalDTO;
    }

    protected MajorFestivalDTO postHookDelete(MajorFestivalDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MajorFestivalDTO postHookGetById(MajorFestivalDTO dto) {
        return dto;
    }

    protected PageDTO<MajorFestivalDTO> postHookGetAll(PageDTO<MajorFestivalDTO> result) {
        return result;
    }
}
