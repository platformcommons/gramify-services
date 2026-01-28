package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HyperLocalMarketProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HyperLocalMarketProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HyperLocalMarketProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HyperLocalMarketProfileFacadeImpl implements HyperLocalMarketProfileFacade {

    private final HyperLocalMarketProfileServiceExt serviceExt;
    private final HyperLocalMarketProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HYPERLOCALMARKETPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HYPERLOCALMARKETPROFILE.CREATE";
    private static final String HYPERLOCALMARKETPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HYPERLOCALMARKETPROFILE.UPDATED";
    private static final String HYPERLOCALMARKETPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HYPERLOCALMARKETPROFILE.DELETE";
    private static final String HYPERLOCALMARKETPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HYPERLOCALMARKETPROFILE.GET";

    public HyperLocalMarketProfileFacadeImpl(HyperLocalMarketProfileServiceExt serviceExt, HyperLocalMarketProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HyperLocalMarketProfile entry in the system.
     *
     * @param HyperLocalMarketProfileDTO The HyperLocalMarketProfile information to be saved
     * @return The saved HyperLocalMarketProfile data
     */
    @Override
    public HyperLocalMarketProfileDTO save(HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO) {
        log.debug("Entry - save(HyperLocalMarketProfileDTO={})", HyperLocalMarketProfileDTO);
        evaluator.evaluate(HYPERLOCALMARKETPROFILE_CREATE, new HashMap<>());
        HyperLocalMarketProfileDTO = preHookSave(HyperLocalMarketProfileDTO);
        HyperLocalMarketProfileDTO dto = serviceExt.save(HyperLocalMarketProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HyperLocalMarketProfile entry.
     *
     * @param HyperLocalMarketProfileDTO The HyperLocalMarketProfile information to be updated
     * @return The updated HyperLocalMarketProfile data
     */
    @Override
    public HyperLocalMarketProfileDTO update(HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO) {
        log.debug("Entry - update(HyperLocalMarketProfileDTO={})", HyperLocalMarketProfileDTO);
        evaluator.evaluate(HYPERLOCALMARKETPROFILE_UPDATE, new HashMap<>());
        HyperLocalMarketProfileDTO = preHookUpdate(HyperLocalMarketProfileDTO);
        HyperLocalMarketProfileDTO dto = serviceExt.update(HyperLocalMarketProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HyperLocalMarketProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HyperLocalMarketProfiles
     */
    @Override
    public PageDTO<HyperLocalMarketProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HYPERLOCALMARKETPROFILE_GET, new HashMap<>());
        PageDTO<HyperLocalMarketProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HyperLocalMarketProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HyperLocalMarketProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HYPERLOCALMARKETPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HyperLocalMarketProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HyperLocalMarketProfile by their ID.
     *
     * @param id The ID of the HyperLocalMarketProfile to retrieve
     * @return The HyperLocalMarketProfile with the specified ID
     */
    @Override
    public HyperLocalMarketProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HYPERLOCALMARKETPROFILE_GET, new HashMap<>());
        HyperLocalMarketProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HyperLocalMarketProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HyperLocalMarketProfileDTO
     */
    @Override
    public Set<HyperLocalMarketProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HYPERLOCALMARKETPROFILE_GET, new HashMap<>());
        Set<HyperLocalMarketProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HyperLocalMarketProfileDTO postHookSave(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected HyperLocalMarketProfileDTO preHookSave(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected HyperLocalMarketProfileDTO postHookUpdate(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected HyperLocalMarketProfileDTO preHookUpdate(HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO) {
        return HyperLocalMarketProfileDTO;
    }

    protected HyperLocalMarketProfileDTO postHookDelete(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HyperLocalMarketProfileDTO postHookGetById(HyperLocalMarketProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HyperLocalMarketProfileDTO> postHookGetAll(PageDTO<HyperLocalMarketProfileDTO> result) {
        return result;
    }
}
