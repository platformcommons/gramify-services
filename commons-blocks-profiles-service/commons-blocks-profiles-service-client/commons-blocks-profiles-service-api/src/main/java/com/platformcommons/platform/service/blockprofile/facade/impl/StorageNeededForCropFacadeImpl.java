package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.StorageNeededForCropFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.StorageNeededForCropProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.StorageNeededForCropServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class StorageNeededForCropFacadeImpl implements StorageNeededForCropFacade {

    private final StorageNeededForCropServiceExt serviceExt;
    private final StorageNeededForCropProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String STORAGENEEDEDFORCROP_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STORAGENEEDEDFORCROP.CREATE";
    private static final String STORAGENEEDEDFORCROP_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STORAGENEEDEDFORCROP.UPDATED";
    private static final String STORAGENEEDEDFORCROP_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STORAGENEEDEDFORCROP.DELETE";
    private static final String STORAGENEEDEDFORCROP_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.STORAGENEEDEDFORCROP.GET";

    public StorageNeededForCropFacadeImpl(StorageNeededForCropServiceExt serviceExt, StorageNeededForCropProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new StorageNeededForCrop entry in the system.
     *
     * @param StorageNeededForCropDTO The StorageNeededForCrop information to be saved
     * @return The saved StorageNeededForCrop data
     */
    @Override
    public StorageNeededForCropDTO save(StorageNeededForCropDTO StorageNeededForCropDTO) {
        log.debug("Entry - save(StorageNeededForCropDTO={})", StorageNeededForCropDTO);
        evaluator.evaluate(STORAGENEEDEDFORCROP_CREATE, new HashMap<>());
        StorageNeededForCropDTO = preHookSave(StorageNeededForCropDTO);
        StorageNeededForCropDTO dto = serviceExt.save(StorageNeededForCropDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing StorageNeededForCrop entry.
     *
     * @param StorageNeededForCropDTO The StorageNeededForCrop information to be updated
     * @return The updated StorageNeededForCrop data
     */
    @Override
    public StorageNeededForCropDTO update(StorageNeededForCropDTO StorageNeededForCropDTO) {
        log.debug("Entry - update(StorageNeededForCropDTO={})", StorageNeededForCropDTO);
        evaluator.evaluate(STORAGENEEDEDFORCROP_UPDATE, new HashMap<>());
        StorageNeededForCropDTO = preHookUpdate(StorageNeededForCropDTO);
        StorageNeededForCropDTO dto = serviceExt.update(StorageNeededForCropDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of StorageNeededForCrops.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of StorageNeededForCrops
     */
    @Override
    public PageDTO<StorageNeededForCropDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(STORAGENEEDEDFORCROP_GET, new HashMap<>());
        PageDTO<StorageNeededForCropDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a StorageNeededForCrop by their ID with a specified reason.
     *
     * @param id     The ID of the StorageNeededForCrop to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(STORAGENEEDEDFORCROP_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        StorageNeededForCropDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a StorageNeededForCrop by their ID.
     *
     * @param id The ID of the StorageNeededForCrop to retrieve
     * @return The StorageNeededForCrop with the specified ID
     */
    @Override
    public StorageNeededForCropDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(STORAGENEEDEDFORCROP_GET, new HashMap<>());
        StorageNeededForCropDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all StorageNeededForCrops by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of StorageNeededForCropDTO
     */
    @Override
    public Set<StorageNeededForCropDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(STORAGENEEDEDFORCROP_GET, new HashMap<>());
        Set<StorageNeededForCropDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected StorageNeededForCropDTO postHookSave(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected StorageNeededForCropDTO preHookSave(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected StorageNeededForCropDTO postHookUpdate(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected StorageNeededForCropDTO preHookUpdate(StorageNeededForCropDTO StorageNeededForCropDTO) {
        return StorageNeededForCropDTO;
    }

    protected StorageNeededForCropDTO postHookDelete(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected StorageNeededForCropDTO postHookGetById(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected PageDTO<StorageNeededForCropDTO> postHookGetAll(PageDTO<StorageNeededForCropDTO> result) {
        return result;
    }
}
