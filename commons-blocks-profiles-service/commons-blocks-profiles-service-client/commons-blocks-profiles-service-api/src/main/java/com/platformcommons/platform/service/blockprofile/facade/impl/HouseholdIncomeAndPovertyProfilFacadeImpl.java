package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdIncomeAndPovertyProfilFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdIncomeAndPovertyProfilProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdIncomeAndPovertyProfilServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdIncomeAndPovertyProfilFacadeImpl implements HouseholdIncomeAndPovertyProfilFacade {

    private final HouseholdIncomeAndPovertyProfilServiceExt serviceExt;
    private final HouseholdIncomeAndPovertyProfilProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLDINCOMEANDPOVERTYPROFIL_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDINCOMEANDPOVERTYPROFIL.CREATE";
    private static final String HOUSEHOLDINCOMEANDPOVERTYPROFIL_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDINCOMEANDPOVERTYPROFIL.UPDATED";
    private static final String HOUSEHOLDINCOMEANDPOVERTYPROFIL_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDINCOMEANDPOVERTYPROFIL.DELETE";
    private static final String HOUSEHOLDINCOMEANDPOVERTYPROFIL_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDINCOMEANDPOVERTYPROFIL.GET";

    public HouseholdIncomeAndPovertyProfilFacadeImpl(HouseholdIncomeAndPovertyProfilServiceExt serviceExt, HouseholdIncomeAndPovertyProfilProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HouseholdIncomeAndPovertyProfil entry in the system.
     *
     * @param HouseholdIncomeAndPovertyProfilDTO The HouseholdIncomeAndPovertyProfil information to be saved
     * @return The saved HouseholdIncomeAndPovertyProfil data
     */
    @Override
    public HouseholdIncomeAndPovertyProfilDTO save(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO) {
        log.debug("Entry - save(HouseholdIncomeAndPovertyProfilDTO={})", HouseholdIncomeAndPovertyProfilDTO);
        evaluator.evaluate(HOUSEHOLDINCOMEANDPOVERTYPROFIL_CREATE, new HashMap<>());
        HouseholdIncomeAndPovertyProfilDTO = preHookSave(HouseholdIncomeAndPovertyProfilDTO);
        HouseholdIncomeAndPovertyProfilDTO dto = serviceExt.save(HouseholdIncomeAndPovertyProfilDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HouseholdIncomeAndPovertyProfil entry.
     *
     * @param HouseholdIncomeAndPovertyProfilDTO The HouseholdIncomeAndPovertyProfil information to be updated
     * @return The updated HouseholdIncomeAndPovertyProfil data
     */
    @Override
    public HouseholdIncomeAndPovertyProfilDTO update(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO) {
        log.debug("Entry - update(HouseholdIncomeAndPovertyProfilDTO={})", HouseholdIncomeAndPovertyProfilDTO);
        evaluator.evaluate(HOUSEHOLDINCOMEANDPOVERTYPROFIL_UPDATE, new HashMap<>());
        HouseholdIncomeAndPovertyProfilDTO = preHookUpdate(HouseholdIncomeAndPovertyProfilDTO);
        HouseholdIncomeAndPovertyProfilDTO dto = serviceExt.update(HouseholdIncomeAndPovertyProfilDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HouseholdIncomeAndPovertyProfils.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdIncomeAndPovertyProfils
     */
    @Override
    public PageDTO<HouseholdIncomeAndPovertyProfilDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLDINCOMEANDPOVERTYPROFIL_GET, new HashMap<>());
        PageDTO<HouseholdIncomeAndPovertyProfilDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HouseholdIncomeAndPovertyProfil by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdIncomeAndPovertyProfil to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLDINCOMEANDPOVERTYPROFIL_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdIncomeAndPovertyProfilDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HouseholdIncomeAndPovertyProfil by their ID.
     *
     * @param id The ID of the HouseholdIncomeAndPovertyProfil to retrieve
     * @return The HouseholdIncomeAndPovertyProfil with the specified ID
     */
    @Override
    public HouseholdIncomeAndPovertyProfilDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLDINCOMEANDPOVERTYPROFIL_GET, new HashMap<>());
        HouseholdIncomeAndPovertyProfilDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HouseholdIncomeAndPovertyProfils by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdIncomeAndPovertyProfilDTO
     */
    @Override
    public Set<HouseholdIncomeAndPovertyProfilDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLDINCOMEANDPOVERTYPROFIL_GET, new HashMap<>());
        Set<HouseholdIncomeAndPovertyProfilDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HouseholdIncomeAndPovertyProfilDTO postHookSave(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected HouseholdIncomeAndPovertyProfilDTO preHookSave(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected HouseholdIncomeAndPovertyProfilDTO postHookUpdate(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected HouseholdIncomeAndPovertyProfilDTO preHookUpdate(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO) {
        return HouseholdIncomeAndPovertyProfilDTO;
    }

    protected HouseholdIncomeAndPovertyProfilDTO postHookDelete(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdIncomeAndPovertyProfilDTO postHookGetById(HouseholdIncomeAndPovertyProfilDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdIncomeAndPovertyProfilDTO> postHookGetAll(PageDTO<HouseholdIncomeAndPovertyProfilDTO> result) {
        return result;
    }
}
