package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageAgriInputDemandProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageAgriInputDemandProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageAgriInputDemandProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageAgriInputDemandProfileFacadeImpl implements VillageAgriInputDemandProfileFacade {

    private final VillageAgriInputDemandProfileServiceExt serviceExt;
    private final VillageAgriInputDemandProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEAGRIINPUTDEMANDPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEAGRIINPUTDEMANDPROFILE.CREATE";
    private static final String VILLAGEAGRIINPUTDEMANDPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEAGRIINPUTDEMANDPROFILE.UPDATED";
    private static final String VILLAGEAGRIINPUTDEMANDPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEAGRIINPUTDEMANDPROFILE.DELETE";
    private static final String VILLAGEAGRIINPUTDEMANDPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEAGRIINPUTDEMANDPROFILE.GET";

    public VillageAgriInputDemandProfileFacadeImpl(VillageAgriInputDemandProfileServiceExt serviceExt, VillageAgriInputDemandProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageAgriInputDemandProfile entry in the system.
     *
     * @param VillageAgriInputDemandProfileDTO The VillageAgriInputDemandProfile information to be saved
     * @return The saved VillageAgriInputDemandProfile data
     */
    @Override
    public VillageAgriInputDemandProfileDTO save(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfileDTO) {
        log.debug("Entry - save(VillageAgriInputDemandProfileDTO={})", VillageAgriInputDemandProfileDTO);
        evaluator.evaluate(VILLAGEAGRIINPUTDEMANDPROFILE_CREATE, new HashMap<>());
        VillageAgriInputDemandProfileDTO = preHookSave(VillageAgriInputDemandProfileDTO);
        VillageAgriInputDemandProfileDTO dto = serviceExt.save(VillageAgriInputDemandProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageAgriInputDemandProfile entry.
     *
     * @param VillageAgriInputDemandProfileDTO The VillageAgriInputDemandProfile information to be updated
     * @return The updated VillageAgriInputDemandProfile data
     */
    @Override
    public VillageAgriInputDemandProfileDTO update(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfileDTO) {
        log.debug("Entry - update(VillageAgriInputDemandProfileDTO={})", VillageAgriInputDemandProfileDTO);
        evaluator.evaluate(VILLAGEAGRIINPUTDEMANDPROFILE_UPDATE, new HashMap<>());
        VillageAgriInputDemandProfileDTO = preHookUpdate(VillageAgriInputDemandProfileDTO);
        VillageAgriInputDemandProfileDTO dto = serviceExt.update(VillageAgriInputDemandProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageAgriInputDemandProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageAgriInputDemandProfiles
     */
    @Override
    public PageDTO<VillageAgriInputDemandProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEAGRIINPUTDEMANDPROFILE_GET, new HashMap<>());
        PageDTO<VillageAgriInputDemandProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageAgriInputDemandProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageAgriInputDemandProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEAGRIINPUTDEMANDPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageAgriInputDemandProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageAgriInputDemandProfile by their ID.
     *
     * @param id The ID of the VillageAgriInputDemandProfile to retrieve
     * @return The VillageAgriInputDemandProfile with the specified ID
     */
    @Override
    public VillageAgriInputDemandProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEAGRIINPUTDEMANDPROFILE_GET, new HashMap<>());
        VillageAgriInputDemandProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageAgriInputDemandProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageAgriInputDemandProfileDTO
     */
    @Override
    public Set<VillageAgriInputDemandProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEAGRIINPUTDEMANDPROFILE_GET, new HashMap<>());
        Set<VillageAgriInputDemandProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of machinesInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param machinesInDemandList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMachinesInDemandToVillageAgriInputDemandProfile(Long id, List<MachinesInDemandDTO> machinesInDemandList){
        serviceExt.addMachinesInDemandToVillageAgriInputDemandProfile(id,machinesInDemandList);
    }
/**
     * Adds a list of purposeOfCreditList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param purposeOfCreditList  to be added
     * @since 1.0.0
     */
    @Override
    public void addPurposeOfCreditToVillageAgriInputDemandProfile(Long id, List<PurposeOfCreditDTO> purposeOfCreditList){
        serviceExt.addPurposeOfCreditToVillageAgriInputDemandProfile(id,purposeOfCreditList);
    }
/**
     * Adds a list of fertilizersInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param fertilizersInDemandList  to be added
     * @since 1.0.0
     */
    @Override
    public void addFertilizersInDemandToVillageAgriInputDemandProfile(Long id, List<FertilizersInDemandDTO> fertilizersInDemandList){
        serviceExt.addFertilizersInDemandToVillageAgriInputDemandProfile(id,fertilizersInDemandList);
    }
/**
     * Adds a list of sourceOfRawMaterialList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param sourceOfRawMaterialList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSourceOfRawMaterialToVillageAgriInputDemandProfile(Long id, List<SourceOfRawMaterialDTO> sourceOfRawMaterialList){
        serviceExt.addSourceOfRawMaterialToVillageAgriInputDemandProfile(id,sourceOfRawMaterialList);
    }
/**
     * Adds a list of seedsInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param seedsInDemandList  to be added
     * @since 1.0.0
     */
    @Override
    public void addSeedsInDemandToVillageAgriInputDemandProfile(Long id, List<SeedsInDemandDTO> seedsInDemandList){
        serviceExt.addSeedsInDemandToVillageAgriInputDemandProfile(id,seedsInDemandList);
    }
/**
     * Adds a list of mainCreditSourceList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param mainCreditSourceList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMainCreditSourceToVillageAgriInputDemandProfile(Long id, List<MainCreditSourceDTO> mainCreditSourceList){
        serviceExt.addMainCreditSourceToVillageAgriInputDemandProfile(id,mainCreditSourceList);
    }
/**
     * Adds a list of existingStorageIssueList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param existingStorageIssueList  to be added
     * @since 1.0.0
     */
    @Override
    public void addExistingStorageIssueToVillageAgriInputDemandProfile(Long id, List<ExistingStorageIssueDTO> existingStorageIssueList){
        serviceExt.addExistingStorageIssueToVillageAgriInputDemandProfile(id,existingStorageIssueList);
    }
/**
     * Adds a list of storageNeededForCropList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param storageNeededForCropList  to be added
     * @since 1.0.0
     */
    @Override
    public void addStorageNeededForCropToVillageAgriInputDemandProfile(Long id, List<StorageNeededForCropDTO> storageNeededForCropList){
        serviceExt.addStorageNeededForCropToVillageAgriInputDemandProfile(id,storageNeededForCropList);
    }
/**
     * Adds a list of whereFarmersBuyInputList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param whereFarmersBuyInputList  to be added
     * @since 1.0.0
     */
    @Override
    public void addWhereFarmersBuyInputToVillageAgriInputDemandProfile(Long id, List<WhereFarmersBuyInputDTO> whereFarmersBuyInputList){
        serviceExt.addWhereFarmersBuyInputToVillageAgriInputDemandProfile(id,whereFarmersBuyInputList);
    }
/**
     * Adds a list of rawMaterialsNeededForIndustryList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param rawMaterialsNeededForIndustryList  to be added
     * @since 1.0.0
     */
    @Override
    public void addRawMaterialsNeededForIndustryToVillageAgriInputDemandProfile(Long id, List<RawMaterialsNeededForIndustryDTO> rawMaterialsNeededForIndustryList){
        serviceExt.addRawMaterialsNeededForIndustryToVillageAgriInputDemandProfile(id,rawMaterialsNeededForIndustryList);
    }
/**
     * Adds a list of pesticidesInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param pesticidesInDemandList  to be added
     * @since 1.0.0
     */
    @Override
    public void addPesticidesInDemandToVillageAgriInputDemandProfile(Long id, List<PesticidesInDemandDTO> pesticidesInDemandList){
        serviceExt.addPesticidesInDemandToVillageAgriInputDemandProfile(id,pesticidesInDemandList);
    }
/**
     * Adds a list of currentStorageMethodList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param currentStorageMethodList  to be added
     * @since 1.0.0
     */
    @Override
    public void addCurrentStorageMethodToVillageAgriInputDemandProfile(Long id, List<CurrentStorageMethodDTO> currentStorageMethodList){
        serviceExt.addCurrentStorageMethodToVillageAgriInputDemandProfile(id,currentStorageMethodList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageAgriInputDemandProfileDTO postHookSave(VillageAgriInputDemandProfileDTO dto) {
        return dto;
    }

    protected VillageAgriInputDemandProfileDTO preHookSave(VillageAgriInputDemandProfileDTO dto) {
        return dto;
    }

    protected VillageAgriInputDemandProfileDTO postHookUpdate(VillageAgriInputDemandProfileDTO dto) {
        return dto;
    }

    protected VillageAgriInputDemandProfileDTO preHookUpdate(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfileDTO) {
        return VillageAgriInputDemandProfileDTO;
    }

    protected VillageAgriInputDemandProfileDTO postHookDelete(VillageAgriInputDemandProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageAgriInputDemandProfileDTO postHookGetById(VillageAgriInputDemandProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageAgriInputDemandProfileDTO> postHookGetAll(PageDTO<VillageAgriInputDemandProfileDTO> result) {
        return result;
    }
}
