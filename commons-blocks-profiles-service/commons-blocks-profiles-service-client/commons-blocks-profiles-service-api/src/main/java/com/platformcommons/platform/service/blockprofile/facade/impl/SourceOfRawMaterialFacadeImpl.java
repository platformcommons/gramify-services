package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.SourceOfRawMaterialFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.SourceOfRawMaterialProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.SourceOfRawMaterialServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class SourceOfRawMaterialFacadeImpl implements SourceOfRawMaterialFacade {

    private final SourceOfRawMaterialServiceExt serviceExt;
    private final SourceOfRawMaterialProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String SOURCEOFRAWMATERIAL_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SOURCEOFRAWMATERIAL.CREATE";
    private static final String SOURCEOFRAWMATERIAL_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SOURCEOFRAWMATERIAL.UPDATED";
    private static final String SOURCEOFRAWMATERIAL_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SOURCEOFRAWMATERIAL.DELETE";
    private static final String SOURCEOFRAWMATERIAL_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SOURCEOFRAWMATERIAL.GET";

    public SourceOfRawMaterialFacadeImpl(SourceOfRawMaterialServiceExt serviceExt, SourceOfRawMaterialProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new SourceOfRawMaterial entry in the system.
     *
     * @param SourceOfRawMaterialDTO The SourceOfRawMaterial information to be saved
     * @return The saved SourceOfRawMaterial data
     */
    @Override
    public SourceOfRawMaterialDTO save(SourceOfRawMaterialDTO SourceOfRawMaterialDTO) {
        log.debug("Entry - save(SourceOfRawMaterialDTO={})", SourceOfRawMaterialDTO);
        evaluator.evaluate(SOURCEOFRAWMATERIAL_CREATE, new HashMap<>());
        SourceOfRawMaterialDTO = preHookSave(SourceOfRawMaterialDTO);
        SourceOfRawMaterialDTO dto = serviceExt.save(SourceOfRawMaterialDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing SourceOfRawMaterial entry.
     *
     * @param SourceOfRawMaterialDTO The SourceOfRawMaterial information to be updated
     * @return The updated SourceOfRawMaterial data
     */
    @Override
    public SourceOfRawMaterialDTO update(SourceOfRawMaterialDTO SourceOfRawMaterialDTO) {
        log.debug("Entry - update(SourceOfRawMaterialDTO={})", SourceOfRawMaterialDTO);
        evaluator.evaluate(SOURCEOFRAWMATERIAL_UPDATE, new HashMap<>());
        SourceOfRawMaterialDTO = preHookUpdate(SourceOfRawMaterialDTO);
        SourceOfRawMaterialDTO dto = serviceExt.update(SourceOfRawMaterialDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of SourceOfRawMaterials.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SourceOfRawMaterials
     */
    @Override
    public PageDTO<SourceOfRawMaterialDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(SOURCEOFRAWMATERIAL_GET, new HashMap<>());
        PageDTO<SourceOfRawMaterialDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a SourceOfRawMaterial by their ID with a specified reason.
     *
     * @param id     The ID of the SourceOfRawMaterial to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(SOURCEOFRAWMATERIAL_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        SourceOfRawMaterialDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a SourceOfRawMaterial by their ID.
     *
     * @param id The ID of the SourceOfRawMaterial to retrieve
     * @return The SourceOfRawMaterial with the specified ID
     */
    @Override
    public SourceOfRawMaterialDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(SOURCEOFRAWMATERIAL_GET, new HashMap<>());
        SourceOfRawMaterialDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all SourceOfRawMaterials by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SourceOfRawMaterialDTO
     */
    @Override
    public Set<SourceOfRawMaterialDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(SOURCEOFRAWMATERIAL_GET, new HashMap<>());
        Set<SourceOfRawMaterialDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SourceOfRawMaterialDTO postHookSave(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected SourceOfRawMaterialDTO preHookSave(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected SourceOfRawMaterialDTO postHookUpdate(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected SourceOfRawMaterialDTO preHookUpdate(SourceOfRawMaterialDTO SourceOfRawMaterialDTO) {
        return SourceOfRawMaterialDTO;
    }

    protected SourceOfRawMaterialDTO postHookDelete(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SourceOfRawMaterialDTO postHookGetById(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected PageDTO<SourceOfRawMaterialDTO> postHookGetAll(PageDTO<SourceOfRawMaterialDTO> result) {
        return result;
    }
}
