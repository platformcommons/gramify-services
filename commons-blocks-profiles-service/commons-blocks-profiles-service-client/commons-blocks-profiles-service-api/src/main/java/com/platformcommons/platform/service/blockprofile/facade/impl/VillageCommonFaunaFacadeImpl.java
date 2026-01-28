package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageCommonFaunaFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageCommonFaunaProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageCommonFaunaServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageCommonFaunaFacadeImpl implements VillageCommonFaunaFacade {

    private final VillageCommonFaunaServiceExt serviceExt;
    private final VillageCommonFaunaProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECOMMONFAUNA_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFAUNA.CREATE";
    private static final String VILLAGECOMMONFAUNA_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFAUNA.UPDATED";
    private static final String VILLAGECOMMONFAUNA_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFAUNA.DELETE";
    private static final String VILLAGECOMMONFAUNA_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFAUNA.GET";

    public VillageCommonFaunaFacadeImpl(VillageCommonFaunaServiceExt serviceExt, VillageCommonFaunaProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageCommonFauna entry in the system.
     *
     * @param VillageCommonFaunaDTO The VillageCommonFauna information to be saved
     * @return The saved VillageCommonFauna data
     */
    @Override
    public VillageCommonFaunaDTO save(VillageCommonFaunaDTO VillageCommonFaunaDTO) {
        log.debug("Entry - save(VillageCommonFaunaDTO={})", VillageCommonFaunaDTO);
        evaluator.evaluate(VILLAGECOMMONFAUNA_CREATE, new HashMap<>());
        VillageCommonFaunaDTO = preHookSave(VillageCommonFaunaDTO);
        VillageCommonFaunaDTO dto = serviceExt.save(VillageCommonFaunaDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageCommonFauna entry.
     *
     * @param VillageCommonFaunaDTO The VillageCommonFauna information to be updated
     * @return The updated VillageCommonFauna data
     */
    @Override
    public VillageCommonFaunaDTO update(VillageCommonFaunaDTO VillageCommonFaunaDTO) {
        log.debug("Entry - update(VillageCommonFaunaDTO={})", VillageCommonFaunaDTO);
        evaluator.evaluate(VILLAGECOMMONFAUNA_UPDATE, new HashMap<>());
        VillageCommonFaunaDTO = preHookUpdate(VillageCommonFaunaDTO);
        VillageCommonFaunaDTO dto = serviceExt.update(VillageCommonFaunaDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageCommonFaunas.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommonFaunas
     */
    @Override
    public PageDTO<VillageCommonFaunaDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECOMMONFAUNA_GET, new HashMap<>());
        PageDTO<VillageCommonFaunaDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageCommonFauna by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommonFauna to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECOMMONFAUNA_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageCommonFaunaDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageCommonFauna by their ID.
     *
     * @param id The ID of the VillageCommonFauna to retrieve
     * @return The VillageCommonFauna with the specified ID
     */
    @Override
    public VillageCommonFaunaDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECOMMONFAUNA_GET, new HashMap<>());
        VillageCommonFaunaDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageCommonFaunas by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonFaunaDTO
     */
    @Override
    public Set<VillageCommonFaunaDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECOMMONFAUNA_GET, new HashMap<>());
        Set<VillageCommonFaunaDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommonFaunaDTO postHookSave(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected VillageCommonFaunaDTO preHookSave(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected VillageCommonFaunaDTO postHookUpdate(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected VillageCommonFaunaDTO preHookUpdate(VillageCommonFaunaDTO VillageCommonFaunaDTO) {
        return VillageCommonFaunaDTO;
    }

    protected VillageCommonFaunaDTO postHookDelete(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommonFaunaDTO postHookGetById(VillageCommonFaunaDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommonFaunaDTO> postHookGetAll(PageDTO<VillageCommonFaunaDTO> result) {
        return result;
    }
}
