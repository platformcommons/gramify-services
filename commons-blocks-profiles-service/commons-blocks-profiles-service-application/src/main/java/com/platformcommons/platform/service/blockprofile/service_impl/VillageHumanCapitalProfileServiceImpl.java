package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageHumanCapitalProfileService;


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

public class VillageHumanCapitalProfileServiceImpl implements VillageHumanCapitalProfileService {

    private final VillageHumanCapitalProfileDTOAssembler assembler;
    private final VillageHumanCapitalProfileRepository repository;


    private final VillageYouthAspirationsDTOAssembler assemblerVillageYouthAspirations;
    private final VillageYouthAspirationsRepository repositoryVillageYouthAspirations;


    private final VillageSkillShortageTypeDTOAssembler assemblerVillageSkillShortageType;
    private final VillageSkillShortageTypeRepository repositoryVillageSkillShortageType;


    public VillageHumanCapitalProfileServiceImpl(
        VillageYouthAspirationsDTOAssembler assemblerVillageYouthAspirations,  VillageYouthAspirationsRepository repositoryVillageYouthAspirations,
        VillageSkillShortageTypeDTOAssembler assemblerVillageSkillShortageType,  VillageSkillShortageTypeRepository repositoryVillageSkillShortageType,
        VillageHumanCapitalProfileRepository repository, VillageHumanCapitalProfileDTOAssembler assembler) {
        this.assemblerVillageYouthAspirations = assemblerVillageYouthAspirations;
        this.repositoryVillageYouthAspirations = repositoryVillageYouthAspirations;
        this.assemblerVillageSkillShortageType = assemblerVillageSkillShortageType;
        this.repositoryVillageSkillShortageType = repositoryVillageSkillShortageType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageHumanCapitalProfile.
     *
     * @param VillageHumanCapitalProfile the VillageHumanCapitalProfile DTO to save
     * @return the saved VillageHumanCapitalProfile DTO
     */
    @Transactional
    @Override
    public VillageHumanCapitalProfileDTO save(VillageHumanCapitalProfileDTO VillageHumanCapitalProfile) {
        log.debug("Entry - save(VillageHumanCapitalProfile={})", VillageHumanCapitalProfile);
        VillageHumanCapitalProfile = preHookSave(VillageHumanCapitalProfile);
        VillageHumanCapitalProfile entity = assembler.fromDTO(VillageHumanCapitalProfile);
        VillageHumanCapitalProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageHumanCapitalProfile.
     *
     * @param VillageHumanCapitalProfile the VillageHumanCapitalProfile DTO to update
     * @return the updated VillageHumanCapitalProfile DTO
     * @throws NotFoundException if the VillageHumanCapitalProfile is not found
     */
    @Transactional
    @Override
    public VillageHumanCapitalProfileDTO update(VillageHumanCapitalProfileDTO VillageHumanCapitalProfile) {
        log.debug("Entry - update(VillageHumanCapitalProfile={})", VillageHumanCapitalProfile);
        VillageHumanCapitalProfile = preHookUpdate(VillageHumanCapitalProfile);
        VillageHumanCapitalProfile saved = repository.findById(VillageHumanCapitalProfile.getId()).orElseThrow(() -> new NotFoundException("VillageHumanCapitalProfile not found"));
        saved.update(assembler.fromDTO(VillageHumanCapitalProfile));
        saved = repository.save(saved);
        VillageHumanCapitalProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageHumanCapitalProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageHumanCapitalProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageHumanCapitalProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageHumanCapitalProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageHumanCapitalProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageHumanCapitalProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageHumanCapitalProfile by ID.
     *
     * @param id     the ID of the VillageHumanCapitalProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageHumanCapitalProfile DTO
     * @throws NotFoundException if the VillageHumanCapitalProfile is not found
     */
    @Transactional
    @Override
    public VillageHumanCapitalProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageHumanCapitalProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanCapitalProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageHumanCapitalProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageHumanCapitalProfile by ID.
     *
     * @param id the ID of the VillageHumanCapitalProfile to retrieve
     * @return the VillageHumanCapitalProfile DTO
     * @throws NotFoundException if the VillageHumanCapitalProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageHumanCapitalProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageHumanCapitalProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanCapitalProfile not found"));
        VillageHumanCapitalProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageHumanCapitalProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageHumanCapitalProfile> savedData = repository.findAllById(ids);
        Set<VillageHumanCapitalProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of villageYouthAspirationsList to a VillageHumanCapitalProfile identified by their ID.
      *
      * @param id               The ID of the VillageHumanCapitalProfile to add hobbies to
      * @param villageYouthAspirationsList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageYouthAspirationsToVillageHumanCapitalProfile(Long id, List<VillageYouthAspirationsDTO> villageYouthAspirationsList){
         VillageHumanCapitalProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanCapitalProfile not found"));
         if(villageYouthAspirationsList != null && !villageYouthAspirationsList.isEmpty()) {

             Set<VillageYouthAspirations> toBeSavedList = villageYouthAspirationsList.stream().map(it-> assemblerVillageYouthAspirations.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHumanCapitalProfile(saved));
             repositoryVillageYouthAspirations.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of villageSkillShortageTypeList to a VillageHumanCapitalProfile identified by their ID.
      *
      * @param id               The ID of the VillageHumanCapitalProfile to add hobbies to
      * @param villageSkillShortageTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageSkillShortageTypeToVillageHumanCapitalProfile(Long id, List<VillageSkillShortageTypeDTO> villageSkillShortageTypeList){
         VillageHumanCapitalProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHumanCapitalProfile not found"));
         if(villageSkillShortageTypeList != null && !villageSkillShortageTypeList.isEmpty()) {

             Set<VillageSkillShortageType> toBeSavedList = villageSkillShortageTypeList.stream().map(it-> assemblerVillageSkillShortageType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHumanCapitalProfile(saved));
             repositoryVillageSkillShortageType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageHumanCapitalProfileDTO postHookSave(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected VillageHumanCapitalProfileDTO preHookSave(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected VillageHumanCapitalProfileDTO postHookUpdate(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected VillageHumanCapitalProfileDTO preHookUpdate(VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO) {
        return VillageHumanCapitalProfileDTO;
    }

    protected VillageHumanCapitalProfileDTO postHookDelete(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHumanCapitalProfileDTO postHookGetById(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHumanCapitalProfileDTO> postHookGetAll(PageDTO<VillageHumanCapitalProfileDTO> result) {
        return result;
    }




}
