package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.LocalFestivalFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.LocalFestivalProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.LocalFestivalServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class LocalFestivalFacadeImpl implements LocalFestivalFacade {

    private final LocalFestivalServiceExt serviceExt;
    private final LocalFestivalProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String LOCALFESTIVAL_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALFESTIVAL.CREATE";
    private static final String LOCALFESTIVAL_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALFESTIVAL.UPDATED";
    private static final String LOCALFESTIVAL_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALFESTIVAL.DELETE";
    private static final String LOCALFESTIVAL_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALFESTIVAL.GET";

    public LocalFestivalFacadeImpl(LocalFestivalServiceExt serviceExt, LocalFestivalProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new LocalFestival entry in the system.
     *
     * @param LocalFestivalDTO The LocalFestival information to be saved
     * @return The saved LocalFestival data
     */
    @Override
    public LocalFestivalDTO save(LocalFestivalDTO LocalFestivalDTO) {
        log.debug("Entry - save(LocalFestivalDTO={})", LocalFestivalDTO);
        evaluator.evaluate(LOCALFESTIVAL_CREATE, new HashMap<>());
        LocalFestivalDTO = preHookSave(LocalFestivalDTO);
        LocalFestivalDTO dto = serviceExt.save(LocalFestivalDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing LocalFestival entry.
     *
     * @param LocalFestivalDTO The LocalFestival information to be updated
     * @return The updated LocalFestival data
     */
    @Override
    public LocalFestivalDTO update(LocalFestivalDTO LocalFestivalDTO) {
        log.debug("Entry - update(LocalFestivalDTO={})", LocalFestivalDTO);
        evaluator.evaluate(LOCALFESTIVAL_UPDATE, new HashMap<>());
        LocalFestivalDTO = preHookUpdate(LocalFestivalDTO);
        LocalFestivalDTO dto = serviceExt.update(LocalFestivalDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of LocalFestivals.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of LocalFestivals
     */
    @Override
    public PageDTO<LocalFestivalDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(LOCALFESTIVAL_GET, new HashMap<>());
        PageDTO<LocalFestivalDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a LocalFestival by their ID with a specified reason.
     *
     * @param id     The ID of the LocalFestival to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(LOCALFESTIVAL_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        LocalFestivalDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a LocalFestival by their ID.
     *
     * @param id The ID of the LocalFestival to retrieve
     * @return The LocalFestival with the specified ID
     */
    @Override
    public LocalFestivalDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(LOCALFESTIVAL_GET, new HashMap<>());
        LocalFestivalDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all LocalFestivals by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of LocalFestivalDTO
     */
    @Override
    public Set<LocalFestivalDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(LOCALFESTIVAL_GET, new HashMap<>());
        Set<LocalFestivalDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected LocalFestivalDTO postHookSave(LocalFestivalDTO dto) {
        return dto;
    }

    protected LocalFestivalDTO preHookSave(LocalFestivalDTO dto) {
        return dto;
    }

    protected LocalFestivalDTO postHookUpdate(LocalFestivalDTO dto) {
        return dto;
    }

    protected LocalFestivalDTO preHookUpdate(LocalFestivalDTO LocalFestivalDTO) {
        return LocalFestivalDTO;
    }

    protected LocalFestivalDTO postHookDelete(LocalFestivalDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected LocalFestivalDTO postHookGetById(LocalFestivalDTO dto) {
        return dto;
    }

    protected PageDTO<LocalFestivalDTO> postHookGetAll(PageDTO<LocalFestivalDTO> result) {
        return result;
    }
}
