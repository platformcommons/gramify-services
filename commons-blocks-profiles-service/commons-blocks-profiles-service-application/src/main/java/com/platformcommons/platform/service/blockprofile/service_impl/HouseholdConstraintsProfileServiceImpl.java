package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdConstraintsProfileService;


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

public class HouseholdConstraintsProfileServiceImpl implements HouseholdConstraintsProfileService {

    private final HouseholdConstraintsProfileDTOAssembler assembler;
    private final HouseholdConstraintsProfileRepository repository;


    private final HHSocialConstraintsDTOAssembler assemblerHHSocialConstraints;
    private final HHSocialConstraintsRepository repositoryHHSocialConstraints;


    private final HHEconomicConstraintsDTOAssembler assemblerHHEconomicConstraints;
    private final HHEconomicConstraintsRepository repositoryHHEconomicConstraints;


    public HouseholdConstraintsProfileServiceImpl(
        HHSocialConstraintsDTOAssembler assemblerHHSocialConstraints,  HHSocialConstraintsRepository repositoryHHSocialConstraints,
        HHEconomicConstraintsDTOAssembler assemblerHHEconomicConstraints,  HHEconomicConstraintsRepository repositoryHHEconomicConstraints,
        HouseholdConstraintsProfileRepository repository, HouseholdConstraintsProfileDTOAssembler assembler) {
        this.assemblerHHSocialConstraints = assemblerHHSocialConstraints;
        this.repositoryHHSocialConstraints = repositoryHHSocialConstraints;
        this.assemblerHHEconomicConstraints = assemblerHHEconomicConstraints;
        this.repositoryHHEconomicConstraints = repositoryHHEconomicConstraints;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HouseholdConstraintsProfile.
     *
     * @param HouseholdConstraintsProfile the HouseholdConstraintsProfile DTO to save
     * @return the saved HouseholdConstraintsProfile DTO
     */
    @Transactional
    @Override
    public HouseholdConstraintsProfileDTO save(HouseholdConstraintsProfileDTO HouseholdConstraintsProfile) {
        log.debug("Entry - save(HouseholdConstraintsProfile={})", HouseholdConstraintsProfile);
        HouseholdConstraintsProfile = preHookSave(HouseholdConstraintsProfile);
        HouseholdConstraintsProfile entity = assembler.fromDTO(HouseholdConstraintsProfile);
        HouseholdConstraintsProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HouseholdConstraintsProfile.
     *
     * @param HouseholdConstraintsProfile the HouseholdConstraintsProfile DTO to update
     * @return the updated HouseholdConstraintsProfile DTO
     * @throws NotFoundException if the HouseholdConstraintsProfile is not found
     */
    @Transactional
    @Override
    public HouseholdConstraintsProfileDTO update(HouseholdConstraintsProfileDTO HouseholdConstraintsProfile) {
        log.debug("Entry - update(HouseholdConstraintsProfile={})", HouseholdConstraintsProfile);
        HouseholdConstraintsProfile = preHookUpdate(HouseholdConstraintsProfile);
        HouseholdConstraintsProfile saved = repository.findById(HouseholdConstraintsProfile.getId()).orElseThrow(() -> new NotFoundException("HouseholdConstraintsProfile not found"));
        saved.update(assembler.fromDTO(HouseholdConstraintsProfile));
        saved = repository.save(saved);
        HouseholdConstraintsProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HouseholdConstraintsProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HouseholdConstraintsProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdConstraintsProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HouseholdConstraintsProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdConstraintsProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdConstraintsProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HouseholdConstraintsProfile by ID.
     *
     * @param id     the ID of the HouseholdConstraintsProfile to delete
     * @param reason the reason for deletion
     * @return the deleted HouseholdConstraintsProfile DTO
     * @throws NotFoundException if the HouseholdConstraintsProfile is not found
     */
    @Transactional
    @Override
    public HouseholdConstraintsProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HouseholdConstraintsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdConstraintsProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdConstraintsProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HouseholdConstraintsProfile by ID.
     *
     * @param id the ID of the HouseholdConstraintsProfile to retrieve
     * @return the HouseholdConstraintsProfile DTO
     * @throws NotFoundException if the HouseholdConstraintsProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdConstraintsProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdConstraintsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdConstraintsProfile not found"));
        HouseholdConstraintsProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdConstraintsProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HouseholdConstraintsProfile> savedData = repository.findAllById(ids);
        Set<HouseholdConstraintsProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHSocialConstraintsList to a HouseholdConstraintsProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdConstraintsProfile to add hobbies to
      * @param hHSocialConstraintsList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHSocialConstraintsToHouseholdConstraintsProfile(Long id, List<HHSocialConstraintsDTO> hHSocialConstraintsList){
         HouseholdConstraintsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdConstraintsProfile not found"));
         if(hHSocialConstraintsList != null && !hHSocialConstraintsList.isEmpty()) {

             Set<HHSocialConstraints> toBeSavedList = hHSocialConstraintsList.stream().map(it-> assemblerHHSocialConstraints.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdConstraintsProfile(saved));
             repositoryHHSocialConstraints.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of hHEconomicConstraintsList to a HouseholdConstraintsProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdConstraintsProfile to add hobbies to
      * @param hHEconomicConstraintsList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHEconomicConstraintsToHouseholdConstraintsProfile(Long id, List<HHEconomicConstraintsDTO> hHEconomicConstraintsList){
         HouseholdConstraintsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdConstraintsProfile not found"));
         if(hHEconomicConstraintsList != null && !hHEconomicConstraintsList.isEmpty()) {

             Set<HHEconomicConstraints> toBeSavedList = hHEconomicConstraintsList.stream().map(it-> assemblerHHEconomicConstraints.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdConstraintsProfile(saved));
             repositoryHHEconomicConstraints.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdConstraintsProfileDTO postHookSave(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected HouseholdConstraintsProfileDTO preHookSave(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected HouseholdConstraintsProfileDTO postHookUpdate(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected HouseholdConstraintsProfileDTO preHookUpdate(HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO) {
        return HouseholdConstraintsProfileDTO;
    }

    protected HouseholdConstraintsProfileDTO postHookDelete(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdConstraintsProfileDTO postHookGetById(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdConstraintsProfileDTO> postHookGetAll(PageDTO<HouseholdConstraintsProfileDTO> result) {
        return result;
    }




}
