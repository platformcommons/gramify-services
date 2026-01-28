package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.LocalArtFormTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.LocalArtFormTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.LocalArtFormTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class LocalArtFormTypeFacadeImpl implements LocalArtFormTypeFacade {

    private final LocalArtFormTypeServiceExt serviceExt;
    private final LocalArtFormTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String LOCALARTFORMTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALARTFORMTYPE.CREATE";
    private static final String LOCALARTFORMTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALARTFORMTYPE.UPDATED";
    private static final String LOCALARTFORMTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALARTFORMTYPE.DELETE";
    private static final String LOCALARTFORMTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.LOCALARTFORMTYPE.GET";

    public LocalArtFormTypeFacadeImpl(LocalArtFormTypeServiceExt serviceExt, LocalArtFormTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new LocalArtFormType entry in the system.
     *
     * @param LocalArtFormTypeDTO The LocalArtFormType information to be saved
     * @return The saved LocalArtFormType data
     */
    @Override
    public LocalArtFormTypeDTO save(LocalArtFormTypeDTO LocalArtFormTypeDTO) {
        log.debug("Entry - save(LocalArtFormTypeDTO={})", LocalArtFormTypeDTO);
        evaluator.evaluate(LOCALARTFORMTYPE_CREATE, new HashMap<>());
        LocalArtFormTypeDTO = preHookSave(LocalArtFormTypeDTO);
        LocalArtFormTypeDTO dto = serviceExt.save(LocalArtFormTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing LocalArtFormType entry.
     *
     * @param LocalArtFormTypeDTO The LocalArtFormType information to be updated
     * @return The updated LocalArtFormType data
     */
    @Override
    public LocalArtFormTypeDTO update(LocalArtFormTypeDTO LocalArtFormTypeDTO) {
        log.debug("Entry - update(LocalArtFormTypeDTO={})", LocalArtFormTypeDTO);
        evaluator.evaluate(LOCALARTFORMTYPE_UPDATE, new HashMap<>());
        LocalArtFormTypeDTO = preHookUpdate(LocalArtFormTypeDTO);
        LocalArtFormTypeDTO dto = serviceExt.update(LocalArtFormTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of LocalArtFormTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of LocalArtFormTypes
     */
    @Override
    public PageDTO<LocalArtFormTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(LOCALARTFORMTYPE_GET, new HashMap<>());
        PageDTO<LocalArtFormTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a LocalArtFormType by their ID with a specified reason.
     *
     * @param id     The ID of the LocalArtFormType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(LOCALARTFORMTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        LocalArtFormTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a LocalArtFormType by their ID.
     *
     * @param id The ID of the LocalArtFormType to retrieve
     * @return The LocalArtFormType with the specified ID
     */
    @Override
    public LocalArtFormTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(LOCALARTFORMTYPE_GET, new HashMap<>());
        LocalArtFormTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all LocalArtFormTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of LocalArtFormTypeDTO
     */
    @Override
    public Set<LocalArtFormTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(LOCALARTFORMTYPE_GET, new HashMap<>());
        Set<LocalArtFormTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected LocalArtFormTypeDTO postHookSave(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected LocalArtFormTypeDTO preHookSave(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected LocalArtFormTypeDTO postHookUpdate(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected LocalArtFormTypeDTO preHookUpdate(LocalArtFormTypeDTO LocalArtFormTypeDTO) {
        return LocalArtFormTypeDTO;
    }

    protected LocalArtFormTypeDTO postHookDelete(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected LocalArtFormTypeDTO postHookGetById(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected PageDTO<LocalArtFormTypeDTO> postHookGetAll(PageDTO<LocalArtFormTypeDTO> result) {
        return result;
    }
}
