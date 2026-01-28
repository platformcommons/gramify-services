package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdHumanResourceProfileService;


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

public class HouseholdHumanResourceProfileServiceImpl implements HouseholdHumanResourceProfileService {

    private final HouseholdHumanResourceProfileDTOAssembler assembler;
    private final HouseholdHumanResourceProfileRepository repository;


    private final HHEmploymentTypeDTOAssembler assemblerHHEmploymentType;
    private final HHEmploymentTypeRepository repositoryHHEmploymentType;


    private final HHEnterpriseTypeDTOAssembler assemblerHHEnterpriseType;
    private final HHEnterpriseTypeRepository repositoryHHEnterpriseType;


    private final HHArtisanTypeDTOAssembler assemblerHHArtisanType;
    private final HHArtisanTypeRepository repositoryHHArtisanType;


    public HouseholdHumanResourceProfileServiceImpl(
        HHEmploymentTypeDTOAssembler assemblerHHEmploymentType,  HHEmploymentTypeRepository repositoryHHEmploymentType,
        HHEnterpriseTypeDTOAssembler assemblerHHEnterpriseType,  HHEnterpriseTypeRepository repositoryHHEnterpriseType,
        HHArtisanTypeDTOAssembler assemblerHHArtisanType,  HHArtisanTypeRepository repositoryHHArtisanType,
        HouseholdHumanResourceProfileRepository repository, HouseholdHumanResourceProfileDTOAssembler assembler) {
        this.assemblerHHEmploymentType = assemblerHHEmploymentType;
        this.repositoryHHEmploymentType = repositoryHHEmploymentType;
        this.assemblerHHEnterpriseType = assemblerHHEnterpriseType;
        this.repositoryHHEnterpriseType = repositoryHHEnterpriseType;
        this.assemblerHHArtisanType = assemblerHHArtisanType;
        this.repositoryHHArtisanType = repositoryHHArtisanType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HouseholdHumanResourceProfile.
     *
     * @param HouseholdHumanResourceProfile the HouseholdHumanResourceProfile DTO to save
     * @return the saved HouseholdHumanResourceProfile DTO
     */
    @Transactional
    @Override
    public HouseholdHumanResourceProfileDTO save(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfile) {
        log.debug("Entry - save(HouseholdHumanResourceProfile={})", HouseholdHumanResourceProfile);
        HouseholdHumanResourceProfile = preHookSave(HouseholdHumanResourceProfile);
        HouseholdHumanResourceProfile entity = assembler.fromDTO(HouseholdHumanResourceProfile);
        HouseholdHumanResourceProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HouseholdHumanResourceProfile.
     *
     * @param HouseholdHumanResourceProfile the HouseholdHumanResourceProfile DTO to update
     * @return the updated HouseholdHumanResourceProfile DTO
     * @throws NotFoundException if the HouseholdHumanResourceProfile is not found
     */
    @Transactional
    @Override
    public HouseholdHumanResourceProfileDTO update(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfile) {
        log.debug("Entry - update(HouseholdHumanResourceProfile={})", HouseholdHumanResourceProfile);
        HouseholdHumanResourceProfile = preHookUpdate(HouseholdHumanResourceProfile);
        HouseholdHumanResourceProfile saved = repository.findById(HouseholdHumanResourceProfile.getId()).orElseThrow(() -> new NotFoundException("HouseholdHumanResourceProfile not found"));
        saved.update(assembler.fromDTO(HouseholdHumanResourceProfile));
        saved = repository.save(saved);
        HouseholdHumanResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HouseholdHumanResourceProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HouseholdHumanResourceProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdHumanResourceProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HouseholdHumanResourceProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdHumanResourceProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdHumanResourceProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HouseholdHumanResourceProfile by ID.
     *
     * @param id     the ID of the HouseholdHumanResourceProfile to delete
     * @param reason the reason for deletion
     * @return the deleted HouseholdHumanResourceProfile DTO
     * @throws NotFoundException if the HouseholdHumanResourceProfile is not found
     */
    @Transactional
    @Override
    public HouseholdHumanResourceProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HouseholdHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdHumanResourceProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdHumanResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HouseholdHumanResourceProfile by ID.
     *
     * @param id the ID of the HouseholdHumanResourceProfile to retrieve
     * @return the HouseholdHumanResourceProfile DTO
     * @throws NotFoundException if the HouseholdHumanResourceProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdHumanResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdHumanResourceProfile not found"));
        HouseholdHumanResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdHumanResourceProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HouseholdHumanResourceProfile> savedData = repository.findAllById(ids);
        Set<HouseholdHumanResourceProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHEmploymentTypeList to a HouseholdHumanResourceProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
      * @param hHEmploymentTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHEmploymentTypeToHouseholdHumanResourceProfile(Long id, List<HHEmploymentTypeDTO> hHEmploymentTypeList){
         HouseholdHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdHumanResourceProfile not found"));
         if(hHEmploymentTypeList != null && !hHEmploymentTypeList.isEmpty()) {

             Set<HHEmploymentType> toBeSavedList = hHEmploymentTypeList.stream().map(it-> assemblerHHEmploymentType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdHumanResourceProfile(saved));
             repositoryHHEmploymentType.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of hHEnterpriseTypeList to a HouseholdHumanResourceProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
      * @param hHEnterpriseTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHEnterpriseTypeToHouseholdHumanResourceProfile(Long id, List<HHEnterpriseTypeDTO> hHEnterpriseTypeList){
         HouseholdHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdHumanResourceProfile not found"));
         if(hHEnterpriseTypeList != null && !hHEnterpriseTypeList.isEmpty()) {

             Set<HHEnterpriseType> toBeSavedList = hHEnterpriseTypeList.stream().map(it-> assemblerHHEnterpriseType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdHumanResourceProfile(saved));
             repositoryHHEnterpriseType.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of hHArtisanTypeList to a HouseholdHumanResourceProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
      * @param hHArtisanTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHArtisanTypeToHouseholdHumanResourceProfile(Long id, List<HHArtisanTypeDTO> hHArtisanTypeList){
         HouseholdHumanResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdHumanResourceProfile not found"));
         if(hHArtisanTypeList != null && !hHArtisanTypeList.isEmpty()) {

             Set<HHArtisanType> toBeSavedList = hHArtisanTypeList.stream().map(it-> assemblerHHArtisanType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdHumanResourceProfile(saved));
             repositoryHHArtisanType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdHumanResourceProfileDTO postHookSave(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected HouseholdHumanResourceProfileDTO preHookSave(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected HouseholdHumanResourceProfileDTO postHookUpdate(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected HouseholdHumanResourceProfileDTO preHookUpdate(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO) {
        return HouseholdHumanResourceProfileDTO;
    }

    protected HouseholdHumanResourceProfileDTO postHookDelete(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdHumanResourceProfileDTO postHookGetById(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdHumanResourceProfileDTO> postHookGetAll(PageDTO<HouseholdHumanResourceProfileDTO> result) {
        return result;
    }




}
