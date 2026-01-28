package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHEconomicConstraintsService;


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

public class HHEconomicConstraintsServiceImpl implements HHEconomicConstraintsService {

    private final HHEconomicConstraintsDTOAssembler assembler;
    private final HHEconomicConstraintsRepository repository;


    public HHEconomicConstraintsServiceImpl(
        HHEconomicConstraintsRepository repository, HHEconomicConstraintsDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHEconomicConstraints.
     *
     * @param HHEconomicConstraints the HHEconomicConstraints DTO to save
     * @return the saved HHEconomicConstraints DTO
     */
    @Transactional
    @Override
    public HHEconomicConstraintsDTO save(HHEconomicConstraintsDTO HHEconomicConstraints) {
        log.debug("Entry - save(HHEconomicConstraints={})", HHEconomicConstraints);
        HHEconomicConstraints = preHookSave(HHEconomicConstraints);
        HHEconomicConstraints entity = assembler.fromDTO(HHEconomicConstraints);
        HHEconomicConstraintsDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHEconomicConstraints.
     *
     * @param HHEconomicConstraints the HHEconomicConstraints DTO to update
     * @return the updated HHEconomicConstraints DTO
     * @throws NotFoundException if the HHEconomicConstraints is not found
     */
    @Transactional
    @Override
    public HHEconomicConstraintsDTO update(HHEconomicConstraintsDTO HHEconomicConstraints) {
        log.debug("Entry - update(HHEconomicConstraints={})", HHEconomicConstraints);
        HHEconomicConstraints = preHookUpdate(HHEconomicConstraints);
        HHEconomicConstraints saved = repository.findById(HHEconomicConstraints.getId()).orElseThrow(() -> new NotFoundException("HHEconomicConstraints not found"));
        saved.update(assembler.fromDTO(HHEconomicConstraints));
        saved = repository.save(saved);
        HHEconomicConstraintsDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHEconomicConstraintss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHEconomicConstraints DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHEconomicConstraintsDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHEconomicConstraints> result = repository.findAll(PageRequest.of(page, size));
        Set<HHEconomicConstraintsDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHEconomicConstraintsDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHEconomicConstraints by ID.
     *
     * @param id     the ID of the HHEconomicConstraints to delete
     * @param reason the reason for deletion
     * @return the deleted HHEconomicConstraints DTO
     * @throws NotFoundException if the HHEconomicConstraints is not found
     */
    @Transactional
    @Override
    public HHEconomicConstraintsDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHEconomicConstraints saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEconomicConstraints not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHEconomicConstraintsDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHEconomicConstraints by ID.
     *
     * @param id the ID of the HHEconomicConstraints to retrieve
     * @return the HHEconomicConstraints DTO
     * @throws NotFoundException if the HHEconomicConstraints is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHEconomicConstraintsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEconomicConstraints saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHEconomicConstraints not found"));
        HHEconomicConstraintsDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHEconomicConstraintsDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHEconomicConstraints> savedData = repository.findAllById(ids);
        Set<HHEconomicConstraintsDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEconomicConstraintsDTO postHookSave(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected HHEconomicConstraintsDTO preHookSave(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected HHEconomicConstraintsDTO postHookUpdate(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected HHEconomicConstraintsDTO preHookUpdate(HHEconomicConstraintsDTO HHEconomicConstraintsDTO) {
        return HHEconomicConstraintsDTO;
    }

    protected HHEconomicConstraintsDTO postHookDelete(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEconomicConstraintsDTO postHookGetById(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected PageDTO<HHEconomicConstraintsDTO> postHookGetAll(PageDTO<HHEconomicConstraintsDTO> result) {
        return result;
    }




}
