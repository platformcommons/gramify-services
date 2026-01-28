package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.CentralSchemeListFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.CentralSchemeListProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.CentralSchemeListServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class CentralSchemeListFacadeImpl implements CentralSchemeListFacade {

    private final CentralSchemeListServiceExt serviceExt;
    private final CentralSchemeListProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String CENTRALSCHEMELIST_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CENTRALSCHEMELIST.CREATE";
    private static final String CENTRALSCHEMELIST_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CENTRALSCHEMELIST.UPDATED";
    private static final String CENTRALSCHEMELIST_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CENTRALSCHEMELIST.DELETE";
    private static final String CENTRALSCHEMELIST_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CENTRALSCHEMELIST.GET";

    public CentralSchemeListFacadeImpl(CentralSchemeListServiceExt serviceExt, CentralSchemeListProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new CentralSchemeList entry in the system.
     *
     * @param CentralSchemeListDTO The CentralSchemeList information to be saved
     * @return The saved CentralSchemeList data
     */
    @Override
    public CentralSchemeListDTO save(CentralSchemeListDTO CentralSchemeListDTO) {
        log.debug("Entry - save(CentralSchemeListDTO={})", CentralSchemeListDTO);
        evaluator.evaluate(CENTRALSCHEMELIST_CREATE, new HashMap<>());
        CentralSchemeListDTO = preHookSave(CentralSchemeListDTO);
        CentralSchemeListDTO dto = serviceExt.save(CentralSchemeListDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing CentralSchemeList entry.
     *
     * @param CentralSchemeListDTO The CentralSchemeList information to be updated
     * @return The updated CentralSchemeList data
     */
    @Override
    public CentralSchemeListDTO update(CentralSchemeListDTO CentralSchemeListDTO) {
        log.debug("Entry - update(CentralSchemeListDTO={})", CentralSchemeListDTO);
        evaluator.evaluate(CENTRALSCHEMELIST_UPDATE, new HashMap<>());
        CentralSchemeListDTO = preHookUpdate(CentralSchemeListDTO);
        CentralSchemeListDTO dto = serviceExt.update(CentralSchemeListDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of CentralSchemeLists.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CentralSchemeLists
     */
    @Override
    public PageDTO<CentralSchemeListDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(CENTRALSCHEMELIST_GET, new HashMap<>());
        PageDTO<CentralSchemeListDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a CentralSchemeList by their ID with a specified reason.
     *
     * @param id     The ID of the CentralSchemeList to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(CENTRALSCHEMELIST_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        CentralSchemeListDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a CentralSchemeList by their ID.
     *
     * @param id The ID of the CentralSchemeList to retrieve
     * @return The CentralSchemeList with the specified ID
     */
    @Override
    public CentralSchemeListDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(CENTRALSCHEMELIST_GET, new HashMap<>());
        CentralSchemeListDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all CentralSchemeLists by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CentralSchemeListDTO
     */
    @Override
    public Set<CentralSchemeListDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(CENTRALSCHEMELIST_GET, new HashMap<>());
        Set<CentralSchemeListDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CentralSchemeListDTO postHookSave(CentralSchemeListDTO dto) {
        return dto;
    }

    protected CentralSchemeListDTO preHookSave(CentralSchemeListDTO dto) {
        return dto;
    }

    protected CentralSchemeListDTO postHookUpdate(CentralSchemeListDTO dto) {
        return dto;
    }

    protected CentralSchemeListDTO preHookUpdate(CentralSchemeListDTO CentralSchemeListDTO) {
        return CentralSchemeListDTO;
    }

    protected CentralSchemeListDTO postHookDelete(CentralSchemeListDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CentralSchemeListDTO postHookGetById(CentralSchemeListDTO dto) {
        return dto;
    }

    protected PageDTO<CentralSchemeListDTO> postHookGetAll(PageDTO<CentralSchemeListDTO> result) {
        return result;
    }
}
