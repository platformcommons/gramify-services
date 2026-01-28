package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageAgriInputDemandProfileService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j

public class VillageAgriInputDemandProfileServiceImpl implements VillageAgriInputDemandProfileService {

    private final VillageAgriInputDemandProfileDTOAssembler assembler;
    private final VillageAgriInputDemandProfileRepository repository;


    private final MachinesInDemandDTOAssembler assemblerMachinesInDemand;
    private final MachinesInDemandRepository repositoryMachinesInDemand;


    private final PurposeOfCreditDTOAssembler assemblerPurposeOfCredit;
    private final PurposeOfCreditRepository repositoryPurposeOfCredit;


    private final FertilizersInDemandDTOAssembler assemblerFertilizersInDemand;
    private final FertilizersInDemandRepository repositoryFertilizersInDemand;


    private final SourceOfRawMaterialDTOAssembler assemblerSourceOfRawMaterial;
    private final SourceOfRawMaterialRepository repositorySourceOfRawMaterial;


    private final SeedsInDemandDTOAssembler assemblerSeedsInDemand;
    private final SeedsInDemandRepository repositorySeedsInDemand;


    private final MainCreditSourceDTOAssembler assemblerMainCreditSource;
    private final MainCreditSourceRepository repositoryMainCreditSource;


    private final ExistingStorageIssueDTOAssembler assemblerExistingStorageIssue;
    private final ExistingStorageIssueRepository repositoryExistingStorageIssue;


    private final StorageNeededForCropDTOAssembler assemblerStorageNeededForCrop;
    private final StorageNeededForCropRepository repositoryStorageNeededForCrop;


    private final WhereFarmersBuyInputDTOAssembler assemblerWhereFarmersBuyInput;
    private final WhereFarmersBuyInputRepository repositoryWhereFarmersBuyInput;


    private final RawMaterialsNeededForIndustryDTOAssembler assemblerRawMaterialsNeededForIndustry;
    private final RawMaterialsNeededForIndustryRepository repositoryRawMaterialsNeededForIndustry;


    private final PesticidesInDemandDTOAssembler assemblerPesticidesInDemand;
    private final PesticidesInDemandRepository repositoryPesticidesInDemand;


    private final CurrentStorageMethodDTOAssembler assemblerCurrentStorageMethod;
    private final CurrentStorageMethodRepository repositoryCurrentStorageMethod;


