package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.GovtSchemesFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.GovtSchemesProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.GovtSchemesServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class GovtSchemesFacadeImpl implements GovtSchemesFacade {

    private final GovtSchemesServiceExt serviceExt;
    private final GovtSchemesProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String GOVTSCHEMES_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.GOVTSCHEMES.CREATE";
    private static final String GOVTSCHEMES_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.GOVTSCHEMES.UPDATED";
    private static final String GOVTSCHEMES_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.GOVTSCHEMES.DELETE";
    private static final String GOVTSCHEMES_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.GOVTSCHEMES.GET";

    public GovtSchemesFacadeImpl(GovtSchemesServiceExt serviceExt, GovtSchemesProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new GovtSchemes entry in the system.
     *
     * @param GovtSchemesDTO The GovtSchemes information to be saved
     * @return The saved GovtSchemes data
     */
    @Override
    public GovtSchemesDTO save(GovtSchemesDTO GovtSchemesDTO) {
        log.debug("Entry - save(GovtSchemesDTO={})", GovtSchemesDTO);
        evaluator.evaluate(GOVTSCHEMES_CREATE, new HashMap<>());
        GovtSchemesDTO = preHookSave(GovtSchemesDTO);
        GovtSchemesDTO dto = serviceExt.save(GovtSchemesDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing GovtSchemes entry.
     *
     * @param GovtSchemesDTO The GovtSchemes information to be updated
     * @return The updated GovtSchemes data
     */
    @Override
    public GovtSchemesDTO update(GovtSchemesDTO GovtSchemesDTO) {
        log.debug("Entry - update(GovtSchemesDTO={})", GovtSchemesDTO);
        evaluator.evaluate(GOVTSCHEMES_UPDATE, new HashMap<>());
        GovtSchemesDTO = preHookUpdate(GovtSchemesDTO);
        GovtSchemesDTO dto = serviceExt.update(GovtSchemesDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of GovtSchemess.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of GovtSchemess
     */
    @Override
    public PageDTO<GovtSchemesDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(GOVTSCHEMES_GET, new HashMap<>());
        PageDTO<GovtSchemesDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a GovtSchemes by their ID with a specified reason.
     *
     * @param id     The ID of the GovtSchemes to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(GOVTSCHEMES_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        GovtSchemesDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a GovtSchemes by their ID.
     *
     * @param id The ID of the GovtSchemes to retrieve
     * @return The GovtSchemes with the specified ID
     */
    @Override
    public GovtSchemesDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(GOVTSCHEMES_GET, new HashMap<>());
        GovtSchemesDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all GovtSchemess by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of GovtSchemesDTO
     */
    @Override
    public Set<GovtSchemesDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(GOVTSCHEMES_GET, new HashMap<>());
        Set<GovtSchemesDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of schemeImplementationChallengeList to a GovtSchemes identified by their ID.
     *
     * @param id               The ID of the GovtSchemes to add hobbies to
     * @param schemeImplementationChallengeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSchemeImplementationChallengeToGovtSchemes(Long id, List<SchemeImplementationChallengeDTO> schemeImplementationChallengeList){
        serviceExt.addSchemeImplementationChallengeToGovtSchemes(id,schemeImplementationChallengeList);
    }
/**
     * Adds a list of centralSchemeListList to a GovtSchemes identified by their ID.
     *
     * @param id               The ID of the GovtSchemes to add hobbies to
     * @param centralSchemeListList  to be added
     * @since 1.0.0
     */
    @Override
    public void addCentralSchemeListToGovtSchemes(Long id, List<CentralSchemeListDTO> centralSchemeListList){
        serviceExt.addCentralSchemeListToGovtSchemes(id,centralSchemeListList);
    }

    /*Hooks to be overridden by subclasses*/
    protected GovtSchemesDTO postHookSave(GovtSchemesDTO dto) {
        return dto;
    }

    protected GovtSchemesDTO preHookSave(GovtSchemesDTO dto) {
        return dto;
    }

    protected GovtSchemesDTO postHookUpdate(GovtSchemesDTO dto) {
        return dto;
    }

    protected GovtSchemesDTO preHookUpdate(GovtSchemesDTO GovtSchemesDTO) {
        return GovtSchemesDTO;
    }

    protected GovtSchemesDTO postHookDelete(GovtSchemesDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected GovtSchemesDTO postHookGetById(GovtSchemesDTO dto) {
        return dto;
    }

    protected PageDTO<GovtSchemesDTO> postHookGetAll(PageDTO<GovtSchemesDTO> result) {
        return result;
    }
}
