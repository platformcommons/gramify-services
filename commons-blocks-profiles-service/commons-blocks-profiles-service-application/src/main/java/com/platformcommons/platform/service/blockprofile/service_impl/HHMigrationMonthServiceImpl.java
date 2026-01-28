package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHMigrationMonthService;


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

public class HHMigrationMonthServiceImpl implements HHMigrationMonthService {

    private final HHMigrationMonthDTOAssembler assembler;
    private final HHMigrationMonthRepository repository;


    public HHMigrationMonthServiceImpl(
        HHMigrationMonthRepository repository, HHMigrationMonthDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHMigrationMonth.
     *
     * @param HHMigrationMonth the HHMigrationMonth DTO to save
     * @return the saved HHMigrationMonth DTO
     */
    @Transactional
    @Override
    public HHMigrationMonthDTO save(HHMigrationMonthDTO HHMigrationMonth) {
        log.debug("Entry - save(HHMigrationMonth={})", HHMigrationMonth);
        HHMigrationMonth = preHookSave(HHMigrationMonth);
        HHMigrationMonth entity = assembler.fromDTO(HHMigrationMonth);
        HHMigrationMonthDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHMigrationMonth.
     *
     * @param HHMigrationMonth the HHMigrationMonth DTO to update
     * @return the updated HHMigrationMonth DTO
     * @throws NotFoundException if the HHMigrationMonth is not found
     */
    @Transactional
    @Override
    public HHMigrationMonthDTO update(HHMigrationMonthDTO HHMigrationMonth) {
        log.debug("Entry - update(HHMigrationMonth={})", HHMigrationMonth);
        HHMigrationMonth = preHookUpdate(HHMigrationMonth);
        HHMigrationMonth saved = repository.findById(HHMigrationMonth.getId()).orElseThrow(() -> new NotFoundException("HHMigrationMonth not found"));
        saved.update(assembler.fromDTO(HHMigrationMonth));
        saved = repository.save(saved);
        HHMigrationMonthDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHMigrationMonths.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHMigrationMonth DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHMigrationMonthDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHMigrationMonth> result = repository.findAll(PageRequest.of(page, size));
        Set<HHMigrationMonthDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHMigrationMonthDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHMigrationMonth by ID.
     *
     * @param id     the ID of the HHMigrationMonth to delete
     * @param reason the reason for deletion
     * @return the deleted HHMigrationMonth DTO
     * @throws NotFoundException if the HHMigrationMonth is not found
     */
    @Transactional
    @Override
    public HHMigrationMonthDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHMigrationMonth saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHMigrationMonth not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHMigrationMonthDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHMigrationMonth by ID.
     *
     * @param id the ID of the HHMigrationMonth to retrieve
     * @return the HHMigrationMonth DTO
     * @throws NotFoundException if the HHMigrationMonth is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHMigrationMonthDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHMigrationMonth saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHMigrationMonth not found"));
        HHMigrationMonthDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHMigrationMonthDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHMigrationMonth> savedData = repository.findAllById(ids);
        Set<HHMigrationMonthDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHMigrationMonthDTO postHookSave(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected HHMigrationMonthDTO preHookSave(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected HHMigrationMonthDTO postHookUpdate(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected HHMigrationMonthDTO preHookUpdate(HHMigrationMonthDTO HHMigrationMonthDTO) {
        return HHMigrationMonthDTO;
    }

    protected HHMigrationMonthDTO postHookDelete(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHMigrationMonthDTO postHookGetById(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected PageDTO<HHMigrationMonthDTO> postHookGetAll(PageDTO<HHMigrationMonthDTO> result) {
        return result;
    }




}
