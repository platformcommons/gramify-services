package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.RawMaterialsNeededForIndustryFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.RawMaterialsNeededForIndustryProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.RawMaterialsNeededForIndustryServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class RawMaterialsNeededForIndustryFacadeImpl implements RawMaterialsNeededForIndustryFacade {

    private final RawMaterialsNeededForIndustryServiceExt serviceExt;
    private final RawMaterialsNeededForIndustryProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String RAWMATERIALSNEEDEDFORINDUSTRY_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RAWMATERIALSNEEDEDFORINDUSTRY.CREATE";
    private static final String RAWMATERIALSNEEDEDFORINDUSTRY_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RAWMATERIALSNEEDEDFORINDUSTRY.UPDATED";
    private static final String RAWMATERIALSNEEDEDFORINDUSTRY_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RAWMATERIALSNEEDEDFORINDUSTRY.DELETE";
    private static final String RAWMATERIALSNEEDEDFORINDUSTRY_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RAWMATERIALSNEEDEDFORINDUSTRY.GET";

    public RawMaterialsNeededForIndustryFacadeImpl(RawMaterialsNeededForIndustryServiceExt serviceExt, RawMaterialsNeededForIndustryProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new RawMaterialsNeededForIndustry entry in the system.
     *
     * @param RawMaterialsNeededForIndustryDTO The RawMaterialsNeededForIndustry information to be saved
     * @return The saved RawMaterialsNeededForIndustry data
     */
    @Override
    public RawMaterialsNeededForIndustryDTO save(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO) {
        log.debug("Entry - save(RawMaterialsNeededForIndustryDTO={})", RawMaterialsNeededForIndustryDTO);
        evaluator.evaluate(RAWMATERIALSNEEDEDFORINDUSTRY_CREATE, new HashMap<>());
        RawMaterialsNeededForIndustryDTO = preHookSave(RawMaterialsNeededForIndustryDTO);
        RawMaterialsNeededForIndustryDTO dto = serviceExt.save(RawMaterialsNeededForIndustryDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing RawMaterialsNeededForIndustry entry.
     *
     * @param RawMaterialsNeededForIndustryDTO The RawMaterialsNeededForIndustry information to be updated
     * @return The updated RawMaterialsNeededForIndustry data
     */
    @Override
    public RawMaterialsNeededForIndustryDTO update(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO) {
        log.debug("Entry - update(RawMaterialsNeededForIndustryDTO={})", RawMaterialsNeededForIndustryDTO);
        evaluator.evaluate(RAWMATERIALSNEEDEDFORINDUSTRY_UPDATE, new HashMap<>());
        RawMaterialsNeededForIndustryDTO = preHookUpdate(RawMaterialsNeededForIndustryDTO);
        RawMaterialsNeededForIndustryDTO dto = serviceExt.update(RawMaterialsNeededForIndustryDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of RawMaterialsNeededForIndustrys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of RawMaterialsNeededForIndustrys
     */
    @Override
    public PageDTO<RawMaterialsNeededForIndustryDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(RAWMATERIALSNEEDEDFORINDUSTRY_GET, new HashMap<>());
        PageDTO<RawMaterialsNeededForIndustryDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a RawMaterialsNeededForIndustry by their ID with a specified reason.
     *
     * @param id     The ID of the RawMaterialsNeededForIndustry to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(RAWMATERIALSNEEDEDFORINDUSTRY_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        RawMaterialsNeededForIndustryDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a RawMaterialsNeededForIndustry by their ID.
     *
     * @param id The ID of the RawMaterialsNeededForIndustry to retrieve
     * @return The RawMaterialsNeededForIndustry with the specified ID
     */
    @Override
    public RawMaterialsNeededForIndustryDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(RAWMATERIALSNEEDEDFORINDUSTRY_GET, new HashMap<>());
        RawMaterialsNeededForIndustryDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all RawMaterialsNeededForIndustrys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of RawMaterialsNeededForIndustryDTO
     */
    @Override
    public Set<RawMaterialsNeededForIndustryDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(RAWMATERIALSNEEDEDFORINDUSTRY_GET, new HashMap<>());
        Set<RawMaterialsNeededForIndustryDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected RawMaterialsNeededForIndustryDTO postHookSave(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected RawMaterialsNeededForIndustryDTO preHookSave(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected RawMaterialsNeededForIndustryDTO postHookUpdate(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected RawMaterialsNeededForIndustryDTO preHookUpdate(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO) {
        return RawMaterialsNeededForIndustryDTO;
    }

    protected RawMaterialsNeededForIndustryDTO postHookDelete(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected RawMaterialsNeededForIndustryDTO postHookGetById(RawMaterialsNeededForIndustryDTO dto) {
        return dto;
    }

    protected PageDTO<RawMaterialsNeededForIndustryDTO> postHookGetAll(PageDTO<RawMaterialsNeededForIndustryDTO> result) {
        return result;
    }
}
