package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdLivelihoodProfileService;


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

public class HouseholdLivelihoodProfileServiceImpl implements HouseholdLivelihoodProfileService {

    private final HouseholdLivelihoodProfileDTOAssembler assembler;
    private final HouseholdLivelihoodProfileRepository repository;


    private final HHMigrationMonthDTOAssembler assemblerHHMigrationMonth;
    private final HHMigrationMonthRepository repositoryHHMigrationMonth;


    public HouseholdLivelihoodProfileServiceImpl(
        HHMigrationMonthDTOAssembler assemblerHHMigrationMonth,  HHMigrationMonthRepository repositoryHHMigrationMonth,
        HouseholdLivelihoodProfileRepository repository, HouseholdLivelihoodProfileDTOAssembler assembler) {
        this.assemblerHHMigrationMonth = assemblerHHMigrationMonth;
        this.repositoryHHMigrationMonth = repositoryHHMigrationMonth;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HouseholdLivelihoodProfile.
     *
     * @param HouseholdLivelihoodProfile the HouseholdLivelihoodProfile DTO to save
     * @return the saved HouseholdLivelihoodProfile DTO
     */
    @Transactional
    @Override
    public HouseholdLivelihoodProfileDTO save(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfile) {
        log.debug("Entry - save(HouseholdLivelihoodProfile={})", HouseholdLivelihoodProfile);
        HouseholdLivelihoodProfile = preHookSave(HouseholdLivelihoodProfile);
        HouseholdLivelihoodProfile entity = assembler.fromDTO(HouseholdLivelihoodProfile);
        HouseholdLivelihoodProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HouseholdLivelihoodProfile.
     *
     * @param HouseholdLivelihoodProfile the HouseholdLivelihoodProfile DTO to update
     * @return the updated HouseholdLivelihoodProfile DTO
     * @throws NotFoundException if the HouseholdLivelihoodProfile is not found
     */
    @Transactional
    @Override
    public HouseholdLivelihoodProfileDTO update(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfile) {
        log.debug("Entry - update(HouseholdLivelihoodProfile={})", HouseholdLivelihoodProfile);
        HouseholdLivelihoodProfile = preHookUpdate(HouseholdLivelihoodProfile);
        HouseholdLivelihoodProfile saved = repository.findById(HouseholdLivelihoodProfile.getId()).orElseThrow(() -> new NotFoundException("HouseholdLivelihoodProfile not found"));
        saved.update(assembler.fromDTO(HouseholdLivelihoodProfile));
        saved = repository.save(saved);
        HouseholdLivelihoodProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HouseholdLivelihoodProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HouseholdLivelihoodProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdLivelihoodProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HouseholdLivelihoodProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdLivelihoodProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdLivelihoodProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HouseholdLivelihoodProfile by ID.
     *
     * @param id     the ID of the HouseholdLivelihoodProfile to delete
     * @param reason the reason for deletion
     * @return the deleted HouseholdLivelihoodProfile DTO
     * @throws NotFoundException if the HouseholdLivelihoodProfile is not found
     */
    @Transactional
    @Override
    public HouseholdLivelihoodProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HouseholdLivelihoodProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdLivelihoodProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdLivelihoodProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HouseholdLivelihoodProfile by ID.
     *
     * @param id the ID of the HouseholdLivelihoodProfile to retrieve
     * @return the HouseholdLivelihoodProfile DTO
     * @throws NotFoundException if the HouseholdLivelihoodProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdLivelihoodProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdLivelihoodProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdLivelihoodProfile not found"));
        HouseholdLivelihoodProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdLivelihoodProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HouseholdLivelihoodProfile> savedData = repository.findAllById(ids);
        Set<HouseholdLivelihoodProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHMigrationMonthList to a HouseholdLivelihoodProfile identified by their ID.
      *
      * @param id               The ID of the HouseholdLivelihoodProfile to add hobbies to
      * @param hHMigrationMonthList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHMigrationMonthToHouseholdLivelihoodProfile(Long id, List<HHMigrationMonthDTO> hHMigrationMonthList){
         HouseholdLivelihoodProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdLivelihoodProfile not found"));
         if(hHMigrationMonthList != null && !hHMigrationMonthList.isEmpty()) {

             Set<HHMigrationMonth> toBeSavedList = hHMigrationMonthList.stream().map(it-> assemblerHHMigrationMonth.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdLivelihoodProfile(saved));
             repositoryHHMigrationMonth.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdLivelihoodProfileDTO postHookSave(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected HouseholdLivelihoodProfileDTO preHookSave(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected HouseholdLivelihoodProfileDTO postHookUpdate(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected HouseholdLivelihoodProfileDTO preHookUpdate(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO) {
        return HouseholdLivelihoodProfileDTO;
    }

    protected HouseholdLivelihoodProfileDTO postHookDelete(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdLivelihoodProfileDTO postHookGetById(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdLivelihoodProfileDTO> postHookGetAll(PageDTO<HouseholdLivelihoodProfileDTO> result) {
        return result;
    }




}
