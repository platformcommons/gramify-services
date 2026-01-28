package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHSocialAspirationFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHSocialAspirationProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHSocialAspirationServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHSocialAspirationFacadeImpl implements HHSocialAspirationFacade {

    private final HHSocialAspirationServiceExt serviceExt;
    private final HHSocialAspirationProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHSOCIALASPIRATION_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALASPIRATION.CREATE";
    private static final String HHSOCIALASPIRATION_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALASPIRATION.UPDATED";
    private static final String HHSOCIALASPIRATION_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALASPIRATION.DELETE";
    private static final String HHSOCIALASPIRATION_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHSOCIALASPIRATION.GET";

    public HHSocialAspirationFacadeImpl(HHSocialAspirationServiceExt serviceExt, HHSocialAspirationProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHSocialAspiration entry in the system.
     *
     * @param HHSocialAspirationDTO The HHSocialAspiration information to be saved
     * @return The saved HHSocialAspiration data
     */
    @Override
    public HHSocialAspirationDTO save(HHSocialAspirationDTO HHSocialAspirationDTO) {
        log.debug("Entry - save(HHSocialAspirationDTO={})", HHSocialAspirationDTO);
        evaluator.evaluate(HHSOCIALASPIRATION_CREATE, new HashMap<>());
        HHSocialAspirationDTO = preHookSave(HHSocialAspirationDTO);
        HHSocialAspirationDTO dto = serviceExt.save(HHSocialAspirationDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHSocialAspiration entry.
     *
     * @param HHSocialAspirationDTO The HHSocialAspiration information to be updated
     * @return The updated HHSocialAspiration data
     */
    @Override
    public HHSocialAspirationDTO update(HHSocialAspirationDTO HHSocialAspirationDTO) {
        log.debug("Entry - update(HHSocialAspirationDTO={})", HHSocialAspirationDTO);
        evaluator.evaluate(HHSOCIALASPIRATION_UPDATE, new HashMap<>());
        HHSocialAspirationDTO = preHookUpdate(HHSocialAspirationDTO);
        HHSocialAspirationDTO dto = serviceExt.update(HHSocialAspirationDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHSocialAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHSocialAspirations
     */
    @Override
    public PageDTO<HHSocialAspirationDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHSOCIALASPIRATION_GET, new HashMap<>());
        PageDTO<HHSocialAspirationDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHSocialAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHSocialAspiration to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHSOCIALASPIRATION_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHSocialAspirationDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHSocialAspiration by their ID.
     *
     * @param id The ID of the HHSocialAspiration to retrieve
     * @return The HHSocialAspiration with the specified ID
     */
    @Override
    public HHSocialAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHSOCIALASPIRATION_GET, new HashMap<>());
        HHSocialAspirationDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHSocialAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSocialAspirationDTO
     */
    @Override
    public Set<HHSocialAspirationDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHSOCIALASPIRATION_GET, new HashMap<>());
        Set<HHSocialAspirationDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHSocialAspirationDTO postHookSave(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected HHSocialAspirationDTO preHookSave(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected HHSocialAspirationDTO postHookUpdate(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected HHSocialAspirationDTO preHookUpdate(HHSocialAspirationDTO HHSocialAspirationDTO) {
        return HHSocialAspirationDTO;
    }

    protected HHSocialAspirationDTO postHookDelete(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHSocialAspirationDTO postHookGetById(HHSocialAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHSocialAspirationDTO> postHookGetAll(PageDTO<HHSocialAspirationDTO> result) {
        return result;
    }
}
