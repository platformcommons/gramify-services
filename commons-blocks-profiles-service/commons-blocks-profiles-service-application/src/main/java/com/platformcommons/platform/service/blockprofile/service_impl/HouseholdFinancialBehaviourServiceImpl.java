package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdFinancialBehaviourService;


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

public class HouseholdFinancialBehaviourServiceImpl implements HouseholdFinancialBehaviourService {

    private final HouseholdFinancialBehaviourDTOAssembler assembler;
    private final HouseholdFinancialBehaviourRepository repository;


    private final HHSavingsModeDTOAssembler assemblerHHSavingsMode;
    private final HHSavingsModeRepository repositoryHHSavingsMode;


    public HouseholdFinancialBehaviourServiceImpl(
        HHSavingsModeDTOAssembler assemblerHHSavingsMode,  HHSavingsModeRepository repositoryHHSavingsMode,
        HouseholdFinancialBehaviourRepository repository, HouseholdFinancialBehaviourDTOAssembler assembler) {
        this.assemblerHHSavingsMode = assemblerHHSavingsMode;
        this.repositoryHHSavingsMode = repositoryHHSavingsMode;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HouseholdFinancialBehaviour.
     *
     * @param HouseholdFinancialBehaviour the HouseholdFinancialBehaviour DTO to save
     * @return the saved HouseholdFinancialBehaviour DTO
     */
    @Transactional
    @Override
    public HouseholdFinancialBehaviourDTO save(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviour) {
        log.debug("Entry - save(HouseholdFinancialBehaviour={})", HouseholdFinancialBehaviour);
        HouseholdFinancialBehaviour = preHookSave(HouseholdFinancialBehaviour);
        HouseholdFinancialBehaviour entity = assembler.fromDTO(HouseholdFinancialBehaviour);
        HouseholdFinancialBehaviourDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HouseholdFinancialBehaviour.
     *
     * @param HouseholdFinancialBehaviour the HouseholdFinancialBehaviour DTO to update
     * @return the updated HouseholdFinancialBehaviour DTO
     * @throws NotFoundException if the HouseholdFinancialBehaviour is not found
     */
    @Transactional
    @Override
    public HouseholdFinancialBehaviourDTO update(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviour) {
        log.debug("Entry - update(HouseholdFinancialBehaviour={})", HouseholdFinancialBehaviour);
        HouseholdFinancialBehaviour = preHookUpdate(HouseholdFinancialBehaviour);
        HouseholdFinancialBehaviour saved = repository.findById(HouseholdFinancialBehaviour.getId()).orElseThrow(() -> new NotFoundException("HouseholdFinancialBehaviour not found"));
        saved.update(assembler.fromDTO(HouseholdFinancialBehaviour));
        saved = repository.save(saved);
        HouseholdFinancialBehaviourDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HouseholdFinancialBehaviours.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HouseholdFinancialBehaviour DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdFinancialBehaviourDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HouseholdFinancialBehaviour> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdFinancialBehaviourDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdFinancialBehaviourDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HouseholdFinancialBehaviour by ID.
     *
     * @param id     the ID of the HouseholdFinancialBehaviour to delete
     * @param reason the reason for deletion
     * @return the deleted HouseholdFinancialBehaviour DTO
     * @throws NotFoundException if the HouseholdFinancialBehaviour is not found
     */
    @Transactional
    @Override
    public HouseholdFinancialBehaviourDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HouseholdFinancialBehaviour saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdFinancialBehaviour not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdFinancialBehaviourDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HouseholdFinancialBehaviour by ID.
     *
     * @param id the ID of the HouseholdFinancialBehaviour to retrieve
     * @return the HouseholdFinancialBehaviour DTO
     * @throws NotFoundException if the HouseholdFinancialBehaviour is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdFinancialBehaviourDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdFinancialBehaviour saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdFinancialBehaviour not found"));
        HouseholdFinancialBehaviourDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdFinancialBehaviourDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HouseholdFinancialBehaviour> savedData = repository.findAllById(ids);
        Set<HouseholdFinancialBehaviourDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHSavingsModeList to a HouseholdFinancialBehaviour identified by their ID.
      *
      * @param id               The ID of the HouseholdFinancialBehaviour to add hobbies to
      * @param hHSavingsModeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHSavingsModeToHouseholdFinancialBehaviour(Long id, List<HHSavingsModeDTO> hHSavingsModeList){
         HouseholdFinancialBehaviour saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdFinancialBehaviour not found"));
         if(hHSavingsModeList != null && !hHSavingsModeList.isEmpty()) {

             Set<HHSavingsMode> toBeSavedList = hHSavingsModeList.stream().map(it-> assemblerHHSavingsMode.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHouseholdFinancialBehaviour(saved));
             repositoryHHSavingsMode.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdFinancialBehaviourDTO postHookSave(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected HouseholdFinancialBehaviourDTO preHookSave(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected HouseholdFinancialBehaviourDTO postHookUpdate(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected HouseholdFinancialBehaviourDTO preHookUpdate(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO) {
        return HouseholdFinancialBehaviourDTO;
    }

    protected HouseholdFinancialBehaviourDTO postHookDelete(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdFinancialBehaviourDTO postHookGetById(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdFinancialBehaviourDTO> postHookGetAll(PageDTO<HouseholdFinancialBehaviourDTO> result) {
        return result;
    }




}
