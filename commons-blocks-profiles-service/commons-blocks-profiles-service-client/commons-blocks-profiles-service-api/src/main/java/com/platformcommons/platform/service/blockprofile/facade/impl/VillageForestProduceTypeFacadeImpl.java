package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageForestProduceTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageForestProduceTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageForestProduceTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageForestProduceTypeFacadeImpl implements VillageForestProduceTypeFacade {

    private final VillageForestProduceTypeServiceExt serviceExt;
    private final VillageForestProduceTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEFORESTPRODUCETYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTPRODUCETYPE.CREATE";
    private static final String VILLAGEFORESTPRODUCETYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTPRODUCETYPE.UPDATED";
    private static final String VILLAGEFORESTPRODUCETYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTPRODUCETYPE.DELETE";
    private static final String VILLAGEFORESTPRODUCETYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTPRODUCETYPE.GET";

    public VillageForestProduceTypeFacadeImpl(VillageForestProduceTypeServiceExt serviceExt, VillageForestProduceTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageForestProduceType entry in the system.
     *
     * @param VillageForestProduceTypeDTO The VillageForestProduceType information to be saved
     * @return The saved VillageForestProduceType data
     */
    @Override
    public VillageForestProduceTypeDTO save(VillageForestProduceTypeDTO VillageForestProduceTypeDTO) {
        log.debug("Entry - save(VillageForestProduceTypeDTO={})", VillageForestProduceTypeDTO);
        evaluator.evaluate(VILLAGEFORESTPRODUCETYPE_CREATE, new HashMap<>());
        VillageForestProduceTypeDTO = preHookSave(VillageForestProduceTypeDTO);
        VillageForestProduceTypeDTO dto = serviceExt.save(VillageForestProduceTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageForestProduceType entry.
     *
     * @param VillageForestProduceTypeDTO The VillageForestProduceType information to be updated
     * @return The updated VillageForestProduceType data
     */
    @Override
    public VillageForestProduceTypeDTO update(VillageForestProduceTypeDTO VillageForestProduceTypeDTO) {
        log.debug("Entry - update(VillageForestProduceTypeDTO={})", VillageForestProduceTypeDTO);
        evaluator.evaluate(VILLAGEFORESTPRODUCETYPE_UPDATE, new HashMap<>());
        VillageForestProduceTypeDTO = preHookUpdate(VillageForestProduceTypeDTO);
        VillageForestProduceTypeDTO dto = serviceExt.update(VillageForestProduceTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageForestProduceTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageForestProduceTypes
     */
    @Override
    public PageDTO<VillageForestProduceTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEFORESTPRODUCETYPE_GET, new HashMap<>());
        PageDTO<VillageForestProduceTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageForestProduceType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageForestProduceType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEFORESTPRODUCETYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageForestProduceTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageForestProduceType by their ID.
     *
     * @param id The ID of the VillageForestProduceType to retrieve
     * @return The VillageForestProduceType with the specified ID
     */
    @Override
    public VillageForestProduceTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEFORESTPRODUCETYPE_GET, new HashMap<>());
        VillageForestProduceTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageForestProduceTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageForestProduceTypeDTO
     */
    @Override
    public Set<VillageForestProduceTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEFORESTPRODUCETYPE_GET, new HashMap<>());
        Set<VillageForestProduceTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageForestProduceTypeDTO postHookSave(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected VillageForestProduceTypeDTO preHookSave(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected VillageForestProduceTypeDTO postHookUpdate(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected VillageForestProduceTypeDTO preHookUpdate(VillageForestProduceTypeDTO VillageForestProduceTypeDTO) {
        return VillageForestProduceTypeDTO;
    }

    protected VillageForestProduceTypeDTO postHookDelete(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageForestProduceTypeDTO postHookGetById(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageForestProduceTypeDTO> postHookGetAll(PageDTO<VillageForestProduceTypeDTO> result) {
        return result;
    }
}
