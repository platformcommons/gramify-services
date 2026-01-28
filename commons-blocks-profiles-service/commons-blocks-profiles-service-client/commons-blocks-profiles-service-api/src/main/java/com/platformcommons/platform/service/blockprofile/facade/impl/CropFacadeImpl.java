package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.CropFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.CropProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.CropServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class CropFacadeImpl implements CropFacade {

    private final CropServiceExt serviceExt;
    private final CropProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String CROP_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROP.CREATE";
    private static final String CROP_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROP.UPDATED";
    private static final String CROP_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROP.DELETE";
    private static final String CROP_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.CROP.GET";

    public CropFacadeImpl(CropServiceExt serviceExt, CropProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new Crop entry in the system.
     *
     * @param CropDTO The Crop information to be saved
     * @return The saved Crop data
     */
    @Override
    public CropDTO save(CropDTO CropDTO) {
        log.debug("Entry - save(CropDTO={})", CropDTO);
        evaluator.evaluate(CROP_CREATE, new HashMap<>());
        CropDTO = preHookSave(CropDTO);
        CropDTO dto = serviceExt.save(CropDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing Crop entry.
     *
     * @param CropDTO The Crop information to be updated
     * @return The updated Crop data
     */
    @Override
    public CropDTO update(CropDTO CropDTO) {
        log.debug("Entry - update(CropDTO={})", CropDTO);
        evaluator.evaluate(CROP_UPDATE, new HashMap<>());
        CropDTO = preHookUpdate(CropDTO);
        CropDTO dto = serviceExt.update(CropDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of Crops.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Crops
     */
    @Override
    public PageDTO<CropDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(CROP_GET, new HashMap<>());
        PageDTO<CropDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a Crop by their ID with a specified reason.
     *
     * @param id     The ID of the Crop to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(CROP_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        CropDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a Crop by their ID.
     *
     * @param id The ID of the Crop to retrieve
     * @return The Crop with the specified ID
     */
    @Override
    public CropDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(CROP_GET, new HashMap<>());
        CropDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all Crops by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CropDTO
     */
    @Override
    public Set<CropDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(CROP_GET, new HashMap<>());
        Set<CropDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CropDTO postHookSave(CropDTO dto) {
        return dto;
    }

    protected CropDTO preHookSave(CropDTO dto) {
        return dto;
    }

    protected CropDTO postHookUpdate(CropDTO dto) {
        return dto;
    }

    protected CropDTO preHookUpdate(CropDTO CropDTO) {
        return CropDTO;
    }

    protected CropDTO postHookDelete(CropDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CropDTO postHookGetById(CropDTO dto) {
        return dto;
    }

    protected PageDTO<CropDTO> postHookGetAll(PageDTO<CropDTO> result) {
        return result;
    }
}
