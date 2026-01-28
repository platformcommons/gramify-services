package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageHumanResourceProfileService;


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

public class VillageHumanResourceProfileServiceImpl implements VillageHumanResourceProfileService {

    private final VillageHumanResourceProfileDTOAssembler assembler;
    private final VillageHumanResourceProfileRepository repository;


    private final HHSkilledWorkerTypeDTOAssembler assemblerHHSkilledWorkerType;
    private final HHSkilledWorkerTypeRepository repositoryHHSkilledWorkerType;


    private final EmergingEnterpriseTypeDTOAssembler assemblerEmergingEnterpriseType;
    private final EmergingEnterpriseTypeRepository repositoryEmergingEnterpriseType;


    private final MainSkilledTradesPresentDTOAssembler assemblerMainSkilledTradesPresent;
    private final MainSkilledTradesPresentRepository repositoryMainSkilledTradesPresent;


    private final ArtisanTypeDTOAssembler assemblerArtisanType;
    private final ArtisanTypeRepository repositoryArtisanType;


    public VillageHumanResourceProfileServiceImpl(
        HHSkilledWorkerTypeDTOAssembler assemblerHHSkilledWorkerType,  HHSkilledWorkerTypeRepository repositoryHHSkilledWorkerType,
        EmergingEnterpriseTypeDTOAssembler assemblerEmergingEnterpriseType,  EmergingEnterpriseTypeRepository repositoryEmergingEnterpriseType,
        MainSkilledTradesPresentDTOAssembler assemblerMainSkilledTradesPresent,  MainSkilledTradesPresentRepository repositoryMainSkilledTradesPresent,
        ArtisanTypeDTOAssembler assemblerArtisanType,  ArtisanTypeRepository repositoryArtisanType,
        VillageHumanResourceProfileRepository repository, VillageHumanResourceProfileDTOAssembler assembler) {
        this.assemblerHHSkilledWorkerType = assemblerHHSkilledWorkerType;
        this.repositoryHHSkilledWorkerType = repositoryHHSkilledWorkerType;
        this.assemblerEmergingEnterpriseType = assemblerEmergingEnterpriseType;
        this.repositoryEmergingEnterpriseType = repositoryEmergingEnterpriseType;
        this.assemblerMainSkilledTradesPresent = assemblerMainSkilledTradesPresent;
        this.repositoryMainSkilledTradesPresent = repositoryMainSkilledTradesPresent;
        this.assemblerArtisanType = assemblerArtisanType;
        this.repositoryArtisanType = repositoryArtisanType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageHumanResourceProfile.
     *
     * @param VillageHumanResourceProfile the VillageHumanResourceProfile DTO to save
     * @return the saved VillageHumanResourceProfile DTO
     */
    @Transactional
    @Override
    public VillageHumanResourceProfileDTO save(VillageHumanResourceProfileDTO VillageHumanResourceProfile) {
        log.debug("Entry - save(VillageHumanResourceProfile={})", VillageHumanResourceProfile);
        VillageHumanResourceProfile = preHookSave(VillageHumanResourceProfile);
        VillageHumanResourceProfile entity = assembler.fromDTO(VillageHumanResourceProfile);
        VillageHumanResourceProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageHumanResourceProfile.
     *
     * @param VillageHumanResourceProfile the VillageHumanResourceProfile DTO to update
     * @return the updated VillageHumanResourceProfile DTO
     * @throws NotFoundException if the VillageHumanResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageHumanResourceProfileDTO update(VillageHumanResourceProfileDTO VillageHumanResourceProfile) {
        log.debug("Entry - update(VillageHumanResourceProfile={})", VillageHumanResourceProfile);
        VillageHumanResourceProfile = preHookUpdate(VillageHumanResourceProfile);
        VillageHumanResourceProfile saved = repository.findById(VillageHumanResourceProfile.getId()).orElseThrow(() -> new NotFoundException("VillageHumanResourceProfile not found"));
        saved.update(assembler.fromDTO(VillageHumanResourceProfile));
        saved = repository.save(saved);
        VillageHumanResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageHumanResourceProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageHumanResourceProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageHumanResourceProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageHumanResourceProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageHumanResourceProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageHumanResourceProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageHumanResourceProfile by ID.
     *
     * @param id     the ID of the VillageHumanResourceProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageHumanResourceProfile DTO
     * @throws NotFoundException if the VillageHumanResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageHumanResourceProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanResourceProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageHumanResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageHumanResourceProfile by ID.
     *
     * @param id the ID of the VillageHumanResourceProfile to retrieve
     * @return the VillageHumanResourceProfile DTO
     * @throws NotFoundException if the VillageHumanResourceProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageHumanResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanResourceProfile not found"));
        VillageHumanResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageHumanResourceProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageHumanResourceProfile> savedData = repository.findAllById(ids);
        Set<VillageHumanResourceProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHSkilledWorkerTypeList to a VillageHumanResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
      * @param hHSkilledWorkerTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHSkilledWorkerTypeToVillageHumanResourceProfile(Long id, List<HHSkilledWorkerTypeDTO> hHSkilledWorkerTypeList){
         VillageHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanResourceProfile not found"));
         if(hHSkilledWorkerTypeList != null && !hHSkilledWorkerTypeList.isEmpty()) {

             Set<HHSkilledWorkerType> toBeSavedList = hHSkilledWorkerTypeList.stream().map(it-> assemblerHHSkilledWorkerType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHumanResourceProfile(saved));
             repositoryHHSkilledWorkerType.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of emergingEnterpriseTypeList to a VillageHumanResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
      * @param emergingEnterpriseTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addEmergingEnterpriseTypeToVillageHumanResourceProfile(Long id, List<EmergingEnterpriseTypeDTO> emergingEnterpriseTypeList){
         VillageHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanResourceProfile not found"));
         if(emergingEnterpriseTypeList != null && !emergingEnterpriseTypeList.isEmpty()) {

             Set<EmergingEnterpriseType> toBeSavedList = emergingEnterpriseTypeList.stream().map(it-> assemblerEmergingEnterpriseType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHumanResourceProfile(saved));
             repositoryEmergingEnterpriseType.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of mainSkilledTradesPresentList to a VillageHumanResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
      * @param mainSkilledTradesPresentList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addMainSkilledTradesPresentToVillageHumanResourceProfile(Long id, List<MainSkilledTradesPresentDTO> mainSkilledTradesPresentList){
         VillageHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanResourceProfile not found"));
         if(mainSkilledTradesPresentList != null && !mainSkilledTradesPresentList.isEmpty()) {

             Set<MainSkilledTradesPresent> toBeSavedList = mainSkilledTradesPresentList.stream().map(it-> assemblerMainSkilledTradesPresent.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHumanResourceProfile(saved));
             repositoryMainSkilledTradesPresent.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of artisanTypeList to a VillageHumanResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
      * @param artisanTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addArtisanTypeToVillageHumanResourceProfile(Long id, List<ArtisanTypeDTO> artisanTypeList){
         VillageHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanResourceProfile not found"));
         if(artisanTypeList != null && !artisanTypeList.isEmpty()) {

             Set<ArtisanType> toBeSavedList = artisanTypeList.stream().map(it-> assemblerArtisanType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHumanResourceProfile(saved));
             repositoryArtisanType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageHumanResourceProfileDTO postHookSave(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected VillageHumanResourceProfileDTO preHookSave(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected VillageHumanResourceProfileDTO postHookUpdate(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected VillageHumanResourceProfileDTO preHookUpdate(VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO) {
        return VillageHumanResourceProfileDTO;
    }

    protected VillageHumanResourceProfileDTO postHookDelete(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHumanResourceProfileDTO postHookGetById(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHumanResourceProfileDTO> postHookGetAll(PageDTO<VillageHumanResourceProfileDTO> result) {
        return result;
    }




}
