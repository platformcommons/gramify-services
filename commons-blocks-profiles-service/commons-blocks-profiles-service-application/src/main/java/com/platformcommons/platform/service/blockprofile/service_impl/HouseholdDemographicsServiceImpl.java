package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HouseholdDemographicsService;


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

public class HouseholdDemographicsServiceImpl implements HouseholdDemographicsService {

    private final HouseholdDemographicsDTOAssembler assembler;
    private final HouseholdDemographicsRepository repository;


    public HouseholdDemographicsServiceImpl(
        HouseholdDemographicsRepository repository, HouseholdDemographicsDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HouseholdDemographics.
     *
     * @param HouseholdDemographics the HouseholdDemographics DTO to save
     * @return the saved HouseholdDemographics DTO
     */
    @Transactional
    @Override
    public HouseholdDemographicsDTO save(HouseholdDemographicsDTO HouseholdDemographics) {
        log.debug("Entry - save(HouseholdDemographics={})", HouseholdDemographics);
        HouseholdDemographics = preHookSave(HouseholdDemographics);
        HouseholdDemographics entity = assembler.fromDTO(HouseholdDemographics);
        HouseholdDemographicsDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HouseholdDemographics.
     *
     * @param HouseholdDemographics the HouseholdDemographics DTO to update
     * @return the updated HouseholdDemographics DTO
     * @throws NotFoundException if the HouseholdDemographics is not found
     */
    @Transactional
    @Override
    public HouseholdDemographicsDTO update(HouseholdDemographicsDTO HouseholdDemographics) {
        log.debug("Entry - update(HouseholdDemographics={})", HouseholdDemographics);
        HouseholdDemographics = preHookUpdate(HouseholdDemographics);
        HouseholdDemographics saved = repository.findById(HouseholdDemographics.getId()).orElseThrow(() -> new NotFoundException("HouseholdDemographics not found"));
        saved.update(assembler.fromDTO(HouseholdDemographics));
        saved = repository.save(saved);
        HouseholdDemographicsDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HouseholdDemographicss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HouseholdDemographics DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HouseholdDemographicsDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HouseholdDemographics> result = repository.findAll(PageRequest.of(page, size));
        Set<HouseholdDemographicsDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HouseholdDemographicsDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HouseholdDemographics by ID.
     *
     * @param id     the ID of the HouseholdDemographics to delete
     * @param reason the reason for deletion
     * @return the deleted HouseholdDemographics DTO
     * @throws NotFoundException if the HouseholdDemographics is not found
     */
    @Transactional
    @Override
    public HouseholdDemographicsDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HouseholdDemographics saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdDemographics not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HouseholdDemographicsDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HouseholdDemographics by ID.
     *
     * @param id the ID of the HouseholdDemographics to retrieve
     * @return the HouseholdDemographics DTO
     * @throws NotFoundException if the HouseholdDemographics is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HouseholdDemographicsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdDemographics saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HouseholdDemographics not found"));
        HouseholdDemographicsDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HouseholdDemographicsDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HouseholdDemographics> savedData = repository.findAllById(ids);
        Set<HouseholdDemographicsDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HouseholdDemographicsDTO postHookSave(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected HouseholdDemographicsDTO preHookSave(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected HouseholdDemographicsDTO postHookUpdate(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected HouseholdDemographicsDTO preHookUpdate(HouseholdDemographicsDTO HouseholdDemographicsDTO) {
        return HouseholdDemographicsDTO;
    }

    protected HouseholdDemographicsDTO postHookDelete(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdDemographicsDTO postHookGetById(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdDemographicsDTO> postHookGetAll(PageDTO<HouseholdDemographicsDTO> result) {
        return result;
    }




}
