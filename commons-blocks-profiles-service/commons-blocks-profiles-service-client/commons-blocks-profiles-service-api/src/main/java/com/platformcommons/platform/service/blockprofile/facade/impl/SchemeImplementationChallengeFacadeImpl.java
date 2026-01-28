package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.SchemeImplementationChallengeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.SchemeImplementationChallengeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.SchemeImplementationChallengeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class SchemeImplementationChallengeFacadeImpl implements SchemeImplementationChallengeFacade {

    private final SchemeImplementationChallengeServiceExt serviceExt;
    private final SchemeImplementationChallengeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String SCHEMEIMPLEMENTATIONCHALLENGE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SCHEMEIMPLEMENTATIONCHALLENGE.CREATE";
    private static final String SCHEMEIMPLEMENTATIONCHALLENGE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SCHEMEIMPLEMENTATIONCHALLENGE.UPDATED";
    private static final String SCHEMEIMPLEMENTATIONCHALLENGE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SCHEMEIMPLEMENTATIONCHALLENGE.DELETE";
    private static final String SCHEMEIMPLEMENTATIONCHALLENGE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SCHEMEIMPLEMENTATIONCHALLENGE.GET";

    public SchemeImplementationChallengeFacadeImpl(SchemeImplementationChallengeServiceExt serviceExt, SchemeImplementationChallengeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new SchemeImplementationChallenge entry in the system.
     *
     * @param SchemeImplementationChallengeDTO The SchemeImplementationChallenge information to be saved
     * @return The saved SchemeImplementationChallenge data
     */
    @Override
    public SchemeImplementationChallengeDTO save(SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO) {
        log.debug("Entry - save(SchemeImplementationChallengeDTO={})", SchemeImplementationChallengeDTO);
        evaluator.evaluate(SCHEMEIMPLEMENTATIONCHALLENGE_CREATE, new HashMap<>());
        SchemeImplementationChallengeDTO = preHookSave(SchemeImplementationChallengeDTO);
        SchemeImplementationChallengeDTO dto = serviceExt.save(SchemeImplementationChallengeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing SchemeImplementationChallenge entry.
     *
     * @param SchemeImplementationChallengeDTO The SchemeImplementationChallenge information to be updated
     * @return The updated SchemeImplementationChallenge data
     */
    @Override
    public SchemeImplementationChallengeDTO update(SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO) {
        log.debug("Entry - update(SchemeImplementationChallengeDTO={})", SchemeImplementationChallengeDTO);
        evaluator.evaluate(SCHEMEIMPLEMENTATIONCHALLENGE_UPDATE, new HashMap<>());
        SchemeImplementationChallengeDTO = preHookUpdate(SchemeImplementationChallengeDTO);
        SchemeImplementationChallengeDTO dto = serviceExt.update(SchemeImplementationChallengeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of SchemeImplementationChallenges.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SchemeImplementationChallenges
     */
    @Override
    public PageDTO<SchemeImplementationChallengeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(SCHEMEIMPLEMENTATIONCHALLENGE_GET, new HashMap<>());
        PageDTO<SchemeImplementationChallengeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a SchemeImplementationChallenge by their ID with a specified reason.
     *
     * @param id     The ID of the SchemeImplementationChallenge to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(SCHEMEIMPLEMENTATIONCHALLENGE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        SchemeImplementationChallengeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a SchemeImplementationChallenge by their ID.
     *
     * @param id The ID of the SchemeImplementationChallenge to retrieve
     * @return The SchemeImplementationChallenge with the specified ID
     */
    @Override
    public SchemeImplementationChallengeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(SCHEMEIMPLEMENTATIONCHALLENGE_GET, new HashMap<>());
        SchemeImplementationChallengeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all SchemeImplementationChallenges by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SchemeImplementationChallengeDTO
     */
    @Override
    public Set<SchemeImplementationChallengeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(SCHEMEIMPLEMENTATIONCHALLENGE_GET, new HashMap<>());
        Set<SchemeImplementationChallengeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SchemeImplementationChallengeDTO postHookSave(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected SchemeImplementationChallengeDTO preHookSave(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected SchemeImplementationChallengeDTO postHookUpdate(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected SchemeImplementationChallengeDTO preHookUpdate(SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO) {
        return SchemeImplementationChallengeDTO;
    }

    protected SchemeImplementationChallengeDTO postHookDelete(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SchemeImplementationChallengeDTO postHookGetById(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected PageDTO<SchemeImplementationChallengeDTO> postHookGetAll(PageDTO<SchemeImplementationChallengeDTO> result) {
        return result;
    }
}
