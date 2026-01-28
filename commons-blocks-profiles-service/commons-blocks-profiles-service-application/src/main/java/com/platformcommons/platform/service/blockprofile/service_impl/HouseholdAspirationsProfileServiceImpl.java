package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdAspirationsProfileService;


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

public class HouseholdAspirationsProfileServiceImpl implements HouseholdAspirationsProfileService {

    private final HouseholdAspirationsProfileDTOAssembler assembler;
    private final HouseholdAspirationsProfileRepository repository;


    private final HHSocialAspirationDTOAssembler assemblerHHSocialAspiration;
    private final HHSocialAspirationRepository repositoryHHSocialAspiration;


    private final HHEconomicAspirationDTOAssembler assemblerHHEconomicAspiration;
    private final HHEconomicAspirationRepository repositoryHHEconomicAspiration;


    private final HHGovernanceAspirationDTOAssembler assemblerHHGovernanceAspiration;
    private final HHGovernanceAspirationRepository repositoryHHGovernanceAspiration;


    public HouseholdAspirationsProfileServiceImpl(
        HHSocialAspirationDTOAssembler assemblerHHSocialAspiration,  HHSocialAspirationRepository repositoryHHSocialAspiration,
        HHEconomicAspirationDTOAssembler assemblerHHEconomicAspiration,  HHEconomicAspirationRepository repositoryHHEconomicAspiration,
        HHGovernanceAspirationDTOAssembler assemblerHHGovernanceAspiration,  HHGovernanceAspirationRepository repositoryHHGovernanceAspiration,
        HouseholdAspirationsProfileRepository repository, HouseholdAspirationsProfileDTOAssembler assembler) {
        this.assemblerHHSocialAspiration = assemblerHHSocialAspiration;
        this.repositoryHHSocialAspiration = repositoryHHSocialAspiration;
        this.assemblerHHEconomicAspiration = assemblerHHEconomicAspiration;
        this.repositoryHHEconomicAspiration = repositoryHHEconomicAspiration;
        this.assemblerHHGovernanceAspiration = assemblerHHGovernanceAspiration;
        this.repositoryHHGovernanceAspiration = repositoryHHGovernanceAspiration;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HouseholdAspirationsProfile.
     *
     * @param HouseholdAspirationsProfile the HouseholdAspirationsProfile DTO to save
     * @return the saved HouseholdAspirationsProfile DTO
     */
    @Transactional
    @Override
    public HouseholdAspirationsProfileDTO save(HouseholdAspirationsProfileDTO HouseholdAspirationsProfile) {
        log.debug("Entry - save(HouseholdAspirationsProfile={})", HouseholdAspirationsProfile);
        HouseholdAspirationsProfile = preHookSave(HouseholdAspirationsProfile);
        HouseholdAspirationsProfile entity = assembler.fromDTO(HouseholdAspirationsProfile);
        HouseholdAspirationsProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HouseholdAspirationsProfile.
     *
     * @param HouseholdAspirationsProfile the HouseholdAspirationsProfile DTO to update
     * @return the updated HouseholdAspirationsProfile DTO
     * @throws NotFoundException if the HouseholdAspirationsProfile is not found
     */
    @Transactional
    @Override
    public HouseholdAspirationsProfileDTO update(HouseholdAspirationsProfileDTO HouseholdAspirationsProfile) {
        log.debug("Entry - update(HouseholdAspirationsProfile={})", HouseholdAspirationsProfile);
        HouseholdAspirationsProfile = preHookUpdate(HouseholdAspirationsProfile);
        HouseholdAspirationsProfile saved = repository.findById(HouseholdAspirationsProfile.getId()).orElseThrow(() -> new NotFoundException("HouseholdAspirationsProfile not found"));
        saved.update(assembler.fromDTO(HouseholdAspirationsProfile));
        saved = repository.save(saved);
        HouseholdAspirationsProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HouseholdAspirationsProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HouseholdAspirationsProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdAspirationsProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HouseholdAspirationsProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdAspirationsProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdAspirationsProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HouseholdAspirationsProfile by ID.
     *
     * @param id     the ID of the HouseholdAspirationsProfile to delete
     * @param reason the reason for deletion
     * @return the deleted HouseholdAspirationsProfile DTO
     * @throws NotFoundException if the HouseholdAspirationsProfile is not found
     */
    @Transactional
    @Override
    public HouseholdAspirationsProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HouseholdAspirationsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdAspirationsProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdAspirationsProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HouseholdAspirationsProfile by ID.
     *
     * @param id the ID of the HouseholdAspirationsProfile to retrieve
     * @return the HouseholdAspirationsProfile DTO
     * @throws NotFoundException if the HouseholdAspirationsProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdAspirationsProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdAspirationsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdAspirationsProfile not found"));
        HouseholdAspirationsProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdAspirationsProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HouseholdAspirationsProfile> savedData = repository.findAllById(ids);
        Set<HouseholdAspirationsProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHSocialAspirationList to a HouseholdAspirationsProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
      * @param hHSocialAspirationList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHSocialAspirationToHouseholdAspirationsProfile(Long id, List<HHSocialAspirationDTO> hHSocialAspirationList){
         HouseholdAspirationsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdAspirationsProfile not found"));
         if(hHSocialAspirationList != null && !hHSocialAspirationList.isEmpty()) {

             Set<HHSocialAspiration> toBeSavedList = hHSocialAspirationList.stream().map(it-> assemblerHHSocialAspiration.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdAspirationsProfile(saved));
             repositoryHHSocialAspiration.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of hHEconomicAspirationList to a HouseholdAspirationsProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
      * @param hHEconomicAspirationList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHEconomicAspirationToHouseholdAspirationsProfile(Long id, List<HHEconomicAspirationDTO> hHEconomicAspirationList){
         HouseholdAspirationsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdAspirationsProfile not found"));
         if(hHEconomicAspirationList != null && !hHEconomicAspirationList.isEmpty()) {

             Set<HHEconomicAspiration> toBeSavedList = hHEconomicAspirationList.stream().map(it-> assemblerHHEconomicAspiration.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdAspirationsProfile(saved));
             repositoryHHEconomicAspiration.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of hHGovernanceAspirationList to a HouseholdAspirationsProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
      * @param hHGovernanceAspirationList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHGovernanceAspirationToHouseholdAspirationsProfile(Long id, List<HHGovernanceAspirationDTO> hHGovernanceAspirationList){
         HouseholdAspirationsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdAspirationsProfile not found"));
         if(hHGovernanceAspirationList != null && !hHGovernanceAspirationList.isEmpty()) {

             Set<HHGovernanceAspiration> toBeSavedList = hHGovernanceAspirationList.stream().map(it-> assemblerHHGovernanceAspiration.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdAspirationsProfile(saved));
             repositoryHHGovernanceAspiration.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdAspirationsProfileDTO postHookSave(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected HouseholdAspirationsProfileDTO preHookSave(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected HouseholdAspirationsProfileDTO postHookUpdate(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected HouseholdAspirationsProfileDTO preHookUpdate(HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO) {
        return HouseholdAspirationsProfileDTO;
    }

    protected HouseholdAspirationsProfileDTO postHookDelete(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdAspirationsProfileDTO postHookGetById(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdAspirationsProfileDTO> postHookGetAll(PageDTO<HouseholdAspirationsProfileDTO> result) {
        return result;
    }




}
