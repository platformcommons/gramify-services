package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdService;


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

public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdDTOAssembler assembler;
    private final HouseholdRepository repository;


    private final HHLoanSourceDTOAssembler assemblerHHLoanSource;
    private final HHLoanSourceRepository repositoryHHLoanSource;


    public HouseholdServiceImpl(
        HHLoanSourceDTOAssembler assemblerHHLoanSource,  HHLoanSourceRepository repositoryHHLoanSource,
        HouseholdRepository repository, HouseholdDTOAssembler assembler) {
        this.assemblerHHLoanSource = assemblerHHLoanSource;
        this.repositoryHHLoanSource = repositoryHHLoanSource;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new Household.
     *
     * @param Household the Household DTO to save
     * @return the saved Household DTO
     */
    @Transactional
    @Override
    public HouseholdDTO save(HouseholdDTO Household) {
        log.debug("Entry - save(Household={})", Household);
        Household = preHookSave(Household);
        Household entity = assembler.fromDTO(Household);
        HouseholdDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing Household.
     *
     * @param Household the Household DTO to update
     * @return the updated Household DTO
     * @throws NotFoundException if the Household is not found
     */
    @Transactional
    @Override
    public HouseholdDTO update(HouseholdDTO Household) {
        log.debug("Entry - update(Household={})", Household);
        Household = preHookUpdate(Household);
        Household saved = repository.findById(Household.getId()).orElseThrow(() -> new NotFoundException("Household not found"));
        saved.update(assembler.fromDTO(Household));
        saved = repository.save(saved);
        HouseholdDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of Households.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing Household DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<Household> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a Household by ID.
     *
     * @param id     the ID of the Household to delete
     * @param reason the reason for deletion
     * @return the deleted Household DTO
     * @throws NotFoundException if the Household is not found
     */
    @Transactional
    @Override
    public HouseholdDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        Household saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Household not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a Household by ID.
     *
     * @param id the ID of the Household to retrieve
     * @return the Household DTO
     * @throws NotFoundException if the Household is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        Household saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Household not found"));
        HouseholdDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<Household> savedData = repository.findAllById(ids);
        Set<HouseholdDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHLoanSourceList to a Household identified by their ID.
      *
      * @param id               The ID of the Household to add hobbies to
      * @param hHLoanSourceList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHLoanSourceToHousehold(Long id, List<HHLoanSourceDTO> hHLoanSourceList){
         Household saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Household not found"));
         if(hHLoanSourceList != null && !hHLoanSourceList.isEmpty()) {

             Set<HHLoanSource> toBeSavedList = hHLoanSourceList.stream().map(it-> assemblerHHLoanSource.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHousehold(saved));
             repositoryHHLoanSource.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdDTO postHookSave(HouseholdDTO dto) {
        return dto;
    }

    protected HouseholdDTO preHookSave(HouseholdDTO dto) {
        return dto;
    }

    protected HouseholdDTO postHookUpdate(HouseholdDTO dto) {
        return dto;
    }

    protected HouseholdDTO preHookUpdate(HouseholdDTO HouseholdDTO) {
        return HouseholdDTO;
    }

    protected HouseholdDTO postHookDelete(HouseholdDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdDTO postHookGetById(HouseholdDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdDTO> postHookGetAll(PageDTO<HouseholdDTO> result) {
        return result;
    }




}
