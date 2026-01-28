package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageSoilTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageSoilTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageSoilTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageSoilTypeFacadeImpl implements VillageSoilTypeFacade {

    private final VillageSoilTypeServiceExt serviceExt;
    private final VillageSoilTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGESOILTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOILTYPE.CREATE";
    private static final String VILLAGESOILTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOILTYPE.UPDATED";
    private static final String VILLAGESOILTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOILTYPE.DELETE";
    private static final String VILLAGESOILTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOILTYPE.GET";

    public VillageSoilTypeFacadeImpl(VillageSoilTypeServiceExt serviceExt, VillageSoilTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageSoilType entry in the system.
     *
     * @param VillageSoilTypeDTO The VillageSoilType information to be saved
     * @return The saved VillageSoilType data
     */
    @Override
    public VillageSoilTypeDTO save(VillageSoilTypeDTO VillageSoilTypeDTO) {
        log.debug("Entry - save(VillageSoilTypeDTO={})", VillageSoilTypeDTO);
        evaluator.evaluate(VILLAGESOILTYPE_CREATE, new HashMap<>());
        VillageSoilTypeDTO = preHookSave(VillageSoilTypeDTO);
        VillageSoilTypeDTO dto = serviceExt.save(VillageSoilTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageSoilType entry.
     *
     * @param VillageSoilTypeDTO The VillageSoilType information to be updated
     * @return The updated VillageSoilType data
     */
    @Override
    public VillageSoilTypeDTO update(VillageSoilTypeDTO VillageSoilTypeDTO) {
        log.debug("Entry - update(VillageSoilTypeDTO={})", VillageSoilTypeDTO);
        evaluator.evaluate(VILLAGESOILTYPE_UPDATE, new HashMap<>());
        VillageSoilTypeDTO = preHookUpdate(VillageSoilTypeDTO);
        VillageSoilTypeDTO dto = serviceExt.update(VillageSoilTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageSoilTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSoilTypes
     */
    @Override
    public PageDTO<VillageSoilTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGESOILTYPE_GET, new HashMap<>());
        PageDTO<VillageSoilTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageSoilType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSoilType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGESOILTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageSoilTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageSoilType by their ID.
     *
     * @param id The ID of the VillageSoilType to retrieve
     * @return The VillageSoilType with the specified ID
     */
    @Override
    public VillageSoilTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGESOILTYPE_GET, new HashMap<>());
        VillageSoilTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageSoilTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSoilTypeDTO
     */
    @Override
    public Set<VillageSoilTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGESOILTYPE_GET, new HashMap<>());
        Set<VillageSoilTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageSoilTypeDTO postHookSave(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected VillageSoilTypeDTO preHookSave(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected VillageSoilTypeDTO postHookUpdate(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected VillageSoilTypeDTO preHookUpdate(VillageSoilTypeDTO VillageSoilTypeDTO) {
        return VillageSoilTypeDTO;
    }

    protected VillageSoilTypeDTO postHookDelete(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSoilTypeDTO postHookGetById(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSoilTypeDTO> postHookGetAll(PageDTO<VillageSoilTypeDTO> result) {
        return result;
    }
}
