package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.KeyConstraintsForSurplusExportFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.KeyConstraintsForSurplusExportProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.KeyConstraintsForSurplusExportServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class KeyConstraintsForSurplusExportFacadeImpl implements KeyConstraintsForSurplusExportFacade {

    private final KeyConstraintsForSurplusExportServiceExt serviceExt;
    private final KeyConstraintsForSurplusExportProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String KEYCONSTRAINTSFORSURPLUSEXPORT_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.KEYCONSTRAINTSFORSURPLUSEXPORT.CREATE";
    private static final String KEYCONSTRAINTSFORSURPLUSEXPORT_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.KEYCONSTRAINTSFORSURPLUSEXPORT.UPDATED";
    private static final String KEYCONSTRAINTSFORSURPLUSEXPORT_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.KEYCONSTRAINTSFORSURPLUSEXPORT.DELETE";
    private static final String KEYCONSTRAINTSFORSURPLUSEXPORT_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.KEYCONSTRAINTSFORSURPLUSEXPORT.GET";

    public KeyConstraintsForSurplusExportFacadeImpl(KeyConstraintsForSurplusExportServiceExt serviceExt, KeyConstraintsForSurplusExportProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new KeyConstraintsForSurplusExport entry in the system.
     *
     * @param KeyConstraintsForSurplusExportDTO The KeyConstraintsForSurplusExport information to be saved
     * @return The saved KeyConstraintsForSurplusExport data
     */
    @Override
    public KeyConstraintsForSurplusExportDTO save(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO) {
        log.debug("Entry - save(KeyConstraintsForSurplusExportDTO={})", KeyConstraintsForSurplusExportDTO);
        evaluator.evaluate(KEYCONSTRAINTSFORSURPLUSEXPORT_CREATE, new HashMap<>());
        KeyConstraintsForSurplusExportDTO = preHookSave(KeyConstraintsForSurplusExportDTO);
        KeyConstraintsForSurplusExportDTO dto = serviceExt.save(KeyConstraintsForSurplusExportDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing KeyConstraintsForSurplusExport entry.
     *
     * @param KeyConstraintsForSurplusExportDTO The KeyConstraintsForSurplusExport information to be updated
     * @return The updated KeyConstraintsForSurplusExport data
     */
    @Override
    public KeyConstraintsForSurplusExportDTO update(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO) {
        log.debug("Entry - update(KeyConstraintsForSurplusExportDTO={})", KeyConstraintsForSurplusExportDTO);
        evaluator.evaluate(KEYCONSTRAINTSFORSURPLUSEXPORT_UPDATE, new HashMap<>());
        KeyConstraintsForSurplusExportDTO = preHookUpdate(KeyConstraintsForSurplusExportDTO);
        KeyConstraintsForSurplusExportDTO dto = serviceExt.update(KeyConstraintsForSurplusExportDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of KeyConstraintsForSurplusExports.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of KeyConstraintsForSurplusExports
     */
    @Override
    public PageDTO<KeyConstraintsForSurplusExportDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(KEYCONSTRAINTSFORSURPLUSEXPORT_GET, new HashMap<>());
        PageDTO<KeyConstraintsForSurplusExportDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a KeyConstraintsForSurplusExport by their ID with a specified reason.
     *
     * @param id     The ID of the KeyConstraintsForSurplusExport to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(KEYCONSTRAINTSFORSURPLUSEXPORT_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        KeyConstraintsForSurplusExportDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a KeyConstraintsForSurplusExport by their ID.
     *
     * @param id The ID of the KeyConstraintsForSurplusExport to retrieve
     * @return The KeyConstraintsForSurplusExport with the specified ID
     */
    @Override
    public KeyConstraintsForSurplusExportDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(KEYCONSTRAINTSFORSURPLUSEXPORT_GET, new HashMap<>());
        KeyConstraintsForSurplusExportDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all KeyConstraintsForSurplusExports by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of KeyConstraintsForSurplusExportDTO
     */
    @Override
    public Set<KeyConstraintsForSurplusExportDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(KEYCONSTRAINTSFORSURPLUSEXPORT_GET, new HashMap<>());
        Set<KeyConstraintsForSurplusExportDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected KeyConstraintsForSurplusExportDTO postHookSave(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected KeyConstraintsForSurplusExportDTO preHookSave(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected KeyConstraintsForSurplusExportDTO postHookUpdate(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected KeyConstraintsForSurplusExportDTO preHookUpdate(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO) {
        return KeyConstraintsForSurplusExportDTO;
    }

    protected KeyConstraintsForSurplusExportDTO postHookDelete(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected KeyConstraintsForSurplusExportDTO postHookGetById(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected PageDTO<KeyConstraintsForSurplusExportDTO> postHookGetAll(PageDTO<KeyConstraintsForSurplusExportDTO> result) {
        return result;
    }
}