    public VillageAgriInputDemandProfileServiceImpl(
        MachinesInDemandDTOAssembler assemblerMachinesInDemand,  MachinesInDemandRepository repositoryMachinesInDemand,
        PurposeOfCreditDTOAssembler assemblerPurposeOfCredit,  PurposeOfCreditRepository repositoryPurposeOfCredit,
        FertilizersInDemandDTOAssembler assemblerFertilizersInDemand,  FertilizersInDemandRepository repositoryFertilizersInDemand,
        SourceOfRawMaterialDTOAssembler assemblerSourceOfRawMaterial,  SourceOfRawMaterialRepository repositorySourceOfRawMaterial,
        SeedsInDemandDTOAssembler assemblerSeedsInDemand,  SeedsInDemandRepository repositorySeedsInDemand,
        MainCreditSourceDTOAssembler assemblerMainCreditSource,  MainCreditSourceRepository repositoryMainCreditSource,
        ExistingStorageIssueDTOAssembler assemblerExistingStorageIssue,  ExistingStorageIssueRepository repositoryExistingStorageIssue,
        StorageNeededForCropDTOAssembler assemblerStorageNeededForCrop,  StorageNeededForCropRepository repositoryStorageNeededForCrop,
        WhereFarmersBuyInputDTOAssembler assemblerWhereFarmersBuyInput,  WhereFarmersBuyInputRepository repositoryWhereFarmersBuyInput,
        RawMaterialsNeededForIndustryDTOAssembler assemblerRawMaterialsNeededForIndustry,  RawMaterialsNeededForIndustryRepository repositoryRawMaterialsNeededForIndustry,
        PesticidesInDemandDTOAssembler assemblerPesticidesInDemand,  PesticidesInDemandRepository repositoryPesticidesInDemand,
        CurrentStorageMethodDTOAssembler assemblerCurrentStorageMethod,  CurrentStorageMethodRepository repositoryCurrentStorageMethod,
        VillageAgriInputDemandProfileRepository repository, VillageAgriInputDemandProfileDTOAssembler assembler) {
        this.assemblerMachinesInDemand = assemblerMachinesInDemand;
        this.repositoryMachinesInDemand = repositoryMachinesInDemand;
        this.assemblerPurposeOfCredit = assemblerPurposeOfCredit;
        this.repositoryPurposeOfCredit = repositoryPurposeOfCredit;
        this.assemblerFertilizersInDemand = assemblerFertilizersInDemand;
        this.repositoryFertilizersInDemand = repositoryFertilizersInDemand;
        this.assemblerSourceOfRawMaterial = assemblerSourceOfRawMaterial;
        this.repositorySourceOfRawMaterial = repositorySourceOfRawMaterial;
        this.assemblerSeedsInDemand = assemblerSeedsInDemand;
        this.repositorySeedsInDemand = repositorySeedsInDemand;
        this.assemblerMainCreditSource = assemblerMainCreditSource;
        this.repositoryMainCreditSource = repositoryMainCreditSource;
        this.assemblerExistingStorageIssue = assemblerExistingStorageIssue;
        this.repositoryExistingStorageIssue = repositoryExistingStorageIssue;
        this.assemblerStorageNeededForCrop = assemblerStorageNeededForCrop;
        this.repositoryStorageNeededForCrop = repositoryStorageNeededForCrop;
        this.assemblerWhereFarmersBuyInput = assemblerWhereFarmersBuyInput;
        this.repositoryWhereFarmersBuyInput = repositoryWhereFarmersBuyInput;
        this.assemblerRawMaterialsNeededForIndustry = assemblerRawMaterialsNeededForIndustry;
        this.repositoryRawMaterialsNeededForIndustry = repositoryRawMaterialsNeededForIndustry;
        this.assemblerPesticidesInDemand = assemblerPesticidesInDemand;
        this.repositoryPesticidesInDemand = repositoryPesticidesInDemand;
        this.assemblerCurrentStorageMethod = assemblerCurrentStorageMethod;
        this.repositoryCurrentStorageMethod = repositoryCurrentStorageMethod;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageAgriInputDemandProfile.
     *
     * @param VillageAgriInputDemandProfile the VillageAgriInputDemandProfile DTO to save
     * @return the saved VillageAgriInputDemandProfile DTO
     */
    @Transactional
    @Override
    public VillageAgriInputDemandProfileDTO save(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfile) {
        log.debug("Entry - save(VillageAgriInputDemandProfile={})", VillageAgriInputDemandProfile);
        VillageAgriInputDemandProfile = preHookSave(VillageAgriInputDemandProfile);
        VillageAgriInputDemandProfile entity = assembler.fromDTO(VillageAgriInputDemandProfile);
        VillageAgriInputDemandProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageAgriInputDemandProfile.
     *
     * @param VillageAgriInputDemandProfile the VillageAgriInputDemandProfile DTO to update
     * @return the updated VillageAgriInputDemandProfile DTO
     * @throws NotFoundException if the VillageAgriInputDemandProfile is not found
     */
    @Transactional
    @Override
    public VillageAgriInputDemandProfileDTO update(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfile) {
        log.debug("Entry - update(VillageAgriInputDemandProfile={})", VillageAgriInputDemandProfile);
        VillageAgriInputDemandProfile = preHookUpdate(VillageAgriInputDemandProfile);
        VillageAgriInputDemandProfile saved = repository.findById(VillageAgriInputDemandProfile.getId()).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
        saved.update(assembler.fromDTO(VillageAgriInputDemandProfile));
        saved = repository.save(saved);
        VillageAgriInputDemandProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageAgriInputDemandProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageAgriInputDemandProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageAgriInputDemandProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageAgriInputDemandProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageAgriInputDemandProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageAgriInputDemandProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageAgriInputDemandProfile by ID.
     *
     * @param id     the ID of the VillageAgriInputDemandProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageAgriInputDemandProfile DTO
     * @throws NotFoundException if the VillageAgriInputDemandProfile is not found
     */
    @Transactional
    @Override
    public VillageAgriInputDemandProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageAgriInputDemandProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageAgriInputDemandProfile by ID.
     *
     * @param id the ID of the VillageAgriInputDemandProfile to retrieve
     * @return the VillageAgriInputDemandProfile DTO
     * @throws NotFoundException if the VillageAgriInputDemandProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageAgriInputDemandProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
        VillageAgriInputDemandProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageAgriInputDemandProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageAgriInputDemandProfile> savedData = repository.findAllById(ids);
        Set<VillageAgriInputDemandProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
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
     @Transactional
     @Override
     public void addMachinesInDemandToVillageAgriInputDemandProfile(Long id, List<MachinesInDemandDTO> machinesInDemandList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(machinesInDemandList != null && !machinesInDemandList.isEmpty()) {

             Set<MachinesInDemand> toBeSavedList = machinesInDemandList.stream().map(it-> assemblerMachinesInDemand.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryMachinesInDemand.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of purposeOfCreditList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param purposeOfCreditList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addPurposeOfCreditToVillageAgriInputDemandProfile(Long id, List<PurposeOfCreditDTO> purposeOfCreditList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(purposeOfCreditList != null && !purposeOfCreditList.isEmpty()) {

             Set<PurposeOfCredit> toBeSavedList = purposeOfCreditList.stream().map(it-> assemblerPurposeOfCredit.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryPurposeOfCredit.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of fertilizersInDemandList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param fertilizersInDemandList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addFertilizersInDemandToVillageAgriInputDemandProfile(Long id, List<FertilizersInDemandDTO> fertilizersInDemandList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(fertilizersInDemandList != null && !fertilizersInDemandList.isEmpty()) {

             Set<FertilizersInDemand> toBeSavedList = fertilizersInDemandList.stream().map(it-> assemblerFertilizersInDemand.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryFertilizersInDemand.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of sourceOfRawMaterialList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param sourceOfRawMaterialList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSourceOfRawMaterialToVillageAgriInputDemandProfile(Long id, List<SourceOfRawMaterialDTO> sourceOfRawMaterialList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(sourceOfRawMaterialList != null && !sourceOfRawMaterialList.isEmpty()) {

             Set<SourceOfRawMaterial> toBeSavedList = sourceOfRawMaterialList.stream().map(it-> assemblerSourceOfRawMaterial.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositorySourceOfRawMaterial.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of seedsInDemandList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param seedsInDemandList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSeedsInDemandToVillageAgriInputDemandProfile(Long id, List<SeedsInDemandDTO> seedsInDemandList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(seedsInDemandList != null && !seedsInDemandList.isEmpty()) {

             Set<SeedsInDemand> toBeSavedList = seedsInDemandList.stream().map(it-> assemblerSeedsInDemand.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositorySeedsInDemand.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of mainCreditSourceList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param mainCreditSourceList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addMainCreditSourceToVillageAgriInputDemandProfile(Long id, List<MainCreditSourceDTO> mainCreditSourceList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(mainCreditSourceList != null && !mainCreditSourceList.isEmpty()) {

             Set<MainCreditSource> toBeSavedList = mainCreditSourceList.stream().map(it-> assemblerMainCreditSource.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryMainCreditSource.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of existingStorageIssueList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param existingStorageIssueList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addExistingStorageIssueToVillageAgriInputDemandProfile(Long id, List<ExistingStorageIssueDTO> existingStorageIssueList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(existingStorageIssueList != null && !existingStorageIssueList.isEmpty()) {

             Set<ExistingStorageIssue> toBeSavedList = existingStorageIssueList.stream().map(it-> assemblerExistingStorageIssue.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryExistingStorageIssue.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of storageNeededForCropList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param storageNeededForCropList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addStorageNeededForCropToVillageAgriInputDemandProfile(Long id, List<StorageNeededForCropDTO> storageNeededForCropList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(storageNeededForCropList != null && !storageNeededForCropList.isEmpty()) {

             Set<StorageNeededForCrop> toBeSavedList = storageNeededForCropList.stream().map(it-> assemblerStorageNeededForCrop.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryStorageNeededForCrop.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of whereFarmersBuyInputList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param whereFarmersBuyInputList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addWhereFarmersBuyInputToVillageAgriInputDemandProfile(Long id, List<WhereFarmersBuyInputDTO> whereFarmersBuyInputList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(whereFarmersBuyInputList != null && !whereFarmersBuyInputList.isEmpty()) {

             Set<WhereFarmersBuyInput> toBeSavedList = whereFarmersBuyInputList.stream().map(it-> assemblerWhereFarmersBuyInput.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryWhereFarmersBuyInput.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of rawMaterialsNeededForIndustryList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param rawMaterialsNeededForIndustryList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addRawMaterialsNeededForIndustryToVillageAgriInputDemandProfile(Long id, List<RawMaterialsNeededForIndustryDTO> rawMaterialsNeededForIndustryList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(rawMaterialsNeededForIndustryList != null && !rawMaterialsNeededForIndustryList.isEmpty()) {

             Set<RawMaterialsNeededForIndustry> toBeSavedList = rawMaterialsNeededForIndustryList.stream().map(it-> assemblerRawMaterialsNeededForIndustry.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryRawMaterialsNeededForIndustry.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of pesticidesInDemandList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param pesticidesInDemandList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addPesticidesInDemandToVillageAgriInputDemandProfile(Long id, List<PesticidesInDemandDTO> pesticidesInDemandList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(pesticidesInDemandList != null && !pesticidesInDemandList.isEmpty()) {

             Set<PesticidesInDemand> toBeSavedList = pesticidesInDemandList.stream().map(it-> assemblerPesticidesInDemand.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryPesticidesInDemand.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of currentStorageMethodList to a VillageAgriInputDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
      * @param currentStorageMethodList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addCurrentStorageMethodToVillageAgriInputDemandProfile(Long id, List<CurrentStorageMethodDTO> currentStorageMethodList){
         VillageAgriInputDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageAgriInputDemandProfile not found"));
         if(currentStorageMethodList != null && !currentStorageMethodList.isEmpty()) {

             Set<CurrentStorageMethod> toBeSavedList = currentStorageMethodList.stream().map(it-> assemblerCurrentStorageMethod.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageAgriInputDemandProfile(saved));
             repositoryCurrentStorageMethod.saveAll(toBeSavedList);

         }
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
