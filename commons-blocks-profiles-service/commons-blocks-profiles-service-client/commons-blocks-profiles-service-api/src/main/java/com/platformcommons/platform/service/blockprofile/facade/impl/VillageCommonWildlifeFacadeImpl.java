package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageCommonWildlifeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageCommonWildlifeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageCommonWildlifeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageCommonWildlifeFacadeImpl implements VillageCommonWildlifeFacade {

    private final VillageCommonWildlifeServiceExt serviceExt;
    private final VillageCommonWildlifeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECOMMONWILDLIFE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONWILDLIFE.CREATE";
    private static final String VILLAGECOMMONWILDLIFE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONWILDLIFE.UPDATED";
    private static final String VILLAGECOMMONWILDLIFE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONWILDLIFE.DELETE";
    private static final String VILLAGECOMMONWILDLIFE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONWILDLIFE.GET";

    public VillageCommonWildlifeFacadeImpl(VillageCommonWildlifeServiceExt serviceExt, VillageCommonWildlifeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageCommonWildlife entry in the system.
     *
     * @param VillageCommonWildlifeDTO The VillageCommonWildlife information to be saved
     * @return The saved VillageCommonWildlife data
     */
    @Override
    public VillageCommonWildlifeDTO save(VillageCommonWildlifeDTO VillageCommonWildlifeDTO) {
        log.debug("Entry - save(VillageCommonWildlifeDTO={})", VillageCommonWildlifeDTO);
        evaluator.evaluate(VILLAGECOMMONWILDLIFE_CREATE, new HashMap<>());
        VillageCommonWildlifeDTO = preHookSave(VillageCommonWildlifeDTO);
        VillageCommonWildlifeDTO dto = serviceExt.save(VillageCommonWildlifeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageCommonWildlife entry.
     *
     * @param VillageCommonWildlifeDTO The VillageCommonWildlife information to be updated
     * @return The updated VillageCommonWildlife data
     */
    @Override
    public VillageCommonWildlifeDTO update(VillageCommonWildlifeDTO VillageCommonWildlifeDTO) {
        log.debug("Entry - update(VillageCommonWildlifeDTO={})", VillageCommonWildlifeDTO);
        evaluator.evaluate(VILLAGECOMMONWILDLIFE_UPDATE, new HashMap<>());
        VillageCommonWildlifeDTO = preHookUpdate(VillageCommonWildlifeDTO);
        VillageCommonWildlifeDTO dto = serviceExt.update(VillageCommonWildlifeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageCommonWildlifes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommonWildlifes
     */
    @Override
    public PageDTO<VillageCommonWildlifeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECOMMONWILDLIFE_GET, new HashMap<>());
        PageDTO<VillageCommonWildlifeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageCommonWildlife by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommonWildlife to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECOMMONWILDLIFE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageCommonWildlifeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageCommonWildlife by their ID.
     *
     * @param id The ID of the VillageCommonWildlife to retrieve
     * @return The VillageCommonWildlife with the specified ID
     */
    @Override
    public VillageCommonWildlifeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECOMMONWILDLIFE_GET, new HashMap<>());
        VillageCommonWildlifeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageCommonWildlifes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonWildlifeDTO
     */
    @Override
    public Set<VillageCommonWildlifeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECOMMONWILDLIFE_GET, new HashMap<>());
        Set<VillageCommonWildlifeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommonWildlifeDTO postHookSave(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected VillageCommonWildlifeDTO preHookSave(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected VillageCommonWildlifeDTO postHookUpdate(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected VillageCommonWildlifeDTO preHookUpdate(VillageCommonWildlifeDTO VillageCommonWildlifeDTO) {
        return VillageCommonWildlifeDTO;
    }

    protected VillageCommonWildlifeDTO postHookDelete(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommonWildlifeDTO postHookGetById(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommonWildlifeDTO> postHookGetAll(PageDTO<VillageCommonWildlifeDTO> result) {
        return result;
    }
}
