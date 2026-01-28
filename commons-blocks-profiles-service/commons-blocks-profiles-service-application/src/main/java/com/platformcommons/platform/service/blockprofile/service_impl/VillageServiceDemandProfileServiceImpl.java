package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageServiceDemandProfileService;


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

public class VillageServiceDemandProfileServiceImpl implements VillageServiceDemandProfileService {

    private final VillageServiceDemandProfileDTOAssembler assembler;
    private final VillageServiceDemandProfileRepository repository;


    private final WherePeopleGoForRepairsDTOAssembler assemblerWherePeopleGoForRepairs;
    private final WherePeopleGoForRepairsRepository repositoryWherePeopleGoForRepairs;


    private final WherePeopleGoForCommonIllnessDTOAssembler assemblerWherePeopleGoForCommonIllness;
    private final WherePeopleGoForCommonIllnessRepository repositoryWherePeopleGoForCommonIllness;


    private final CommonRepairNeedDTOAssembler assemblerCommonRepairNeed;
    private final CommonRepairNeedRepository repositoryCommonRepairNeed;


    public VillageServiceDemandProfileServiceImpl(
        WherePeopleGoForRepairsDTOAssembler assemblerWherePeopleGoForRepairs,  WherePeopleGoForRepairsRepository repositoryWherePeopleGoForRepairs,
        WherePeopleGoForCommonIllnessDTOAssembler assemblerWherePeopleGoForCommonIllness,  WherePeopleGoForCommonIllnessRepository repositoryWherePeopleGoForCommonIllness,
        CommonRepairNeedDTOAssembler assemblerCommonRepairNeed,  CommonRepairNeedRepository repositoryCommonRepairNeed,
        VillageServiceDemandProfileRepository repository, VillageServiceDemandProfileDTOAssembler assembler) {
        this.assemblerWherePeopleGoForRepairs = assemblerWherePeopleGoForRepairs;
        this.repositoryWherePeopleGoForRepairs = repositoryWherePeopleGoForRepairs;
        this.assemblerWherePeopleGoForCommonIllness = assemblerWherePeopleGoForCommonIllness;
        this.repositoryWherePeopleGoForCommonIllness = repositoryWherePeopleGoForCommonIllness;
        this.assemblerCommonRepairNeed = assemblerCommonRepairNeed;
        this.repositoryCommonRepairNeed = repositoryCommonRepairNeed;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageServiceDemandProfile.
     *
     * @param VillageServiceDemandProfile the VillageServiceDemandProfile DTO to save
     * @return the saved VillageServiceDemandProfile DTO
     */
    @Transactional
    @Override
    public VillageServiceDemandProfileDTO save(VillageServiceDemandProfileDTO VillageServiceDemandProfile) {
        log.debug("Entry - save(VillageServiceDemandProfile={})", VillageServiceDemandProfile);
        VillageServiceDemandProfile = preHookSave(VillageServiceDemandProfile);
        VillageServiceDemandProfile entity = assembler.fromDTO(VillageServiceDemandProfile);
        VillageServiceDemandProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageServiceDemandProfile.
     *
     * @param VillageServiceDemandProfile the VillageServiceDemandProfile DTO to update
     * @return the updated VillageServiceDemandProfile DTO
     * @throws NotFoundException if the VillageServiceDemandProfile is not found
     */
    @Transactional
    @Override
    public VillageServiceDemandProfileDTO update(VillageServiceDemandProfileDTO VillageServiceDemandProfile) {
        log.debug("Entry - update(VillageServiceDemandProfile={})", VillageServiceDemandProfile);
        VillageServiceDemandProfile = preHookUpdate(VillageServiceDemandProfile);
        VillageServiceDemandProfile saved = repository.findById(VillageServiceDemandProfile.getId()).orElseThrow(() -> new NotFoundException("VillageServiceDemandProfile not found"));
        saved.update(assembler.fromDTO(VillageServiceDemandProfile));
        saved = repository.save(saved);
        VillageServiceDemandProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageServiceDemandProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageServiceDemandProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageServiceDemandProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageServiceDemandProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageServiceDemandProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageServiceDemandProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageServiceDemandProfile by ID.
     *
     * @param id     the ID of the VillageServiceDemandProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageServiceDemandProfile DTO
     * @throws NotFoundException if the VillageServiceDemandProfile is not found
     */
    @Transactional
    @Override
    public VillageServiceDemandProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageServiceDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageServiceDemandProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageServiceDemandProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageServiceDemandProfile by ID.
     *
     * @param id the ID of the VillageServiceDemandProfile to retrieve
     * @return the VillageServiceDemandProfile DTO
     * @throws NotFoundException if the VillageServiceDemandProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageServiceDemandProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageServiceDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageServiceDemandProfile not found"));
        VillageServiceDemandProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageServiceDemandProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageServiceDemandProfile> savedData = repository.findAllById(ids);
        Set<VillageServiceDemandProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of wherePeopleGoForRepairsList to a VillageServiceDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
      * @param wherePeopleGoForRepairsList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addWherePeopleGoForRepairsToVillageServiceDemandProfile(Long id, List<WherePeopleGoForRepairsDTO> wherePeopleGoForRepairsList){
         VillageServiceDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageServiceDemandProfile not found"));
         if(wherePeopleGoForRepairsList != null && !wherePeopleGoForRepairsList.isEmpty()) {

             Set<WherePeopleGoForRepairs> toBeSavedList = wherePeopleGoForRepairsList.stream().map(it-> assemblerWherePeopleGoForRepairs.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageServiceDemandProfile(saved));
             repositoryWherePeopleGoForRepairs.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of wherePeopleGoForCommonIllnessList to a VillageServiceDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
      * @param wherePeopleGoForCommonIllnessList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addWherePeopleGoForCommonIllnessToVillageServiceDemandProfile(Long id, List<WherePeopleGoForCommonIllnessDTO> wherePeopleGoForCommonIllnessList){
         VillageServiceDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageServiceDemandProfile not found"));
         if(wherePeopleGoForCommonIllnessList != null && !wherePeopleGoForCommonIllnessList.isEmpty()) {

             Set<WherePeopleGoForCommonIllness> toBeSavedList = wherePeopleGoForCommonIllnessList.stream().map(it-> assemblerWherePeopleGoForCommonIllness.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageServiceDemandProfile(saved));
             repositoryWherePeopleGoForCommonIllness.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of commonRepairNeedList to a VillageServiceDemandProfile identified by their ID.
      *
      * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
      * @param commonRepairNeedList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addCommonRepairNeedToVillageServiceDemandProfile(Long id, List<CommonRepairNeedDTO> commonRepairNeedList){
         VillageServiceDemandProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageServiceDemandProfile not found"));
         if(commonRepairNeedList != null && !commonRepairNeedList.isEmpty()) {

             Set<CommonRepairNeed> toBeSavedList = commonRepairNeedList.stream().map(it-> assemblerCommonRepairNeed.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageServiceDemandProfile(saved));
             repositoryCommonRepairNeed.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageServiceDemandProfileDTO postHookSave(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected VillageServiceDemandProfileDTO preHookSave(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected VillageServiceDemandProfileDTO postHookUpdate(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected VillageServiceDemandProfileDTO preHookUpdate(VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO) {
        return VillageServiceDemandProfileDTO;
    }

    protected VillageServiceDemandProfileDTO postHookDelete(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageServiceDemandProfileDTO postHookGetById(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageServiceDemandProfileDTO> postHookGetAll(PageDTO<VillageServiceDemandProfileDTO> result) {
        return result;
    }




}
