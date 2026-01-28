package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageFinancialInstitutionProfFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageFinancialInstitutionProfProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageFinancialInstitutionProfServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageFinancialInstitutionProfFacadeImpl implements VillageFinancialInstitutionProfFacade {

    private final VillageFinancialInstitutionProfServiceExt serviceExt;
    private final VillageFinancialInstitutionProfProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEFINANCIALINSTITUTIONPROF_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFINANCIALINSTITUTIONPROF.CREATE";
    private static final String VILLAGEFINANCIALINSTITUTIONPROF_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFINANCIALINSTITUTIONPROF.UPDATED";
    private static final String VILLAGEFINANCIALINSTITUTIONPROF_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFINANCIALINSTITUTIONPROF.DELETE";
    private static final String VILLAGEFINANCIALINSTITUTIONPROF_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFINANCIALINSTITUTIONPROF.GET";

    public VillageFinancialInstitutionProfFacadeImpl(VillageFinancialInstitutionProfServiceExt serviceExt, VillageFinancialInstitutionProfProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageFinancialInstitutionProf entry in the system.
     *
     * @param VillageFinancialInstitutionProfDTO The VillageFinancialInstitutionProf information to be saved
     * @return The saved VillageFinancialInstitutionProf data
     */
    @Override
    public VillageFinancialInstitutionProfDTO save(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO) {
        log.debug("Entry - save(VillageFinancialInstitutionProfDTO={})", VillageFinancialInstitutionProfDTO);
        evaluator.evaluate(VILLAGEFINANCIALINSTITUTIONPROF_CREATE, new HashMap<>());
        VillageFinancialInstitutionProfDTO = preHookSave(VillageFinancialInstitutionProfDTO);
        VillageFinancialInstitutionProfDTO dto = serviceExt.save(VillageFinancialInstitutionProfDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageFinancialInstitutionProf entry.
     *
     * @param VillageFinancialInstitutionProfDTO The VillageFinancialInstitutionProf information to be updated
     * @return The updated VillageFinancialInstitutionProf data
     */
    @Override
    public VillageFinancialInstitutionProfDTO update(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO) {
        log.debug("Entry - update(VillageFinancialInstitutionProfDTO={})", VillageFinancialInstitutionProfDTO);
        evaluator.evaluate(VILLAGEFINANCIALINSTITUTIONPROF_UPDATE, new HashMap<>());
        VillageFinancialInstitutionProfDTO = preHookUpdate(VillageFinancialInstitutionProfDTO);
        VillageFinancialInstitutionProfDTO dto = serviceExt.update(VillageFinancialInstitutionProfDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageFinancialInstitutionProfs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageFinancialInstitutionProfs
     */
    @Override
    public PageDTO<VillageFinancialInstitutionProfDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEFINANCIALINSTITUTIONPROF_GET, new HashMap<>());
        PageDTO<VillageFinancialInstitutionProfDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageFinancialInstitutionProf by their ID with a specified reason.
     *
     * @param id     The ID of the VillageFinancialInstitutionProf to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEFINANCIALINSTITUTIONPROF_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageFinancialInstitutionProfDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageFinancialInstitutionProf by their ID.
     *
     * @param id The ID of the VillageFinancialInstitutionProf to retrieve
     * @return The VillageFinancialInstitutionProf with the specified ID
     */
    @Override
    public VillageFinancialInstitutionProfDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEFINANCIALINSTITUTIONPROF_GET, new HashMap<>());
        VillageFinancialInstitutionProfDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageFinancialInstitutionProfs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageFinancialInstitutionProfDTO
     */
    @Override
    public Set<VillageFinancialInstitutionProfDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEFINANCIALINSTITUTIONPROF_GET, new HashMap<>());
        Set<VillageFinancialInstitutionProfDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageFinancialInstitutionProfDTO postHookSave(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected VillageFinancialInstitutionProfDTO preHookSave(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected VillageFinancialInstitutionProfDTO postHookUpdate(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected VillageFinancialInstitutionProfDTO preHookUpdate(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO) {
        return VillageFinancialInstitutionProfDTO;
    }

    protected VillageFinancialInstitutionProfDTO postHookDelete(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageFinancialInstitutionProfDTO postHookGetById(VillageFinancialInstitutionProfDTO dto) {
        return dto;
    }

    protected PageDTO<VillageFinancialInstitutionProfDTO> postHookGetAll(PageDTO<VillageFinancialInstitutionProfDTO> result) {
        return result;
    }
}
